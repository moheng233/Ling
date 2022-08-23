package site.moheng.ling.spell.entry;

import site.moheng.ling.spell.SpellNode.NodeIOPoint;

public final class SpellLinkEntry {
    public final SpellNodeEntry node;
    public final NodeIOPoint<?> point;

    public SpellLinkEntry(SpellNodeEntry node, NodeIOPoint<?> point) {
        this.node = node;
        this.point = point;
    }

    @Override
    public String toString() {
        return node.id + "$" + point.name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((node == null) ? 0 : node.hashCode());
        result = prime * result + ((point == null) ? 0 : point.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpellLinkEntry other = (SpellLinkEntry) obj;
        if (node == null) {
            if (other.node != null)
                return false;
        } else if (!node.equals(other.node))
            return false;
        if (point == null) {
            if (other.point != null)
                return false;
        } else if (!point.equals(other.point))
            return false;
        return true;
    }
}
