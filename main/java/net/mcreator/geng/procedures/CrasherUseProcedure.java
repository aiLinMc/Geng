package net.mcreator.geng.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.geng.GengMod;

public class CrasherUseProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		GengMod.LOGGER.fatal("Goodbye Game !!!");
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal("3.."), false);
		GengMod.LOGGER.fatal("3..");
		GengMod.queueServerWork(20, () -> {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("2.."), false);
			GengMod.LOGGER.fatal("2..");
			GengMod.queueServerWork(20, () -> {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("1.."), false);
				GengMod.LOGGER.fatal("1..");
				GengMod.queueServerWork(20, () -> {
					if (!(entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null).level().isClientSide())
						(entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null).discard();
				});
			});
		});
	}
}