package net.mcreator.geng.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModItems;

public class PinxixiMacheteHitProcedure {
	public static void execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		{
			GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.PxxHitNum = sourceentity.getData(GengModVariables.PLAYER_VARIABLES).PxxHitNum + 1;
			_vars.syncPlayerVariables(sourceentity);
		}
		if (sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress < 40000) {
			{
				GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.bargaining_progress = sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress + 20000;
				_vars.syncPlayerVariables(sourceentity);
			}
		} else if (sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress < 70000) {
			{
				GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.bargaining_progress = sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress + 10000;
				_vars.syncPlayerVariables(sourceentity);
			}
		} else if (sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress < 85000) {
			{
				GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.bargaining_progress = sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress + 5000;
				_vars.syncPlayerVariables(sourceentity);
			}
		} else if (sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress < 92000) {
			{
				GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.bargaining_progress = sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress + 2000;
				_vars.syncPlayerVariables(sourceentity);
			}
		} else if (sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress < 99000) {
			{
				GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.bargaining_progress = sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress + 1000;
				_vars.syncPlayerVariables(sourceentity);
			}
		} else if (sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress < 99900) {
			{
				GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.bargaining_progress = sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress + 100;
				_vars.syncPlayerVariables(sourceentity);
			}
		} else if (sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress < 99990) {
			{
				GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.bargaining_progress = sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress + 10;
				_vars.syncPlayerVariables(sourceentity);
			}
		} else if (sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress < 99999) {
			{
				GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.bargaining_progress = sourceentity.getData(GengModVariables.PLAYER_VARIABLES).bargaining_progress + 1;
				_vars.syncPlayerVariables(sourceentity);
			}
		} else {
			if (Mth.nextInt(RandomSource.create(), 1, 5) == 3 && !sourceentity.getData(GengModVariables.PLAYER_VARIABLES).AlreadyGetCoins && !hasEntityInInventory(sourceentity, new ItemStack(GengModItems.GOLD_COIN.get()))) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("chat.geng.pxx.machete_success").getString())), false);
				{
					GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.AlreadyGetCoins = true;
					_vars.syncPlayerVariables(sourceentity);
				}
				if (sourceentity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(GengModItems.GOLD_COIN.get()).copy();
					_setstack.setCount(63);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				{
					GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.bargaining_progress = 0;
					_vars.syncPlayerVariables(sourceentity);
				}
			} else if (Mth.nextInt(RandomSource.create(), 1, 6) == 2) {
				{
					GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.bargaining_progress = 0;
					_vars.syncPlayerVariables(sourceentity);
				}
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("chat.geng.pxx.network_problem").getString())), false);
			} else {
				{
					GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.bargaining_progress = 90000;
					_vars.syncPlayerVariables(sourceentity);
				}
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("chat.geng.pxx.network_slow").getString())), false);
			}
		}
		if (sourceentity.getData(GengModVariables.PLAYER_VARIABLES).PxxHitNum >= 300) {
			if (entity instanceof ServerPlayer _player) {
				AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("geng:pxx_attack_300_times"));
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

	private static boolean hasEntityInInventory(Entity entity, ItemStack itemstack) {
		if (entity instanceof Player player)
			return player.getInventory().contains(stack -> !stack.isEmpty() && ItemStack.isSameItem(stack, itemstack));
		return false;
	}
}