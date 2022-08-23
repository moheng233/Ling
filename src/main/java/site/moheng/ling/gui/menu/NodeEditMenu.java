package site.moheng.ling.gui.menu;

import java.util.HashMap;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import site.moheng.ling.gui.SpellEditScreen;
import site.moheng.ling.spell.SpellNode;
import site.moheng.ling.spell.SpellRegistry;
import site.moheng.ling.util.MathRect;
import site.moheng.ling.util.NinePatchHelper;

/**
 * 选择节点的菜单
 */
public class NodeEditMenu extends AbsEditMenu {

    public static final NinePatchHelper BACKGROUND = SpellEditScreen.BACKGROUND;

    public final int width = 80;
    public final int height = 100;
    public final float scale = 0.6f;

    public float yOffset = 0;

    public final HashMap<SpellNode, MathRect> nodeMap = new HashMap<>();

    public final NodeEditMenuOnSelected onNodeSelected;

    public NodeEditMenu(MinecraftClient client, int x, int y, NodeEditMenuOnSelected onNodeSelected) {
        super(client, x, y);
        this.onNodeSelected = onNodeSelected;
        var count_y = 0;
        for (var node : SpellRegistry.SPELL_NODE) {
            nodeMap.put(node, node.getRect(new Vec2f(5, count_y)));
            count_y += node.getHeight() * scale + 5;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        matrices.translate(x, y, 0);
        BACKGROUND.draw(matrices, 0, 0, width, height);
        enableScissor(x, y, x + width - BACKGROUND.rPadding,
                y + height - BACKGROUND.bPadding);
        matrices.translate(0, -yOffset, 0);
        nodeMap.forEach((node, rect) -> {
            matrices.push();
            matrices.translate(rect.getX(), rect.getY(), 0);
            matrices.scale(scale, scale, 1);
            node.drawNode(matrices, this, client.textRenderer, new NbtCompound());
            matrices.pop();
        });
        disableScissor();
        matrices.pop();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        var mouse = new Vec2f((float) mouseX - x, (float) mouseY - y);
        for (var entry : nodeMap.entrySet()) {
            if (entry.getValue().add(new Vec2f(0, -yOffset)).contains(mouse)) {
                onNodeSelected.accept(entry.getKey(), new Vec2f((float) mouseX, (float) mouseY));
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        yOffset = (float) MathHelper.clamp(yOffset + amount * -8, 0, 2000);

        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @FunctionalInterface
    public static interface NodeEditMenuOnSelected {
        public void accept(SpellNode node, Vec2f offset);
    }
}
