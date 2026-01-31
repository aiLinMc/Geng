package net.mcreator.geng.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;

import net.mcreator.geng.init.GengModMobEffects;
import net.mcreator.geng.GengMod;

import java.util.List;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = GengMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class VillagerGiftHandler {
    // 最小冷却时间：8秒（160 tick）
    private static final int MIN_COOLDOWN_TICKS = 160;
    // 最大冷却时间：16秒（320 tick）
    private static final int MAX_COOLDOWN_TICKS = 320;
    // 效果检测半径：20格
    private static final int EFFECT_RADIUS = 20;
    // 投掷距离：4格
    private static final float THROW_DISTANCE = 4.0f;
    // 基础投掷力度
    private static final float BASE_THROW_POWER = 0.3f;
    // 最小投掷力度（防止太近扔得太猛）
    private static final float MIN_THROW_POWER = 0.2f;
    // 最大投掷力度（防止太远扔不动）
    private static final float MAX_THROW_POWER = 0.5f;
    // 向上投掷力度（让物品有个小抛物线）
    private static final float UPWARD_THROW_POWER = 0.18f;
    
    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        // 只在服务端执行
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        
        // 处理村民（投掷绿宝石）
        if (event.getEntity() instanceof Villager) {
            handleVillagerGift((Villager) event.getEntity());
        }
        // 处理僵尸猪灵（投掷金粒/金锭）
        else if (event.getEntity() instanceof ZombifiedPiglin) {
            handleZombifiedPiglinGift((ZombifiedPiglin) event.getEntity());
        }
    }
    
    private static void handleVillagerGift(Villager villager) {
        String cooldownKey = "VillagerGiftCooldown";
        
        // 检查冷却时间
        if (villager.getPersistentData().contains(cooldownKey)) {
            int cooldown = villager.getPersistentData().getInt(cooldownKey);
            if (cooldown > 0) {
                villager.getPersistentData().putInt(cooldownKey, cooldown - 1);
                return;
            }
        }
        
        // 获取效果Holder
        ResourceLocation effectLocation = net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.getKey(GengModMobEffects.WISHING_YOU_PROSPERITY.get());
        if (effectLocation == null) {
            return;
        }
        
        Holder<net.minecraft.world.effect.MobEffect> effectHolder;
        try {
            effectHolder = villager.level().registryAccess()
                .lookupOrThrow(Registries.MOB_EFFECT)
                .getOrThrow(ResourceKey.create(Registries.MOB_EFFECT, effectLocation));
        } catch (Exception e) {
            GengMod.LOGGER.error("Failed to get effect holder for WISHING_YOU_PROSPERITY", e);
            return;
        }
        
        // 查找附近有WISHING_YOU_PROSPERITY效果的玩家
        List<Player> playersWithEffect = villager.level().getEntitiesOfClass(Player.class, 
            villager.getBoundingBox().inflate(EFFECT_RADIUS))
            .stream()
            .filter(player -> {
                MobEffectInstance effectInstance = player.getEffect(effectHolder);
                return effectInstance != null && effectInstance.getDuration() > 0;
            })
            .collect(Collectors.toList());
        
        if (playersWithEffect.isEmpty()) {
            return;
        }
        
        // 找到距离最近的玩家
        Player nearestPlayer = null;
        double nearestDistance = Double.MAX_VALUE;
        
        for (Player player : playersWithEffect) {
            double distance = villager.distanceToSqr(player);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPlayer = player;
            }
        }
        
        if (nearestPlayer == null) {
            return;
        }
        
        // 先尝试寻路到玩家
        villager.getNavigation().moveTo(nearestPlayer, 0.7);
        
        // 检查村民是否在投掷范围内
        if (villager.distanceTo(nearestPlayer) <= THROW_DISTANCE) {
            // 执行投掷（村民投掷绿宝石）
            performVillagerThrow(villager, nearestPlayer);
            
            // 为村民设置新的随机冷却时间
            int newCooldown = MIN_COOLDOWN_TICKS + villager.getRandom().nextInt(MAX_COOLDOWN_TICKS - MIN_COOLDOWN_TICKS + 1);
            villager.getPersistentData().putInt(cooldownKey, newCooldown);
            
            GengMod.LOGGER.debug("村民 {} 向玩家 {} 投掷绿宝石，下次冷却时间: {} tick", 
                villager.getName().getString(), 
                nearestPlayer.getName().getString(), 
                newCooldown);
        }
    }
    
    private static void handleZombifiedPiglinGift(ZombifiedPiglin zombifiedPiglin) {
        String cooldownKey = "ZombifiedPiglinGiftCooldown";
        
        // 检查冷却时间
        if (zombifiedPiglin.getPersistentData().contains(cooldownKey)) {
            int cooldown = zombifiedPiglin.getPersistentData().getInt(cooldownKey);
            if (cooldown > 0) {
                zombifiedPiglin.getPersistentData().putInt(cooldownKey, cooldown - 1);
                return;
            }
        }
        
        // 获取效果Holder
        ResourceLocation effectLocation = net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.getKey(GengModMobEffects.WISHING_YOU_PROSPERITY.get());
        if (effectLocation == null) {
            return;
        }
        
        Holder<net.minecraft.world.effect.MobEffect> effectHolder;
        try {
            effectHolder = zombifiedPiglin.level().registryAccess()
                .lookupOrThrow(Registries.MOB_EFFECT)
                .getOrThrow(ResourceKey.create(Registries.MOB_EFFECT, effectLocation));
        } catch (Exception e) {
            GengMod.LOGGER.error("Failed to get effect holder for WISHING_YOU_PROSPERITY", e);
            return;
        }
        
        // 查找附近有WISHING_YOU_PROSPERITY效果的玩家
        List<Player> playersWithEffect = zombifiedPiglin.level().getEntitiesOfClass(Player.class, 
            zombifiedPiglin.getBoundingBox().inflate(EFFECT_RADIUS))
            .stream()
            .filter(player -> {
                MobEffectInstance effectInstance = player.getEffect(effectHolder);
                return effectInstance != null && effectInstance.getDuration() > 0;
            })
            .collect(Collectors.toList());
        
        if (playersWithEffect.isEmpty()) {
            return;
        }
        
        // 找到距离最近的玩家
        Player nearestPlayer = null;
        double nearestDistance = Double.MAX_VALUE;
        
        for (Player player : playersWithEffect) {
            double distance = zombifiedPiglin.distanceToSqr(player);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPlayer = player;
            }
        }
        
        if (nearestPlayer == null) {
            return;
        }
        
        // 确保僵尸猪灵对玩家无仇恨
        if (zombifiedPiglin.getTarget() == nearestPlayer) {
            zombifiedPiglin.setTarget(null);
        }
        
        // 先尝试寻路到玩家
        zombifiedPiglin.getNavigation().moveTo(nearestPlayer, 1.2);
        
        // 检查僵尸猪灵是否在投掷范围内
        if (zombifiedPiglin.distanceTo(nearestPlayer) <= THROW_DISTANCE) {
            // 执行投掷（僵尸猪灵投掷金粒/金锭）
            performZombifiedPiglinThrow(zombifiedPiglin, nearestPlayer);
            
            // 为僵尸猪灵设置新的随机冷却时间
            int newCooldown = MIN_COOLDOWN_TICKS + zombifiedPiglin.getRandom().nextInt(MAX_COOLDOWN_TICKS - MIN_COOLDOWN_TICKS + 1);
            zombifiedPiglin.getPersistentData().putInt(cooldownKey, newCooldown);
            
            GengMod.LOGGER.debug("僵尸猪灵 {} 向玩家 {} 投掷礼物，下次冷却时间: {} tick", 
                zombifiedPiglin.getName().getString(), 
                nearestPlayer.getName().getString(), 
                newCooldown);
        }
    }
    
    private static void performVillagerThrow(Villager villager, Player player) {
        // 村民投掷1颗绿宝石
        ItemStack gift = new ItemStack(Items.EMERALD, 1);
        
        createAndThrowItem(villager, player, gift, SoundEvents.VILLAGER_CELEBRATE);
        
        // 让村民看向玩家
        villager.getLookControl().setLookAt(player);
        
        // 添加一个小的村民动画
        villager.swing(villager.getUsedItemHand());
    }
    
    private static void performZombifiedPiglinThrow(ZombifiedPiglin zombifiedPiglin, Player player) {
        // 随机选择投掷金粒或金锭
        ItemStack gift;
        RandomSource random = zombifiedPiglin.getRandom();
        
        if (random.nextBoolean()) {
            // 金粒：1-3个
            gift = new ItemStack(Items.GOLD_NUGGET, 1 + random.nextInt(3));
        } else {
            // 金锭：1个
            gift = new ItemStack(Items.GOLD_INGOT, 1);
        }
        
        createAndThrowItem(zombifiedPiglin, player, gift, SoundEvents.PIGLIN_CELEBRATE);
        
        // 让僵尸猪灵看向玩家
        zombifiedPiglin.getLookControl().setLookAt(player);
        
        // 添加一个小的动画
        zombifiedPiglin.swing(zombifiedPiglin.getUsedItemHand());
    }
    
    private static void createAndThrowItem(net.minecraft.world.entity.Entity thrower, Player target, ItemStack gift, net.minecraft.sounds.SoundEvent celebrateSound) {
        // 创建物品实体
        ItemEntity itemEntity = new ItemEntity(
            thrower.level(),
            thrower.getX(),
            thrower.getEyeY() - 0.2, // 稍微降低一点发射点，看起来更自然
            thrower.getZ(),
            gift
        );
        
        // 计算到玩家的距离
        double dx = target.getX() - thrower.getX();
        double dy = (target.getY() + target.getEyeHeight() * 0.5) - thrower.getEyeY(); // 瞄准玩家腰部
        double dz = target.getZ() - thrower.getZ();
        
        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
        double totalDistance = Math.sqrt(horizontalDistance * horizontalDistance + dy * dy);
        
        // 根据距离调整投掷力度
        float throwPower = BASE_THROW_POWER;
        
        // 距离越远，投掷力度越大（但在限制范围内）
        if (totalDistance > 0) {
            // 根据距离动态调整力度：距离越远，力度越大，但有上下限
            float distanceFactor = (float) (totalDistance / THROW_DISTANCE);
            throwPower = BASE_THROW_POWER * distanceFactor;
            throwPower = Math.max(MIN_THROW_POWER, Math.min(MAX_THROW_POWER, throwPower));
        }
        
        // 标准化方向并应用力度
        if (horizontalDistance > 0) {
            dx = dx / horizontalDistance * throwPower;
            dz = dz / horizontalDistance * throwPower;
        }
        
        // 垂直方向：基础向上力度加上轻微瞄准调整
        dy = UPWARD_THROW_POWER + (dy / Math.max(1.0, totalDistance)) * 0.1;
        
        // 减少随机偏移，使投掷更准确
        double randomFactor = 0.05; // 从0.1减小到0.05，减少随机性
        RandomSource random = thrower.getRandom();
        dx += (random.nextDouble() - 0.5) * randomFactor;
        dy += (random.nextDouble() - 0.5) * randomFactor * 0.5;
        dz += (random.nextDouble() - 0.5) * randomFactor;
        
        itemEntity.setDeltaMovement(dx, dy, dz);
        
        // 设置拾取延迟
        itemEntity.setPickUpDelay(12);
        
        // 添加一些旋转效果，看起来更自然
        itemEntity.setYRot(random.nextFloat() * 360.0f);
        itemEntity.setXRot(random.nextFloat() * 180.0f - 90.0f);
        
        // 生成物品实体
        thrower.level().addFreshEntity(itemEntity);
        
        // 播放庆祝音效
        thrower.level().playSound(null, thrower.getX(), thrower.getY(), thrower.getZ(),
            celebrateSound, SoundSource.NEUTRAL, 1.0f, 1.0f);
        
        // 播放投掷音效
        thrower.level().playSound(null, thrower.getX(), thrower.getY(), thrower.getZ(),
            SoundEvents.ARROW_SHOOT, SoundSource.NEUTRAL, 0.3f, 1.2f);
    }
}