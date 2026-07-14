package com.yourname.educheat;

import com.yourname.educheat.config.ConfigManager;
import com.yourname.educheat.features.AimBot;
import com.yourname.educheat.features.TriggerBot;
import com.yourname.educheat.gui.CheatMenu;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod(EduCheat.MODID)
public class EduCheat {
    public static final String MODID = "educheat";
    private static EduCheat instance;
    
    // GUI
    private CheatMenu cheatMenu;
    private boolean menuOpen = false;
    
    // Features
    private AimBot aimBot;
    private TriggerBot triggerBot;
    
    // Настройки (сохраняются в config)
    public boolean aimbotEnabled = true;
    public float aimbotSmoothness = 0.08f;
    public double aimbotRange = 8.0;
    public double aimbotFov = 120.0;
    public boolean aimbotOnlyMonsters = true;
    
    public boolean triggerbotEnabled = true;
    public int triggerDelay = 50;
    public boolean triggerOnlyOnAim = true;
    
    public boolean espEnabled = true;
    
    public EduCheat() {
        instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void clientSetup(FMLClientSetupEvent event) {
        cheatMenu = new CheatMenu();
        aimBot = new AimBot();
        triggerBot = new TriggerBot();
        
        MinecraftForge.EVENT_BUS.register(aimBot);
        MinecraftForge.EVENT_BUS.register(triggerBot);
        
        ConfigManager.loadConfig();
    }
    
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.world == null) return;
        
        // Открытие по INS
        if (mc.currentScreen == null) {
            if (GLFW.glfwGetKey(mc.getMainWindow().getHandle(), GLFW.GLFW_KEY_INSERT) == GLFW.GLFW_PRESS) {
                if (!menuOpen) {
                    menuOpen = true;
                    mc.displayGuiScreen(cheatMenu);
                }
            }
        } else if (mc.currentScreen == cheatMenu) {
            // Закрытие по ESC
            if (GLFW.glfwGetKey(mc.getMainWindow().getHandle(), GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
                closeMenu();
            }
        }
    }
    
    public void closeMenu() {
        menuOpen = false;
        Minecraft.getInstance().displayGuiScreen(null);
    }
    
    public static EduCheat getInstance() { return instance; }
    public AimBot getAimBot() { return aimBot; }
    public TriggerBot getTriggerBot() { return triggerBot; }
    public CheatMenu getCheatMenu() { return cheatMenu; }
}
