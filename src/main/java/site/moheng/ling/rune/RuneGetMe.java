package site.moheng.ling.rune;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import site.moheng.ling.components.IMagician;
import site.moheng.ling.components.MagicianComponents;

public class RuneGetMe extends AbsSpellRune{

    @Override
    public void appendRuneTooltip(ItemStack stack, List<Text> tooltip) {
        super.appendRuneTooltip(stack, tooltip);
    }
    
    @Override
    public void exec(IMagician magician) {
        if(magician instanceof MagicianComponents entity_magician) {
            entity_magician.push(entity_magician.getPlayer());
        } else {
            magician.sendMessage(Text.of("只可以通过玩家使用"));
        }
        super.exec(magician);
    }
}
