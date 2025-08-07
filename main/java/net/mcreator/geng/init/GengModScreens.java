/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.geng.client.gui.PxxExchangeGuiScreen;
import net.mcreator.geng.client.gui.PxxCraftScreen;
import net.mcreator.geng.client.gui.PinxiLotteryBoxGuiScreen;
import net.mcreator.geng.client.gui.MelonStallGuiScreen;
import net.mcreator.geng.client.gui.LieDetectorTruthScreen;
import net.mcreator.geng.client.gui.LieDetectorLieScreen;
import net.mcreator.geng.client.gui.HaveAChoiceScreen;
import net.mcreator.geng.client.gui.ChooseFriendGuiScreen;
import net.mcreator.geng.client.gui.CTPGuiScreen;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GengModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(GengModMenus.MELON_STALL_GUI.get(), MelonStallGuiScreen::new);
		event.register(GengModMenus.LIE_DETECTOR_TRUTH.get(), LieDetectorTruthScreen::new);
		event.register(GengModMenus.LIE_DETECTOR_LIE.get(), LieDetectorLieScreen::new);
		event.register(GengModMenus.HAVE_A_CHOICE.get(), HaveAChoiceScreen::new);
		event.register(GengModMenus.PINXI_LOTTERY_BOX_GUI.get(), PinxiLotteryBoxGuiScreen::new);
		event.register(GengModMenus.PXX_EXCHANGE_GUI.get(), PxxExchangeGuiScreen::new);
		event.register(GengModMenus.CHOOSE_FRIEND_GUI.get(), ChooseFriendGuiScreen::new);
		event.register(GengModMenus.PXX_CRAFT.get(), PxxCraftScreen::new);
		event.register(GengModMenus.CTP_GUI.get(), CTPGuiScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}