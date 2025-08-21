package net.mcreator.geng.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import net.mcreator.geng.world.inventory.PolarBearLocationMenu;
import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModItems;
import net.mcreator.geng.init.GengModAttributes;

import javax.annotation.Nullable;

import io.netty.buffer.Unpooled;

@EventBusSubscriber
public class PlayerRidePolarBeaeProcedure {
	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		if (event.getHand() != InteractionHand.MAIN_HAND)
			return;
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getTarget(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()).equals("minecraft:polar_bear")) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == GengModItems.POLAR_BEAR_SADDLE.get()
					&& (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(GengModAttributes.HAVE_SADDLE) ? _livingEntity3.getAttribute(GengModAttributes.HAVE_SADDLE).getValue() : 0) == 0) {
				if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(GengModAttributes.HAVE_SADDLE))
					_livingEntity4.getAttribute(GengModAttributes.HAVE_SADDLE).setBaseValue(1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.horse.saddle")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.horse.saddle")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				if (getEntityGameType(sourceentity) == GameType.SURVIVAL || getEntityGameType(entity) == GameType.ADVENTURE) {
					if (sourceentity instanceof Player _player) {
						ItemStack _stktoremove = new ItemStack(GengModItems.POLAR_BEAR_SADDLE.get());
						_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
					}
				}
			} else if ((entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(GengModAttributes.HAVE_SADDLE) ? _livingEntity9.getAttribute(GengModAttributes.HAVE_SADDLE).getValue() : 0) == 1) {
				sourceentity.startRiding(entity);
				{
					GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.polar_bear_x = x;
					_vars.syncPlayerVariables(sourceentity);
				}
				{
					GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.polar_bear_y = y;
					_vars.syncPlayerVariables(sourceentity);
				}
				{
					GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
					_vars.polar_bear_z = z;
					_vars.syncPlayerVariables(sourceentity);
				}
				if (sourceentity instanceof ServerPlayer _ent) {
					BlockPos _bpos = BlockPos.containing(x, y, z);
					_ent.openMenu(new MenuProvider() {
						@Override
						public Component getDisplayName() {
							return Component.literal("PolarBearLocation");
						}

						@Override
						public boolean shouldTriggerClientSideContainerClosingOnOpen() {
							return false;
						}

						@Override
						public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
							return new PolarBearLocationMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
						}
					}, _bpos);
				}
			}
		}
	}

	private static GameType getEntityGameType(Entity entity) {
		if (entity instanceof ServerPlayer serverPlayer) {
			return serverPlayer.gameMode.getGameModeForPlayer();
		} else if (entity instanceof Player player && player.level().isClientSide()) {
			PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
			if (playerInfo != null)
				return playerInfo.getGameMode();
		}
		return null;
	}
}