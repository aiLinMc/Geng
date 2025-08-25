package net.mcreator.geng.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.mcreator.geng.GengMod;

public class CrasherPlusUseProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		GengMod.LOGGER.fatal("Goodbye Game !!!");
		CrasherPlusUseProcedure.execute(world, entity);
	}
}