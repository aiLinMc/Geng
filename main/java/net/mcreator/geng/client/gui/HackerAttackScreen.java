package net.mcreator.geng.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;

public class HackerAttackScreen extends Screen {
    private final String message;
    private int tickCount = 0;
    private final boolean isTestMode; // 测试模式不崩溃
    
    public HackerAttackScreen(String message, boolean isTestMode) {
        super(Component.translatable("chat.geng.lthacker.crash"));
        this.message = message;
        this.isTestMode = isTestMode;
    }
    
    @Override
    protected void init() {
        super.init();
    }
    
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.fill(0, 0, this.width, this.height, 0xCC000000);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTextOnTop(guiGraphics);
    }
    

    private void renderTextOnTop(GuiGraphics guiGraphics) {
        // 分割消息行
        String[] lines = message.split("\n");
        
        // 计算总高度
        int totalTextHeight = lines.length * 20;
        int startY = this.height / 2 - totalTextHeight / 2;
        
        // 渲染标题
        guiGraphics.drawCenteredString(this.font, this.title, 
            this.width / 2, startY - 40, 0xFF0000);
        
        // 渲染消息内容
        for (int i = 0; i < lines.length; i++) {
            guiGraphics.drawCenteredString(this.font, lines[i], 
                this.width / 2, startY + i * 20, 0xFFFFFF);
        }
        
        // 渲染底部信息
        int bottomY = this.height - 60;
        
        if (!isTestMode) {
            // 渲染倒计时
            int secondsLeft = Math.max(0, 4 - (tickCount / 20));
            guiGraphics.drawCenteredString(this.font, 
                Component.translatable("chat.geng.lthacker.crash.countdown", secondsLeft), 
                this.width / 2, bottomY, 0xFF5555);
            
            // 渲染无法关闭的提示
            guiGraphics.drawCenteredString(this.font, 
                Component.translatable("chat.geng.lthacker.crash.cannot_close"), 
                this.width / 2, bottomY + 20, 0xAAAAAA);
        } else {
            guiGraphics.drawCenteredString(this.font, 
                Component.translatable("chat.geng.lthacker.crash.test_mode"), 
                this.width / 2, bottomY, 0x55FF55);
            
            // 渲染可关闭的提示
            guiGraphics.drawCenteredString(this.font, 
                Component.translatable("chat.geng.lthacker.crash.press_esc"), 
                this.width / 2, bottomY + 20, 0xAAAAAA);
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        tickCount++;
        
        if (!isTestMode && tickCount >= 80) { // 4秒后关闭（游戏会崩溃）
            Minecraft.getInstance().setScreen(null);
        }
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        return isTestMode; // 测试模式下可以ESC关闭，崩溃模式下不可以
    }
    
    @Override
    public boolean isPauseScreen() {
        return true; // 暂停游戏
    }
}