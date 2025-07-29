package net.mcreator.geng.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.geng.procedures.ResourceDepletionTickProcedure;
import net.mcreator.geng.procedures.ResourceDepletionBeginProcedure;

public class ResourceDepletionMobEffect extends MobEffect {
	public ResourceDepletionMobEffect() {
		super(MobEffectCategory.HARMFUL, -13421773);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		ResourceDepletionBeginProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		ResourceDepletionTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return super.applyEffectTick(entity, amplifier);
	}
}