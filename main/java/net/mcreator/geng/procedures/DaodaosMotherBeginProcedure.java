package net.mcreator.geng.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.geng.network.GengModVariables;

public class DaodaosMotherBeginProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.ddm_sound_tick = 0;
			_vars.syncPlayerVariables(entity);
		}
	}
}