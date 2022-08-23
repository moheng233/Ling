package site.moheng.ling.spell.data;

import net.minecraft.text.Text;
import site.moheng.ling.spell.SpellVariableType;

public class SpellVariableBool extends SpellVariableType<Boolean> {

    @Override
    public Text getDisplayData(Boolean data) {
        return Text.of(String.valueOf(data));
    }

    @Override
    public Text getDisplayType() {
        return Text.translatable("spell_stack_data.lingmod.bool");
    }
    
}
