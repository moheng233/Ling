package site.moheng.ling;

import io.wispforest.owo.network.OwoNetChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import site.moheng.ling.util.ServerUtil;

public class LingModNetwork {
    public static final OwoNetChannel CHANNEL = OwoNetChannel.create(new Identifier(LingMod.MODID, "main"));

    public static void initServer() {
        ServerUtil.init();
    }

    public static void initClient() {
        ServerUtil.initClient();
    }

    protected static void onOpenSkillGui() {

    }

    @Environment(EnvType.CLIENT)
    public static void openSkillGui() {
        
    }
}
