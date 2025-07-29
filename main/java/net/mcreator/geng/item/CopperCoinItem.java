package net.mcreator.geng.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class CopperCoinItem extends Item {
	public CopperCoinItem() {
		super(new Item.Properties().rarity(Rarity.RARE));
	}
}