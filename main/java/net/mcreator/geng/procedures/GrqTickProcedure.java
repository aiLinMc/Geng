package net.mcreator.geng.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModMobEffects;

public class GrqTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double randint = 0;
		double tick = 0;
		if (Mth.nextInt(RandomSource.create(), 0, 96 - 12 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.GET_RICH_QUICK) ? _livEnt.getEffect(GengModMobEffects.GET_RICH_QUICK).getAmplifier() : 0)) == 1) {
			randint = Mth.nextInt(RandomSource.create(), 1, 13);
			if (randint == 1 || randint == 2) {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("give " + entity.getDisplayName().getString() + " diamond 1"));
			} else if (randint == 3 || randint == 4 || randint == 5) {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("give " + entity.getDisplayName().getString() + " iron_ingot 1"));
			} else if (randint == 6 || randint == 7 || randint == 8 || randint == 9) {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("give " + entity.getDisplayName().getString() + " lapis_lazuli 1"));
			} else if (randint == 10 || randint == 11 || randint == 12) {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("give " + entity.getDisplayName().getString() + " gold_ingot 1"));
			} else {
				if (Mth.nextInt(RandomSource.create(), 0, 28) == 28) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("give " + entity.getDisplayName().getString() + " netherite_scrap 3"));
				} else {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("give " + entity.getDisplayName().getString() + " gold_ingot 1"));
				}
			}
		}
		if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.GET_RICH_QUICK) ? _livEnt.getEffect(GengModMobEffects.GET_RICH_QUICK).getAmplifier() : 0) >= 3) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("execute as " + entity.getDisplayName().getString() + " at " + entity.getDisplayName().getString() + " run fill ~-3 ~-2 ~-3 ~3 ~2 ~3 mycelium replace grass_block"));
		}
		if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.GET_RICH_QUICK) ? _livEnt.getEffect(GengModMobEffects.GET_RICH_QUICK).getAmplifier() : 0) >= 6) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("execute as " + entity.getDisplayName().getString() + " at " + entity.getDisplayName().getString() + " run fill ~-6 ~-5 ~-6 ~6 ~5 ~6 mycelium replace grass_block"));
		}
		if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.GET_RICH_QUICK) ? _livEnt.getEffect(GengModMobEffects.GET_RICH_QUICK).getAmplifier() : 0) > 6) {
			tick = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.GET_RICH_QUICK) ? _livEnt.getEffect(GengModMobEffects.GET_RICH_QUICK).getDuration() : 0;
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(GengModMobEffects.GET_RICH_QUICK);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(GengModMobEffects.GET_RICH_QUICK, (int) tick, 6));
		}
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.num2 = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.GET_RICH_QUICK) ? _livEnt.getEffect(GengModMobEffects.GET_RICH_QUICK).getAmplifier() : 0;
			_vars.syncPlayerVariables(entity);
		}
	}
}