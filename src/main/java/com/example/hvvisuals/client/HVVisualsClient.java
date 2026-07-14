package com.example.hvvisuals.client;

import com.example.hvvisuals.client.events.HitSoundHandler;
import com.example.hvvisuals.client.events.ToggleSprintHandler;
import com.example.hvvisuals.client.hud.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HVVisualsClient implements ClientModInitializer {
    public static KeyBinding toggleSprintKey;

    @Override
    public void onInitializeClient() {
        // Регистрация HUD элементов
        HudRenderCallback.EVENT.register(new CoordsHud());
        HudRenderCallback.EVENT.register(new DurabilityHud());
        HudRenderCallback.EVENT.register(new TimeHud());
        HudRenderCallback.EVENT.register(new ArmorHud());

        // Регистрация обработчиков событий
        HitSoundHandler.register();
        ToggleSprintHandler.register();

        // Регистрация клавиши для ToggleSprint
        toggleSprintKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hvvisuals.toggle_sprint",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.hvvisuals"
        ));

        // Обработка нажатий клавиш
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleSprintKey.wasPressed()) {
                ToggleSprintHandler.toggle();
            }
        });
    }
}
