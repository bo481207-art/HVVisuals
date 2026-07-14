package com.example.hvvisuals.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class DurabilityHud implements HudRenderCallback {
    @Override
    public void render(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        ItemStack mainHand = client.player.getMainHandStack();
        if (!mainHand.isEmpty() && mainHand.isDamageable()) {
            int durability = mainHand.getMaxDamage() - mainHand.getDamage();
            int maxDurability = mainHand.getMaxDamage();
            String text = String.format("⚔ %d/%d", durability, maxDurability);

            TextRenderer renderer = client.textRenderer;
            if (renderer != null) {
                matrices.push();
                matrices.translate(5, 20, 0);
                renderer.draw(matrices, Text.literal(text), 0, 0, 0xFFFF55);
                matrices.pop();
            }
        }
    }
}
