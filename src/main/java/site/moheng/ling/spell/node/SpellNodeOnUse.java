package site.moheng.ling.spell.node;

import net.minecraft.entity.Entity;
import site.moheng.ling.SpellVariableTypes;
import site.moheng.ling.spell.SpellEventNode;

public class SpellNodeOnUse extends SpellEventNode {
    public final NodeIOPoint<Entity> ownerOutput = NodeIOPoint.create("owner", IOType.OUTPUT, SpellVariableTypes.ENTITY);
    
    public SpellNodeOnUse() {
        io.define(ownerOutput);
    }
    
}
