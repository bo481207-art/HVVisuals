package com.yourname.educheat.gui.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class ToggleButton extends Button {
    private boolean state;
    private final String label;
    
    public ToggleButton(int x, int y, int width, int height, String label, boolean initialState, IPressable onPress) {
        super(x, y, width, height, new StringTextComponent(label), onPress);
        this.label = label;
        this.state = initialState;
    }
    
    @Override
    public void onPress() {
        this.state = !this.state;
        super.onPress();
    }
    
    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        
        // Фон
        int bgColor = this.state ? 0xCC00AA00 : 0xCCAA0000;
        if (this.isHovered()) {
            bgColor = this.state ? 0xDD00CC00 : 0xDDCC0000;
        }
        fill(matrixStack, this.x, this.y, this.x + this.width, this.y + this.height, bgColor);
        fill(matrixStack, this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, 0xDD1A1A2E);
        
        // Текст
        String status = this.state ? "§aON" : "§cOFF";
        String displayText = this.label + " §7[ " + status + " §7]";
        mc.fontRenderer.drawStringWithShadow(
            matrixStack,
            displayText,
            this.x + 5,
            this.y + (this.height - 8) / 2,
            0xFFFFFFFF
        );
    }
    
    public boolean getState() { return state; }
    public void setState(boolean state) { this.state = state; }
}
