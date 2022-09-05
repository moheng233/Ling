package site.moheng.ling;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import site.moheng.ling.blocks.ShelfBlockEntity;
import site.moheng.ling.client.ModModelProvider;
import site.moheng.ling.client.ShelfEntityRenderer;
import site.moheng.ling.gui.hud.SpellHudScreen;

public class LingModClient implements ClientModInitializer {

    private static KeyBinding openSkillGuiKeyBinding;

    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider((rm) -> new ModModelProvider());
        initKeyBing();
        initGui();
        initBlockEntityRenderer();
        LingModNetwork.initClient();
    }
    
    public void initBlockEntityRenderer() {
        BlockEntityRendererRegistry.register(LingModBlockEntitys.SHELF_BLOCK_ENTITY, ShelfEntityRenderer::new);
    }

    public void initGui() {
        HudRenderCallback.EVENT.register(new SpellHudScreen());
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
