package site.moheng.ling;

import io.wispforest.owo.network.OwoNetChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class LingModNetwork {
    public static final OwoNetChannel CHANNEL = OwoNetChannel.create(new Identifier(LingMod.MODID, "main"));

    public static void initServer() {
        
    }

    public static void initClient() {

    }

    protected static void onOpenSkillGui() {

    }

    @Environment(EnvType.CLIENT)
    public static void openSkillGui() {
        
    }
}
