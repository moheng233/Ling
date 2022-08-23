package site.moheng.ling.spell.data;

import net.minecraft.text.Text;
import site.moheng.ling.spell.entry.SpellNodeEntry;
import site.moheng.ling.spell.SpellVariableType;

/**
 * 用来表示一个
 */
public class SpellVariableNodeLink extends SpellVariableType<SpellNodeEntry> {
    @Override
    public Text getDisplayData(SpellNodeEntry data) {
        return Text.of("node");
    }
    
}
