package net.mcreator.geng.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.geng.init.GengModMenus;

public class MelonStallGui02Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (getAmountInGUISlot(entity, 1) == 5) {
			if (entity instanceof Player _player && _player.containerMenu instanceof GengModMenus.MenuAccessor _menu) {
				_menu.getSlots().get(0).remove(2);
				_player.containerMenu.broadcastChanges();
			}
		} else if (getAmountInGUISlot(entity, 1) == 4) {
			if (entity instanceof Player _player && _player.containerMenu instanceof GengModMenus.MenuAccessor _menu) {
				_menu.getSlots().get(0).remove(3);
				_player.containerMenu.broadcastChanges();
			}
		} else if (getAmountInGUISlot(entity, 1) == 1) {
			if (entity instanceof Player _player && _player.containerMenu instanceof GengModMenus.MenuAccessor _menu) {
				_menu.getSlots().get(0).remove(24);
				_player.containerMenu.broadcastChanges();
			}
		}
	}

	private static int getAmountInGUISlot(Entity entity, int sltid) {
		if (entity instanceof Player player && player.containerMenu instanceof GengModMenus.MenuAccessor menuAccessor) {
			ItemStack stack = menuAccessor.getSlots().get(sltid).getItem();
			if (stack != null)
				return stack.getCount();
		}
		return 0;
	}
}