package site.moheng.ling.items;

import org.lwjgl.system.windows.KEYBDINPUT;

import dev.emi.trinkets.api.TrinketItem;
import io.wispforest.owo.nbt.NbtKey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import site.moheng.ling.LingModItems;
import site.moheng.ling.LingModNetwork;
import site.moheng.ling.gui.SpellEditScreen;
import site.moheng.ling.packs.SpellSavePack;

public class SpellCodeItem extends Item {
    private static final NbtKey<NbtCompound> KEY_CODE = new NbtKey<>("code", NbtKey.Type.COMPOUND);

    public SpellCodeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            var stack = user.getStackInHand(hand);
            if (stack.isOf(this)) {
                var nbt = stack.getOrCreateNbt();
                if (nbt.has(KEY_CODE)) {
                    var screen = SpellEditScreen.readAndOnSave(nbt.get(KEY_CODE), SpellCodeItem::editOnSave);
                    MinecraftClient.getInstance().setScreen(screen);
                } else {
                    var screen = new SpellEditScreen(SpellCodeItem::editOnSave);
                    MinecraftClient.getInstance().setScreen(screen);
                }
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Environment(EnvType.CLIENT)
    public static void editOnSave(NbtCompound compound) {
        LingModNetwork.CHANNEL.clientHandle().send(new SpellSavePack(compound));
    }

    public static void writeCode(ItemStack stack,NbtCompound compound) {
        if(stack.isOf(LingModItems.SPELL_CODE)) {
            stack.put(KEY_CODE, compound);
        }
    }
}
