package site.moheng.ling.spell.node;

import net.minecraft.entity.Entity;
import site.moheng.ling.SpellVariableTypes;
import site.moheng.ling.components.MagicianComponents;
import site.moheng.ling.spell.SpellEventNode;
import site.moheng.ling.spell.SpellStream;
import site.moheng.ling.spell.entry.SpellNodeEntry;

public class SpellNodeOnUse extends SpellEventNode {
    public final NodeIOPoint<Entity> ownerOutput = NodeIOPoint.create("owner", IOType.OUTPUT, SpellVariableTypes.ENTITY);
    
    public SpellNodeOnUse() {
        io.define(ownerOutput);
    }
    
    @Override
    public void tick(SpellStream stream, SpellNodeEntry entry,MagicianComponents magician) {

    }
}
