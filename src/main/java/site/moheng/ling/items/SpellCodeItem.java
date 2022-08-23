package site.moheng.ling.items;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import site.moheng.ling.gui.SpellEditScreen;

public class SpellCodeItem extends Item {

    public SpellCodeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            var screen = new SpellEditScreen();
            MinecraftClient.getInstance().setScreen(screen);
        }
        return super.use(world, user, hand);
    }
    
}
