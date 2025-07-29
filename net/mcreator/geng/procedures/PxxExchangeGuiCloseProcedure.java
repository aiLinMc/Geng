package net.mcreator.geng.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.geng.network.GengModVariables;

public class PxxExchangeGuiCloseProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.num7 = 1;
			_vars.syncPlayerVariables(entity);
		}
	}
}