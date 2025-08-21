package net.mcreator.geng.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.chat.Component;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.GengMod;

public class HaveAChoiceCloseProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		GengMod.queueServerWork(1, () -> {
			if (entity.getData(GengModVariables.PLAYER_VARIABLES).choose == false && entity.getData(GengModVariables.PLAYER_VARIABLES).InChoiceGui == false) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("chat.geng.lie_detector.do_not_make_choice").getString())), false);
				entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.MOB_ATTACK), entity, entity), 6);
			}
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.InChoiceGui = false;
				_vars.syncPlayerVariables(entity);
			}
		});
	}
}