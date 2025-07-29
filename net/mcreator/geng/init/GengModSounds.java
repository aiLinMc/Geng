/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.geng.GengMod;

public class GengModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, GengMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> CAPITAL_SMIRK = REGISTRY.register("capital_smirk", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "capital_smirk")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CUT_MELON = REGISTRY.register("cut_melon", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "cut_melon")));
	public static final DeferredHolder<SoundEvent, SoundEvent> HIT_VILLAGER = REGISTRY.register("hit_villager", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "hit_villager")));
	public static final DeferredHolder<SoundEvent, SoundEvent> TRUTH = REGISTRY.register("truth", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "truth")));
	public static final DeferredHolder<SoundEvent, SoundEvent> LIE = REGISTRY.register("lie", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "lie")));
}