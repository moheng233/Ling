package site.moheng.ling;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class LingModClient implements ClientModInitializer {

    private static KeyBinding openSkillGuiKeyBinding;

    @Override
    public void onInitializeClient() {
        initKeyBing();
        initGui();
        LingModNetwork.initClient();
    }
    
    public void initGui() {
        LingModGui.initScreen();
    }

    /**
     * 初始化按键绑定
     */
    public void initKeyBing() {
        openSkillGuiKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.lingmod.open_skill_gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.lingmod.category"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openSkillGuiKeyBinding.wasPressed()) {
                
            }
        });
    }
}
