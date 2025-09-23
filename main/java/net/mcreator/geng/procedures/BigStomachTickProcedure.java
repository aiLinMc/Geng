package net.mcreator.geng.procedures;

import virtuoel.pehkui.api.ScaleTypes;
import virtuoel.pehkui.api.ScaleOperations;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModItems;
import net.mcreator.geng.GengMod;

public class BigStomachTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		ScaleTypes.HITBOX_WIDTH.getScaleData(entity).setTargetScale((float) ScaleOperations.SET.applyAsDouble(ScaleTypes.HITBOX_WIDTH.getScaleData(entity).getTargetScale(), (ScaleTypes.WIDTH.getScaleData(entity).getTargetScale() * 1.7)));
		if (entity instanceof ServerPlayer _player) {
			AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("geng:taste_clan"));
			if (_adv != null) {
				AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
				if (!_ap.isDone()) {
					for (String criteria : _ap.getRemainingCriteria())
						_player.getAdvancements().award(_adv, criteria);
				}
			}
		}
		entity.fallDistance = 0;
		GengMod.queueServerWork(2, () -> {
			if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == GengModItems.BIG_STOMACH_CHESTPLATE.get())) {
				ScaleTypes.HITBOX_WIDTH.getScaleData(entity).setTargetScale((float) ScaleOperations.SET.applyAsDouble(ScaleTypes.HITBOX_WIDTH.getScaleData(entity).getTargetScale(), ScaleTypes.WIDTH.getScaleData(entity).getTargetScale()));
				if (entity instanceof Player _player)
					_player.getFoodData().setFoodLevel(0);
				if (entity instanceof Player _player)
					_player.getFoodData().setSaturation(0);
			}
		});
		if (!entity.onGround()) {
			if (entity.getDeltaMovement().y() < entity.getData(GengModVariables.PLAYER_VARIABLES).max_y_speed) {
				{
					GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.max_y_speed = entity.getDeltaMovement().y();
					_vars.syncPlayerVariables(entity);
				}
			}
		}
		if (entity.getData(GengModVariables.PLAYER_VARIABLES).max_y_speed > -0.08) {
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.max_y_speed = 0;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.onGround()) {
			entity.push(0, Math.abs(entity.getData(GengModVariables.PLAYER_VARIABLES).max_y_speed), 0);
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.max_y_speed = entity.getData(GengModVariables.PLAYER_VARIABLES).max_y_speed / 1.48;
				_vars.syncPlayerVariables(entity);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.big_fall")), SoundSource.NEUTRAL,
							(float) Math.abs(entity.getData(GengModVariables.PLAYER_VARIABLES).max_y_speed), 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.big_fall")), SoundSource.NEUTRAL, (float) Math.abs(entity.getData(GengModVariables.PLAYER_VARIABLES).max_y_speed), 1, false);
				}
			}
		}
		if (Mth.nextInt(RandomSource.create(), 1, 45) == 10) {
			if (entity instanceof Player _player)
				_player.causeFoodExhaustion(1);
		}
	}
}