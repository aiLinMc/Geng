package net.mcreator.geng.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.geng.procedures.RawMelonEggsTickProcedure;

public class RawMelonEggsMobEffect extends MobEffect {
	public RawMelonEggsMobEffect() {
		super(MobEffectCategory.HARMFUL, -16256915);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		RawMelonEggsTickProcedure.execute(entity.getX(), entity.getY(), entity.getZ(), entity);
		return super.applyEffectTick(entity, amplifier);
	}
}