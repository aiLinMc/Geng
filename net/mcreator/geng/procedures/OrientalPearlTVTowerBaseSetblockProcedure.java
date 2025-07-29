package net.mcreator.geng.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class OrientalPearlTVTowerBaseSetblockProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		boolean SetBlock = false;
		if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Blocks.STONE) {
			SetBlock = true;
		} else {
			SetBlock = false;
		}
		return SetBlock;
	}
}