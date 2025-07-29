package net.mcreator.geng.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.geng.world.inventory.HaveAChoiceMenu;
import net.mcreator.geng.procedures.LieDetectorChoiceTxtProcedure;
import net.mcreator.geng.network.HaveAChoiceButtonMessage;
import net.mcreator.geng.init.GengModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class HaveAChoiceScreen extends AbstractContainerScreen<HaveAChoiceMenu> implements GengModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	Button button_yes;
	Button button_no;

	public HaveAChoiceScreen(HaveAChoiceMenu container, Inventory inventory, Component text) {
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
		guiGraphics.drawString(this.font, LieDetectorChoiceTxtProcedure.execute(entity), 4, 42, -256, false);
	}

	@Override
	public void init() {
		super.init();
		button_yes = Button.builder(Component.translatable("gui.geng.have_a_choice.button_yes"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new HaveAChoiceButtonMessage(0, x, y, z));
				HaveAChoiceButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 27, this.topPos + 80, 120, 20).build();
		this.addRenderableWidget(button_yes);
		button_no = Button.builder(Component.translatable("gui.geng.have_a_choice.button_no"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new HaveAChoiceButtonMessage(1, x, y, z));
				HaveAChoiceButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 27, this.topPos + 105, 120, 20).build();
		this.addRenderableWidget(button_no);
	}
}