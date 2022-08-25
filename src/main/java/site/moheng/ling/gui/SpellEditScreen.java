package site.moheng.ling.gui;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import com.mojang.blaze3d.systems.RenderSystem;

import io.wispforest.owo.nbt.NbtKey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.command.TranslatableBuiltInExceptions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3i;
import site.moheng.ling.LingMod;
import site.moheng.ling.SpellNodes;
import site.moheng.ling.gui.menu.AbsEditMenu;
import site.moheng.ling.gui.menu.NodeEditMenu;
import site.moheng.ling.spell.SpellNode;
import site.moheng.ling.spell.SpellRegistry;
import site.moheng.ling.spell.SpellNode.IOType;
import site.moheng.ling.spell.SpellNode.NodeIOPoint;
import site.moheng.ling.spell.entry.SpellLinkEntry;
import site.moheng.ling.spell.entry.SpellNodeEntry;
import site.moheng.ling.util.MathRect;
import site.moheng.ling.util.MatrixHelper;
import site.moheng.ling.util.NanoIdUtils;
import site.moheng.ling.util.NinePatchHelper;

@Environment(EnvType.CLIENT)
public class SpellEditScreen extends Screen {
    private static final NbtKey<NbtCompound> KEY_NODE_MAP = new NbtKey<>("node_map", NbtKey.Type.COMPOUND);
    private static final NbtKey<NbtCompound> KEY_POS_MAP = new NbtKey<>("pos_map", NbtKey.Type.COMPOUND);
    private static final NbtKey<NbtCompound> KEY_LINK_MAP = new NbtKey<>("link_map", NbtKey.Type.COMPOUND);

    protected int editWidth = 400;
    protected int editHeight = 230;

    public static final Identifier TEXTURE = new Identifier(LingMod.MODID, "textures/gui/spell_edit.png");
    public static final NinePatchHelper BACKGROUND = new NinePatchHelper(TEXTURE, 0, 8, 40, 48, 0, 8, 40, 48);

    private Optional<SpellNodeEntry> mouseDragNode = Optional.empty();
    private Optional<SpellLinkEntry> mouseLinkPoint = Optional.empty();
    private final HashMap<String, SpellNodeEntry> nodeMap = new HashMap<>();
    private final HashMap<SpellNodeEntry, Vec2f> posMap = new HashMap<>();
    private final DualHashBidiMap<SpellLinkEntry, SpellLinkEntry> linkMap = new DualHashBidiMap<>();

    private double editOffsetX = 0;
    private double editOffsetY = 0;
    private float editScale = 1;

    private Consumer<NbtCompound> onSave = (nbt) -> {
    };

    private Optional<AbsEditMenu> menu = Optional.empty();

    public SpellEditScreen(Consumer<NbtCompound> onSave) {
        super(Text.translatable(Util.createTranslationKey("gui", new Identifier(LingMod.MODID, "edit_screen"))));

        this.onSave = onSave;

        for (var node : SpellRegistry.SPELL_NODE) {
            node.layout(true);
        }
    }

    public static SpellEditScreen readAndOnSave(NbtCompound nbt, Consumer<NbtCompound> onSave) {
        var edit = new SpellEditScreen(onSave);
        edit.readFromNbt(nbt);
        return edit;
    }

    public Vec3i getStartPos() {
        return new Vec3i((width - editWidth) / 2, (height - editHeight) / 2, 0);
    }

    public int getStartX() {
        return (width - editWidth) / 2;
    }

    public int getStartY() {
        return (height - editHeight) / 2;
    }

    /**
     * 获取编辑区的矩形
     * 
     * @return
     */
    public MathRect getEditRect() {
        return new MathRect(getStartX() + BACKGROUND.lPadding, getStartY() + BACKGROUND.tPadding,
                getStartX() + editWidth - BACKGROUND.rPadding,
                getStartY() + editHeight - BACKGROUND.bPadding);
    }

    @Override
    public void close() {
        onSave.accept(saveToNbt());
        super.close();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.disableDepthTest();
        drawBackground(matrices, delta, mouseX, mouseY);

        drawEditRect(matrices, getStartX(), getStartY(), editOffsetX,
                editOffsetY, editScale, editWidth, editHeight,
                mouseX, mouseY);

        if (menu.isPresent()) {
            menu.get().render(matrices, mouseX, mouseY, delta);
        }

        RenderSystem.enableDepthTest();
    }

    protected void drawEditRect(MatrixStack matrices, int x, int y, double editOffsetX, double editOffsetY, float scale,
            int width,
            int height, int mouseX, int mouseY) {
        matrices.push();
        matrices.translate(x, y, 0);
        {
            var pos = MatrixHelper.getPosVec(matrices);
            enableScissor(pos.getX(), pos.getY(), pos.getX() + width, pos.getY() + height);
        }
        matrices.push();
        // 这里进行缩放和偏移处理
        matrices.translate(editOffsetX, editOffsetY, 0);
        matrices.scale(scale, scale, 1);
        drawCenteredText(matrices, textRenderer, Text.of("X"), 0, 0, 0xffffff);
        // TODO 目前是全部渲染，后面需要根据坐标选择部分渲染
        for (var entry : nodeMap.values()) {
            matrices.push();
            Vec2f postion = posMap.getOrDefault(entry, Vec2f.ZERO);
            matrices.translate(postion.x, postion.y, 0);
            drawNode(matrices, entry);
            matrices.pop();
        }
        for (var entry : linkMap.entrySet()) {
            var one = entry.getKey().node.getPoint(entry.getKey().point).add(posMap.get(entry.getKey().node)).center();
            var two = entry.getValue().node.getPoint(entry.getValue().point).add(posMap.get(entry.getValue().node))
                    .center();
            drawLine(matrices, one.x, one.y, two.x, two.y, entry.getKey().point.varType.getDisplayColor());
        }
        // 绘制鼠标拖动时的链接线
        if (mouseLinkPoint.isPresent()) {
            matrices.push();
            var point = mouseLinkPoint.get().node.getPoint(mouseLinkPoint.get().point)
                    .add(posMap.get(mouseLinkPoint.get().node)).center();
            var mouse = convertEditPos(new Vec2f(mouseX, mouseY));
            drawLine(matrices, point.x, point.y, mouse.x, mouse.y,
                    mouseLinkPoint.get().point.varType.getDisplayColor());
            matrices.pop();
        }
        matrices.pop();
        disableScissor();
        matrices.pop();
    }

    protected void drawLine(MatrixStack matrices, float x, float y, float x1, float y1, int color) {
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        var matrix = matrices.peek().getPositionMatrix();
        var normal = matrices.peek().getNormalMatrix();
        var builder = Tessellator.getInstance().getBuffer();
        RenderSystem.lineWidth(5f);
        builder.begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        builder.vertex(matrix, x, y, 1).color(color).next();
        builder.vertex(matrix, x1, y1, 1).color(color).next();
        BufferRenderer.drawWithShader(builder.end());
        RenderSystem.enableTexture();
        RenderSystem.lineWidth(1f);
    }

    /**
     * 将屏幕坐标转换为编辑栏坐标
     * 
     * @param screenPos 屏幕坐标
     * @return
     */
    protected Vec2f convertEditPos(Vec2f screenPos) {
        return new Vec2f(screenPos.x - getStartX() - (float) editOffsetX,
                screenPos.y - getStartY() - (float) editOffsetY).multiply(1 / editScale);
    }

    /**
     * 将屏幕坐标转换为编辑栏坐标
     * 
     * @param screenPos 屏幕坐标
     * @return
     */
    protected Vec2f convertEditPosNoScale(Vec2f screenPos) {
        return new Vec2f(screenPos.x - getStartX() - (float) editOffsetX,
                screenPos.y - getStartY() - (float) editOffsetY);
    }

    protected Optional<SpellLinkEntry> getNodePointBy(Vec2f pos) {
        for (var entry : nodeMap.values()) {
            for (var point : entry.getPoints().entrySet()) {
                if (point.getValue().add(posMap.get(entry)).contains(pos)) {
                    return Optional.of(new SpellLinkEntry(entry, point.getKey()));
                }
            }
        }

        return Optional.empty();
    }

    /**
     * 使用编辑框内部坐标检索节点
     * 
     * @param pos 内部坐标
     * @return
     */
    protected Optional<SpellNodeEntry> getNodeEntryBy(Vec2f pos) {
        for (var entry : nodeMap.values()) {
            if (entry.getRect().add(posMap.get(entry)).contains(pos)) {
                return Optional.of(entry);
            }
        }

        return Optional.empty();
    }

    /**
     * 使用编辑框内部坐标检索节点
     * 
     * @param pos 内部坐标
     * @return
     */
    protected Optional<SpellNodeEntry> getNodeEntryBy(Vec3i pos) {
        return getNodeEntryBy(new Vec2f(pos.getX(), pos.getY()));
    }

    /**
     * 使用编辑框内部坐标检索节点
     * 
     * @param x
     * @param y
     * @return
     */
    protected Optional<SpellNodeEntry> getNodeEntryBy(int x, int y) {
        return getNodeEntryBy(new Vec2f(x, y));
    }

    /**
     * 从屏幕空间的坐标转换为内部坐标并检索节点
     * 
     * @param screenPos
     * @return
     */
    protected Optional<SpellNodeEntry> getNodeEntryByScreen(Vec2f screenPos) {
        return getNodeEntryBy(convertEditPos(screenPos));
    }

    /**
     * 从屏幕空间的坐标转换为内部坐标并检索IO连接点
     * 
     * @param screenPos
     * @return
     */
    protected Optional<SpellLinkEntry> getNodePointByScreen(Vec2f screenPos) {
        return getNodePointBy(convertEditPos(screenPos));
    }

    /**
     * 添加一个节点
     * 
     * @param pos  节点所在的位置
     * @param node 节点的类型
     * @return 新添加节点的id
     */
    protected String addNode(Vec2f pos, SpellNode node) {
        var id = NanoIdUtils.randomNanoId();
        addNode(id, pos, node);
        return id;
    }

    protected void addNode(String id, Vec2f pos, SpellNode node) {
        var entry = new SpellNodeEntry(id, node);
        nodeMap.put(id, entry);
        posMap.put(entry, pos);
    }

    protected void moveNode(SpellNodeEntry node, Vec2f pos) {
        posMap.put(node, pos);
    }

    /**
     * 移动节点
     * 
     * @param id  节点的ID
     * @param pos 节点的新位置
     */
    protected void moveNode(String id, Vec2f pos) {
        moveNode(nodeMap.get(id), pos);
    }

    protected void deleteNode(SpellNodeEntry entry) {
        Set<SpellLinkEntry> set = linkMap.keySet();
        for (int i = 0; i < 2; i++) {
            for (var node : set) {
                if (node.node == entry) {
                    unLinkNodePoint(node);
                }
            }

            set = linkMap.values();
        }

        nodeMap.remove(entry.id);
    }

    /**
     * 删除对应id的节点
     * 
     * @param id
     */
    protected void deleteNode(String id) {
        deleteNode(nodeMap.get(id));
    }

    protected void drawNode(MatrixStack matrices, SpellNodeEntry entry) {
        entry.node.drawNode(matrices, this, textRenderer, entry.extend);
    }

    protected boolean linkNodePoint(SpellNodeEntry form, NodeIOPoint<?> formPoint, SpellNodeEntry target,
            NodeIOPoint<?> targePoint) {
        if (formPoint.io == IOType.OUTPUT) {
            if (formPoint.varType.canLink(targePoint.varType)) {
                linkMap.put(new SpellLinkEntry(form, formPoint), new SpellLinkEntry(target, targePoint));
                return true;
            }
        } else {
            if (targePoint.varType.canLink(formPoint.varType)) {
                linkMap.put(new SpellLinkEntry(target, targePoint), new SpellLinkEntry(form, formPoint));
                return true;
            }
        }

        return false;

    }

    protected void unLinkNodePoint(SpellLinkEntry link) {
        BidiMap<SpellLinkEntry, SpellLinkEntry> map = linkMap;
        for (int i = 0; i < 2; i++) {
            var target = map.get(link);
            if (target != null) {
                map.remove(link);
            }
            map = map.inverseBidiMap();
        }

    }

    protected Optional<SpellLinkEntry> nodePointHasLink(SpellLinkEntry link) {
        BidiMap<SpellLinkEntry, SpellLinkEntry> map = linkMap;
        for (int i = 0; i < 2; i++) {
            var target = map.get(link);
            if (target != null) {
                return Optional.of(target);
            }

            map = map.inverseBidiMap();
        }

        return Optional.empty();
    }

    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.editWidth) / 2;
        int j = (this.height - this.editHeight) / 2;
        BACKGROUND.draw(matrices, i, j, this.editWidth, this.editHeight);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (menu.isEmpty()) {

        } else {
            menu.get().charTyped(chr, modifiers);
        }
        return super.charTyped(chr, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (menu.isEmpty()) {
            int i = (int) (this.client.mouse.getX() * (double) this.client.getWindow().getScaledWidth()
                    / (double) this.client.getWindow().getWidth());
            int j = (int) (this.client.mouse.getY() * (double) this.client.getWindow().getScaledHeight()
                    / (double) this.client.getWindow().getHeight());
            var mouse = new Vec2f(i, j);
            if (keyCode == InputUtil.GLFW_KEY_DELETE && getEditRect().contains(mouse)) {
                var node = getNodeEntryByScreen(mouse);
                if (node.isPresent()) {
                    deleteNode(node.get());
                }
            }
        } else {
            menu.get().keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (menu.isEmpty()) {
            var mouse = new Vec2f((float) mouseX, (float) mouseY);
            var last_pos = convertEditPos(mouse);
            editScale = (float) MathHelper.clamp(editScale + amount * 0.1, 0.2, 2);
            var now_pos = convertEditPos(mouse);
            editOffsetX += (now_pos.x - last_pos.x);
            editOffsetY += (now_pos.y - last_pos.y);
        } else {
            menu.get().mouseScrolled(mouseX, mouseY, amount);
        }
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (menu.isEmpty()) {
            if (button == 0) {
                var mouse = new Vec2f((float) mouseX, (float) mouseY);

                if (getEditRect().contains(mouse)) {
                    // 节点IO链接检测
                    var point = getNodePointByScreen(mouse);
                    if (point.isPresent()) {
                        var hasLink = nodePointHasLink(point.get());
                        if (hasLink.isPresent()) {
                            unLinkNodePoint(point.get());
                            mouseLinkPoint = hasLink;
                        } else {
                            mouseLinkPoint = point;
                        }
                        return true;
                    }

                    // 节点拖动检测
                    var entry = getNodeEntryByScreen(mouse);
                    if (entry.isPresent()) {
                        mouseDragNode = entry;
                        return true;
                    }
                }
            }

            if (button == 1) {
                if (menu.isEmpty()) {
                    var mouse = new Vec2f((float) mouseX, (float) mouseY);
                    var entry = getNodeEntryByScreen(mouse);
                    if (entry.isPresent()) {
                        menu = entry.get().node.getExtaMenu(client, mouse, entry.get().extend);
                    } else {
                        menu = Optional.of(new NodeEditMenu(client, (int) mouseX, (int) mouseY, (node, offset) -> {
                            menu = Optional.empty();
                            addNode(convertEditPos(offset), node);
                        }));
                    }
                }
            }
        } else {
            if (!menu.get().mouseClicked(mouseX, mouseY, button)) {
                menu.get().menuClose();
                menu = Optional.empty();
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (menu.isEmpty()) {
            if (mouseDragNode.isPresent()) {
                mouseDragNode = Optional.empty();
            }

            if (mouseLinkPoint.isPresent()) {
                var target = getNodePointByScreen(new Vec2f((float) mouseX, (float) mouseY));
                if (target.isPresent()) {
                    if (IOType.canLink(mouseLinkPoint.get().point.io, target.get().point.io)) {
                        linkNodePoint(mouseLinkPoint.get().node, mouseLinkPoint.get().point, target.get().node,
                                target.get().point);
                    }
                }

                mouseLinkPoint = Optional.empty();
            }
        } else {
            menu.get().mouseReleased(mouseX, mouseY, button);
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (menu.isEmpty()) {
            if (button == 0 && mouseLinkPoint.isPresent()) {
                return true;
            }

            if (button == 0 && mouseDragNode.isPresent()) {
                var entry = mouseDragNode.get();
                var postion = posMap.get(entry);
                moveNode(entry, new Vec2f(postion.x + (float) deltaX / editScale,
                        postion.y + (float) deltaY / editScale));

                return true;
            }

            if (mouseDragNode.isEmpty()) {
                if (button == 0) {
                    editOffsetX += deltaX;
                    editOffsetY += deltaY;

                    return true;
                }
            }
        } else {
            menu.get().mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    public NbtCompound saveToNbt() {
        var nbt = new NbtCompound();
        {
            var node_nbt = new NbtCompound();
            nodeMap.forEach((id, entry) -> {
                node_nbt.put(id, entry.toNbt());
            });
            nbt.put(KEY_NODE_MAP, node_nbt);
        }
        {
            var pos_nbt = new NbtCompound();
            posMap.forEach((entry, pos) -> {
                pos_nbt.putFloat(entry.id + "_x", pos.x);
                pos_nbt.putFloat(entry.id + "_y", pos.y);
            });
            nbt.put(KEY_POS_MAP, pos_nbt);
        }
        {
            var link_nbt = new NbtCompound();
            linkMap.forEach((from, to) -> {
                link_nbt.putString(from.toString(), to.toString());
            });
            nbt.put(KEY_LINK_MAP, link_nbt);
        }

        return nbt;
    }

    public void readFromNbt(NbtCompound nbt) {
        nodeMap.clear();
        posMap.clear();
        linkMap.clear();

        {
            var node_nbt = nbt.get(KEY_NODE_MAP);
            for (var key : node_nbt.getKeys()) {
                nodeMap.put(key, SpellNodeEntry.fromNbt(node_nbt.getCompound(key)));
            }
        }
        {
            var pos_nbt = nbt.get(KEY_POS_MAP);
            for (var key : nodeMap.values()) {
                posMap.put(key, new Vec2f(pos_nbt.getFloat(key.id + "_x"), pos_nbt.getFloat(key.id + "_y")));
            }
        }
        {
            var link_nbt = nbt.get(KEY_LINK_MAP);
            for (var key : link_nbt.getKeys()) {
                var key_s = key.split("\\$");
                var val_s = link_nbt.getString(key).split("\\$");

                var from = nodeMap.get(key_s[0]);
                var next = nodeMap.get(val_s[0]);

                linkMap.put(new SpellLinkEntry(from, from.node.getNodeIO().getPoint(key_s[1])),
                        new SpellLinkEntry(next, next.node.getNodeIO().getPoint(val_s[1])));
            }
        }
    }
}
