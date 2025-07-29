package net.mcreator.geng.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModMobEffects;
import net.mcreator.geng.GengMod;

public class GetRichQuickOverProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		GengMod.queueServerWork(4, () -> {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(GengModMobEffects.RESOURCE_DEPLETION, 3600, (int) entity.getData(GengModVariables.PLAYER_VARIABLES).num2));
		});
	}
}