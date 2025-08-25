package net.mcreator.geng.procedures;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.BuiltInRegistries;

import javax.annotation.Nullable;

import java.util.Comparator;

@EventBusSubscriber
public class MakePolarbearAngryProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		execute(null, world, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()).equals("minecraft:player")) {
			{
				final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
				for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
					if ((BuiltInRegistries.ENTITY_TYPE.getKey(entityiterator.getType()).toString()).equals("minecraft:polar_bear") && (entity.getStringUUID()).equals(entityiterator.getPersistentData().getString("Owner"))
							&& !(entityiterator == sourceentity)) {
						if (entityiterator instanceof Mob _entity && sourceentity instanceof LivingEntity _ent)
							_entity.setTarget(_ent);
					}
				}
			}
		}
		if ((BuiltInRegistries.ENTITY_TYPE.getKey(sourceentity.getType()).toString()).equals("minecraft:player")) {
			{
				final Vec3 _center = new Vec3((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()));
				for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
					if ((BuiltInRegistries.ENTITY_TYPE.getKey(entityiterator.getType()).toString()).equals("minecraft:polar_bear") && (sourceentity.getStringUUID()).equals(entityiterator.getPersistentData().getString("Owner"))
							&& !(entityiterator == entity)) {
						if (entityiterator instanceof Mob _entity && entity instanceof LivingEntity _ent)
							_entity.setTarget(_ent);
					}
				}
			}
		}
	}
}