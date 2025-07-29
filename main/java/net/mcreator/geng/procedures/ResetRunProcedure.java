package net.mcreator.geng.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.geng.network.GengModVariables;

public class ResetRunProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.bargaining_progress = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.SilverDollarNum = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.CopperCoinNum = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.AlreadyGetCoins = false;
			_vars.syncPlayerVariables(entity);
		}
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal("Already reset pxx data"), false);
	}
}