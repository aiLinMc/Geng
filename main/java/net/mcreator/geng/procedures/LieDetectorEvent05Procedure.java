package net.mcreator.geng.procedures;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.MenuProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.geng.world.inventory.HaveAChoiceMenu;
import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModMobEffects;
import net.mcreator.geng.init.GengModGameRules;
import net.mcreator.geng.init.GengModBlocks;

import javax.annotation.Nullable;

import io.netty.buffer.Unpooled;

@EventBusSubscriber
public class LieDetectorEvent05Procedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (!(entity == sourceentity)) {
			if (world.getLevelData().getGameRules().getBoolean(GengModGameRules.LIE_DETECTOR_MODE)) {
				if (Mth.nextInt(RandomSource.create(), 1, 2) == 1) {
					{
						GengModVariables.PlayerVariables _vars = sourceentity.getData(GengModVariables.PLAYER_VARIABLES);
						_vars.txt02 = Component.translatable("gui.geng.lie_detector.choice.05").getString();
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
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == GengModBlocks.DESK.get().asItem()) {
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(
							Component.literal((((Component.translatable("chat.geng.broken_legs").getString()).replace("%1", entity.getDisplayName().getString())).replace("%2", sourceentity.getDisplayName().getString()))), false);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(GengModMobEffects.LEG_DISABILITY, 300, 0));
			}
		}
	}
}