package site.moheng.ling.spell.node;

import net.minecraft.util.math.BlockPos;
import site.moheng.ling.SpellVariableTypes;
import site.moheng.ling.spell.SpellHandleNode;

public class SpellNodePosAdd extends SpellHandleNode {
    public NodeIOPoint<BlockPos> pos1Input = NodeIOPoint.create("pos1", IOType.INPUT, SpellVariableTypes.POSITION);
    public NodeIOPoint<BlockPos> pos2Input = NodeIOPoint.create("pos2", IOType.INPUT, SpellVariableTypes.POSITION);

    public NodeIOPoint<BlockPos> posOutput = NodeIOPoint.create("pos", IOType.OUTPUT, SpellVariableTypes.POSITION);

    public SpellNodePosAdd() {
        io.define(pos1Input).define(pos2Input).define(posOutput);
    }
}
