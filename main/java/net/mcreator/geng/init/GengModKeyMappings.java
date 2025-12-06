/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import org.lwjgl.glfw.GLFW;

import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.mcreator.geng.network.ElegantPeopleDanceMessage;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class GengModKeyMappings {
	public static final KeyMapping ELEGANT_PEOPLE_DANCE = new KeyMapping("key.geng.elegant_people_dance", GLFW.GLFW_KEY_G, "key.categories.geng") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				ELEGANT_PEOPLE_DANCE_LASTPRESS = System.currentTimeMillis();
			} else if (isDownOld != isDown && !isDown) {
				int dt = (int) (System.currentTimeMillis() - ELEGANT_PEOPLE_DANCE_LASTPRESS);
				PacketDistributor.sendToServer(new ElegantPeopleDanceMessage(1, dt));
				ElegantPeopleDanceMessage.pressAction(Minecraft.getInstance().player, 1, dt);
			}
			isDownOld = isDown;
		}
	};
	private static long ELEGANT_PEOPLE_DANCE_LASTPRESS = 0;

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(ELEGANT_PEOPLE_DANCE);
	}

	@EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(ClientTickEvent.Post event) {
			if (Minecraft.getInstance().screen == null) {
				ELEGANT_PEOPLE_DANCE.consumeClick();
			}
		}
	}
}