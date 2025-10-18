package net.mcreator.geng.procedures;

import org.checkerframework.checker.units.qual.m;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import javax.annotation.Nullable;

@EventBusSubscriber
public class OneshotKillAttackProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getDirectEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, immediatesourceentity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		double n = 0;
		double m = 0;
		double dx = 0;
		double dy = 0;
		double dz = 0;
		if (immediatesourceentity.getPersistentData().getBoolean("OneShotKill") && !entity.isInvulnerable()) {
			entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.ARROW), sourceentity), 800);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.FLASH, x, y, z, 20, 0.2, 0.2, 0.2, 1);
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
			n = 0.2;
			m = 0.4;
			dx = sourceentity.getX() - Math.sin(Math.toRadians(sourceentity.getYRot())) * n;
			dy = sourceentity.getY() + 1.2;
			dz = sourceentity.getZ() + Math.cos(Math.toRadians(sourceentity.getYRot())) * n;
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.END_ROD, dx, dy, dz, 1, 0, 0, 0, 0);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.END_ROD, (dx - Math.cos(Math.toRadians(sourceentity.getYRot())) * m), dy, (dz - Math.sin(Math.toRadians(sourceentity.getYRot())) * m), 1, 0, 0, 0, 0);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.END_ROD, (dx - Math.cos(Math.toRadians(sourceentity.getYRot())) * m * 2), dy, (dz - Math.sin(Math.toRadians(sourceentity.getYRot())) * m * 2), 1, 0, 0, 0, 0);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.END_ROD, (dx + Math.cos(Math.toRadians(sourceentity.getYRot())) * m), dy, (dz + Math.sin(Math.toRadians(sourceentity.getYRot())) * m), 1, 0, 0, 0, 0);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.END_ROD, (dx + Math.cos(Math.toRadians(sourceentity.getYRot())) * m * 2), dy, (dz + Math.sin(Math.toRadians(sourceentity.getYRot())) * m * 2), 1, 0, 0, 0, 0);
		}
	}
}