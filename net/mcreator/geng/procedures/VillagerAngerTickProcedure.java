package net.mcreator.geng.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.GengMod;

public class VillagerAngerTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()).equals("minecraft:villager")) {
			if (Mth.nextInt(RandomSource.create(), 0, 1000) == 0) {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							"summon minecraft:iron_golem ~ ~1 ~ {Health:5,Tags:[\"angry_golem\"]}");
				GengMod.queueServerWork(2, () -> {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"execute as @e[type=iron_golem,tag=angry_golem] run damage @s 0 minecraft:player_attack by @p");
				});
			}
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						"execute as @e[type=iron_golem,tag=angry_golem] run data modify entity @s AngerTime set value 20");
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("execute as @e[type=iron_golem,tag=angry_golem] run data modify entity @s AngryAt set value " + entity.getData(GengModVariables.PLAYER_VARIABLES).uuid01));
		}
	}
}