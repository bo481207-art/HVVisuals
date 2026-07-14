package com.yourname.educheat.gui.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class Slider extends Button {
    private float min, max, value;
    private final String label;
    private final SliderChangeListener listener;
    private boolean isDragging = false;
    
    public interface SliderChangeListener {
        void onValueChange(float value);
    }
    
    public Slider(int x, int y, int width, int height, String label, float min, float max, float initial, SliderChangeListener listener) {
        super(x, y, width, height, new StringTextComponent(label), (button) -> {});
        this.min = min;
        this.max = max;
        this.value = Math.max(min, Math.min(max, initial));
        this.label = label;
        this.listener = listener;
    }
    
    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        
        // Фон
        fill(matrixStack, this.x, this.y, this.x + this.width, this.y + this.height, 0xCC222244);
        fill(matrixStack, this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, 0xDD1A1A2E);
        
        // Полоса прогресса
        float progress = (this.value - this.min) / (this.max - this.min);
        int barWidth = (int) ((this.width - 10) * progress);
        fill(matrixStack, this.x + 5, this.y + 14, this.x + 5 + barWidth, this.y + 17, 0xFF6C6CFF);
        
        // Полоса (фон)
        fill(matrixStack, this.x + 5, this.y + 14, this.x + this.width - 5, this.y + 17, 0x44FFFFFF);
        
        // Текст
        String displayText = this.label + " §7" + String.format("%.2f", this.value);
        mc.fontRenderer.drawStringWithShadow(
            matrixStack,
            displayText,
            this.x + 5,
            this.y + 2,
            0xFFFFFFFF
        );
        
        // Значение
        String valueText = String.format("%.2f", this.value);
        mc.fontRenderer.drawStringWithShadow(
            matrixStack,
            valueText,
            this.x + this.width - mc.fontRenderer.getStringWidth(valueText) - 5,
            this.y + 2,
            0xFFAAAAAA
        );
    }
    
    @Override
    public void onClick(double mouseX, double mouseY) {
        this.isDragging = true;
        updateValue(mouseX);
    }
    
    @Override
    public void onRelease(double mouseX, double mouseY) {
        this.isDragging = false;
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.isDragging) {
            updateValue(mouseX);
            return true;
        }
        return false;
    }
    
    private void updateValue(double mouseX) {
        float relativeX = (float) ((mouseX - this.x - 5) / (this.width - 10));
        this.value = Math.max(this.min, Math.min(this.max, this.min + relativeX * (this.max - this.min)));
        if (this.listener != null) {
            this.listener.onValueChange(this.value);
        }
    }
    
    public float getValue() { return value; }
    public void setValue(float value) { 
        this.value = Math.max(min, Math.min(max, value));
        if (this.listener != null) {
            this.listener.onValueChange(this.value);
        }
    }
}
