/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.registries.Registries;

import net.mcreator.geng.GengMod;

public class GengModPotions {
	public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(Registries.POTION, GengMod.MODID);
	public static final DeferredHolder<Potion, Potion> WEIGHT_GAIN_P = REGISTRY.register("weight_gain_p", () -> new Potion(new MobEffectInstance(GengModMobEffects.WEIGHT_GAIN, 6000, 0, false, true)));
	public static final DeferredHolder<Potion, Potion> KINDNESS_P = REGISTRY.register("kindness_p", () -> new Potion(new MobEffectInstance(GengModMobEffects.KINDNESS, 1200, 0, false, true)));
	public static final DeferredHolder<Potion, Potion> RUSSIAN_PROPORTION_P = REGISTRY.register("russian_proportion_p", () -> new Potion(new MobEffectInstance(GengModMobEffects.RUSSIAN_PROPORTION, 2400, 0, false, true)));
	public static final DeferredHolder<Potion, Potion> MIXUE_DRINK = REGISTRY.register("mixue_drink", () -> new Potion(new MobEffectInstance(GengModMobEffects.THE_GRACE_OF_THE_SNOW_KING, 3600, 0, false, true)));
	public static final DeferredHolder<Potion, Potion> TASTING_IN_PROGRESS = REGISTRY.register("tasting_in_progress", () -> new Potion(new MobEffectInstance(GengModMobEffects.TASTING, 3600, 0, false, true)));
}