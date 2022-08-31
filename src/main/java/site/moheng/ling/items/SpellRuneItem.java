package site.moheng.ling.items;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SpellRuneItem extends Item {
    public SpellRuneItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        // TODO Auto-generated method stub
        super.appendTooltip(stack, world, tooltip, context);
    }
}
