package net.mcreator.geng.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.geng.procedures.LegDisabilityTickProcedure;

public class LegDisabilityMobEffect extends MobEffect {
	public LegDisabilityMobEffect() {
		super(MobEffectCategory.HARMFUL, -3355393);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		LegDisabilityTickProcedure.execute(entity);
		return super.applyEffectTick(entity, amplifier);
	}
}