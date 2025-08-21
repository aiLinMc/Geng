package net.mcreator.geng.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.geng.init.GengModMobEffects;

public class EatTcocProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.ender_dragon.growl")), SoundSource.PLAYERS, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.ender_dragon.growl")), SoundSource.PLAYERS, 1, 1, false);
			}
		}
		if (world instanceof ServerLevel _level)
			_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
					("tellraw " + entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("chat.geng.capital").getString() + "\u00A7f> \u00A7e" + Component.translatable("chat.geng.capital.eat_cake").getString() + "\""));
		if (entity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(GengModMobEffects.GET_RICH_QUICK)) {
			if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.GET_RICH_QUICK) ? _livEnt.getEffect(GengModMobEffects.GET_RICH_QUICK).getAmplifier() : 0) < 6) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(GengModMobEffects.GET_RICH_QUICK, 3600,
							(entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.GET_RICH_QUICK) ? _livEnt.getEffect(GengModMobEffects.GET_RICH_QUICK).getAmplifier() : 0) + 1));
			} else {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(GengModMobEffects.GET_RICH_QUICK, 3600, 6));
			}
		} else {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(GengModMobEffects.GET_RICH_QUICK, 3600, 0));
		}
		if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.GET_RICH_QUICK) ? _livEnt.getEffect(GengModMobEffects.GET_RICH_QUICK).getAmplifier() : 0) == 6) {
			if (entity instanceof ServerPlayer _player) {
				AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("geng:capital_win"));
				if (_adv != null) {
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
			}
		}
	}
}