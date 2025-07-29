package net.mcreator.geng.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.geng.network.GengModVariables;

public class PutinTxtProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		if (!(entity.getData(GengModVariables.PLAYER_VARIABLES).PutinMode).equals("reset") && !(entity.getData(GengModVariables.PLAYER_VARIABLES).PutinMode).equals("short")
				&& !(entity.getData(GengModVariables.PLAYER_VARIABLES).PutinMode).equals("tall")) {
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.PutinMode = "reset";
				_vars.syncPlayerVariables(entity);
			}
		}
		return Component.translatable("translation.putin.info.click").getString() + "\n" + Component.translatable("translation.putin.info.mode").getString() + "\u00A75"
				+ Component.translatable(("translation.putin.info." + entity.getData(GengModVariables.PLAYER_VARIABLES).PutinMode)).getString();
	}
}