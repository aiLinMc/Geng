package net.mcreator.geng.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.geng.network.GengModVariables;

public class ResourceDepletionBeginProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("geng:capital_smirk")), SoundSource.PLAYERS, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("geng:capital_smirk")), SoundSource.PLAYERS, 1, 1, false);
			}
		}
		if (entity instanceof ServerPlayer _player) {
			AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("geng:good_luck_over"));
			if (_adv != null) {
				AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
				if (!_ap.isDone()) {
					for (String criteria : _ap.getRemainingCriteria())
						_player.getAdvancements().award(_adv, criteria);
				}
			}
		}
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.num6 = 1;
			_vars.syncPlayerVariables(entity);
		}
	}
}