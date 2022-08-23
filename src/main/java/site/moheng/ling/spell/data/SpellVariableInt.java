package site.moheng.ling.spell.data;

import net.minecraft.text.Text;
import site.moheng.ling.spell.SpellVariableType;

public class SpellVariableInt extends SpellVariableType<Integer> {
    @Override
    public Text getDisplayData(Integer data) {
        return Text.of(String.valueOf(data));
    }

    @Override
    public Text getDisplayType() {
        return Text.translatable("spell_stack_data.lingmod.int");
    }
        
}