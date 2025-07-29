package net.mcreator.geng.procedures;

import virtuoel.pehkui.api.ScaleTypes;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleOperations;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModMobEffects;

public class PutinAttackProcedure {
	public static void execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(GengModMobEffects.RUSSIAN_PROPORTION))) {
			if ((sourceentity.getData(GengModVariables.PLAYER_VARIABLES).PutinMode).equals("reset")) {
				for (final ScaleType type : ScaleRegistries.SCALE_TYPES.values()) {
					type.getScaleData(entity).setPersistence(type.getScaleData(entity).getPersistence());
					type.getScaleData(entity).resetScale();
				}
			} else if ((sourceentity.getData(GengModVariables.PLAYER_VARIABLES).PutinMode).equals("short")) {
				if (ScaleTypes.HEIGHT.getScaleData(entity).getTargetScale() > 0.12) {
					ScaleTypes.HEIGHT.getScaleData(entity).setTargetScale((float) ScaleOperations.SUBTRACT.applyAsDouble(ScaleTypes.HEIGHT.getScaleData(entity).getTargetScale(), 0.12));
					ScaleTypes.WIDTH.getScaleData(entity).setTargetScale((float) ScaleOperations.ADD.applyAsDouble(ScaleTypes.WIDTH.getScaleData(entity).getTargetScale(), 0.12));
				}
			} else {
				if (ScaleTypes.WIDTH.getScaleData(entity).getTargetScale() > 0.12) {
					ScaleTypes.HEIGHT.getScaleData(entity).setTargetScale((float) ScaleOperations.ADD.applyAsDouble(ScaleTypes.HEIGHT.getScaleData(entity).getTargetScale(), 0.12));
					ScaleTypes.WIDTH.getScaleData(entity).setTargetScale((float) ScaleOperations.SUBTRACT.applyAsDouble(ScaleTypes.WIDTH.getScaleData(entity).getTargetScale(), 0.12));
				}
			}
		}
	}
}