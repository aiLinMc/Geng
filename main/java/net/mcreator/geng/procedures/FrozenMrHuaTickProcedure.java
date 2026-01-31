package net.mcreator.geng.procedures;

import virtuoel.pehkui.api.ScaleTypes;
import virtuoel.pehkui.api.ScaleOperations;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Comparator;

public class FrozenMrHuaTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double Add = 0;
		double biome = 0;
		double light = 0;
		String IncrementSpeed = "";
		biome = (world.getBiome(BlockPos.containing(x, y, z)).value().getBaseTemperature() * 100f) / 320d;
		light = (world.getBrightness(LightLayer.BLOCK, BlockPos.containing(x, y, z)) - 7.5) / 60;
		Add = 0.25 + biome + light;
		if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Blocks.CAMPFIRE) {
			Add = 0.75 + Add;
		} else if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Blocks.SOUL_CAMPFIRE) {
			Add = 0.9 + Add;
		} else if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Blocks.LAVA_CAULDRON) {
			Add = 1.1 + Add;
		}
		if (!world.isClientSide()) {
			BlockPos _bp = BlockPos.containing(x, y, z);
			BlockEntity _blockEntity = world.getBlockEntity(_bp);
			BlockState _bs = world.getBlockState(_bp);
			if (_blockEntity != null)
				_blockEntity.getPersistentData().putDouble("UnfreezingProgress", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "UnfreezingProgress") + Add));
			if (world instanceof Level _level)
				_level.sendBlockUpdated(_bp, _bs, _bs, 3);
		}
		if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "UnfreezingProgress") > 8000) {
			{
				int _value = 3;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		} else if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "UnfreezingProgress") > 5000) {
			{
				int _value = 2;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		} else if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "UnfreezingProgress") > 2000) {
			{
				int _value = 1;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		}
		IncrementSpeed = Math.round(Add * 100) / 500d + "%/s";
		if (!world.isClientSide()) {
			BlockPos _bp = BlockPos.containing(x, y, z);
			BlockEntity _blockEntity = world.getBlockEntity(_bp);
			BlockState _bs = world.getBlockState(_bp);
			if (_blockEntity != null)
				_blockEntity.getPersistentData().putString("IncrementSpeed", IncrementSpeed);
			if (world instanceof Level _level)
				_level.sendBlockUpdated(_bp, _bs, _bs, 3);
		}
		if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "UnfreezingProgress") >= 10000) {
			String placedByUUID = "";
			BlockPos _bp = BlockPos.containing(x, y, z);
			BlockEntity _blockEntity = world.getBlockEntity(_bp);
			if (_blockEntity != null) {
				placedByUUID = _blockEntity.getPersistentData().getString("PlacedBy");
			}
			
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.glass.break")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.glass.break")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
			
			if (!placedByUUID.isEmpty() && world instanceof ServerLevel serverLevel) {
				Player placedBy = serverLevel.getPlayerByUUID(java.util.UUID.fromString(placedByUUID));
				if (placedBy instanceof ServerPlayer serverPlayer) {
					grantAdvancement(serverPlayer, "geng:unfreeze");
				}
			}
		}
		//{
		//	final Vec3 _center = new Vec3((x + 0.5), (y + 0.5), (z + 0.5));
		//	for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(1 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
		//		if ((BuiltInRegistries.ENTITY_TYPE.getKey(entityiterator.getType()).toString()).equals("geng:mr_hua")) {
		//			ScaleTypes.WIDTH.getScaleData(entityiterator).setTargetScale((float) ScaleOperations.SET.applyAsDouble(ScaleTypes.WIDTH.getScaleData(entityiterator).getTargetScale(), 1.6));
		//			ScaleTypes.HEIGHT.getScaleData(entityiterator).setTargetScale((float) ScaleOperations.SET.applyAsDouble(ScaleTypes.HEIGHT.getScaleData(entityiterator).getTargetScale(), 1.6));
		//		}
		//	}
		//}
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDouble(tag);
		return -1;
	}
	
	// 修复了进度授予方法
	private static void grantAdvancement(ServerPlayer player, String advancementId) {
		if (player == null || player.level().isClientSide()) {
			return;
		}
		
		// NeoForge使用AdvancementHolder而不是Advancement
		AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.parse(advancementId));
		if (advancement != null) {
			AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
			if (!progress.isDone()) {
				for (String criterion : progress.getRemainingCriteria()) {
					player.getAdvancements().award(advancement, criterion);
				}
			}
		}
	}
}