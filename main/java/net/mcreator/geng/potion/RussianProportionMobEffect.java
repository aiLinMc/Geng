package net.mcreator.geng.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.geng.procedures.RussianProportionStartProcedure;

public class RussianProportionMobEffect extends MobEffect {
	public RussianProportionMobEffect() {
		super(MobEffectCategory.NEUTRAL, -13421773);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		RussianProportionStartProcedure.execute(entity);
	}
}