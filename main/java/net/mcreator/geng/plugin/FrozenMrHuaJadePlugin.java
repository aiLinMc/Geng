// 文件: FrozenMrHuaJadePlugin.java - 简化版本
package net.mcreator.geng.plugin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

import net.mcreator.geng.block.FrozenMrHuaBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

@WailaPlugin
public class FrozenMrHuaJadePlugin implements IWailaPlugin {
    
    public static final ResourceLocation FROZEN_MR_HUA_PROGRESS = 
        ResourceLocation.fromNamespaceAndPath("geng", "frozen_mr_hua_thawing");
    
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new FrozenMrHuaProgressProvider(), 
            net.mcreator.geng.block.entity.FrozenMrHuaBlockEntity.class);
    }
    
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new FrozenMrHuaProgressProvider(), 
            FrozenMrHuaBlock.class);
        
        // 注释掉添加配置的代码，先让功能正常工作
        // registration.addConfig(FROZEN_MR_HUA_PROGRESS, true);
    }
    
    public static class FrozenMrHuaProgressProvider implements 
            IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
        
        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, 
                IPluginConfig config) {
            // 直接显示，不检查配置
            CompoundTag serverData = accessor.getServerData();
            
            // 显示解冻进度
            if (serverData.contains("UnfreezingProgress")) {
                double progress = serverData.getDouble("UnfreezingProgress");
                double percentage = progress / 100.0;
                
                // 格式化显示：保留2位小数
                String formatted = String.format("%.2f", percentage);
                tooltip.add(Component.literal(Component.translatable("jade.geng.frozen_mr_hua_progress").getString() + formatted + "%"));
            }
            
            // 显示增量速度
            if (serverData.contains("IncrementSpeed")) {
                String speed = serverData.getString("IncrementSpeed");
                tooltip.add(Component.literal(Component.translatable("jade.geng.frozen_mr_hua_speed").getString() + speed));
            }
        }
        
        @Override
        public void appendServerData(CompoundTag data, BlockAccessor accessor) {
            BlockEntity blockEntity = accessor.getBlockEntity();
            if (blockEntity instanceof net.mcreator.geng.block.entity.FrozenMrHuaBlockEntity frozenBlockEntity) {
                CompoundTag persistentData = frozenBlockEntity.getPersistentData();
                
                // 传输解冻进度
                if (persistentData.contains("UnfreezingProgress")) {
                    double progress = persistentData.getDouble("UnfreezingProgress");
                    data.putDouble("UnfreezingProgress", progress);
                }
                
                // 传输增量速度
                if (persistentData.contains("IncrementSpeed")) {
                    String speed = persistentData.getString("IncrementSpeed");
                    data.putString("IncrementSpeed", speed);
                }
            }
        }
        
        @Override
        public ResourceLocation getUid() {
            return FROZEN_MR_HUA_PROGRESS;
        }
        
        // 可选：设置优先级
        @Override
        public int getDefaultPriority() {
            return 1000; // 设置一个较高的优先级
        }
    }
}