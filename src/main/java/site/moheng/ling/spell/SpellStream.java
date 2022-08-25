package site.moheng.ling.spell;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import site.moheng.ling.components.MagicianComponents;
import site.moheng.ling.spell.SpellNode.IExecNode;
import site.moheng.ling.spell.SpellNode.IOType;
import site.moheng.ling.spell.SpellNode.NodeIOPoint;
import site.moheng.ling.spell.SpellNode.NodeType;
import site.moheng.ling.spell.data.SpellVariableNodeLink;
import site.moheng.ling.spell.entry.SpellLinkEntry;
import site.moheng.ling.spell.entry.SpellNodeEntry;

/**
 * 每一个正在执行的术式都作为一个流
 * 同一段代码可以开启多个流
 * 流不会做保存
 * 流必须要有一个事件节点作为开始
 * 流是一个状态机，可以处在不同节点的状态
 */
public class SpellStream {
    private final Map<String, SpellNodeEntry> nodes = new HashMap<>();
    private final DualHashBidiMap<SpellLinkEntry, SpellLinkEntry> links = new DualHashBidiMap<>();

    private SpellNodeEntry cur;
    private final Deque<SpellNodeEntry> nodeStack = new ArrayDeque<>();

    private final HashMap<SpellLinkEntry, SpellVariableEntry<?>> variables = new HashMap<>();

    private final MagicianComponents magician;

    private StreamStatus status = StreamStatus.STOP;

    public SpellStream(MagicianComponents magician, Map<String, SpellNodeEntry> nodes,
            Map<SpellLinkEntry, SpellLinkEntry> links, SpellNodeEntry node) {
        this.nodes.putAll(nodes);
        this.links.putAll(links);

        this.cur = node;

        this.magician = magician;
    }

    public void serverTick() {
        var node = cur.node;
        if (node instanceof IExecNode) {
            IExecNode exec = (IExecNode) node;
            exec.tick(this, cur, magician);
        }
    }

    public Optional<SpellLinkEntry> getPointLink(SpellNodeEntry entry, NodeIOPoint<?> point) {
        Map<SpellLinkEntry, SpellLinkEntry> map;
        if (point.io == IOType.INPUT) {
            map = links;
        } else {
            map = links.inverseBidiMap();
        }
        var other = map.get(new SpellLinkEntry(entry, point));
        if (other != null) {
            return Optional.of(other);
        }

        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public <D> Optional<SpellVariableEntry<D>> getPointVariable(SpellNodeEntry entry, NodeIOPoint<D> point) {
        assert !(point.varType instanceof SpellVariableNodeLink);
        var v = variables.get(new SpellLinkEntry(entry, point));

        if (v != null) {
            return Optional.of((SpellVariableEntry<D>) v);
        }

        return Optional.empty();
    }

    public <D> void setPointVariable(SpellNodeEntry entry, NodeIOPoint<D> point, D data) {
        assert entry.node.getType() == NodeType.PROCESS;
        variables.put(new SpellLinkEntry(entry, point), new SpellVariableEntry<D>(point.varType, data));
    }

    public StreamStatus getStatus() {
        return status;
    }

    public static enum StreamStatus {
        RUN, STOP
    }
}
