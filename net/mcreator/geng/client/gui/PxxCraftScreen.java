package net.mcreator.geng.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.geng.world.inventory.PxxCraftMenu;
import net.mcreator.geng.network.PxxCraftButtonMessage;
import net.mcreator.geng.init.GengModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class PxxCraftScreen extends AbstractContainerScreen<PxxCraftMenu> implements GengModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	Button button_craft;
	Button button_craft1;

	public PxxCraftScreen(PxxCraftMenu container, Inventory inventory, Component text) {
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
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_craft.label_craft"), 2, 2, -256, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_craft.label_1_silver_dollar"), 3, 40, -16711732, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_craft.label_64_copper_coins_need_to_be_consu"), 3, 51, -65536, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_craft.label_1_gold_coin"), 3, 104, -16711732, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_craft.label_64_silver_dollars_need_to_be_con"), 3, 116, -65536, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.pxx_craft.label_please_place_consumables_in_the"), 3, 14, -1, false);
	}

	@Override
	public void init() {
		super.init();
		button_craft = Button.builder(Component.translatable("gui.geng.pxx_craft.button_craft"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new PxxCraftButtonMessage(0, x, y, z));
				PxxCraftButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 3, this.topPos + 62, 103, 20).build();
		this.addRenderableWidget(button_craft);
		button_craft1 = Button.builder(Component.translatable("gui.geng.pxx_craft.button_craft1"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new PxxCraftButtonMessage(1, x, y, z));
				PxxCraftButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 3, this.topPos + 128, 103, 20).build();
		this.addRenderableWidget(button_craft1);
	}
}