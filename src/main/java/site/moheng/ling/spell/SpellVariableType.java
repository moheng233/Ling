package site.moheng.ling.spell;

import net.minecraft.text.Text;
import net.minecraft.util.Util;

/**
 * 在术式栈中的一个数据的抽象类
 */
public abstract class SpellVariableType<D> {
    
    public abstract Text getDisplayData(D data); 
    public Text getDisplayType() {
        return Text.translatable(Util.createTranslationKey("spell_stack_data", SpellRegistry.SPELL_VAR_TYPE.getId(this)));
    }

    public int getDisplayColor() {
        return 0xffffffff;
    }

    public boolean canLink(SpellVariableType<?> other) {
        if(this == other) {
            return true;
        }

        return false;
    }
}
