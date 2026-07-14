package com.yourname.educheat.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yourname.educheat.EduCheat;
import com.yourname.educheat.gui.components.Slider;
import com.yourname.educheat.gui.components.ToggleButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CheatMenu extends Screen {
    private static final int MENU_WIDTH = 400;
    private static final int MENU_HEIGHT = 450;
    private int menuX, menuY;
    
    // Компоненты
    private ToggleButton aimbotToggle;
    private ToggleButton triggerToggle;
    private ToggleButton espToggle;
    private Slider smoothnessSlider;
    private Slider rangeSlider;
    private Slider delaySlider;
    private ToggleButton monsterOnlyToggle;
    
    public CheatMenu() {
        super(new TranslationTextComponent("menu.educheat.title"));
    }
    
    @Override
    protected void init() {
        super.init();
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        menuX = centerX - MENU_WIDTH / 2;
        menuY = centerY - MENU_HEIGHT / 2;
        
        int x = menuX + 30;
        int y = menuY + 40;
        int width = MENU_WIDTH - 60;
        
        EduCheat config = EduCheat.getInstance();
        
        // Заголовок
        this.addButton(new Button(
            x, y - 10, width, 20,
            new StringTextComponent("§6§lEduCheat §7v1.0 §8| §7Educational Purposes Only"),
            b -> {}
        ));
        
        y += 30;
        
        // AimBot Toggle
        aimbotToggle = new ToggleButton(
            x, y, width, 20,
            "AimBot",
            config.aimbotEnabled,
            (button) -> {
                config.aimbotEnabled = !config.aimbotEnabled;
            }
        );
        this.addButton(aimbotToggle);
        
        y += 30;
        
        // TriggerBot Toggle
        triggerToggle = new ToggleButton(
            x, y, width, 20,
            "TriggerBot",
            config.triggerbotEnabled,
            (button) -> {
                config.triggerbotEnabled = !config.triggerbotEnabled;
            }
        );
        this.addButton(triggerToggle);
        
        y += 30;
        
        // ESP Toggle
        espToggle = new ToggleButton(
            x, y, width, 20,
            "ESP (Wallhack)",
            config.espEnabled,
            (button) -> {
                config.espEnabled = !config.espEnabled;
            }
        );
        this.addButton(espToggle);
        
        y += 30;
        
        // Monsters Only Toggle
        monsterOnlyToggle = new ToggleButton(
            x, y, width, 20,
            "Only Monsters",
            config.aimbotOnlyMonsters,
            (button) -> {
                config.aimbotOnlyMonsters = !config.aimbotOnlyMonsters;
            }
        );
        this.addButton(monsterOnlyToggle);
        
        y += 40;
        
        // Slider: Smoothness
        smoothnessSlider = new Slider(
            x, y, width, 20,
            "Smoothness",
            0.01f, 0.5f, config.aimbotSmoothness,
            (value) -> {
                config.aimbotSmoothness = value;
            }
        );
        this.addButton(smoothnessSlider);
        
        y += 30;
        
        // Slider: Range
        rangeSlider = new Slider(
            x, y, width, 20,
            "Range",
            3.0f, 16.0f, (float) config.aimbotRange,
            (value) -> {
                config.aimbotRange = value;
            }
        );
        this.addButton(rangeSlider);
        
        y += 30;
        
        // Slider: Trigger Delay
        delaySlider = new Slider(
            x, y, width, 20,
            "Trigger Delay (ms)",
            0, 500, config.triggerDelay,
            (value) -> {
                config.triggerDelay = (int) value;
            }
        );
        this.addButton(delaySlider);
        
        y += 50;
        
        // Кнопка Close
        this.addButton(new Button(
            menuX + MENU_WIDTH / 2 - 50,
            menuY + MENU_HEIGHT - 40,
            100, 20,
            new StringTextComponent("§cClose"),
            (button) -> {
                EduCheat.getInstance().closeMenu();
            }
        ));
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Затемнение фона
        RenderSystem.color4f(0.0F, 0.0F, 0.0F, 0.7F);
        this.fillGradient(matrixStack, 0, 0, this.width, this.height, 0x80000000, 0x80000000);
        
        // Фон меню
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        fill(matrixStack, menuX, menuY, menuX + MENU_WIDTH, menuY + MENU_HEIGHT, 0xD0000000);
        fill(matrixStack, menuX + 1, menuY + 1, menuX + MENU_WIDTH - 1, menuY + MENU_HEIGHT - 1, 0xDD1A1A2E);
        
        // Рамка
        drawBorder(matrixStack, menuX, menuY, MENU_WIDTH, MENU_HEIGHT, 0xFF6C6CFF);
        
        // Рисуем компоненты
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        
        // Информация внизу
        String info = "§7Press §6INS §7to open/close";
        this.font.drawStringWithShadow(
            matrixStack,
            info,
            menuX + MENU_WIDTH / 2 - this.font.getStringWidth(info) / 2,
            menuY + MENU_HEIGHT - 20,
            0x88FFFFFF
        );
    }
    
    private void drawBorder(MatrixStack matrixStack, int x, int y, int width, int height, int color) {
        // Верхняя
        fill(matrixStack, x, y, x + width, y + 2, color);
        // Нижняя
        fill(matrixStack, x, y + height - 2, x + width, y + height, color);
        // Левая
        fill(matrixStack, x, y, x + 2, y + height, color);
        // Правая
        fill(matrixStack, x + width - 2, y, x + width, y + height, color);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
