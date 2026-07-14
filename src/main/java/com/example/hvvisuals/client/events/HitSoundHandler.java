package com.example.hvvisuals.client.events;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class HitSoundHandler {
    private static boolean registered = false;

    public static void register() {
        if (registered) return;
        registered = true;

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient && hand == Hand.MAIN_HAND && entity instanceof LivingEntity) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player != null) {
                    client.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_HAT, 1.0f, 1.0f);
                }
            }
            return ActionResult.PASS;
        });
    }
}
