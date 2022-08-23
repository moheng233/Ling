package site.moheng.ling.spell;

public abstract class SpellHandleNode extends SpellNode {
    @Override
    public NodeType getType() {
        return NodeType.HANDLE;
    }
}
