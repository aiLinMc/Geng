package net.mcreator.geng.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.geng.procedures.DaodaosMotherTickProcedure;
import net.mcreator.geng.procedures.DaodaosMotherBeginProcedure;

public class DaodaosMotherMobEffect extends MobEffect {
	public DaodaosMotherMobEffect() {
		super(MobEffectCategory.NEUTRAL, -65281);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		DaodaosMotherBeginProcedure.execute(entity);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		DaodaosMotherTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return super.applyEffectTick(entity, amplifier);
	}
}