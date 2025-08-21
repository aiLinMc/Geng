package net.mcreator.geng.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.mcreator.geng.world.inventory.PolarBearLocationMenu;
import net.mcreator.geng.network.PolarBearLocationButtonMessage;
import net.mcreator.geng.init.GengModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class PolarBearLocationScreen extends AbstractContainerScreen<PolarBearLocationMenu> implements GengModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	EditBox location_x;
	EditBox location_y;
	EditBox location_z;
	Button button_ok;

	public PolarBearLocationScreen(PolarBearLocationMenu container, Inventory inventory, Component text) {
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
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("location_x"))
				location_x.setValue(stringState);
			else if (name.equals("location_y"))
				location_y.setValue(stringState);
			else if (name.equals("location_z"))
				location_z.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		location_x.render(guiGraphics, mouseX, mouseY, partialTicks);
		location_y.render(guiGraphics, mouseX, mouseY, partialTicks);
		location_z.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (location_x.isFocused())
			return location_x.keyPressed(key, b, c);
		if (location_y.isFocused())
			return location_y.keyPressed(key, b, c);
		if (location_z.isFocused())
			return location_z.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String location_xValue = location_x.getValue();
		String location_yValue = location_y.getValue();
		String location_zValue = location_z.getValue();
		super.resize(minecraft, width, height);
		location_x.setValue(location_xValue);
		location_y.setValue(location_yValue);
		location_z.setValue(location_zValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.polar_bear_location.label_polar_bear_location"), 15, 33, -256, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.polar_bear_location.label_x"), 14, 62, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.polar_bear_location.label_y"), 14, 84, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.polar_bear_location.label_z"), 14, 106, -1, false);
	}

	@Override
	public void init() {
		super.init();
		location_x = new EditBox(this.font, this.leftPos + 29, this.topPos + 58, 118, 18, Component.translatable("gui.geng.polar_bear_location.location_x"));
		location_x.setMaxLength(8192);
		location_x.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "location_x", content, false);
		});
		location_x.setHint(Component.translatable("gui.geng.polar_bear_location.location_x"));
		this.addWidget(this.location_x);
		location_y = new EditBox(this.font, this.leftPos + 29, this.topPos + 81, 118, 18, Component.translatable("gui.geng.polar_bear_location.location_y"));
		location_y.setMaxLength(8192);
		location_y.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "location_y", content, false);
		});
		location_y.setHint(Component.translatable("gui.geng.polar_bear_location.location_y"));
		this.addWidget(this.location_y);
		location_z = new EditBox(this.font, this.leftPos + 29, this.topPos + 104, 118, 18, Component.translatable("gui.geng.polar_bear_location.location_z"));
		location_z.setMaxLength(8192);
		location_z.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "location_z", content, false);
		});
		location_z.setHint(Component.translatable("gui.geng.polar_bear_location.location_z"));
		this.addWidget(this.location_z);
		button_ok = Button.builder(Component.translatable("gui.geng.polar_bear_location.button_ok"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new PolarBearLocationButtonMessage(0, x, y, z));
				PolarBearLocationButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 28, this.topPos + 135, 49, 20).build();
		this.addRenderableWidget(button_ok);
	}
}