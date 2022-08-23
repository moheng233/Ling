package site.moheng.ling.spell;

import site.moheng.ling.spell.entry.SpellNodeEntry;

public abstract class SpellProcessNode extends SpellNode {
    public final NodeIOPoint<SpellNodeEntry> formProcess = NodeIOPoint.create("from", IOType.INPUT);
    public final NodeIOPoint<SpellNodeEntry> nextProcess = NodeIOPoint.create("next", IOType.OUTPUT);

    public SpellProcessNode() {
        io.define(formProcess);
        io.define(nextProcess);
    }

    @Override
    public NodeType getType() {
        return NodeType.PROCESS;
    }
}
