package net.mcreator.geng.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.geng.world.inventory.CTPGuiMenu;
import net.mcreator.geng.network.CTPGuiButtonMessage;
import net.mcreator.geng.init.GengModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class CTPGuiScreen extends AbstractContainerScreen<CTPGuiMenu> implements GengModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	Button button_268;
	Button button_468;
	Button button_768;
	Button button_968;
	Button button_i_wont_buy_it_anymore;

	public CTPGuiScreen(CTPGuiMenu container, Inventory inventory, Component text) {
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
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.ctp_gui.label_ticket_stall"), 22, 22, -256, false);
	}

	@Override
	public void init() {
		super.init();
		button_268 = Button.builder(Component.translatable("gui.geng.ctp_gui.button_268"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new CTPGuiButtonMessage(0, x, y, z));
				CTPGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 22, this.topPos + 42, 61, 20).build();
		this.addRenderableWidget(button_268);
		button_468 = Button.builder(Component.translatable("gui.geng.ctp_gui.button_468"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new CTPGuiButtonMessage(1, x, y, z));
				CTPGuiButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 91, this.topPos + 42, 61, 20).build();
		this.addRenderableWidget(button_468);
		button_768 = Button.builder(Component.translatable("gui.geng.ctp_gui.button_768"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new CTPGuiButtonMessage(2, x, y, z));
				CTPGuiButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 22, this.topPos + 68, 61, 20).build();
		this.addRenderableWidget(button_768);
		button_968 = Button.builder(Component.translatable("gui.geng.ctp_gui.button_968"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new CTPGuiButtonMessage(3, x, y, z));
				CTPGuiButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 91, this.topPos + 68, 61, 20).build();
		this.addRenderableWidget(button_968);
		button_i_wont_buy_it_anymore = Button.builder(Component.translatable("gui.geng.ctp_gui.button_i_wont_buy_it_anymore"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new CTPGuiButtonMessage(4, x, y, z));
				CTPGuiButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		}).bounds(this.leftPos + 21, this.topPos + 111, 131, 20).build();
		this.addRenderableWidget(button_i_wont_buy_it_anymore);
	}
}