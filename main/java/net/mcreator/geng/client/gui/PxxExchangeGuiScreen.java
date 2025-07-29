package net.mcreator.geng.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.geng.world.inventory.PxxExchangeGuiMenu;
import net.mcreator.geng.network.PxxExchangeGuiButtonMessage;
import net.mcreator.geng.init.GengModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class PxxExchangeGuiScreen extends AbstractContainerScreen<PxxExchangeGuiMenu> implements GengModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	Button button_invite_a_friend_and_exchange;
	Button button_no;

	public PxxExchangeGuiScreen(PxxExchangeGuiMenu container, Inventory inventory, Component text) {
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
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_exchange_gui.label_exchange"), 2, 8, -256, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_exchange_gui.label_20_diamonds"), 3, 30, -16711732, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_exchange_gui.label_but_it_costs_600_gold_coins"), 3, 42, -65536, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_exchange_gui.label_are_you_sure_about_the_exchange"), 2, 90, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_exchange_gui.label_you_need_to_invite_a_friend_to_e"), 2, 78, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_exchange_gui.label_please_place_the_gold_coins_in_t"), 3, 59, -1, false);
	}

	@Override
	public void init() {
		super.init();
		button_invite_a_friend_and_exchange = Button.builder(Component.translatable("gui.geng.pxx_exchange_gui.button_invite_a_friend_and_exchange"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new PxxExchangeGuiButtonMessage(0, x, y, z));
				PxxExchangeGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 10, this.topPos + 112, 155, 20).build();
		this.addRenderableWidget(button_invite_a_friend_and_exchange);
		button_no = Button.builder(Component.translatable("gui.geng.pxx_exchange_gui.button_no"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new PxxExchangeGuiButtonMessage(1, x, y, z));
				PxxExchangeGuiButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 10, this.topPos + 136, 155, 20).build();
		this.addRenderableWidget(button_no);
	}
}