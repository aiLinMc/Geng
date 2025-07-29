package net.mcreator.geng.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.geng.network.GengModVariables;

public class HaveAChoiceTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.InChoiceGui = true;
			_vars.syncPlayerVariables(entity);
		}
	}
}