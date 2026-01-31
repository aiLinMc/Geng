/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;

import net.mcreator.geng.block.PinxixiLotteryBoxBlock;
import net.mcreator.geng.block.OrientalpearltvtowerbaselowersphereBlock;
import net.mcreator.geng.block.OrientalPearlTVTowerTopBlock;
import net.mcreator.geng.block.OrientalPearlTVTowerPillarBlock;
import net.mcreator.geng.block.OrientalPearlTVTowerCoreBlock;
import net.mcreator.geng.block.OrientalPearlTVTowerBaseBlock;
import net.mcreator.geng.block.MelonStallBlock;
import net.mcreator.geng.block.FrozenMrHuaBlock;
import net.mcreator.geng.block.DeskBlock;
import net.mcreator.geng.GengMod;

public class GengModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(GengMod.MODID);
	public static final DeferredBlock<Block> MELON_STALL = REGISTRY.register("melon_stall", MelonStallBlock::new);
	public static final DeferredBlock<Block> PINXIXI_LOTTERY_BOX = REGISTRY.register("pinxixi_lottery_box", PinxixiLotteryBoxBlock::new);
	public static final DeferredBlock<Block> ORIENTAL_PEARL_TV_TOWER_BASE = REGISTRY.register("oriental_pearl_tv_tower_base", OrientalPearlTVTowerBaseBlock::new);
	public static final DeferredBlock<Block> ORIENTAL_PEARL_TV_TOWER_BASE_LOWER_SPHERE = REGISTRY.register("oriental_pearl_tv_tower_base_lower_sphere", OrientalpearltvtowerbaselowersphereBlock::new);
	public static final DeferredBlock<Block> ORIENTAL_PEARL_TV_TOWER_PILLAR = REGISTRY.register("oriental_pearl_tv_tower_pillar", OrientalPearlTVTowerPillarBlock::new);
	public static final DeferredBlock<Block> ORIENTAL_PEARL_TV_TOWER_CORE = REGISTRY.register("oriental_pearl_tv_tower_core", OrientalPearlTVTowerCoreBlock::new);
	public static final DeferredBlock<Block> ORIENTAL_PEARL_TV_TOWER_TOP = REGISTRY.register("oriental_pearl_tv_tower_top", OrientalPearlTVTowerTopBlock::new);
	public static final DeferredBlock<Block> DESK = REGISTRY.register("desk", DeskBlock::new);
	public static final DeferredBlock<Block> FROZEN_MR_HUA = REGISTRY.register("frozen_mr_hua", FrozenMrHuaBlock::new);
	// Start of user code block custom blocks
	// End of user code block custom blocks
}