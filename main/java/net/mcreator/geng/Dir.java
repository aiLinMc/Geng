package net.mcreator.geng;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.Commands;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class Dir {
    // 用于存储正在扫描的玩家和对应的扫描任务
    private static final Map<UUID, DirScanner> activeScanners = new ConcurrentHashMap<>();
    
    public Dir() {
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        new Dir();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
    }

    @EventBusSubscriber
    private static class DirForgeBusEvents {
        @SubscribeEvent
        public static void serverLoad(ServerStartingEvent event) {
        }
        
        // 注册命令
        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent event) {
            event.getDispatcher().register(Commands.literal("dir")
                .then(Commands.literal("start")
                    .executes(context -> {
                        ServerPlayer player = context.getSource().getPlayer();
                        if (player != null) {
                            startDirectoryScan(player);
                            player.sendSystemMessage(Component.literal("§a开始递归扫描所有目录... "));
                        }
                        return 1;
                    })
                )
                .then(Commands.literal("stop")
                    .executes(context -> {
                        ServerPlayer player = context.getSource().getPlayer();
                        if (player != null) {
                            stopDirectoryScan(player);
                            player.sendSystemMessage(Component.literal("§c停止目录扫描"));
                        }
                        return 1;
                    })
                )
            );
        }
        
        // 服务器tick事件，用于处理扫描任务
        @SubscribeEvent
        public static void onServerTick(ServerTickEvent.Post event) {
            // 遍历所有活跃的扫描器并处理
            Iterator<Map.Entry<UUID, DirScanner>> iterator = activeScanners.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<UUID, DirScanner> entry = iterator.next();
                DirScanner scanner = entry.getValue();
                
                if (!scanner.isActive()) {
                    iterator.remove();
                    continue;
                }
                
                // 处理一批文件
                scanner.processBatch();
            }
        }
    }
    
    // 开始目录扫描
    private static void startDirectoryScan(ServerPlayer player) {
        UUID playerId = player.getUUID();
        
        // 如果已有扫描任务，先停止
        stopDirectoryScan(player);
        
        // 创建新的扫描任务
        DirScanner scanner = new DirScanner(player);
        activeScanners.put(playerId, scanner);
    }
    
    // 停止目录扫描
    private static void stopDirectoryScan(ServerPlayer player) {
        UUID playerId = player.getUUID();
        DirScanner scanner = activeScanners.remove(playerId);
        if (scanner != null) {
            scanner.stop();
        }
    }
    
    // 目录扫描器类 - 类似 dir /b /s 的效果
    private static class DirScanner {
        private final ServerPlayer player;
        private final Queue<File> directoriesToScan;
        private final Queue<String> pathsToDisplay;
        private volatile boolean active;
        private File currentDirectory;
        private long startTime;
        private int totalFilesFound;
        
        public DirScanner(ServerPlayer player) {
            this.player = player;
            this.directoriesToScan = new LinkedList<>();
            this.pathsToDisplay = new LinkedList<>();
            this.active = true;
            this.startTime = System.currentTimeMillis();
            this.totalFilesFound = 0;
            
            // 获取所有可用的根目录（Windows下的盘符，Linux和Mac的根目录）
            File[] roots = File.listRoots();
            if (roots != null && roots.length > 0) {
                for (File root : roots) {
                    if (root.exists() && root.isDirectory()) {
                        directoriesToScan.offer(root);
                        player.sendSystemMessage(Component.literal("§6添加根目录: " + root.getAbsolutePath()));
                    }
                }
                player.sendSystemMessage(Component.literal("§6开始递归扫描所有目录，共 " + roots.length + " 个根目录"));
                player.sendSystemMessage(Component.literal("§7模式: 类似 dir /b /s - 显示完整路径"));
                player.sendSystemMessage(Component.literal("§c警告：这将扫描整个文件系统，可能会产生大量输出！"));
            } else {
                // 如果无法获取根目录，使用当前工作目录
                File currentDir = new File(".").getAbsoluteFile();
                directoriesToScan.offer(currentDir);
                player.sendSystemMessage(Component.literal("§6开始递归扫描目录: " + currentDir.getAbsolutePath()));
                player.sendSystemMessage(Component.literal("§7模式: 类似 dir /b /s - 显示完整路径"));
            }
        }
        
        public boolean isActive() {
            return active;
        }
        
        public void stop() {
            active = false;
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime) / 1000;
            player.sendSystemMessage(Component.literal("§6扫描结束，找到 " + totalFilesFound + " 个文件/目录，耗时: " + duration + " 秒"));
        }
        
        public void processBatch() {
            if (!active) return;
            
            // 每次tick显示的文件数量限制，避免刷屏
            int filesDisplayed = 0;
            int maxFilesPerTick = 10; // 降低显示频率，避免刷屏
            
            // 先显示已经准备好的路径
            while (filesDisplayed < maxFilesPerTick && !pathsToDisplay.isEmpty()) {
                String path = pathsToDisplay.poll();
                if (path != null) {
                    player.sendSystemMessage(Component.literal(path));
                    filesDisplayed++;
                    totalFilesFound++;
                    
                    // 每扫描1000个文件显示一次进度
                    if (totalFilesFound % 1000 == 0) {
                        player.sendSystemMessage(Component.literal("§e已扫描 " + totalFilesFound + " 个文件/目录..."));
                    }
                }
            }
            
            // 如果还有显示任务，先不扫描新目录
            if (!pathsToDisplay.isEmpty()) {
                return;
            }
            
            // 扫描新目录
            int maxScanPerTick = 50; // 降低扫描频率，减少服务器负载
            int scanned = 0;
            
            while (scanned < maxScanPerTick && !directoriesToScan.isEmpty()) {
                File dir = directoriesToScan.poll();
                if (dir != null && dir.exists() && dir.isDirectory()) {
                    currentDirectory = dir;
                    
                    try {
                        File[] files = dir.listFiles();
                        if (files != null) {
                            // 显示当前扫描的目录（可选，避免过多输出）
                            if (totalFilesFound % 500 == 0) {
                                pathsToDisplay.offer("§d[扫描中] " + dir.getAbsolutePath());
                            }
                            
                            // 处理目录中的所有文件和子目录
                            for (File file : files) {
                                if (!active) return;
                                
                                try {
                                    if (file.isDirectory()) {
                                        // 目录添加到扫描队列
                                        directoriesToScan.offer(file);
                                        // 显示目录路径
                                        pathsToDisplay.offer("§9[DIR] " + file.getAbsolutePath());
                                    } else {
                                        // 文件路径添加到显示队列
                                        pathsToDisplay.offer("§f" + file.getAbsolutePath());
                                    }
                                    
                                    scanned++;
                                    if (scanned >= maxScanPerTick) {
                                        break;
                                    }
                                } catch (SecurityException e) {
                                    // 忽略权限错误，继续扫描其他文件
                                    pathsToDisplay.offer("§c权限不足: " + file.getAbsolutePath());
                                } catch (Exception e) {
                                    // 忽略其他错误，继续扫描
                                    pathsToDisplay.offer("§c错误: " + file.getAbsolutePath());
                                }
                            }
                        }
                    } catch (SecurityException e) {
                        pathsToDisplay.offer("§c无法访问目录: " + dir.getAbsolutePath());
                    } catch (Exception e) {
                        pathsToDisplay.offer("§c错误扫描目录: " + dir.getAbsolutePath() + " - " + e.getMessage());
                    }
                }
            }
            
            // 如果没有更多目录要扫描，结束任务
            if (directoriesToScan.isEmpty() && pathsToDisplay.isEmpty()) {
                stop();
                player.sendSystemMessage(Component.literal("§a递归扫描完成！总共找到 " + totalFilesFound + " 个文件/目录"));
            }
        }
    }
}