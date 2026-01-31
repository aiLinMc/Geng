package net.mcreator.geng.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;

import net.mcreator.geng.init.GengModMobEffects;
import net.mcreator.geng.GengMod;

import java.util.Random;

@EventBusSubscriber(modid = GengMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class MobDeathGoldDropHandler {
    
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        // 只在服务端执行
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        
        LivingEntity entity = event.getEntity();
        
        // 跳过玩家（可选，如果不想让玩家死亡也掉金锭）
        if (entity instanceof Player) {
            return;
        }
        
        // 获取效果Holder
        ResourceLocation effectLocation = net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.getKey(GengModMobEffects.WISHING_YOU_PROSPERITY.get());
        if (effectLocation == null) {
            return;
        }
        
        // 检查生物是否有"恭喜发财"效果
        Holder<net.minecraft.world.effect.MobEffect> effectHolder;
        try {
            effectHolder = entity.level().registryAccess()
                .lookupOrThrow(Registries.MOB_EFFECT)
                .getOrThrow(ResourceKey.create(Registries.MOB_EFFECT, effectLocation));
        } catch (Exception e) {
            GengMod.LOGGER.error("Failed to get effect holder for WISHING_YOU_PROSPERITY", e);
            return;
        }
        
        MobEffectInstance effectInstance = entity.getEffect(effectHolder);
        if (effectInstance == null || effectInstance.getDuration() <= 0) {
            return;
        }
        
        // 生成死亡位置
        double x = entity.getX();
        double y = entity.getY() + 0.5;
        double z = entity.getZ();
        
        // 创建一个金锭物品实体
        ItemStack goldIngot = new ItemStack(Items.GOLD_INGOT, 1);
        ItemEntity itemEntity = new ItemEntity(
            entity.level(),
            x,
            y,
            z,
            goldIngot
        );
        
        // 设置随机速度，使其散落更自然
        Random random = new Random();
        itemEntity.setDeltaMovement(
            (random.nextDouble() - 0.5) * 0.2,
            0.2,
            (random.nextDouble() - 0.5) * 0.2
        );
        
        // 设置拾取延迟
        itemEntity.setPickUpDelay(20); // 1秒后可以拾取
        
        // 添加到世界
        entity.level().addFreshEntity(itemEntity);
        
        // 可选：播放掉落音效
        entity.level().playSound(null, x, y, z,
            net.minecraft.sounds.SoundEvents.ITEM_PICKUP,
            net.minecraft.sounds.SoundSource.NEUTRAL,
            0.5f, 1.0f);
        
        GengMod.LOGGER.debug("{} 死亡并掉落金锭，因其有恭喜发财效果", entity.getName().getString());
    }
}