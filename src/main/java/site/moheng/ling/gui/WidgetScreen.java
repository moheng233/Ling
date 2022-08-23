package site.moheng.ling.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import site.moheng.ling.gui.widget.WButtom;
import site.moheng.ling.gui.widget.WContainer;
import site.moheng.ling.gui.widget.WLable;
import site.moheng.ling.gui.widget.WPanel;
import site.moheng.ling.gui.widget.WScreen;
import site.moheng.ling.gui.widget.WSpellEdit;
import site.moheng.ling.gui.widget.WContainer.LayoutDirection;

public class WidgetScreen extends Screen {

    protected WScreen root;

    public WidgetScreen() {
        super(Text.of("Text"));
    }

    @Override
    protected void init() {
        super.init();
        root = new WScreen(width, height);
        root.add(new WPanel(300, 200,
                new WSpellEdit(300, 200, new WContainer(LayoutDirection.VERTICAL,
                        new WContainer(LayoutDirection.HORIZONTAL,
                                new WLable(Text.of("11")),
                                new WLable(Text.of("12"))),
                        new WLable(Text.of("2")),
                        new WLable(Text.of("3")),
                        new WButtom(13, 23, Text.of("测试"))))));
        root.layout();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        root.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return root.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return root.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        root.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return root.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return root.mouseScrolled(mouseX, mouseY, amount);
    }
}
