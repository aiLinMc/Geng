/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import net.mcreator.geng.GengMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GengModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GengMod.MODID);

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			tabData.accept(GengModItems.HUNDRED_TON_KING.get());
			tabData.accept(GengModItems.SUN.get());
			tabData.accept(GengModItems.GOLD_COIN.get());
			tabData.accept(GengModItems.SILVER_DOLLAR.get());
			tabData.accept(GengModItems.COPPER_COIN.get());
			tabData.accept(GengModItems.ZI_ER.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
			tabData.accept(GengModItems.THE_CAKE_OF_CAPITAL.get());
			tabData.accept(GengModItems.SLICED_MELON.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			tabData.accept(GengModItems.DEEPSEEK.get());
			tabData.accept(GengModItems.PUTIN.get());
			tabData.accept(GengModItems.CONCERT_TICKET_PURCHASE.get());
			tabData.accept(GengModItems.POLAR_BEAR_SADDLE.get());
			tabData.accept(GengModItems.CRASHER.get());
			tabData.accept(GengModItems.CRASHER_PLUS.get());
			tabData.accept(GengModItems.BIG_STOMACH_CHESTPLATE.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.COMBAT) {
			tabData.accept(GengModItems.KNIFE_OF_HUAQIANG.get());
			tabData.accept(GengModItems.SUN.get());
			tabData.accept(GengModItems.PINXIXI_MACHETE.get());
			tabData.accept(GengModItems.ZI_ER.get());
			tabData.accept(GengModItems.HEADGEAR_HELMET.get());
			tabData.accept(GengModItems.JIAHAO_HELMET.get());
			tabData.accept(GengModItems.JIAHAO_CHESTPLATE.get());
			tabData.accept(GengModItems.JIAHAO_LEGGINGS.get());
			tabData.accept(GengModItems.JIAHAO_BOOTS.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			tabData.accept(GengModBlocks.MELON_STALL.get().asItem());
			tabData.accept(GengModBlocks.PINXIXI_LOTTERY_BOX.get().asItem());
			tabData.accept(GengModBlocks.DESK.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			tabData.accept(GengModBlocks.ORIENTAL_PEARL_TV_TOWER_BASE.get().asItem());
			tabData.accept(GengModBlocks.ORIENTAL_PEARL_TV_TOWER_BASE_LOWER_SPHERE.get().asItem());
			tabData.accept(GengModBlocks.ORIENTAL_PEARL_TV_TOWER_PILLAR.get().asItem());
			tabData.accept(GengModBlocks.ORIENTAL_PEARL_TV_TOWER_CORE.get().asItem());
			tabData.accept(GengModBlocks.ORIENTAL_PEARL_TV_TOWER_TOP.get().asItem());
			tabData.accept(GengModBlocks.DESK.get().asItem());
		}
	}
}