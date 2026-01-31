/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.registries.Registries;

import net.mcreator.geng.procedures.RussianProportionOverProcedure;
import net.mcreator.geng.procedures.ResourceDepletionOverProcedure;
import net.mcreator.geng.procedures.GetRichQuickOverProcedure;
import net.mcreator.geng.potion.WishingYouProsperityMobEffect;
import net.mcreator.geng.potion.WeightGainMobEffect;
import net.mcreator.geng.potion.VillagerAngerMobEffect;
import net.mcreator.geng.potion.TheGraceOfTheSnowKingMobEffect;
import net.mcreator.geng.potion.TastingMobEffect;
import net.mcreator.geng.potion.RussianProportionMobEffect;
import net.mcreator.geng.potion.ResourceDepletionMobEffect;
import net.mcreator.geng.potion.RawMelonEggsMobEffect;
import net.mcreator.geng.potion.LegDisabilityMobEffect;
import net.mcreator.geng.potion.KindnessMobEffect;
import net.mcreator.geng.potion.GetRichQuickMobEffect;
import net.mcreator.geng.potion.DaodaosMotherMobEffect;
import net.mcreator.geng.GengMod;

@EventBusSubscriber
public class GengModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT, GengMod.MODID);
	public static final DeferredHolder<MobEffect, MobEffect> WEIGHT_GAIN = REGISTRY.register("weight_gain", () -> new WeightGainMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> GET_RICH_QUICK = REGISTRY.register("get_rich_quick", () -> new GetRichQuickMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> RESOURCE_DEPLETION = REGISTRY.register("resource_depletion", () -> new ResourceDepletionMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> RAW_MELON_EGGS = REGISTRY.register("raw_melon_eggs", () -> new RawMelonEggsMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> VILLAGER_ANGER = REGISTRY.register("villager_anger", () -> new VillagerAngerMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> KINDNESS = REGISTRY.register("kindness", () -> new KindnessMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> RUSSIAN_PROPORTION = REGISTRY.register("russian_proportion", () -> new RussianProportionMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> THE_GRACE_OF_THE_SNOW_KING = REGISTRY.register("the_grace_of_the_snow_king", () -> new TheGraceOfTheSnowKingMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> DAODAOS_MOTHER = REGISTRY.register("daodaos_mother", () -> new DaodaosMotherMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> LEG_DISABILITY = REGISTRY.register("leg_disability", () -> new LegDisabilityMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> TASTING = REGISTRY.register("tasting", () -> new TastingMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> WISHING_YOU_PROSPERITY = REGISTRY.register("wishing_you_prosperity", () -> new WishingYouProsperityMobEffect());

	@SubscribeEvent
	public static void onEffectRemoved(MobEffectEvent.Remove event) {
		MobEffectInstance effectInstance = event.getEffectInstance();
		if (effectInstance != null) {
			expireEffects(event.getEntity(), effectInstance);
		}
	}

	@SubscribeEvent
	public static void onEffectExpired(MobEffectEvent.Expired event) {
		MobEffectInstance effectInstance = event.getEffectInstance();
		if (effectInstance != null) {
			expireEffects(event.getEntity(), effectInstance);
		}
	}

	private static void expireEffects(Entity entity, MobEffectInstance effectInstance) {
		if (effectInstance.getEffect().is(GET_RICH_QUICK)) {
			GetRichQuickOverProcedure.execute(entity.level(), entity);
		} else if (effectInstance.getEffect().is(RESOURCE_DEPLETION)) {
			ResourceDepletionOverProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		} else if (effectInstance.getEffect().is(RUSSIAN_PROPORTION)) {
			RussianProportionOverProcedure.execute(entity);
		}
	}
}