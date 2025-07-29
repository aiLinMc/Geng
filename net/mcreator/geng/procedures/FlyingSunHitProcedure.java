package net.mcreator.geng.procedures;

import net.minecraft.world.entity.Entity;

public class FlyingSunHitProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.igniteForSeconds(15);
	}
}