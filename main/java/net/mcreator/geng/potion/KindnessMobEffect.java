package net.mcreator.geng.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.geng.procedures.KindnessTickProcedure;

public class KindnessMobEffect extends MobEffect {
	public KindnessMobEffect() {
		super(MobEffectCategory.NEUTRAL, -6724096);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		KindnessTickProcedure.execute(entity);
		return super.applyEffectTick(entity, amplifier);
	}
}