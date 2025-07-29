package net.mcreator.geng.procedures;

import virtuoel.pehkui.api.ScaleTypes;
import virtuoel.pehkui.api.ScaleOperations;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.geng.init.GengModMobEffects;

public class RussianProportionStartProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		ScaleTypes.HEIGHT.getScaleData(entity).setTargetScale((float) ScaleOperations.SET.applyAsDouble(ScaleTypes.HEIGHT.getScaleData(entity).getTargetScale(), 1));
		ScaleTypes.WIDTH.getScaleData(entity).setTargetScale((float) ScaleOperations.SET.applyAsDouble(ScaleTypes.WIDTH.getScaleData(entity).getTargetScale(), 1));
		ScaleTypes.HEIGHT.getScaleData(entity).setTargetScale((float) ScaleOperations.DIVIDE.applyAsDouble(ScaleTypes.HEIGHT.getScaleData(entity).getTargetScale(),
				(2.2 + 0.1 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RUSSIAN_PROPORTION) ? _livEnt.getEffect(GengModMobEffects.RUSSIAN_PROPORTION).getAmplifier() : 0))));
		ScaleTypes.WIDTH.getScaleData(entity).setTargetScale((float) ScaleOperations.MULTIPLY.applyAsDouble(ScaleTypes.WIDTH.getScaleData(entity).getTargetScale(),
				(2.2 + 0.1 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RUSSIAN_PROPORTION) ? _livEnt.getEffect(GengModMobEffects.RUSSIAN_PROPORTION).getAmplifier() : 0))));
	}
}