package net.mcreator.geng.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.geng.network.GengModVariables;

public class DaodaosMotherTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.ddm_sound_tick = entity.getData(GengModVariables.PLAYER_VARIABLES).ddm_sound_tick + 1;
			_vars.syncPlayerVariables(entity);
		}
		if (entity.getData(GengModVariables.PLAYER_VARIABLES).ddm_sound_tick > 41) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("geng:daodaos_mother")), SoundSource.VOICE, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("geng:daodaos_mother")), SoundSource.VOICE, 1, 1, false);
				}
			}
		}
		if (entity.getData(GengModVariables.PLAYER_VARIABLES).ddm_sound_tick == 45) {
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.ddm_sound_tick = 0;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}