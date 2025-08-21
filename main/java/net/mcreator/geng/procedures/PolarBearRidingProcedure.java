package net.mcreator.geng.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModAttributes;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PolarBearRidingProcedure {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if ((BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()).equals("minecraft:polar_bear") && entity.isVehicle()
				&& (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(GengModAttributes.HAVE_SADDLE) ? _livingEntity2.getAttribute(GengModAttributes.HAVE_SADDLE).getValue() : 0) == 1) {
			if (entity instanceof Mob _entity)
				_entity.getNavigation().moveTo((entity.getFirstPassenger()).getData(GengModVariables.PLAYER_VARIABLES).polar_bear_x, (entity.getFirstPassenger()).getData(GengModVariables.PLAYER_VARIABLES).polar_bear_y,
						(entity.getFirstPassenger()).getData(GengModVariables.PLAYER_VARIABLES).polar_bear_z, 1.5);
			if (entity instanceof Mob _entity)
				_entity.setTarget(null);
		}
	}
}