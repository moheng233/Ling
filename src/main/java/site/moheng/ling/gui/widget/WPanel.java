package site.moheng.ling.gui.widget;

import java.util.LinkedHashSet;
import java.util.LinkedList;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import site.moheng.ling.LingMod;
import site.moheng.ling.util.NinePatchHelper;

/**
 * 带有九宫格背景的面板组件
 */
public class WPanel extends AbsParentWidget {
    public static final Identifier TEXTURE = new Identifier(LingMod.MODID, "textures/gui/spell_edit.png");

    protected NinePatchHelper backage = new NinePatchHelper(TEXTURE, 0, 8, 40, 48, 0, 8, 40, 48);

    protected int width;
    protected int height;

    public WPanel(NinePatchHelper backage, int width, int height, AbsWidget ...widgets) {
        super(widgets);
        this.backage = backage;
        this.width = width;
        this.height = height;
    }

    public WPanel(int width, int height, AbsWidget ...widgets) {
        super(widgets);
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        backage.draw(matrices, getX(), getY(), getWidth(), getHeight());
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getX(AbsWidget widget) {
        return 0;
    }

    @Override
    public int getY(AbsWidget widget) {
        return 0;
    }
}
