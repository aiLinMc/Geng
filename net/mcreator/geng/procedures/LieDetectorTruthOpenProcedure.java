package net.mcreator.geng.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.GengMod;

public class LieDetectorTruthOpenProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("geng:truth")), SoundSource.NEUTRAL, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("geng:truth")), SoundSource.NEUTRAL, 1, 1, false);
			}
		}
		GengMod.queueServerWork(4, () -> {
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.choose = false;
				_vars.syncPlayerVariables(entity);
			}
		});
	}
}