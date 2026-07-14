package com.example.hvvisuals.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class CoordsHud implements HudRenderCallback {
    @Override
    public void render(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        double x = client.player.getX();
        double y = client.player.getY();
        double z = client.player.getZ();
        String coords = String.format("XYZ: %.1f, %.1f, %.1f", x, y, z);

        TextRenderer textRenderer = client.textRenderer;
        if (textRenderer == null) return;

        matrices.push();
        matrices.translate(5, 5, 0);
        textRenderer.draw(matrices, Text.literal(coords), 0, 0, 0xFFFFFF);
        matrices.pop();
    }
}
