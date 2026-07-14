package com.example.hvvisuals.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TimeHud implements HudRenderCallback {
    @Override
    public void render(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        long time = client.world.getTimeOfDay() % 24000;
        String timeString;
        
        if (time < 1000) timeString = "🌅 Рассвет";
        else if (time < 6000) timeString = "☀️ День";
        else if (time < 12000) timeString = "🌇 Закат";
        else if (time < 13000) timeString = "🌆 Сумерки";
        else if (time < 23000) timeString = "🌙 Ночь";
        else timeString = "🌅 Рассвет";

        TextRenderer renderer = client.textRenderer;
        if (renderer != null) {
            matrices.push();
            matrices.translate(5, 35, 0);
            renderer.draw(matrices, Text.literal(timeString), 0, 0, 0x88FF88);
            matrices.pop();
        }
    }
}
