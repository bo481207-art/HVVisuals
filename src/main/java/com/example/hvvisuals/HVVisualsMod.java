package com.example.hvvisuals;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HVVisualsMod implements ModInitializer {
    public static final String MOD_ID = "hvvisuals";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("HVVisuals initialized!");
    }
}
