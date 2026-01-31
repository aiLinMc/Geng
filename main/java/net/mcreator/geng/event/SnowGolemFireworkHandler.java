package net.mcreator.geng.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.geng.init.GengModMobEffects;
import net.mcreator.geng.GengMod;

import java.util.Random;

@EventBusSubscriber(modid = GengMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SnowGolemFireworkHandler {
    
    // 基础烟花颜色
    private static final int[] BASE_FIREWORK_COLORS = {
        0xFF5555, // 亮红色
        0xFFAA00, // 橙色
        0xFFFF55, // 黄色
        0x55FF55, // 亮绿色
        0x55FFFF, // 青色
        0x5555FF, // 蓝色
        0xFF55FF, // 品红色
        0xFFAAAA, // 粉红色
        0xAAAAAA, // 浅灰色
        0xAA00AA, // 紫色
        0x00AAAA, // 深青色
        0xAA5500  // 棕色
    };
    
    // 烟花形状类型
    private static final String[] FIREWORK_SHAPES = {
        "small_ball",    // 小型球状
        "large_ball",    // 大型球状
        "star",          // 星形
        "burst"          // 爆裂形
    };
    
    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        // 只在服务端执行
        if (event.getProjectile().level().isClientSide()) {
            return;
        }
        
        // 只处理雪球
        if (!(event.getProjectile() instanceof Snowball)) {
            return;
        }
        
        Snowball snowball = (Snowball) event.getProjectile();
        
        // 检查发射者是否为雪傀儡
        if (!(snowball.getOwner() instanceof SnowGolem)) {
            return;
        }
        
        SnowGolem snowGolem = (SnowGolem) snowball.getOwner();
        
        // 获取效果Holder
        ResourceLocation effectLocation = net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.getKey(GengModMobEffects.WISHING_YOU_PROSPERITY.get());
        if (effectLocation == null) {
            return;
        }
        
        // 检查雪傀儡是否有"恭喜发财"效果
        Holder<net.minecraft.world.effect.MobEffect> effectHolder;
        try {
            effectHolder = snowGolem.level().registryAccess()
                .lookupOrThrow(Registries.MOB_EFFECT)
                .getOrThrow(ResourceKey.create(Registries.MOB_EFFECT, effectLocation));
        } catch (Exception e) {
            GengMod.LOGGER.error("Failed to get effect holder for WISHING_YOU_PROSPERITY", e);
            return;
        }
        
        MobEffectInstance effectInstance = snowGolem.getEffect(effectHolder);
        if (effectInstance == null || effectInstance.getDuration() <= 0) {
            return;
        }
        
        // 取消原始事件（防止雪球造成伤害和击退）
        event.setCanceled(true);
        
        // 获取撞击位置
        double x = snowball.getX();
        double y = snowball.getY();
        double z = snowball.getZ();
        Level level = snowball.level();
        
        // 确保是服务器端
        if (!(level instanceof ServerLevel)) {
            return;
        }
        
        ServerLevel serverLevel = (ServerLevel) level;
        Random random = new Random();
        
        // 1. 生成简化版瞬爆烟花实体
        String summonCommand = generateSimplifiedFireworkCommand(x, y, z, random);
        
        // 执行summon命令
        serverLevel.getServer().getCommands().performPrefixedCommand(
            serverLevel.getServer().createCommandSourceStack()
                .withSuppressedOutput()  // 抑制命令输出到聊天栏
                .withLevel(serverLevel),
            summonCommand
        );
        
        // 输出日志
        GengMod.LOGGER.info("雪傀儡烟花爆炸 - 执行命令: {}", summonCommand);
        
        // 2. 生成1-3个铁粒
        int ironCount = 1 + random.nextInt(3);
        for (int i = 0; i < ironCount; i++) {
            ItemStack ironNugget = new ItemStack(Items.IRON_NUGGET, 1);
            ItemEntity itemEntity = new ItemEntity(
                level,
                x + (random.nextDouble() - 0.5) * 0.5,
                y + 0.5,
                z + (random.nextDouble() - 0.5) * 0.5,
                ironNugget
            );
            
            // 设置随机速度
            itemEntity.setDeltaMovement(
                (random.nextDouble() - 0.5) * 0.2,
                0.1 + random.nextDouble() * 0.2,
                (random.nextDouble() - 0.5) * 0.2
            );
            
            itemEntity.setPickUpDelay(10);
            level.addFreshEntity(itemEntity);
        }
        
        // 3. 移除雪球实体
        snowball.discard();
        
        GengMod.LOGGER.debug("恭喜发财雪傀儡雪球在 ({}, {}, {}) 处爆炸，掉落 {} 个铁粒", 
            String.format("%.2f", x), String.format("%.2f", y), String.format("%.2f", z), ironCount);
    }
    
    private static String generateSimplifiedFireworkCommand(double x, double y, double z, Random random) {
        // 随机选择基础颜色
        int baseColor = BASE_FIREWORK_COLORS[random.nextInt(BASE_FIREWORK_COLORS.length)];
        
        // 随机选择形状
        String shape = FIREWORK_SHAPES[random.nextInt(FIREWORK_SHAPES.length)];
        
        // 随机决定是否使用渐变（70%概率使用1-3种颜色的渐变）
        boolean useGradient = random.nextDouble() < 0.7;
        int[] colors;
        
        if (useGradient) {
            // 生成1-3种颜色的渐变
            int gradientType = random.nextInt(4); // 0-3，4种渐变方式
            int colorCount = 1 + random.nextInt(2); // 2-3种颜色
            
            colors = generateColorGradient(baseColor, gradientType, colorCount, random);
        } else {
            // 单色
            colors = new int[]{baseColor};
        }
        
        // 构建命令
        StringBuilder command = new StringBuilder();
        
        command.append("summon minecraft:firework_rocket ")
               .append(String.format("%.2f", x)).append(" ")
               .append(String.format("%.2f", y)).append(" ")
               .append(String.format("%.2f", z))
               .append(" {FireworksItem:{");
        
        command.append("id:\"minecraft:firework_rocket\",");
        command.append("count:1,");
        command.append("components:{\"minecraft:fireworks\":{");
        
        // 飞行持续时间：设为0使其立即爆炸
        command.append("\"flight_duration\":0,");
        
        // 爆炸效果
        command.append("\"explosions\":[{");
        command.append("\"shape\":\"").append(shape).append("\",");
        
        // 颜色数组
        command.append("\"colors\":[I;");
        for (int i = 0; i < colors.length; i++) {
            if (i > 0) {
                command.append(",");
            }
            command.append(colors[i]);
        }
        command.append("]");
        
        // 如果有渐变色，设置淡出颜色
        if (colors.length > 1) {
            // 淡出颜色使用渐变的最后一种颜色
            command.append(",\"fade_colors\":[I;").append(colors[colors.length - 1]).append("]");
        }
        
        // 随机是否闪烁（30%概率）
        if (random.nextDouble() < 0.3) {
            command.append(",\"has_twinkle\":1");
        }
        
        // 随机是否拖尾（20%概率）
        if (random.nextDouble() < 0.2) {
            command.append(",\"has_trail\":1");
        }
        
        command.append("}]}"); // 结束explosions和fireworks组件
        command.append("}}"); // 结束components和FireworksItem
        
        // 设置生命周期，确保立即爆炸
        command.append(",Life:0,LifeTime:0");
        
        command.append("}"); // 结束整个NBT
        
        return command.toString();
    }
    
    /**
     * 生成颜色渐变数组
     * @param baseColor 基础颜色
     * @param gradientType 渐变类型：0=亮度递增，1=亮度递减，2=色调微调，3=补色渐变
     * @param colorCount 颜色数量（2-3）
     * @param random 随机数生成器
     * @return 颜色数组
     */
    private static int[] generateColorGradient(int baseColor, int gradientType, int colorCount, Random random) {
        int[] colors = new int[colorCount];
        
        // 提取RGB分量
        int r = (baseColor >> 16) & 0xFF;
        int g = (baseColor >> 8) & 0xFF;
        int b = baseColor & 0xFF;
        
        switch (gradientType) {
            case 0: // 亮度递增
                colors[0] = baseColor;
                for (int i = 1; i < colorCount; i++) {
                    // 逐渐增加亮度（向白色靠近）
                    float factor = 0.2f * i;
                    int newR = (int)(r + (255 - r) * factor);
                    int newG = (int)(g + (255 - g) * factor);
                    int newB = (int)(b + (255 - b) * factor);
                    colors[i] = (newR << 16) | (newG << 8) | newB;
                }
                break;
                
            case 1: // 亮度递减
                colors[0] = baseColor;
                for (int i = 1; i < colorCount; i++) {
                    // 逐渐降低亮度（向黑色靠近）
                    float factor = 0.3f * i;
                    int newR = (int)(r * (1 - factor));
                    int newG = (int)(g * (1 - factor));
                    int newB = (int)(b * (1 - factor));
                    colors[i] = (newR << 16) | (newG << 8) | newB;
                }
                break;
                
            case 2: // 色调微调（轻微调整色相）
                colors[0] = baseColor;
                for (int i = 1; i < colorCount; i++) {
                    // 轻微调整RGB值（±20）
                    int delta = 20 * (i % 2 == 0 ? 1 : -1);
                    int newR = Math.max(0, Math.min(255, r + delta));
                    int newG = Math.max(0, Math.min(255, g + delta));
                    int newB = Math.max(0, Math.min(255, b + delta));
                    colors[i] = (newR << 16) | (newG << 8) | newB;
                }
                break;
                
            case 3: // 补色渐变（向补色过渡）
                colors[0] = baseColor;
                for (int i = 1; i < colorCount; i++) {
                    // 计算补色
                    int complementR = 255 - r;
                    int complementG = 255 - g;
                    int complementB = 255 - b;
                    
                    // 线性插值到补色
                    float ratio = 0.3f * i;
                    int newR = (int)(r + (complementR - r) * ratio);
                    int newG = (int)(g + (complementG - g) * ratio);
                    int newB = (int)(b + (complementB - b) * ratio);
                    colors[i] = (newR << 16) | (newG << 8) | newB;
                }
                break;
        }
        
        return colors;
    }
}