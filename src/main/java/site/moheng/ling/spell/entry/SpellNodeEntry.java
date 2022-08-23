package site.moheng.ling.spell.entry;

import java.util.Map;

import io.wispforest.owo.nbt.NbtKey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import site.moheng.ling.spell.SpellNode;
import site.moheng.ling.spell.SpellRegistry;
import site.moheng.ling.spell.SpellNode.NodeIOPoint;
import site.moheng.ling.util.MathRect;

public final class SpellNodeEntry {
    protected static final NbtKey<String> KEY_ID = new NbtKey<>("id", NbtKey.Type.STRING);
    protected static final NbtKey<Identifier> KEY_NODE = new NbtKey<>("node", NbtKey.Type.IDENTIFIER);
    protected static final NbtKey<NbtCompound> KEY_EXTEND = new NbtKey<>("extend", NbtKey.Type.COMPOUND);

    public String id;
    public final SpellNode node;
    public NbtCompound extend = new NbtCompound();

    public SpellNodeEntry(String id, SpellNode node) {
        this.id = id;
        this.node = node;
    }

    @Environment(EnvType.CLIENT)
    public MathRect getRect() {
        return node.getRect();
    }

    public static SpellNodeEntry fromNbt(NbtCompound nbt) {
        var entry = new SpellNodeEntry(nbt.get(KEY_ID), SpellRegistry.SPELL_NODE.get(nbt.get(KEY_NODE)));
        entry.extend = nbt.get(KEY_EXTEND);

        return entry;
    }

    public NbtCompound toNbt() {
        var nbt = new NbtCompound();
        nbt.put(KEY_ID, id);
        nbt.put(KEY_NODE, node.getId());
        nbt.put(KEY_EXTEND, extend);

        return nbt;
    }

    /**
     * 注意：这里获得的点的Rect需要手动加上当前的位置
     * 
     * @return
     */
    @Environment(EnvType.CLIENT)
    public Map<NodeIOPoint<?>, MathRect> getPoints() {
        return node.getPointRects();
    }

    @Environment(EnvType.CLIENT)
    public MathRect getPoint(NodeIOPoint<?> point) {
        return node.getPointRect(point);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpellNodeEntry other = (SpellNodeEntry) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
