package net.mcreator.geng.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;

import net.mcreator.geng.network.GengModVariables;

public class PutinModeProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if ((entity.getData(GengModVariables.PLAYER_VARIABLES).PutinMode).equals("reset")) {
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.PutinMode = "short";
				_vars.syncPlayerVariables(entity);
			}
		} else if ((entity.getData(GengModVariables.PLAYER_VARIABLES).PutinMode).equals("short")) {
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.PutinMode = "tall";
				_vars.syncPlayerVariables(entity);
			}
		} else {
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.PutinMode = "reset";
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity instanceof LivingEntity _entity)
			_entity.swing(InteractionHand.MAIN_HAND, true);
	}
}