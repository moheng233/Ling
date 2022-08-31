package site.moheng.ling.rune;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class RuneGetMe implements ISpellRune{

    @Override
    public void appendRuneTooltip(ItemStack stack, List<Text> tooltip) {
        ISpellRune.super.appendRuneTooltip(stack, tooltip);
    }
    
}
