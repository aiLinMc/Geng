package net.mcreator.geng.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.geng.init.GengModBlocks;

public class OrientalPearlTVTowerPillarSetblockProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		boolean SetBlock = false;
		if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == GengModBlocks.ORIENTAL_PEARL_TV_TOWER_BASE_LOWER_SPHERE.get()) {
			SetBlock = true;
		} else {
			SetBlock = false;
		}
		return SetBlock;
	}
}