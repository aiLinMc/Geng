package net.mcreator.geng.item;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

public class ElegantPeopleItem extends Item {
	public ElegantPeopleItem() {
		super(new Item.Properties());
	}

	@Override
	public float getDestroySpeed(ItemStack itemstack, BlockState state) {
		return 4f;
	}
}