package net.mcreator.geng.procedures;

import net.neoforged.neoforge.event.entity.living.AnimalTameEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.geng.world.inventory.HaveAChoiceMenu;
import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModGameRules;

import javax.annotation.Nullable;

import io.netty.buffer.Unpooled;

@EventBusSubscriber
public class LieDetectorEvent06Procedure {
	@SubscribeEvent
	public static void onEntityTamed(AnimalTameEvent event) {
		execute(event, event.getAnimal().level(), event.getAnimal().getX(), event.getAnimal().getY(), event.getAnimal().getZ(), event.getTamer());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		execute(null, world, x, y, z, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		if (world.getLevelData().getGameRules().getBoolean(GengModGameRules.LIE_DETECTOR_MODE)) {
			{
				GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
				_vars.txt02 = Component.translatable("gui.geng.lie_detector.choice.06").getString();
				_vars.syncPlayerVariables(sourceentity);
			}
			if (sourceentity instanceof ServerPlayer _ent) {
				BlockPos _bpos = BlockPos.containing(x, y, z);
				_ent.openMenu(new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return Component.literal("HaveAChoice");
					}

					@Override
					public boolean shouldTriggerClientSideContainerClosingOnOpen() {
						return false;
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						return new HaveAChoiceMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
					}
				}, _bpos);
			}
		}
	}
}