package net.mcreator.geng.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.geng.init.GengModMobEffects;

public class KindnessTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.onGround()) {
			entity.setDeltaMovement(new Vec3(0, (0.42 + 0.12 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.KINDNESS) ? _livEnt.getEffect(GengModMobEffects.KINDNESS).getAmplifier() : 0)), 0));
		}
	}
}