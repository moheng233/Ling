package site.moheng.ling;

import io.wispforest.owo.network.OwoNetChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import site.moheng.ling.items.SpellCodeItem;
import site.moheng.ling.packs.SpellSavePack;

public class LingModNetwork {
    public static final OwoNetChannel CHANNEL = OwoNetChannel.create(new Identifier(LingMod.MODID, "main"));

    public static void initServer() {
        CHANNEL.registerServerbound(SpellSavePack.class, (message, access) -> {
            var stack = access.netHandler().player.getStackInHand(Hand.MAIN_HAND);
            SpellCodeItem.writeCode(stack, message.nbt());
        });
    }

    public static void initClient() {

    }

    protected static void onOpenSkillGui() {

    }

    @Environment(EnvType.CLIENT)
    public static void openSkillGui() {
        
    }
}
