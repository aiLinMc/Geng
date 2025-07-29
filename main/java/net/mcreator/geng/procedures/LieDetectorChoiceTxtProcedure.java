package net.mcreator.geng.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.geng.network.GengModVariables;

public class LieDetectorChoiceTxtProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return entity.getData(GengModVariables.PLAYER_VARIABLES).txt02;
	}
}