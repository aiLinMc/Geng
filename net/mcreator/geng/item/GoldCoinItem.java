package net.mcreator.geng.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class GoldCoinItem extends Item {
	public GoldCoinItem() {
		super(new Item.Properties().rarity(Rarity.EPIC));
	}
}