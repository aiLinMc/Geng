package net.mcreator.geng.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.geng.network.GengModVariables;

public class BargainingProgressTxt2Procedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return Component.translatable("translation.key.info.just1").getString() + "" + (100000 - entity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress) / 1000 + Component.translatable("translation.key.info.text001").getString();
	}
}