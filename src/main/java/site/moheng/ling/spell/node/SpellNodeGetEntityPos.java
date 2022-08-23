package site.moheng.ling.spell.node;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import site.moheng.ling.SpellVariableTypes;
import site.moheng.ling.spell.SpellHandleNode;

public class SpellNodeGetEntityPos extends SpellHandleNode {
    public final NodeIOPoint<Entity> entityInput = NodeIOPoint.create("entity", IOType.INPUT, SpellVariableTypes.ENTITY);
    public final NodeIOPoint<BlockPos> vectorOutput = NodeIOPoint.create("vector", IOType.OUTPUT, SpellVariableTypes.POSITION);

    public SpellNodeGetEntityPos() {
        io.define(entityInput);
        io.define(vectorOutput);
    }
}
