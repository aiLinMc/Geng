package net.mcreator.geng.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.commands.arguments.EntityAnchorArgument;

public class RawMelonEggsTickProcedure {
	public static void execute(double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((x + Mth.nextDouble(RandomSource.create(), -2, 2)), (y + Mth.nextDouble(RandomSource.create(), -2, 2)), (z + Mth.nextDouble(RandomSource.create(), -2, 2))));
	}
}