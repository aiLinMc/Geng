/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.geng.client.model.Modelheadgear;
import net.mcreator.geng.client.model.ModelBigStomach;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class GengModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelheadgear.LAYER_LOCATION, Modelheadgear::createBodyLayer);
		event.registerLayerDefinition(ModelBigStomach.LAYER_LOCATION, ModelBigStomach::createBodyLayer);
	}
}