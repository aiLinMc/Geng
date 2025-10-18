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
	public static final DeferredHolder<SoundEvent, SoundEvent> HOW_MANY_ZIER = REGISTRY.register("how_many_zier", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "how_many_zier")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DAODAOS_MOTHER = REGISTRY.register("daodaos_mother", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "daodaos_mother")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WHAT_KIND_OF_PRESENCE_TO_BRUSH = REGISTRY.register("what_kind_of_presence_to_brush",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "what_kind_of_presence_to_brush")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WHY_SHOULD_TALK_TO_YOU = REGISTRY.register("why_should_talk_to_you", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "why_should_talk_to_you")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DUANG = REGISTRY.register("duang", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "duang")));
	public static final DeferredHolder<SoundEvent, SoundEvent> FADED = REGISTRY.register("faded", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "faded")));
	public static final DeferredHolder<SoundEvent, SoundEvent> ONESHOT = REGISTRY.register("oneshot", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "oneshot")));
	public static final DeferredHolder<SoundEvent, SoundEvent> BANGBANG = REGISTRY.register("bangbang", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("geng", "bangbang")));
}