/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.geng.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.geng.GengMod;

import java.util.stream.Collectors;
import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GengModAttributes {
	public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, GengMod.MODID);
	public static final DeferredHolder<Attribute, Attribute> HAVE_SADDLE = REGISTRY.register("have_saddle", () -> new RangedAttribute("attribute.geng.have_saddle", 0, 0, 1).setSyncable(true));

	@SubscribeEvent
	public static void addAttributes(EntityAttributeModificationEvent event) {
		List.of(EntityType.POLAR_BEAR).stream().filter(DefaultAttributes::hasSupplier).map(entityType -> (EntityType<? extends LivingEntity>) entityType).collect(Collectors.toList()).forEach(entity -> event.add(entity, HAVE_SADDLE));
	}
}