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

import net.mcreator.geng.world.inventory.ChooseFriendGuiMenu;
import net.mcreator.geng.network.ChooseFriendGuiButtonMessage;
import net.mcreator.geng.init.GengModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class ChooseFriendGuiScreen extends AbstractContainerScreen<ChooseFriendGuiMenu> implements GengModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	EditBox FriendName;
	Button button_send;

	public ChooseFriendGuiScreen(ChooseFriendGuiMenu container, Inventory inventory, Component text) {
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
			if (name.equals("FriendName"))
				FriendName.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	@Override
	public boolean isPauseScreen() {
		return true;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		FriendName.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (FriendName.isFocused())
			return FriendName.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String FriendNameValue = FriendName.getValue();
		super.resize(minecraft, width, height);
		FriendName.setValue(FriendNameValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.geng.choose_friend_gui.label_enter_your_friends_name"), 14, 57, -1, false);
	}

	@Override
	public void init() {
		super.init();
		FriendName = new EditBox(this.font, this.leftPos + 15, this.topPos + 73, 146, 18, Component.translatable("gui.geng.choose_friend_gui.FriendName"));
		FriendName.setMaxLength(8192);
		FriendName.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "FriendName", content, false);
		});
		this.addWidget(this.FriendName);
		button_send = Button.builder(Component.translatable("gui.geng.choose_friend_gui.button_send"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ChooseFriendGuiButtonMessage(0, x, y, z));
				ChooseFriendGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 48, this.topPos + 113, 83, 20).build();
		this.addRenderableWidget(button_send);
	}
}