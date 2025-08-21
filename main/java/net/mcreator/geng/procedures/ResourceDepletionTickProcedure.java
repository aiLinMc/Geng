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
		double px = 0;
		double py = 0;
		double pz = 0;
		double randint_mob = 0;
		double randint_durability = 0;
		double randint_itemstack = 0;
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
				randint_durability = Math.random();
				while (randint_durability > 0.4 || randint_durability <= 0.01) {
					randint_durability = Math.random();
				}
				randint_itemstack = Mth.nextInt(RandomSource.create(), 0, 35);
				if (!((entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandler19 ? _modHandler19.getStackInSlot((int) randint_itemstack).copy() : ItemStack.EMPTY).isDamageableItem())) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("/tellraw " + entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("chat.geng.capital").getString() + "\u00A7f> \u00A7e"
										+ Component.translatable("chat.geng.capital.change_item_quantity").getString() + "\""));
					if (entity instanceof Player _player) {
						ItemStack _stktoremove = (entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandler25 ? _modHandler25.getStackInSlot((int) randint_itemstack).copy() : ItemStack.EMPTY);
						_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
					}
				} else {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("/tellraw " + entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("chat.geng.capital").getString() + "\u00A7f> \u00A7e"
										+ Component.translatable("chat.geng.capital.change_item_durability_value").getString() + "\""));
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(
									new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(),
											_ent.level().getServer(), _ent),
									("execute as " + entity.getDisplayName().getString() + " run item modify entity @s container." + randint_itemstack + " {\"function\":\"set_damage\",\"damage\":" + randint_durability + "}"));
						}
					}
					break;
				}
			}
		}
		if (Mth.nextInt(RandomSource.create(), 0, 420 - 10 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getAmplifier() : 0)) == 150) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("/tellraw " + entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("chat.geng.capital").getString() + "\u00A7f> \u00A7e" + Component.translatable("chat.geng.capital.thunder").getString() + "\""));
			if (world instanceof ServerLevel _level) {
				LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
				entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(x, y, z)));;
				_level.addFreshEntity(entityToSpawn);
			}
		}
		if (Mth.nextInt(RandomSource.create(), 0, 500 - 12 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getAmplifier() : 0)) == 150) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("/tellraw " + entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("chat.geng.capital").getString() + "\u00A7f> \u00A7e" + Component.translatable("chat.geng.capital.tnt").getString() + "\""));
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 2, Level.ExplosionInteraction.NONE);
		}
		if (Mth.nextInt(RandomSource.create(), 0, 900 - 30 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(GengModMobEffects.RESOURCE_DEPLETION) ? _livEnt.getEffect(GengModMobEffects.RESOURCE_DEPLETION).getAmplifier() : 0)) == 150) {
			randint_mob = Mth.nextInt(RandomSource.create(), 1, 7);
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("/tellraw " + entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("chat.geng.capital").getString() + "\u00A7f> \u00A7e" + Component.translatable("chat.geng.capital.mob_summon").getString() + "\""));
			if (randint_mob == 1) {
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
			} else if (randint_mob == 2) {
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
			} else if (randint_mob == 3) {
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
			} else if (randint_mob == 4) {
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
			} else if (randint_mob == 5) {
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
			} else if (randint_mob == 6) {
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
				px = Mth.nextInt(RandomSource.create(), -4, 4);
				py = Mth.nextInt(RandomSource.create(), -4, 4);
				pz = Mth.nextInt(RandomSource.create(), -4, 4);
				if (itemFromBlockInventory(world, BlockPos.containing(x + px, y + py, z + pz), 0).getCount() != 0) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("/tellraw " + entity.getDisplayName().getString() + " \"<\u00A74" + Component.translatable("chat.geng.capital").getString() + "\u00A7f> \u00A7e" + Component.translatable("chat.geng.capital.box_items").getString()
										+ "\""));
					if ((world.getBlockState(BlockPos.containing(x + px, y + py, z + pz))).getBlock() == Blocks.HOPPER) {
						if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x + px, y + py, z + pz), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
							int _slotid = Mth.nextInt(RandomSource.create(), 0, 4);
							ItemStack _stk = _itemHandlerModifiable.getStackInSlot(_slotid).copy();
							_stk.shrink(Mth.nextInt(RandomSource.create(), 2, 6));
							_itemHandlerModifiable.setStackInSlot(_slotid, _stk);
						}
					} else if ((world.getBlockState(BlockPos.containing(x + px, y + py, z + pz))).getBlock() == Blocks.SMOKER || (world.getBlockState(BlockPos.containing(x + px, y + py, z + pz))).getBlock() == Blocks.BLAST_FURNACE
							|| (world.getBlockState(BlockPos.containing(x + px, y + py, z + pz))).getBlock() == Blocks.FURNACE) {
						if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x + px, y + py, z + pz), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
							int _slotid = Mth.nextInt(RandomSource.create(), 0, 2);
							ItemStack _stk = _itemHandlerModifiable.getStackInSlot(_slotid).copy();
							_stk.shrink(Mth.nextInt(RandomSource.create(), 2, 6));
							_itemHandlerModifiable.setStackInSlot(_slotid, _stk);
						}
					} else if ((world.getBlockState(BlockPos.containing(x + px, y + py, z + pz))).getBlock() == Blocks.DISPENSER || (world.getBlockState(BlockPos.containing(x + px, y + py, z + pz))).getBlock() == Blocks.DROPPER) {
						if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x + px, y + py, z + pz), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
							int _slotid = Mth.nextInt(RandomSource.create(), 0, 8);
							ItemStack _stk = _itemHandlerModifiable.getStackInSlot(_slotid).copy();
							_stk.shrink(Mth.nextInt(RandomSource.create(), 2, 6));
							_itemHandlerModifiable.setStackInSlot(_slotid, _stk);
						}
					} else if ((world.getBlockState(BlockPos.containing(x + px, y + py, z + pz))).getBlock() == Blocks.BARREL || (world.getBlockState(BlockPos.containing(x + px, y + py, z + pz))).getBlock() == Blocks.CHEST
							|| (world.getBlockState(BlockPos.containing(x + px, y + py, z + pz))).getBlock() == Blocks.TRAPPED_CHEST
							|| (BuiltInRegistries.BLOCK.getKey((world.getBlockState(BlockPos.containing(x + px, y + py, z + pz))).getBlock()).toString()).contains("box")
									&& (BuiltInRegistries.BLOCK.getKey((world.getBlockState(BlockPos.containing(x + px, y + py, z + pz))).getBlock()).toString()).contains("shul")) {
						if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x + px, y + py, z + pz), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
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