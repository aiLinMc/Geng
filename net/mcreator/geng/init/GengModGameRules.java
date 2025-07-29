/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.GameRules;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GengModGameRules {
	public static GameRules.Key<GameRules.BooleanValue> LIE_DETECTOR_MODE;

	@SubscribeEvent
	public static void registerGameRules(FMLCommonSetupEvent event) {
		LIE_DETECTOR_MODE = GameRules.register("lieDetectorMode", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
	}
}