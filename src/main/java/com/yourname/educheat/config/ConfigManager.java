package com.yourname.educheat.config;

import com.yourname.educheat.EduCheat;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ConfigManager {
    private static final File CONFIG_FILE = new File("config/educheat.properties");
    private static Properties properties = new Properties();
    
    public static void loadConfig() {
        if (!CONFIG_FILE.exists()) {
            saveConfig();
            return;
        }
        
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            
            EduCheat config = EduCheat.getInstance();
            
            // Загружаем настройки
            config.aimbotEnabled = Boolean.parseBoolean(properties.getProperty("aimbotEnabled", "true"));
            config.aimbotSmoothness = Float.parseFloat(properties.getProperty("aimbotSmoothness", "0.08"));
            config.aimbotRange = Double.parseDouble(properties.getProperty("aimbotRange", "8.0"));
            config.aimbotFov = Double.parseDouble(properties.getProperty("aimbotFov", "120.0"));
            config.aimbotOnlyMonsters = Boolean.parseBoolean(properties.getProperty("aimbotOnlyMonsters", "true"));
            
            config.triggerbotEnabled = Boolean.parseBoolean(properties.getProperty("triggerbotEnabled", "true"));
            config.triggerDelay = Integer.parseInt(properties.getProperty("triggerDelay", "50"));
            
            config.espEnabled = Boolean.parseBoolean(properties.getProperty("espEnabled", "true"));
            
        } catch (Exception e) {
            e.printStackTrace();
            saveConfig();
        }
    }
    
    public static void saveConfig() {
        try {
            EduCheat config = EduCheat.getInstance();
            
            properties.setProperty("aimbotEnabled", String.valueOf(config.aimbotEnabled));
            properties.setProperty("aimbotSmoothness", String.valueOf(config.aimbotSmoothness));
            properties.setProperty("aimbotRange", String.valueOf(config.aimbotRange));
            properties.setProperty("aimbotFov", String.valueOf(config.aimbotFov));
            properties.setProperty("aimbotOnlyMonsters", String.valueOf(config.aimbotOnlyMonsters));
            
            properties.setProperty("triggerbotEnabled", String.valueOf(config.triggerbotEnabled));
            properties.setProperty("triggerDelay", String.valueOf(config.triggerDelay));
            
            properties.setProperty("espEnabled", String.valueOf(config.espEnabled));
            
            CONFIG_FILE.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
                properties.store(fos, "EduCheat Configuration");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Сохраняем конфиг при закрытии игры
    @SubscribeEvent
    public void onClientShutdown(net.minecraftforge.event.world.WorldEvent.Unload event) {
        saveConfig();
    }
}
