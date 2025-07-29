package net.mcreator.geng.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.geng.procedures.VillagerAngerTickProcedure;
import net.mcreator.geng.procedures.VillagerAngerBeginProcedure;

public class VillagerAngerMobEffect extends MobEffect {
	public VillagerAngerMobEffect() {
		super(MobEffectCategory.NEUTRAL, -42752);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		VillagerAngerBeginProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		VillagerAngerTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return super.applyEffectTick(entity, amplifier);
	}
}