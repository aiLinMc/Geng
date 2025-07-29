package net.mcreator.geng.client.screens;

import org.checkerframework.checker.units.qual.h;

import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;

import net.mcreator.geng.procedures.PinxixiMacheteInHandProcedure;
import net.mcreator.geng.procedures.BargainingProgressTxtProcedure;
import net.mcreator.geng.procedures.BargainingProgressTxt2Procedure;

@EventBusSubscriber({Dist.CLIENT})
public class BargainProgressDisplayOverlay {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();
		Level world = null;
		double x = 0;
		double y = 0;
		double z = 0;
		Player entity = Minecraft.getInstance().player;
		if (entity != null) {
			world = entity.level();
			x = entity.getX();
			y = entity.getY();
			z = entity.getZ();
		}
		if (PinxixiMacheteInHandProcedure.execute(entity)) {
			if (PinxixiMacheteInHandProcedure.execute(entity))
				event.getGuiGraphics().drawString(Minecraft.getInstance().font,

						BargainingProgressTxtProcedure.execute(entity), w / 2 + 9, h / 2 + 5, -65536, false);
			if (PinxixiMacheteInHandProcedure.execute(entity))
				event.getGuiGraphics().drawString(Minecraft.getInstance().font,

						BargainingProgressTxt2Procedure.execute(entity), w / 2 + 9, h / 2 + 19, -65536, false);
		}
	}
}