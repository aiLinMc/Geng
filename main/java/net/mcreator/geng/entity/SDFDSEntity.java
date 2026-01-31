package net.mcreator.geng.entity;

import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.geng.init.GengModBlocks;
import net.mcreator.geng.init.GengModItems;
import net.mcreator.geng.init.GengModEntities;
import net.mcreator.geng.init.GengModMobEffects;

import javax.annotation.Nullable;
import java.util.List;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import java.util.Collections;
import net.minecraft.world.entity.PathfinderMob;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class SDFDSEntity extends AbstractArrow implements ItemSupplier {
	public static final ItemStack PROJECTILE_ITEM = new ItemStack(GengModItems.DE_HUA.get());
	private int knockback = 0;
	private boolean hasHitBlock = false;
	private BlockPos lastHitPos = null;
	private int tickCounter = 0;
	private int checkCounter = 0;
	private boolean hasUnfrozenTag = false;
	private boolean isDroppingItem = false;
	private int effectCounter = 0;
	private int attractCooldown = 0;
	private int nextAttractInterval = 0;

	public SDFDSEntity(EntityType<? extends SDFDSEntity> type, Level world) {
		super(type, world);
		setNoGravity(true);
		this.setNoPhysics(true);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public SDFDSEntity(EntityType<? extends SDFDSEntity> type, double x, double y, double z, Level world, @Nullable ItemStack firedFromWeapon) {
		super(type, x, y, z, world, PROJECTILE_ITEM, firedFromWeapon);
		setNoGravity(true);
		this.setNoPhysics(true);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
		if (firedFromWeapon != null)
			setKnockback(EnchantmentHelper.getItemEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), firedFromWeapon));
	}

	public SDFDSEntity(EntityType<? extends SDFDSEntity> type, LivingEntity entity, Level world, @Nullable ItemStack firedFromWeapon) {
		super(type, entity, world, PROJECTILE_ITEM, firedFromWeapon);
		setNoGravity(true);
		this.setNoPhysics(true);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
		if (firedFromWeapon != null)
			setKnockback(EnchantmentHelper.getItemEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), firedFromWeapon));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return PROJECTILE_ITEM;
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return new ItemStack(GengModItems.DE_HUA.get());
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.setArrowCount(entity.getArrowCount() - 1);
	}

	public void setKnockback(int knockback) {
		this.knockback = knockback;
	}

	@Override
	protected void doKnockback(LivingEntity livingEntity, DamageSource damageSource) {
		if (knockback > 0.0) {
			double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
			Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(knockback * 0.6 * d1);
			if (vec3.lengthSqr() > 0.0) {
				livingEntity.push(vec3.x, 0.1, vec3.z);
			}
		} else {
			super.doKnockback(livingEntity, damageSource);
		}
	}

	@Nullable
	@Override
	protected EntityHitResult findHitEntity(Vec3 projectilePosition, Vec3 deltaPosition) {
		double d0 = Double.MAX_VALUE;
		Entity entity = null;
		AABB lookupBox = this.getBoundingBox();
		for (Entity entity1 : this.level().getEntities(this, lookupBox, this::canHitEntity)) {
			if (entity1 == this.getOwner())
				continue;
			AABB aabb = entity1.getBoundingBox();
			if (aabb.intersects(lookupBox)) {
				double d1 = projectilePosition.distanceToSqr(projectilePosition);
				if (d1 < d0) {
					entity = entity1;
					d0 = d1;
				}
			}
		}
		return entity == null ? null : new EntityHitResult(entity);
	}

	private Direction determineHitDirection(AABB entityBox, AABB blockBox) {
		double dx = entityBox.getCenter().x - blockBox.getCenter().x;
		double dy = entityBox.getCenter().y - blockBox.getCenter().y;
		double dz = entityBox.getCenter().z - blockBox.getCenter().z;
		double absDx = Math.abs(dx);
		double absDy = Math.abs(dy);
		double absDz = Math.abs(dz);
		if (absDy > absDx && absDy > absDz) {
			return dy > 0 ? Direction.DOWN : Direction.UP;
		} else if (absDx > absDz) {
			return dx > 0 ? Direction.WEST : Direction.EAST;
		} else {
			return dz > 0 ? Direction.NORTH : Direction.SOUTH;
		}
	}

	@Override
	public void tick() {
		super.tick();
		
		tickCounter++;
		
		if (tickCounter > 20) {
			tickCounter = 0;
			this.setNoGravity(true);
			this.setDeltaMovement(0, 0, 0);
		}
		
		this.setNoGravity(true);
		this.setDeltaMovement(0, 0, 0);
		
		if (!hasHitBlock) {
			BlockPos belowPos = new BlockPos((int)Math.floor(this.getX()), (int)Math.floor(this.getY() - 0.1), (int)Math.floor(this.getZ()));
			BlockState belowBlock = this.level().getBlockState(belowPos);
			
			if (belowBlock.is(Blocks.ICE)) {
				hasHitBlock = true;
				lastHitPos = belowPos;
				spawnDeHuaItemAndDiscard();
				return;
			}
			
			for (VoxelShape collision : this.level().getBlockCollisions(this, this.getBoundingBox())) {
				for (AABB blockAABB : collision.toAabbs()) {
					if (this.getBoundingBox().intersects(blockAABB)) {
						BlockPos blockPos = new BlockPos((int) blockAABB.minX, (int) blockAABB.minY, (int) blockAABB.minZ);
						
						if (lastHitPos == null || !lastHitPos.equals(blockPos)) {
							Vec3 intersectionPoint = new Vec3((blockAABB.minX + blockAABB.maxX) / 2, 
															 (blockAABB.minY + blockAABB.maxY) / 2, 
															 (blockAABB.minZ + blockAABB.maxZ) / 2);
							Direction hitDirection = determineHitDirection(this.getBoundingBox(), blockAABB);
							
							lastHitPos = blockPos;
							hasHitBlock = true;
							
							this.setDeltaMovement(Vec3.ZERO);
							this.setNoGravity(true);
							this.setNoPhysics(true);
							
							this.inGround = true;
							this.pickup = AbstractArrow.Pickup.DISALLOWED;
							
							this.setPos(intersectionPoint.x, intersectionPoint.y, intersectionPoint.z);
							
							this.onCustomHitBlock(new BlockHitResult(intersectionPoint, hitDirection, blockPos, false));
						}
					}
				}
			}
		}
		
		if (hasHitBlock) {
			this.setDeltaMovement(Vec3.ZERO);
			this.setNoGravity(true);
			this.setNoPhysics(true);
		}
		
		checkCounter++;
		if (checkCounter >= 5) {
			checkCounter = 0;
			checkAndRemoveIfNotSupported();
		}
		
		if (hasUnfrozenTag) {
			provideWishingYouProsperityEffect();
		}

		if (hasUnfrozenTag) {
			if (nextAttractInterval <= 0) {
				nextAttractInterval = 300 + this.random.nextInt(301);
			}

			attractCooldown++;

			if (attractCooldown >= nextAttractInterval) {
				attractCooldown = 0;
				nextAttractInterval = 300 + this.random.nextInt(301);

				if (!this.level().isClientSide()) {
					//attractNearbyMobs();
				}
			}
		}
	}
	
	private void provideWishingYouProsperityEffect() {
		if (this.level() == null || this.level().isClientSide()) {
			return;
		}
		
		effectCounter++;
		if (effectCounter < 20) {
			return;
		}
		effectCounter = 0;
		
		// 使用UUID来获取效果的Holder
		ResourceLocation effectLocation = BuiltInRegistries.MOB_EFFECT.getKey(GengModMobEffects.WISHING_YOU_PROSPERITY.get());
		if (effectLocation == null) {
			return;
		}
		
		Holder<net.minecraft.world.effect.MobEffect> effectHolder = this.level().registryAccess()
			.lookupOrThrow(Registries.MOB_EFFECT)
			.getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.MOB_EFFECT, effectLocation));
		
		MobEffectInstance effectInstance = new MobEffectInstance(
			effectHolder,
			6000,
			0,
			false,
			true,
			true
		);
		
		Vec3 center = this.position();
		AABB area = new AABB(
			center.x - 32, center.y - 32, center.z - 32,
			center.x + 32, center.y + 32, center.z + 32
		);
		
		List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, area);
		
		for (LivingEntity entity : entities) {
			// 使用UUID比较而不是对象引用比较
			if (entity.getUUID().equals(this.getUUID())) {
				continue;
			}
			
			if (entity.isAlive()) {
				// 检查生物是否已经有该效果
				MobEffectInstance existingEffect = entity.getEffect(effectHolder);
				boolean hasEffect = existingEffect != null;
				
				// 如果没有效果或者效果即将结束（小于60 tick），添加新效果
				if (!hasEffect || existingEffect.getDuration() < 60) {
					entity.addEffect(effectInstance);
				}
			}
		}
	}
	
	private void checkAndRemoveIfNotSupported() {
		if (this.level() == null || this.level().isClientSide()) {
			return;
		}
		
		BlockPos belowPos = new BlockPos((int)Math.floor(this.getX()), (int)Math.floor(this.getY() - 0.1), (int)Math.floor(this.getZ()));
		BlockState belowBlock = this.level().getBlockState(belowPos);
		
		if (belowBlock.is(Blocks.ICE)) {
			if (!hasHitBlock) {
				hasHitBlock = true;
				lastHitPos = belowPos;
			}
			spawnDeHuaItemAndDiscard();
			return;
		}
		
		BlockPos entityPos = new BlockPos((int)Math.floor(this.getX()), (int)Math.floor(this.getY()), (int)Math.floor(this.getZ()));
		BlockState blockState = this.level().getBlockState(entityPos);
		
		if (blockState.is(GengModBlocks.FROZEN_MR_HUA.get())) {
			checkAndSetUnfrozenTag(entityPos);
		} else {
			if (!hasUnfrozenTag) {
				this.discard();
			}
		}
	}
	
	private void checkAndSetUnfrozenTag(BlockPos blockPos) {
		if (this.level() == null || this.level().isClientSide() || hasUnfrozenTag) {
			return;
		}
		
		var blockEntity = this.level().getBlockEntity(blockPos);
		if (blockEntity != null) {
			CompoundTag nbt = blockEntity.getPersistentData();
			if (nbt.contains("UnfreezingProgress")) {
				double progress = nbt.getDouble("UnfreezingProgress");
				if (progress >= 9975) {
					CompoundTag entityTag = this.getPersistentData();
					entityTag.putBoolean("HasUnfrozenOnce", true);
					hasUnfrozenTag = true;
					
					entityTag.putDouble("UnfrozenAtProgress", progress);
					entityTag.putLong("UnfrozenTime", this.level().getGameTime());
					
					if (this.level() instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(
							net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER,
							this.getX(), this.getY() + 0.5, this.getZ(),
							10,
							0.5, 0.5, 0.5,
							0.1
						);
					}
				}
			}
		}
	}
	
	private void onCustomHitBlock(BlockHitResult hitResult) {
		BlockState blockState = this.level().getBlockState(hitResult.getBlockPos());
		if (blockState.is(Blocks.ICE)) {
			spawnDeHuaItemAndDiscard();
			return;
		}
		
		this.setPos(hitResult.getLocation());
		this.setDeltaMovement(Vec3.ZERO);
		this.setNoGravity(true);
		this.setNoPhysics(true);
		this.inGround = true;
		hasHitBlock = true;
		lastHitPos = hitResult.getBlockPos();
		
		if (blockState.is(GengModBlocks.FROZEN_MR_HUA.get())) {
			checkAndSetUnfrozenTag(hitResult.getBlockPos());
		} else {
			if (!hasUnfrozenTag) {
				this.discard();
			}
		}
	}
	
	private void spawnDeHuaItemAndDiscard() {
		if (isDroppingItem) {
			return;
		}
		
		isDroppingItem = true;
		
		if (!this.level().isClientSide()) {
			ItemEntity itemEntity = new ItemEntity(
				this.level(),
				this.getX(),
				this.getY(),
				this.getZ(),
				new ItemStack(GengModItems.DE_HUA.get())
			);
			
			itemEntity.setDeltaMovement(
				(this.random.nextDouble() - 0.5) * 0.1,
				this.random.nextDouble() * 0.1 + 0.1,
				(this.random.nextDouble() - 0.5) * 0.1
			);
			
			this.level().addFreshEntity(itemEntity);
			
			CompoundTag entityData = this.getPersistentData();
			String placedByUUID = "";
			
			if (entityData.contains("PlacedBy")) {
				placedByUUID = entityData.getString("PlacedBy");
			}
			
			if (!placedByUUID.isEmpty()) {
				try {
					java.util.UUID uuid = java.util.UUID.fromString(placedByUUID);
					Entity owner = this.level().getPlayerByUUID(uuid);
					if (owner instanceof ServerPlayer serverPlayer) {
						grantAdvancement(serverPlayer, "geng:refreeze");
					}
				} catch (IllegalArgumentException e) {
					// UUID格式错误，忽略
				}
			}
			
			Entity owner = this.getOwner();
			if (owner instanceof ServerPlayer serverPlayer) {
				grantAdvancement(serverPlayer, "geng:refreeze");
			}
		}
		
		this.discard();
	}

    private void attractNearbyMobs() {
        if (this.level().isClientSide()) {
			return;
        }
        
        Vec3 center = this.position();
        AABB searchArea = new AABB(
            center.x - 24, center.y - 6, center.z - 24,
            center.x + 24, center.y + 6, center.z + 24
        );

        List<net.minecraft.world.entity.npc.Villager> villagers = this.level().getEntitiesOfClass(
            net.minecraft.world.entity.npc.Villager.class, searchArea
        );
        List<net.minecraft.world.entity.monster.ZombifiedPiglin> piglins = this.level().getEntitiesOfClass(
            net.minecraft.world.entity.monster.ZombifiedPiglin.class, searchArea
        );

        List<net.minecraft.world.entity.PathfinderMob> allMobs = new java.util.ArrayList<>();
        allMobs.addAll(villagers);
        allMobs.addAll(piglins);

        if (allMobs.isEmpty()) {
            return;
        }

        java.util.Random shuffleRandom = new java.util.Random(this.random.nextLong());
        java.util.Collections.shuffle(allMobs, shuffleRandom);
        
        int mobsToAttract = Math.min(1 + this.random.nextInt(3), allMobs.size());
        
        for (int i = 0; i < mobsToAttract; i++) {
            net.minecraft.world.entity.PathfinderMob mob = allMobs.get(i);

            double targetX = this.getX() + (this.random.nextDouble() - 0.5) * 12.0;
            double targetZ = this.getZ() + (this.random.nextDouble() - 0.5) * 12.0;
            int targetY = this.level().getHeight(
                net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (int) targetX, (int) targetZ
            );

            net.minecraft.core.BlockPos targetPos = new net.minecraft.core.BlockPos(
                (int) targetX, targetY, (int) targetZ
            );

        	double speed;
			if (mob instanceof Villager) {
				speed = 0.68; // 村民移动速度
			} else if (mob instanceof ZombifiedPiglin) {
				speed = 1.12; // 僵尸猪灵移动速度
			} else {
				speed = 0.7; // 默认速度
			}
			
            mob.getNavigation().moveTo(
                targetPos.getX(),
                targetPos.getY(),
                targetPos.getZ(),
                speed
            );
        
            this.level().playSound(
                null,
                this.getX(), this.getY(), this.getZ(),
                net.minecraft.sounds.SoundEvents.VILLAGER_YES,
                net.minecraft.sounds.SoundSource.NEUTRAL,
                0.8f,
                1.0f + this.random.nextFloat() * 0.2f
            );
        }

        if (this.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(
                net.minecraft.core.particles.ParticleTypes.NOTE,
                this.getX(), this.getY() + 1.0, this.getZ(),
                5,
                0.5, 0.5, 0.5,
                0.0
            );
        }
    }
	
	private void grantAdvancement(ServerPlayer player, String advancementId) {
		if (player == null || player.level().isClientSide()) {
			return;
		}
		
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
	
	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		onCustomHitBlock(hitResult);
	}
	
	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		this.setDeltaMovement(Vec3.ZERO);
		this.setNoGravity(true);
		this.setNoPhysics(true);
		hasHitBlock = true;
		
		checkAndRemoveIfNotSupported();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (hasUnfrozenTag) {
			compound.putBoolean("HasUnfrozenOnce", true);
		}
		compound.putBoolean("hasHitBlock", hasHitBlock);
		compound.putBoolean("isDroppingItem", isDroppingItem);
		compound.putInt("effectCounter", effectCounter);
		if (lastHitPos != null) {
			compound.putInt("lastHitX", lastHitPos.getX());
			compound.putInt("lastHitY", lastHitPos.getY());
			compound.putInt("lastHitZ", lastHitPos.getZ());
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("HasUnfrozenOnce")) {
			hasUnfrozenTag = compound.getBoolean("HasUnfrozenOnce");
		}
		if (compound.contains("hasHitBlock")) {
			hasHitBlock = compound.getBoolean("hasHitBlock");
		}
		if (compound.contains("isDroppingItem")) {
			isDroppingItem = compound.getBoolean("isDroppingItem");
		}
		if (compound.contains("effectCounter")) {
			effectCounter = compound.getInt("effectCounter");
		}
		if (compound.contains("lastHitX") && compound.contains("lastHitY") && compound.contains("lastHitZ")) {
			lastHitPos = new BlockPos(
				compound.getInt("lastHitX"),
				compound.getInt("lastHitY"),
				compound.getInt("lastHitZ")
			);
		}
		
		if (compound.contains("PlacedBy")) {
			this.getPersistentData().putString("PlacedBy", compound.getString("PlacedBy"));
		}
	}

	public static SDFDSEntity shoot(Level world, LivingEntity entity, RandomSource source) {
		return shoot(world, entity, source, 0f, 0, 0);
	}

	public static SDFDSEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
		return shoot(world, entity, source, pullingPower * 0f, 0, 0);
	}

	public static SDFDSEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		SDFDSEntity entityarrow = new SDFDSEntity(GengModEntities.MR_HUA.get(), entity, world, null);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.dye.use")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static SDFDSEntity shoot(LivingEntity entity, LivingEntity target) {
		SDFDSEntity entityarrow = new SDFDSEntity(GengModEntities.MR_HUA.get(), entity, entity.level(), null);
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 0f * 2, 12.0F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(0);
		entityarrow.setKnockback(0);
		entityarrow.setCritArrow(false);
		entity.level().addFreshEntity(entityarrow);
		entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.dye.use")), SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
}