package net.mcreator.geng.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModItems;
import net.mcreator.geng.GengMod;

public class PxxRaffleProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player)
			_player.closeContainer();
		if (entity.getData(GengModVariables.PLAYER_VARIABLES).InLottery) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("chat.geng.pxx.cannot_draw").getString())), false);
		} else {
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.InLottery = true;
				_vars.syncPlayerVariables(entity);
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("chat.geng.pxx.lottery").getString())), false);
			GengMod.queueServerWork(40, () -> {
				if (Mth.nextInt(RandomSource.create(), 1, 3) == 1) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("chat.geng.pxx.lottery_none").getString())), false);
				} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).SilverDollarNum < 63) {
					{
						GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
						_vars.randomPxx = Mth.nextInt(RandomSource.create(), 1, 5);
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("chat.geng.pxx.lottery_success").getString())), false);
					if (entity.getData(GengModVariables.PLAYER_VARIABLES).randomPxx == 1 && entity.getData(GengModVariables.PLAYER_VARIABLES).SilverDollarNum < 39) {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "give @s geng:silver_dollar 25");
							}
						}
						{
							GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
							_vars.SilverDollarNum = entity.getData(GengModVariables.PLAYER_VARIABLES).SilverDollarNum + 25;
							_vars.syncPlayerVariables(entity);
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((new ItemStack(GengModItems.SILVER_DOLLAR.get()).getDisplayName().getString() + " x 25")), false);
					} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).randomPxx == 2 && entity.getData(GengModVariables.PLAYER_VARIABLES).SilverDollarNum < 46) {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "give @s geng:silver_dollar 18");
							}
						}
						{
							GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
							_vars.SilverDollarNum = entity.getData(GengModVariables.PLAYER_VARIABLES).SilverDollarNum + 18;
							_vars.syncPlayerVariables(entity);
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((new ItemStack(GengModItems.SILVER_DOLLAR.get()).getDisplayName().getString() + " x 18")), false);
					} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).randomPxx == 3 && entity.getData(GengModVariables.PLAYER_VARIABLES).SilverDollarNum < 54) {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "give @s geng:silver_dollar 10");
							}
						}
						{
							GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
							_vars.SilverDollarNum = entity.getData(GengModVariables.PLAYER_VARIABLES).SilverDollarNum + 10;
							_vars.syncPlayerVariables(entity);
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((new ItemStack(GengModItems.SILVER_DOLLAR.get()).getDisplayName().getString() + " x 10")), false);
					} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).randomPxx == 4 && entity.getData(GengModVariables.PLAYER_VARIABLES).SilverDollarNum < 61) {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "give @s geng:silver_dollar 3");
							}
						}
						{
							GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
							_vars.SilverDollarNum = entity.getData(GengModVariables.PLAYER_VARIABLES).SilverDollarNum + 3;
							_vars.syncPlayerVariables(entity);
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((new ItemStack(GengModItems.SILVER_DOLLAR.get()).getDisplayName().getString() + " x 3")), false);
					} else {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "give @s geng:silver_dollar 1");
							}
						}
						{
							GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
							_vars.SilverDollarNum = entity.getData(GengModVariables.PLAYER_VARIABLES).SilverDollarNum + 1;
							_vars.syncPlayerVariables(entity);
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((new ItemStack(GengModItems.SILVER_DOLLAR.get()).getDisplayName().getString() + " x 1")), false);
					}
				} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).CopperCoinNum < 63) {
					{
						GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
						_vars.randomPxx = Mth.nextInt(RandomSource.create(), 1, 5);
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("chat.geng.pxx.lottery_success").getString())), false);
					if (entity.getData(GengModVariables.PLAYER_VARIABLES).randomPxx == 1 && entity.getData(GengModVariables.PLAYER_VARIABLES).CopperCoinNum < 39) {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "give @s geng:copper_coin 25");
							}
						}
						{
							GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
							_vars.CopperCoinNum = entity.getData(GengModVariables.PLAYER_VARIABLES).CopperCoinNum + 25;
							_vars.syncPlayerVariables(entity);
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((new ItemStack(GengModItems.COPPER_COIN.get()).getDisplayName().getString() + " x 25")), false);
					} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).randomPxx == 2 && entity.getData(GengModVariables.PLAYER_VARIABLES).CopperCoinNum < 46) {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "give @s geng:copper_coin 18");
							}
						}
						{
							GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
							_vars.CopperCoinNum = entity.getData(GengModVariables.PLAYER_VARIABLES).CopperCoinNum + 18;
							_vars.syncPlayerVariables(entity);
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((new ItemStack(GengModItems.COPPER_COIN.get()).getDisplayName().getString() + " x 18")), false);
					} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).randomPxx == 3 && entity.getData(GengModVariables.PLAYER_VARIABLES).CopperCoinNum < 54) {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "give @s geng:copper_coin 10");
							}
						}
						{
							GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
							_vars.CopperCoinNum = entity.getData(GengModVariables.PLAYER_VARIABLES).CopperCoinNum + 10;
							_vars.syncPlayerVariables(entity);
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((new ItemStack(GengModItems.COPPER_COIN.get()).getDisplayName().getString() + " x 10")), false);
					} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).randomPxx == 4 && entity.getData(GengModVariables.PLAYER_VARIABLES).CopperCoinNum < 61) {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "give @s geng:copper_coin 3");
							}
						}
						{
							GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
							_vars.CopperCoinNum = entity.getData(GengModVariables.PLAYER_VARIABLES).CopperCoinNum + 3;
							_vars.syncPlayerVariables(entity);
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((new ItemStack(GengModItems.COPPER_COIN.get()).getDisplayName().getString() + " x 3")), false);
					} else {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "give @s geng:copper_coin 1");
							}
						}
						{
							GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
							_vars.CopperCoinNum = entity.getData(GengModVariables.PLAYER_VARIABLES).CopperCoinNum + 1;
							_vars.syncPlayerVariables(entity);
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((new ItemStack(GengModItems.COPPER_COIN.get()).getDisplayName().getString() + " x 1")), false);
					}
				} else {
					if (Mth.nextInt(RandomSource.create(), 1, 18) == 5 && entity.getData(GengModVariables.PLAYER_VARIABLES).SilverDollarNum == 63) {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "give @s geng:copper_coin 1");
							}
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((new ItemStack(GengModItems.COPPER_COIN.get()).getDisplayName().getString() + " x 1")), false);
						{
							GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
							_vars.SilverDollarNum = 64;
							_vars.syncPlayerVariables(entity);
						}
					} else {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((Component.translatable("chat.geng.pxx.lottery_none").getString())), false);
					}
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(" "), false);
				{
					GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.InLottery = false;
					_vars.syncPlayerVariables(entity);
				}
			});
		}
	}
}