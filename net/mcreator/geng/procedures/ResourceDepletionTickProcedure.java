package net.mcreator.geng.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModMobEffects;

public class ResourceDepletionTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getDuration() : 0) < 60) {
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.num6 = 0;
				_vars.syncPlayerVariables(entity);
			}
		} else {
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.num6 = 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (Mth.nextInt(RandomSource.create(), 0, 720 - 20 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getAmplifier() : 0)) == 150) {
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem(), 400);
		}
		if (Mth.nextInt(RandomSource.create(), 0, 720 - 20 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getAmplifier() : 0)) == 150) {
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem(), 400);
		}
		if (Mth.nextInt(RandomSource.create(), 0, 640 - 16 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getAmplifier() : 0)) == 150) {
			for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 3, 6); index0++) {
				if (entity instanceof Player _player)
					_player.getCooldowns().addCooldown(
							(entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandler13 ? _modHandler13.getStackInSlot(Mth.nextInt(RandomSource.create(), 0, 35)).copy() : ItemStack.EMPTY).getItem(),
							400);
			}
		}
		if (Mth.nextInt(RandomSource.create(), 0, 350 - 8 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getAmplifier() : 0)) == 150) {
			for (int index1 = 0; index1 < Mth.nextInt(RandomSource.create(), 1, 3); index1++) {
				{
					GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.num4 = Math.random();
					_vars.syncPlayerVariables(entity);
				}
				while (entity.getData(GengModVariables.PLAYER_VARIABLES).num4 > 0.4 || entity.getData(GengModVariables.PLAYER_VARIABLES).num4 <= 0.01) {
					{
						GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
						_vars.num4 = Math.random();
						_vars.syncPlayerVariables(entity);
					}
				}
				{
					GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.num3 = Mth.nextInt(RandomSource.create(), 0, 35);
					_vars.syncPlayerVariables(entity);
				}
				if (!((entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandler19 ? _modHandler19.getStackInSlot((int) entity.getData(GengModVariables.PLAYER_VARIABLES).num3).copy() : ItemStack.EMPTY)
						.isDamageableItem())) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("/tellraw " + entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("translation.key.name.002").getString() + "\u00A7f> \u00A7e" + Component.translatable("translation.key.name.003").getString()
										+ "\""));
					if (entity instanceof Player _player) {
						ItemStack _stktoremove = (entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandler25
								? _modHandler25.getStackInSlot((int) entity.getData(GengModVariables.PLAYER_VARIABLES).num3).copy()
								: ItemStack.EMPTY);
						_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
					}
				} else {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("/tellraw " + entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("translation.key.name.002").getString() + "\u00A7f> \u00A7e" + Component.translatable("translation.key.name.004").getString()
										+ "\""));
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(
									new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(),
											_ent.level().getServer(), _ent),
									("execute as " + entity.getDisplayName().getString() + " run item modify entity @s container." + Math.round(entity.getData(GengModVariables.PLAYER_VARIABLES).num3) + " {\"function\":\"set_damage\",\"damage\":"
											+ entity.getData(GengModVariables.PLAYER_VARIABLES).num4 + "}"));
						}
					}
					break;
				}
			}
		}
		if (Mth.nextInt(RandomSource.create(), 0, 420 - 10 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getAmplifier() : 0)) == 150) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(), ("/tellraw "
						+ entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("translation.key.name.002").getString() + "\u00A7f> \u00A7e" + Component.translatable("translation.key.name.005").getString() + "\""));
			if (world instanceof ServerLevel _level) {
				LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
				entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(x, y, z)));;
				_level.addFreshEntity(entityToSpawn);
			}
		}
		if (Mth.nextInt(RandomSource.create(), 0, 500 - 12 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getAmplifier() : 0)) == 150) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(), ("/tellraw "
						+ entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("translation.key.name.002").getString() + "\u00A7f> \u00A7e" + Component.translatable("translation.key.name.008").getString() + "\""));
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 2, Level.ExplosionInteraction.NONE);
		}
		if (Mth.nextInt(RandomSource.create(), 0, 900 - 30 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getAmplifier() : 0)) == 150) {
			{
				GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.num5 = Mth.nextInt(RandomSource.create(), 1, 7);
				_vars.syncPlayerVariables(entity);
			}
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(), ("/tellraw "
						+ entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("translation.key.name.002").getString() + "\u00A7f> \u00A7e" + Component.translatable("translation.key.name.006").getString() + "\""));
			if (entity.getData(GengModVariables.PLAYER_VARIABLES).num5 == 1) {
				for (int index3 = 0; index3 < Mth.nextInt(RandomSource.create(), 5, 9); index3++) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = EntityType.HUSK.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @e[type=minecraft:husk,distance=..5] minecraft:glowing infinite");
						}
					}
				}
			} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).num5 == 2) {
				for (int index4 = 0; index4 < Mth.nextInt(RandomSource.create(), 1, 4); index4++) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = EntityType.CREEPER.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @e[type=minecraft:creeper,distance=..5] minecraft:glowing infinite");
						}
					}
				}
			} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).num5 == 3) {
				for (int index5 = 0; index5 < Mth.nextInt(RandomSource.create(), 2, 5); index5++) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "summon breeze");
						}
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @e[type=minecraft:breeze,distance=..5] minecraft:glowing infinite");
						}
					}
				}
			} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).num5 == 4) {
				for (int index6 = 0; index6 < Mth.nextInt(RandomSource.create(), 7, 14); index6++) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = EntityType.SILVERFISH.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @e[type=minecraft:silverfish,distance=..5] minecraft:glowing infinite");
						}
					}
				}
			} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).num5 == 5) {
				for (int index7 = 0; index7 < Mth.nextInt(RandomSource.create(), 3, 6); index7++) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = EntityType.SKELETON.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @e[type=minecraft:skeleton,distance=..5] minecraft:fire_resistance infinite");
						}
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @e[type=minecraft:skeleton,distance=..5] minecraft:glowing infinite");
						}
					}
				}
			} else if (entity.getData(GengModVariables.PLAYER_VARIABLES).num5 == 6) {
				for (int index8 = 0; index8 < Mth.nextInt(RandomSource.create(), 2, 4); index8++) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = EntityType.BLAZE.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @e[type=minecraft:blaze,distance=..5] minecraft:glowing infinite");
						}
					}
				}
			} else {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = EntityType.EVOKER.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @e[type=minecraft:evoker,distance=..5] minecraft:glowing infinite");
					}
				}
			}
		}
		if (Mth.nextInt(RandomSource.create(), 0, 530 - 30 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getAmplifier() : 0)) == 50) {
			for (int index9 = 0; index9 < 1440; index9++) {
				{
					GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.x01 = Mth.nextInt(RandomSource.create(), -4, 4);
					_vars.syncPlayerVariables(entity);
				}
				{
					GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.y01 = Mth.nextInt(RandomSource.create(), -4, 4);
					_vars.syncPlayerVariables(entity);
				}
				{
					GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.z01 = Mth.nextInt(RandomSource.create(), -4, 4);
					_vars.syncPlayerVariables(entity);
				}
				if (itemFromBlockInventory(world, BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01), 0)
						.getCount() != 0) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("/tellraw " + entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("translation.key.name.002").getString() + "\u00A7f> \u00A7e" + Component.translatable("translation.key.name.007").getString()
										+ "\""));
					if ((world.getBlockState(BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01)))
							.getBlock() == Blocks.HOPPER) {
						if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK,
								BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01),
								null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
							int _slotid = Mth.nextInt(RandomSource.create(), 0, 4);
							ItemStack _stk = _itemHandlerModifiable.getStackInSlot(_slotid).copy();
							_stk.shrink(Mth.nextInt(RandomSource.create(), 2, 6));
							_itemHandlerModifiable.setStackInSlot(_slotid, _stk);
						}
					} else if ((world.getBlockState(BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01)))
							.getBlock() == Blocks.SMOKER
							|| (world.getBlockState(BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01)))
									.getBlock() == Blocks.BLAST_FURNACE
							|| (world.getBlockState(BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01)))
									.getBlock() == Blocks.FURNACE) {
						if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK,
								BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01),
								null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
							int _slotid = Mth.nextInt(RandomSource.create(), 0, 2);
							ItemStack _stk = _itemHandlerModifiable.getStackInSlot(_slotid).copy();
							_stk.shrink(Mth.nextInt(RandomSource.create(), 2, 6));
							_itemHandlerModifiable.setStackInSlot(_slotid, _stk);
						}
					} else if ((world.getBlockState(BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01)))
							.getBlock() == Blocks.DISPENSER
							|| (world.getBlockState(BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01)))
									.getBlock() == Blocks.DROPPER) {
						if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK,
								BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01),
								null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
							int _slotid = Mth.nextInt(RandomSource.create(), 0, 8);
							ItemStack _stk = _itemHandlerModifiable.getStackInSlot(_slotid).copy();
							_stk.shrink(Mth.nextInt(RandomSource.create(), 2, 6));
							_itemHandlerModifiable.setStackInSlot(_slotid, _stk);
						}
					} else if ((world.getBlockState(BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01)))
							.getBlock() == Blocks.BARREL
							|| (world.getBlockState(BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01)))
									.getBlock() == Blocks.CHEST
							|| (world.getBlockState(BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01)))
									.getBlock() == Blocks.TRAPPED_CHEST
							|| (BuiltInRegistries.BLOCK.getKey((world
									.getBlockState(BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01)))
									.getBlock()).toString()).contains("box")
									&& (BuiltInRegistries.BLOCK.getKey((world.getBlockState(
											BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01)))
											.getBlock()).toString()).contains("shul")) {
						if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK,
								BlockPos.containing(x + entity.getData(GengModVariables.PLAYER_VARIABLES).x01, y + entity.getData(GengModVariables.PLAYER_VARIABLES).y01, z + entity.getData(GengModVariables.PLAYER_VARIABLES).z01),
								null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
							int _slotid = Mth.nextInt(RandomSource.create(), 0, 26);
							ItemStack _stk = _itemHandlerModifiable.getStackInSlot(_slotid).copy();
							_stk.shrink(Mth.nextInt(RandomSource.create(), 2, 6));
							_itemHandlerModifiable.setStackInSlot(_slotid, _stk);
						}
					}
					break;
				}
			}
		}
	}

	private static ItemStack itemFromBlockInventory(LevelAccessor world, BlockPos pos, int slot) {
		if (world instanceof ILevelExtension ext) {
			IItemHandler itemHandler = ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
			if (itemHandler != null)
				return itemHandler.getStackInSlot(slot);
		}
		return ItemStack.EMPTY;
	}
}