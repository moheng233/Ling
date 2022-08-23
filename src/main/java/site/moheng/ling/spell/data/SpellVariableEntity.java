package site.moheng.ling.spell.data;

import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import site.moheng.ling.spell.SpellVariableType;

public class SpellVariableEntity extends SpellVariableType<Entity> {

    @Override
    public Text getDisplayData(Entity data) {
        return data.getName();
    }
    
}
