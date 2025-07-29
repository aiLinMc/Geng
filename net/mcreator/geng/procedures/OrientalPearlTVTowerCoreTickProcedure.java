package net.mcreator.geng.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.geng.init.GengModMobEffects;

import java.util.Comparator;

public class OrientalPearlTVTowerCoreTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double i = 0;
		double vx = 0;
		double vy = 0;
		double vz = 0;
		{
			final Vec3 _center = new Vec3(x, y, z);
			for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
				if (entityiterator instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(GengModMobEffects.THE_GRACE_OF_THE_SNOW_KING)) {
					vx = (x + 0.5) - entityiterator.getX();
					vy = y - (entityiterator.getY() + 1);
					vz = (z + 0.5) - entityiterator.getZ();
					i = 0;
					if (Mth.nextInt(RandomSource.create(), 1, 80) == 20) {
						if (Mth.nextInt(RandomSource.create(), 1, 8) == 5) {
							for (int index0 = 0; index0 < 150; index0++) {
								if (world instanceof ServerLevel _level)
									_level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, (Math.round((x - i * (vx / 150)) * 1000) / 1000d + 0.5), (Math.round((y - i * (vy / 150)) * 1000) / 1000d),
											(Math.round((z - i * (vz / 150)) * 1000) / 1000d + 0.5), 1, 0.1, 0.1, 0.1, 0);
								i++;
							}
							entityiterator.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("geng:kicked_out_of_shanghai")))), 50);
						} else {
							for (int index1 = 0; index1 < 160; index1++) {
								if (world instanceof ServerLevel _level)
									_level.sendParticles(ParticleTypes.SQUID_INK, (Math.round((x - i * (vx / 160)) * 1000) / 1000d + 0.5), (Math.round((y - i * (vy / 160)) * 1000) / 1000d), (Math.round((z - i * (vz / 160)) * 1000) / 1000d + 0.5), 1,
											0, 0, 0, 0);
								i++;
							}
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 1, true, false));
						}
					} else {
						for (int index2 = 0; index2 < 80; index2++) {
							if (world instanceof ServerLevel _level)
								_level.sendParticles(ParticleTypes.SMALL_FLAME, (Math.round((x - i * (vx / 80)) * 1000) / 1000d + 0.5), (Math.round((y - i * (vy / 80)) * 1000) / 1000d), (Math.round((z - i * (vz / 80)) * 1000) / 1000d + 0.5), 1, 0, 0,
										0, 0);
							i++;
						}
						entityiterator.igniteForSeconds(1);
					}
				}
			}
		}
	}
}