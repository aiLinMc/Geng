/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.geng.item.ZiErItem;
import net.mcreator.geng.item.TheCakeOfCapitalItem;
import net.mcreator.geng.item.SunItem;
import net.mcreator.geng.item.SlicedMelonItem;
import net.mcreator.geng.item.SilverDollarItem;
import net.mcreator.geng.item.PutinItem;
import net.mcreator.geng.item.PolarBearSaddleItem;
import net.mcreator.geng.item.PinxixiMacheteItem;
import net.mcreator.geng.item.KnifeOfHuaqiangItem;
import net.mcreator.geng.item.HundredTonKingItem;
import net.mcreator.geng.item.HeadgearItem;
import net.mcreator.geng.item.GoldCoinItem;
import net.mcreator.geng.item.DeepseekItem;
import net.mcreator.geng.item.CrasherPlusItem;
import net.mcreator.geng.item.CrasherItem;
import net.mcreator.geng.item.CopperCoinItem;
import net.mcreator.geng.item.ConcertTicketPurchaseItem;
import net.mcreator.geng.item.BigStomachItem;
import net.mcreator.geng.item.A1Item;
import net.mcreator.geng.GengMod;

public class GengModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(GengMod.MODID);
	public static final DeferredItem<Item> HUNDRED_TON_KING = REGISTRY.register("hundred_ton_king", HundredTonKingItem::new);
	public static final DeferredItem<Item> THE_CAKE_OF_CAPITAL = REGISTRY.register("the_cake_of_capital", TheCakeOfCapitalItem::new);
	public static final DeferredItem<Item> A_1 = REGISTRY.register("a_1", A1Item::new);
	public static final DeferredItem<Item> DEEPSEEK = REGISTRY.register("deepseek", DeepseekItem::new);
	public static final DeferredItem<Item> KNIFE_OF_HUAQIANG = REGISTRY.register("knife_of_huaqiang", KnifeOfHuaqiangItem::new);
	public static final DeferredItem<Item> SLICED_MELON = REGISTRY.register("sliced_melon", SlicedMelonItem::new);
	public static final DeferredItem<Item> MELON_STALL = block(GengModBlocks.MELON_STALL);
	public static final DeferredItem<Item> SUN = REGISTRY.register("sun", SunItem::new);
	public static final DeferredItem<Item> PINXIXI_MACHETE = REGISTRY.register("pinxixi_machete", PinxixiMacheteItem::new);
	public static final DeferredItem<Item> GOLD_COIN = REGISTRY.register("gold_coin", GoldCoinItem::new);
	public static final DeferredItem<Item> PINXIXI_LOTTERY_BOX = block(GengModBlocks.PINXIXI_LOTTERY_BOX, new Item.Properties().rarity(Rarity.UNCOMMON));
	public static final DeferredItem<Item> SILVER_DOLLAR = REGISTRY.register("silver_dollar", SilverDollarItem::new);
	public static final DeferredItem<Item> COPPER_COIN = REGISTRY.register("copper_coin", CopperCoinItem::new);
	public static final DeferredItem<Item> PUTIN = REGISTRY.register("putin", PutinItem::new);
	public static final DeferredItem<Item> ORIENTAL_PEARL_TV_TOWER_BASE = block(GengModBlocks.ORIENTAL_PEARL_TV_TOWER_BASE);
	public static final DeferredItem<Item> ORIENTAL_PEARL_TV_TOWER_BASE_LOWER_SPHERE = block(GengModBlocks.ORIENTAL_PEARL_TV_TOWER_BASE_LOWER_SPHERE);
	public static final DeferredItem<Item> ORIENTAL_PEARL_TV_TOWER_PILLAR = block(GengModBlocks.ORIENTAL_PEARL_TV_TOWER_PILLAR);
	public static final DeferredItem<Item> ORIENTAL_PEARL_TV_TOWER_CORE = block(GengModBlocks.ORIENTAL_PEARL_TV_TOWER_CORE, new Item.Properties().rarity(Rarity.UNCOMMON));
	public static final DeferredItem<Item> ORIENTAL_PEARL_TV_TOWER_TOP = block(GengModBlocks.ORIENTAL_PEARL_TV_TOWER_TOP);
	public static final DeferredItem<Item> ZI_ER = REGISTRY.register("zi_er", ZiErItem::new);
	public static final DeferredItem<Item> HEADGEAR_HELMET = REGISTRY.register("headgear_helmet", HeadgearItem.Helmet::new);
	public static final DeferredItem<Item> CONCERT_TICKET_PURCHASE = REGISTRY.register("concert_ticket_purchase", ConcertTicketPurchaseItem::new);
	public static final DeferredItem<Item> DESK = block(GengModBlocks.DESK);
	public static final DeferredItem<Item> POLAR_BEAR_SADDLE = REGISTRY.register("polar_bear_saddle", PolarBearSaddleItem::new);
	public static final DeferredItem<Item> CRASHER = REGISTRY.register("crasher", CrasherItem::new);
	public static final DeferredItem<Item> CRASHER_PLUS = REGISTRY.register("crasher_plus", CrasherPlusItem::new);
	public static final DeferredItem<Item> BIG_STOMACH_CHESTPLATE = REGISTRY.register("big_stomach_chestplate", BigStomachItem.Chestplate::new);

	// Start of user code block custom items
	// End of user code block custom items
	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties));
	}
}