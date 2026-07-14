 EduCheat - Educational Minecraft Cheat Mod

**⚠️ WARNING: For educational purposes only! Use only in singleplayer! ⚠️**

## 📖 About
EduCheat is an educational mod for Minecraft 1.16.5 that demonstrates how various cheat mechanics work. Created for learning purposes to understand:
- Java programming
- Minecraft Forge API
- Event-driven architecture
- GUI development
- Ray tracing and entity manipulation

## ✨ Features
- **AimBot** - Smooth auto-aiming with customizable smoothness
- **TriggerBot** - Auto-attack with configurable delay
- **ESP** - See entities through walls (coming soon)
- **Beautiful GUI** - Clean menu with toggle buttons and sliders
- **Settings persistence** - Config saved automatically

## 🎮 Controls
- **INSERT** - Open/close menu
- **ESC** - Close menu (from menu)

## 🔧 Installation
1. Download the latest release `.jar` file
2. Place it in your `.minecraft/mods` folder
3. Launch Minecraft 1.16.5 with Forge installed

## 🛠️ Building from source
```bash
git clone https://github.com/yourname/EduCheat.git
cd EduCheat
./gradlew build
The mod will be in build/libs/

📁 Project Structure
text
EduCheat/
├── src/main/java/com/yourname/educheat/
│   ├── EduCheat.java          # Main mod class
│   ├── features/              # Cheat features
│   │   ├── AimBot.java
│   │   └── TriggerBot.java
│   ├── gui/                   # GUI components
│   │   ├── CheatMenu.java
│   │   └── components/
│   │       ├── ToggleButton.java
│   │       └── Slider.java
│   └── config/                # Configuration
│       └── ConfigManager.java
└── src/main/resources/        # Resources
    ├── META-INF/mods.toml
    └── assets/educheat/lang/en_us.json
⚙️ Settings
AimBot - Toggle aimbot

TriggerBot - Toggle auto-attack

ESP - Wallhack mode

Only Monsters - Target only monsters

Smoothness - Aim smoothness (0.01-0.5)

Range - Max targeting distance

Trigger Delay - Delay between attacks (ms)

🧪 How it works
AimBot
Scans for living entities in range

Filters by FOV (Field of View)

Smoothly rotates player's view toward target

TriggerBot
Checks if player is looking at entity

Verifies entity is alive and valid

Attacks with configurable delay

📝 License
This project is for educational purposes only. Use at your own risk.

⚠️ Disclaimer
This mod is created SOLELY for educational purposes to understand game mechanics and Java programming. Using this on multiplayer servers violates most server rules and can result in a permanent ban.

Made with ❤️ for learning
