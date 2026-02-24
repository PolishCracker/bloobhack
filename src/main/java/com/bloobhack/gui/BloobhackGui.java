package com.bloobhack.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Main GUI for bloobhack
 * Features:
 * - Modern design with gradient backgrounds
 * - Module list with toggle buttons
 * - Settings panel
 * - Custom color scheme
 */
public class BloobhackGui extends Screen {
    private MinecraftClient mc = MinecraftClient.getInstance();
    private static final int BACKGROUND_COLOR = 0xFF1a1a1a;
    private static final int ACCENT_COLOR = 0xFF00d4ff;
    private static final int TEXT_COLOR = 0xFFffffff;
    
    private int guiX = 10;
    private int guiY = 10;
    private int guiWidth = 250;
    private int guiHeight = 300;
    
    public BloobhackGui() {
        super(Text.literal("bloobhack"));
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Draw background
        context.fill(guiX, guiY, guiX + guiWidth, guiY + guiHeight, BACKGROUND_COLOR);
        
        // Draw border
        context.fill(guiX, guiY, guiX + guiWidth, guiY + 2, ACCENT_COLOR);
        context.fill(guiX, guiY, guiX + 2, guiY + guiHeight, ACCENT_COLOR);
        
        // Draw title
        context.drawTextWithShadow(
            this.textRenderer,
            "§6bloobhack §8v1.0.0",
            guiX + 10,
            guiY + 10,
            TEXT_COLOR
        );
        
        // Draw author
        context.drawTextWithShadow(
            this.textRenderer,
            "§7Author: §fPogracz",
            guiX + 10,
            guiY + 25,
            TEXT_COLOR
        );
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
    
    @Override
    public void close() {
        super.close();
    }
}
