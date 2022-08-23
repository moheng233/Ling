package site.moheng.ling.spell.node;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import site.moheng.ling.SpellVariableTypes;
import site.moheng.ling.spell.SpellProcessNode;

public class SpellNodeBreakBlock extends SpellProcessNode {
    public NodeIOPoint<BlockPos> posInput = NodeIOPoint.create("position", IOType.INPUT, SpellVariableTypes.POSITION);

    public SpellNodeBreakBlock() {
        io.define(posInput);
    }

}
