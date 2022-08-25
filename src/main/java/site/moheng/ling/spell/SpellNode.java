package site.moheng.ling.spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.collections4.iterators.FilterIterator;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec2f;
import site.moheng.ling.LingMod;
import site.moheng.ling.SpellVariableTypes;
import site.moheng.ling.components.MagicianComponents;
import site.moheng.ling.gui.menu.AbsEditMenu;
import site.moheng.ling.spell.entry.SpellNodeEntry;
import site.moheng.ling.util.GridHelper;
import site.moheng.ling.util.MathRect;
import site.moheng.ling.util.NinePatchHelper;
import site.moheng.ling.util.GridHelper.GridCell;
import site.moheng.ling.util.GridHelper.GridHDir;
import site.moheng.ling.util.GridHelper.GridRow;
import site.moheng.ling.util.GridHelper.GridVDir;

public abstract class SpellNode {
    private static final Identifier EXTA = new Identifier(LingMod.MODID, "exta");

    @Environment(EnvType.CLIENT)
    private static final Identifier TEXTURE = new Identifier(LingMod.MODID, "textures/gui/spell_edit.png");

    @Environment(EnvType.CLIENT)
    private static final NinePatchHelper BACKAGE = new NinePatchHelper(TEXTURE, 0, 4, 12, 16, 48, 52, 60, 64);

    @Environment(EnvType.CLIENT)
    private Optional<Map<NodeIOPoint<?>, MathRect>> pointRectMap = Optional.empty();

    @Environment(EnvType.CLIENT)
    private GridHelper grid = new GridHelper(87, 0);

    protected final NodeIO io = new NodeIO();

    public Identifier getId() {
        return SpellRegistry.SPELL_NODE.getId(this);
    }

    @Environment(EnvType.CLIENT)
    public Text getName() {
        return Text.translatable(Util.createTranslationKey("spell_block", SpellRegistry.SPELL_NODE.getId(this)));
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(Consumer<Text> add) {

    }

    public abstract NodeType getType();

    @Environment(EnvType.CLIENT)
    public List<Text> getTooltip() {
        ArrayList<Text> list = Lists.newArrayList();
        list.add(Text.empty().append(this.getName()).formatted(getType().formatting));
        list.add(Text.empty().append(
                Text.translatable(Util.createTranslationKey("gui", new Identifier(LingMod.MODID, "spell_block.type")))
                        .append(Text.translatable(getType().key))));
        appendTooltip((text) -> list.add(text));
        return list;
    }

    @Environment(EnvType.CLIENT)
    public int getWidth() {
        return grid.getWidth();
    }

    @Environment(EnvType.CLIENT)
    public int getHeight() {
        return grid.getHeight();
    }

    @Environment(EnvType.CLIENT)
    public int getExtWidth() {
        return 0;
    }

    @Environment(EnvType.CLIENT)
    public int getExtHeight() {
        return 0;
    }

    @Environment(EnvType.CLIENT)
    public void layout(boolean first) {
        if (first) {
            var client = MinecraftClient.getInstance();
            grid.clear();
            var row = grid.createRow();
            row.createColumn(16, 16).setDir(GridHDir.LEFT, GridVDir.CENTER).link(TEXTURE);
            row.createColumn(client.textRenderer.getWidth(getName()), client.textRenderer.fontHeight)
                    .setDir(GridHDir.LEFT, GridVDir.CENTER)
                    .link(getName());

            int count = Math.max(io.getPointSize(IOType.INPUT), io.getPointSize(IOType.OUTPUT));
            grid.createTable(count, 4);
            for (int i = 0; i < count; i++) {
                grid.getColumn(1 + i, 0)
                        .setContentSize(6, 6)
                        .setDir(GridHDir.CENTER, GridVDir.CENTER);
                grid.getColumn(1 + i, 1)
                        .setFlex(1)
                        .setDir(GridHDir.LEFT, GridVDir.CENTER);
                // grid.getColumn(1 + i, 2).setContentSize(4, 8).setFlex(-1);
                grid.getColumn(1 + i, 2)
                        .setFlex(1)
                        .setDir(GridHDir.RIGHT, GridVDir.CENTER);
                grid.getColumn(1 + i, 3)
                        .setContentSize(6, 6)
                        .setDir(GridHDir.CENTER, GridVDir.CENTER);
            }
            {
                var i = 0;
                for (var point : io.getPoints(IOType.INPUT)) {
                    grid.getColumn(1 + i, 0).link(new IOPointID(point));
                    grid.getColumn(1 + i, 1)
                            .setContentSize(client.textRenderer.getWidth(point.name),
                                    Math.max(8, client.textRenderer.fontHeight))
                            .link(point);
                    ;
                    i += 1;
                }
            }
            for (int i = 0; i < count; i++) {
                grid.getColumn(1 + i, 2).setContentSize(4, 8);
            }
            {
                var i = 0;
                for (var point : io.getPoints(IOType.OUTPUT)) {
                    grid.getColumn(1 + i, 3).link(new IOPointID(point));
                    grid.getColumn(1 + i, 2)
                            .setContentSize(client.textRenderer.getWidth(point.name),
                                    Math.max(8, client.textRenderer.fontHeight))
                            .link(point);

                    i += 1;
                }
            }
            grid.createRow().createColumn(getExtWidth(), getExtHeight()).link(EXTA);
            ;
        }

        grid.layout();
    }

    @Environment(EnvType.CLIENT)
    public void drawNode(MatrixStack matrices, DrawableHelper helper, TextRenderer textRenderer, NbtCompound exta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        BACKAGE.draw(matrices, 0, 0, getWidth(), getHeight());
        matrices.push();
        GridCell cell = grid.getCell(TEXTURE);
        helper.drawTexture(matrices, cell.getX(), cell.getY(), 32, 208, cell.contentWidth, cell.contentWidth);
        cell = grid.getCell(getName());
        textRenderer.drawWithShadow(matrices, getName(), cell.getX(), cell.getY(), 0xFFFFFF);
        var io = getNodeIO();
        for (var point : io.getPoints()) {
            drawIOPoint(matrices, point, helper, textRenderer);
        }
        matrices.push();
        cell = grid.getCell(EXTA);
        matrices.translate(cell.getX(), cell.getY(), 0);
        drawExta(matrices, helper, textRenderer, exta);
        matrices.pop();
        matrices.pop();
    }

    @Environment(EnvType.CLIENT)
    protected void drawExta(MatrixStack matrices, DrawableHelper helper, TextRenderer textRenderer, NbtCompound exta) {

    }

    @Environment(EnvType.CLIENT)
    public Optional<AbsEditMenu> getExtaMenu(MinecraftClient client, Vec2f pos, NbtCompound exta) {
        return Optional.empty();
    }

    @Environment(EnvType.CLIENT)
    protected void drawIOPoint(MatrixStack matrices, NodeIOPoint<?> point, DrawableHelper helper,
            TextRenderer textRenderer) {
        GridCell column = grid.getCell(point);
        textRenderer.drawWithShadow(matrices, point.name, column.getX(), column.getY(), 0xffffffff);
        column = grid.getCell(new IOPointID(point));
        DrawableHelper.fill(matrices, column.getX(), column.getY(), column.getX() + 6, column.getY() + 6,
                point.varType.getDisplayColor());
    }

    @Environment(EnvType.CLIENT)
    public MathRect getRect(Vec2f postion) {
        return new MathRect(postion, getWidth(), getHeight());
    }

    @Environment(EnvType.CLIENT)
    public MathRect getRect() {
        return getRect(Vec2f.ZERO);
    }

    @Environment(EnvType.CLIENT)
    public Map<NodeIOPoint<?>, MathRect> getPointRects() {
        if (pointRectMap.isEmpty()) {
            var map = new HashMap<NodeIOPoint<?>, MathRect>();

            for (var point : io.getPoints()) {
                map.put(point, grid.getCell(new IOPointID(point)).getRect());
            }

            pointRectMap = Optional.of(map);
        }

        return pointRectMap.get();
    }

    @Environment(EnvType.CLIENT)
    public MathRect getPointRect(NodeIOPoint<?> point) {
        return grid.getCell(new IOPointID(point)).getRect();
    }

    /**
     * 获得Block的输出与输出参数
     * 
     * @return
     */
    public NodeIO getNodeIO() {
        return io;
    }

    @Override
    public String toString() {
        return getId().toString();
    }

    /**
     * 表示Block的一个IO的输出
     * 
     */
    public static final class NodeIO {
        public final LinkedHashMap<String, NodeIOPoint<?>> points = new LinkedHashMap<>();

        public NodeIO() {

        }

        public NodeIO define(NodeIOPoint<?> point) {
            points.put(point.name, point);

            return this;
        }

        public NodeIOPoint<?> getPoint(String name) {
            return points.get(name);
        }

        public int getPointSize(IOType io) {
            int size = 0;
            for (var point : points.values()) {
                if (point.io == io) {
                    size += 1;
                }
            }

            return size;
        }

        public int getPointSize() {
            return points.size();
        }

        public Iterable<NodeIOPoint<?>> getPoints(IOType io) {
            return new Iterable<NodeIOPoint<?>>() {

                @Override
                public Iterator<NodeIOPoint<?>> iterator() {
                    return new FilterIterator<>(points.values().iterator(), (v) -> {
                        return v.io == io;
                    });
                }

            };
        }

        public Iterable<NodeIOPoint<?>> getPoints() {
            return points.values();
        }
    }

    /**
     * 节点中的一个链接点
     */
    public static final class NodeIOPoint<T> {
        public String name;
        public IOType io;
        public SpellVariableType<T> varType;

        protected NodeIOPoint(String name, IOType io, SpellVariableType<T> varType) {
            this.name = name;
            this.io = io;
            this.varType = varType;
        }

        public static <D> NodeIOPoint<D> create(String name, IOType io, SpellVariableType<D> type) {
            return new NodeIOPoint<>(name, io, type);
        }

        public static NodeIOPoint<SpellNodeEntry> create(String name, IOType io) {
            return new NodeIOPoint<>(name, io, SpellVariableTypes.NODE_LINK);
        }

        public boolean isProcessPoint() {
            return varType == SpellVariableTypes.NODE_LINK;
        }

        public boolean isVarPoint() {
            return varType != SpellVariableTypes.NODE_LINK;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((io == null) ? 0 : io.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
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
            NodeIOPoint<?> other = (NodeIOPoint<?>) obj;
            if (io != other.io)
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }

        @Environment(EnvType.CLIENT)
        public void render(MatrixStack matriers, int mouseX, int mouseY, float delta) {

        }
    }

    public static enum IOType {
        INPUT,
        OUTPUT;

        public static boolean canLink(IOType io1, IOType io2) {
            if (io1 == INPUT && io2 == OUTPUT) {
                return true;
            }
            if (io1 == OUTPUT && io2 == INPUT) {
                return true;
            }
            return false;
        }
    }

    public static enum NodeType {
        PROCESS(0xff0000ff, Formatting.BLUE, true,
                Util.createTranslationKey("gui", new Identifier(LingMod.MODID, "spell_block.type.process"))),
        HANDLE(0xff00ff00, Formatting.GREEN, true,
                Util.createTranslationKey("gui", new Identifier(LingMod.MODID, "spell_block.type.handle")));

        public final int color;
        public final Formatting formatting;
        public final boolean hasChildBlock;
        public final String key;

        private NodeType(int color, Formatting formatting, boolean hasChildBlock, String key) {
            this.color = color;
            this.formatting = formatting;
            this.hasChildBlock = hasChildBlock;
            this.key = key;
        }
    }

    private static record IOPointID(NodeIOPoint<?> point) {

    }

    public static interface IExecNode {
        public default void tick(SpellStream stream, SpellNodeEntry entry,MagicianComponents magician) {
        }
    }
}
