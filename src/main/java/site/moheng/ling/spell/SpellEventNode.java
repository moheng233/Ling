package site.moheng.ling.spell;

import site.moheng.ling.spell.entry.SpellNodeEntry;

public abstract class SpellEventNode extends SpellNode {
    public final NodeIOPoint<SpellNodeEntry> nextProcess = NodeIOPoint.create("next", IOType.OUTPUT);

    public SpellEventNode() {
        io.define(nextProcess);
    }

    @Override
    public NodeType getType() {
        return NodeType.PROCESS;
    }
}
