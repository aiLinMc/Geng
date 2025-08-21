package net.mcreator.geng.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.geng.network.GengModVariables;

public class BargainingProgressTxt2Procedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return (Component.translatable("gui.geng.pxx.machete.just").getString()).replace("{value}", "" + (100000 - entity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress) / 1000);
	}
}