package net.mcreator.geng.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.geng.init.GengModMenus;
import net.mcreator.geng.init.GengModItems;

public class MelonStallGui01Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (getAmountInGUISlot(entity, 0) == 2) {
			if (entity instanceof Player _player && _player.containerMenu instanceof GengModMenus.MenuAccessor _menu) {
				ItemStack _setstack = new ItemStack(GengModItems.SLICED_MELON.get()).copy();
				_setstack.setCount(5);
				_menu.getSlots().get(1).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
		} else if (getAmountInGUISlot(entity, 0) == 3) {
			if (entity instanceof Player _player && _player.containerMenu instanceof GengModMenus.MenuAccessor _menu) {
				ItemStack _setstack = new ItemStack(Items.MELON_SLICE).copy();
				_setstack.setCount(4);
				_menu.getSlots().get(1).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
		} else if (getAmountInGUISlot(entity, 0) == 24) {
			if (entity instanceof Player _player && _player.containerMenu instanceof GengModMenus.MenuAccessor _menu) {
				ItemStack _setstack = new ItemStack(GengModItems.KNIFE_OF_HUAQIANG.get()).copy();
				_setstack.setCount(1);
				_menu.getSlots().get(1).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof GengModMenus.MenuAccessor _menu) {
				ItemStack _setstack = new ItemStack(Blocks.AIR).copy();
				_setstack.setCount(1);
				_menu.getSlots().get(1).set(_setstack);
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