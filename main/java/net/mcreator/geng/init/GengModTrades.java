/*
*	MCreator note: This file will be REGENERATED on each build.
*/
package net.mcreator.geng.init;

import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

@EventBusSubscriber
public class GengModTrades {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		if (event.getType() == GengModVillagerProfessions.MELON_VENDOR.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2), new ItemStack(GengModItems.SLICED_MELON.get(), 5), 10, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 5), new ItemStack(Blocks.MELON), 14, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3), new ItemStack(Items.MELON_SLICE, 4), 15, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 10), new ItemStack(Items.GLISTERING_MELON_SLICE, 2), 8, 5, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 24), new ItemStack(GengModItems.KNIFE_OF_HUAQIANG.get()), 6, 5, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.IRON_SWORD), new ItemStack(Blocks.MELON), new ItemStack(GengModItems.KNIFE_OF_HUAQIANG.get()), 3, 5, 0.05f));
		}
	}
}