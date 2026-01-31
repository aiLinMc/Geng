package net.mcreator.geng.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;

import net.mcreator.geng.init.GengModMobEffects;
import net.mcreator.geng.GengMod;

import java.util.EnumSet;
import java.util.Random;
import java.util.List;

@EventBusSubscriber(modid = GengMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DiamondFoxHandler {
    
    // 自定义标签用于标记钻石狐狸
    private static final String DIAMOND_FOX_TAG = "DiamondFox";
    private static final String HAS_DELIVERED_TAG = "HasDeliveredDiamond";
    
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        // 只在服务端执行，只处理狐狸
        if (event.getLevel().isClientSide() || !(event.getEntity() instanceof Fox)) {
            return;
        }
        
        Fox fox = (Fox) event.getEntity();
        
        // 检查周围20格内是否有带"恭喜发财"效果的玩家
        List<Player> nearbyPlayers = fox.level().getEntitiesOfClass(Player.class, 
            fox.getBoundingBox().inflate(20.0));
        
        boolean hasPlayerWithEffect = false;
        Player targetPlayer = null;
        
        // 获取效果Holder
        ResourceLocation effectLocation = net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.getKey(GengModMobEffects.WISHING_YOU_PROSPERITY.get());
        if (effectLocation == null) {
            return;
        }
        
        Holder<net.minecraft.world.effect.MobEffect> effectHolder;
        try {
            effectHolder = fox.level().registryAccess()
                .lookupOrThrow(Registries.MOB_EFFECT)
                .getOrThrow(ResourceKey.create(Registries.MOB_EFFECT, effectLocation));
        } catch (Exception e) {
            GengMod.LOGGER.error("Failed to get effect holder for WISHING_YOU_PROSPERITY", e);
            return;
        }
        
        for (Player player : nearbyPlayers) {
            MobEffectInstance effectInstance = player.getEffect(effectHolder);
            if (effectInstance != null && effectInstance.getDuration() > 0) {
                hasPlayerWithEffect = true;
                targetPlayer = player;
                break;
            }
        }
        
        if (!hasPlayerWithEffect) {
            return;
        }
        
        // 10%概率生成钻石狐狸
        Random random = new Random();
        if (random.nextDouble() > 0.15) { // 90%概率不生成
            return;
        }
        
        // 标记为钻石狐狸
        CompoundTag persistentData = fox.getPersistentData();
        persistentData.putBoolean(DIAMOND_FOX_TAG, true);
        persistentData.putBoolean(HAS_DELIVERED_TAG, false);
        
        // 设置目标玩家（如果有多个，选择第一个检测到的）
        if (targetPlayer != null) {
            persistentData.putUUID("TargetPlayer", targetPlayer.getUUID());
        }
        
        // 给狐狸叼上钻石
        fox.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND, 1));
        
        // 添加自定义AI目标
        fox.goalSelector.addGoal(3, new DeliverDiamondGoal(fox));
        
        GengMod.LOGGER.debug("生成钻石狐狸，目标玩家: {}", 
            targetPlayer != null ? targetPlayer.getName().getString() : "无");
    }
    
    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        // 只在服务端执行，只处理狐狸
        if (event.getEntity().level().isClientSide() || !(event.getEntity() instanceof Fox)) {
            return;
        }
        
        Fox fox = (Fox) event.getEntity();
        CompoundTag persistentData = fox.getPersistentData();
        
        // 检查是否是钻石狐狸且还未送达
        if (!persistentData.getBoolean(DIAMOND_FOX_TAG) || persistentData.getBoolean(HAS_DELIVERED_TAG)) {
            return;
        }
        
        // 检查是否还叼着钻石
        ItemStack heldItem = fox.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND);
        if (!heldItem.is(Items.DIAMOND)) {
            // 钻石已掉落或被拿走，清除标记
            persistentData.putBoolean(DIAMOND_FOX_TAG, false);
            return;
        }
    }
    
    // 自定义AI目标：运送钻石给玩家
    private static class DeliverDiamondGoal extends Goal {
        private final Fox fox;
        private Player targetPlayer;
        private int deliveryCooldown = 0;
        
        public DeliverDiamondGoal(Fox fox) {
            this.fox = fox;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }
        
        @Override
        public boolean canUse() {
            CompoundTag persistentData = fox.getPersistentData();
            
            // 检查是否是钻石狐狸且还未送达
            if (!persistentData.getBoolean(DIAMOND_FOX_TAG) || persistentData.getBoolean(HAS_DELIVERED_TAG)) {
                return false;
            }
            
            // 检查是否还叼着钻石
            ItemStack heldItem = fox.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND);
            if (!heldItem.is(Items.DIAMOND)) {
                persistentData.putBoolean(DIAMOND_FOX_TAG, false);
                return false;
            }
            
            // 查找目标玩家
            if (persistentData.hasUUID("TargetPlayer")) {
                targetPlayer = fox.level().getPlayerByUUID(persistentData.getUUID("TargetPlayer"));
            }
            
            // 如果目标玩家丢失，重新查找附近带有"恭喜发财"效果的玩家
            if (targetPlayer == null || !targetPlayer.isAlive()) {
                targetPlayer = findPlayerWithEffect();
                if (targetPlayer != null) {
                    persistentData.putUUID("TargetPlayer", targetPlayer.getUUID());
                }
            }
            
            return targetPlayer != null && fox.distanceToSqr(targetPlayer) > 4.0; // 距离大于2格时继续接近
        }
        
        @Override
        public boolean canContinueToUse() {
            if (deliveryCooldown > 0) {
                deliveryCooldown--;
                return false;
            }
            
            CompoundTag persistentData = fox.getPersistentData();
            if (!persistentData.getBoolean(DIAMOND_FOX_TAG) || persistentData.getBoolean(HAS_DELIVERED_TAG)) {
                return false;
            }
            
            ItemStack heldItem = fox.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND);
            if (!heldItem.is(Items.DIAMOND)) {
                persistentData.putBoolean(DIAMOND_FOX_TAG, false);
                return false;
            }
            
            return targetPlayer != null && targetPlayer.isAlive() && fox.distanceToSqr(targetPlayer) > 1.0;
        }
        
        @Override
        public void start() {
            if (targetPlayer != null) {
                fox.getNavigation().moveTo(targetPlayer, 1.2);
            }
        }
        
        @Override
        public void stop() {
            fox.getNavigation().stop();
        }
        
        @Override
        public void tick() {
            if (targetPlayer == null) {
                return;
            }
            
            // 看向玩家
            fox.getLookControl().setLookAt(targetPlayer, 10.0F, (float) fox.getMaxHeadXRot());
            
            // 移动到玩家附近
            fox.getNavigation().moveTo(targetPlayer, 1.2);
            
            // 检查是否到达玩家附近（2格内）
            if (fox.distanceToSqr(targetPlayer) <= 4.0) {
                deliverDiamond();
            }
        }
        
        private void deliverDiamond() {
            CompoundTag persistentData = fox.getPersistentData();
            
            // 放下钻石
            fox.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            
            // 创建钻石物品实体
            ItemStack diamond = new ItemStack(Items.DIAMOND, 1);
            net.minecraft.world.entity.item.ItemEntity itemEntity = new net.minecraft.world.entity.item.ItemEntity(
                fox.level(),
                fox.getX(),
                fox.getY() + 0.5,
                fox.getZ(),
                diamond
            );
            
            // 设置轻微弹跳效果
            Random random = new Random();
            itemEntity.setDeltaMovement(
                (random.nextDouble() - 0.5) * 0.1,
                0.2,
                (random.nextDouble() - 0.5) * 0.1
            );
            
            itemEntity.setPickUpDelay(0); // 立即可以拾取
            fox.level().addFreshEntity(itemEntity);
            
            // 标记为已送达
            persistentData.putBoolean(HAS_DELIVERED_TAG, true);
            persistentData.putBoolean(DIAMOND_FOX_TAG, false);
            
            // 播放高兴的音效
            fox.level().playSound(null, fox.getX(), fox.getY(), fox.getZ(),
                net.minecraft.sounds.SoundEvents.FOX_AMBIENT,
                net.minecraft.sounds.SoundSource.NEUTRAL,
                1.0f, 1.0f);
            
            // 添加粒子效果
            for (int i = 0; i < 10; i++) {
                fox.level().addParticle(
                    net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER,
                    fox.getX() + (random.nextDouble() - 0.5) * 0.5,
                    fox.getY() + random.nextDouble() * 0.5,
                    fox.getZ() + (random.nextDouble() - 0.5) * 0.5,
                    0, 0, 0
                );
            }
            
            // 设置冷却时间，防止立即重新开始
            deliveryCooldown = 40; // 2秒冷却
            
            // 让狐狸逃跑
            fox.getNavigation().moveTo(
                fox.getX() + (random.nextDouble() - 0.5) * 10,
                fox.getY(),
                fox.getZ() + (random.nextDouble() - 0.5) * 10,
                1.5
            );
            
            GengMod.LOGGER.debug("钻石狐狸向玩家 {} 送达钻石", 
                targetPlayer != null ? targetPlayer.getName().getString() : "未知");
        }
        
        private Player findPlayerWithEffect() {
            // 获取效果Holder
            ResourceLocation effectLocation = net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.getKey(GengModMobEffects.WISHING_YOU_PROSPERITY.get());
            if (effectLocation == null) {
                return null;
            }
            
            Holder<net.minecraft.world.effect.MobEffect> effectHolder;
            try {
                effectHolder = fox.level().registryAccess()
                    .lookupOrThrow(Registries.MOB_EFFECT)
                    .getOrThrow(ResourceKey.create(Registries.MOB_EFFECT, effectLocation));
            } catch (Exception e) {
                return null;
            }
            
            // 查找30格内带有"恭喜发财"效果的玩家
            List<Player> players = fox.level().getEntitiesOfClass(Player.class, 
                fox.getBoundingBox().inflate(40.0));
            
            for (Player player : players) {
                MobEffectInstance effectInstance = player.getEffect(effectHolder);
                if (effectInstance != null && effectInstance.getDuration() > 0) {
                    return player;
                }
            }
            
            return null;
        }
    }
}