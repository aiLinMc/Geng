package net.mcreator.geng.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;

import net.mcreator.geng.procedures.OilBottleUseProcedure;

public class OilBottleItem extends Item {
	public OilBottleItem() {
		super(new Item.Properties().stacksTo(1));
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
		return new ItemStack(Items.GLASS_BOTTLE);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		OilBottleUseProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getPlayer());
		return InteractionResult.SUCCESS;
	}
}