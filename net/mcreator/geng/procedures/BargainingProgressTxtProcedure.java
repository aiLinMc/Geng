package net.mcreator.geng.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.geng.network.GengModVariables;

public class BargainingProgressTxtProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return Component.translatable("translation.key.info.bargain_progress").getString() + "" + entity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress / 1000 + "%";
	}
}