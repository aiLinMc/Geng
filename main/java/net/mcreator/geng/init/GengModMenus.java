/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import net.mcreator.geng.world.inventory.PxxExchangeGuiMenu;
import net.mcreator.geng.world.inventory.PxxCraftMenu;
import net.mcreator.geng.world.inventory.PinxiLotteryBoxGuiMenu;
import net.mcreator.geng.world.inventory.MelonStallGuiMenu;
import net.mcreator.geng.world.inventory.LieDetectorTruthMenu;
import net.mcreator.geng.world.inventory.LieDetectorLieMenu;
import net.mcreator.geng.world.inventory.HaveAChoiceMenu;
import net.mcreator.geng.world.inventory.ChooseFriendGuiMenu;
import net.mcreator.geng.network.MenuStateUpdateMessage;
import net.mcreator.geng.GengMod;

import java.util.Map;

public class GengModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, GengMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<MelonStallGuiMenu>> MELON_STALL_GUI = REGISTRY.register("melon_stall_gui", () -> IMenuTypeExtension.create(MelonStallGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<LieDetectorTruthMenu>> LIE_DETECTOR_TRUTH = REGISTRY.register("lie_detector_truth", () -> IMenuTypeExtension.create(LieDetectorTruthMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<LieDetectorLieMenu>> LIE_DETECTOR_LIE = REGISTRY.register("lie_detector_lie", () -> IMenuTypeExtension.create(LieDetectorLieMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<HaveAChoiceMenu>> HAVE_A_CHOICE = REGISTRY.register("have_a_choice", () -> IMenuTypeExtension.create(HaveAChoiceMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<PinxiLotteryBoxGuiMenu>> PINXI_LOTTERY_BOX_GUI = REGISTRY.register("pinxi_lottery_box_gui", () -> IMenuTypeExtension.create(PinxiLotteryBoxGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<PxxExchangeGuiMenu>> PXX_EXCHANGE_GUI = REGISTRY.register("pxx_exchange_gui", () -> IMenuTypeExtension.create(PxxExchangeGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<ChooseFriendGuiMenu>> CHOOSE_FRIEND_GUI = REGISTRY.register("choose_friend_gui", () -> IMenuTypeExtension.create(ChooseFriendGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<PxxCraftMenu>> PXX_CRAFT = REGISTRY.register("pxx_craft", () -> IMenuTypeExtension.create(PxxCraftMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide) {
				if (Minecraft.getInstance().screen instanceof GengModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				PacketDistributor.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
			}
		}

		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}