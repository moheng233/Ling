package site.moheng.ling.spell.node;

import net.minecraft.entity.Entity;
import site.moheng.ling.SpellVariableTypes;
import site.moheng.ling.spell.SpellHandleNode;

public class SpellNodeGetOwer extends SpellHandleNode {
    public final NodeIOPoint<Entity> ownerOutput = NodeIOPoint.create("owner", IOType.INPUT, SpellVariableTypes.ENTITY);

    public SpellNodeGetOwer() {
        io.define(ownerOutput);
    }


}
