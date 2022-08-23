package site.moheng.ling;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import site.moheng.ling.gui.SKillGuiScreenHandler;
import site.moheng.ling.gui.SkillGuiScreen;

public class LingModGui {
    /**
	 * 释放术式的窗口
	 */
	public static final ScreenHandlerType<SKillGuiScreenHandler> SKILL_GUI_SCREEN_HANDLER_TYPE = new ScreenHandlerType<>(SKillGuiScreenHandler::new);

    /**
     * 
     */
    public static void initScreenHandler() {
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(LingMod.MODID, "skill_gui"), SKILL_GUI_SCREEN_HANDLER_TYPE);
    }
    
    /**
     * 初始化Screen
     * 仅在客户端
     */
    @Environment(EnvType.CLIENT)
    public static void initScreen() {
        HandledScreens.register(SKILL_GUI_SCREEN_HANDLER_TYPE, SkillGuiScreen::new);
    }
}
