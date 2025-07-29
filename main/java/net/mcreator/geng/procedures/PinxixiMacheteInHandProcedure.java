package net.mcreator.geng.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.geng.network.GengModVariables;

public class PinxixiMacheteInHandProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress_display;
	}
}