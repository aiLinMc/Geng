package net.mcreator.geng.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.geng.world.inventory.PinxiLotteryBoxGuiMenu;
import net.mcreator.geng.network.PinxiLotteryBoxGuiButtonMessage;
import net.mcreator.geng.init.GengModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class PinxiLotteryBoxGuiScreen extends AbstractContainerScreen<PinxiLotteryBoxGuiMenu> implements GengModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	Button button_raffle;
	Button button_craft;
	Button button_exchange;

	public PinxiLotteryBoxGuiScreen(PinxiLotteryBoxGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public boolean isPauseScreen() {
		return true;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pinxi_lottery_box_gui.label_please_select_the_operation_you"), 15, 33, -256, false);
	}

	@Override
	public void init() {
		super.init();
		button_raffle = Button.builder(Component.translatable("gui.geng.pinxi_lottery_box_gui.button_raffle"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new PinxiLotteryBoxGuiButtonMessage(0, x, y, z));
				PinxiLotteryBoxGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 19, this.topPos + 89, 138, 20).build();
		this.addRenderableWidget(button_raffle);
		button_craft = Button.builder(Component.translatable("gui.geng.pinxi_lottery_box_gui.button_craft"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new PinxiLotteryBoxGuiButtonMessage(1, x, y, z));
				PinxiLotteryBoxGuiButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 19, this.topPos + 63, 67, 20).build();
		this.addRenderableWidget(button_craft);
		button_exchange = Button.builder(Component.translatable("gui.geng.pinxi_lottery_box_gui.button_exchange"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new PinxiLotteryBoxGuiButtonMessage(2, x, y, z));
				PinxiLotteryBoxGuiButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 90, this.topPos + 63, 67, 20).build();
		this.addRenderableWidget(button_exchange);
	}
}