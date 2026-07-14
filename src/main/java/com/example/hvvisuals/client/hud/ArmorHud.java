package com.example.hvvisuals.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class ArmorHud implements HudRenderCallback {
    @Override
    public void render(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        int totalArmor = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                ItemStack stack = client.player.getEquippedStack(slot);
                if (!stack.isEmpty()) {
                    totalArmor += stack.getItem().getProtection();
                }
            }
        }

        if (totalArmor > 0) {
            TextRenderer renderer = client.textRenderer;
            if (renderer != null) {
                matrices.push();
                matrices.translate(5, 50, 0);
                renderer.draw(matrices, Text.literal("🛡 Броня: " + totalArmor), 0, 0, 0x66CCFF);
                matrices.pop();
            }
        }
    }
}
