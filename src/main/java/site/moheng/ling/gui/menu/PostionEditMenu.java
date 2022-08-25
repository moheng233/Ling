package site.moheng.ling.gui.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import site.moheng.ling.gui.SpellEditScreen;
import site.moheng.ling.util.NinePatchHelper;

public class PostionEditMenu extends AbsEditMenu implements ParentElement {
    public static final NinePatchHelper BACKGROUND = SpellEditScreen.BACKGROUND;

    public List<Element> childrens = new ArrayList<>();
    public List<Drawable> drawables = new ArrayList<>();

    public TextFieldWidget xFieldWidget;
    public TextFieldWidget yFieldWidget;
    public TextFieldWidget zFieldWidget;

    @Nullable
    private Element focused;
    private boolean dragging;

    public final static int width = 150;
    public final static int height = 30;

    public Consumer<BlockPos> onSave = (pos) -> {
    };

    public PostionEditMenu(MinecraftClient client, int x, int y, BlockPos pos, Consumer<BlockPos> onSave) {
        super(client, x, y);
        {
            int startX = this.x + BACKGROUND.lPadding;
            int startY = this.y - BACKGROUND.tPadding + height - BACKGROUND.vPadding;
            int fieldW = (width - BACKGROUND.hPadding) / 3;
            int fieldH = height - BACKGROUND.vPadding;
            xFieldWidget = new TextFieldWidget(client.textRenderer, startX, startY, fieldW, fieldH, Text.of("Vector"));
            startX += fieldW;
            yFieldWidget = new TextFieldWidget(client.textRenderer, startX, startY, fieldW, fieldH, Text.of("Vector"));
            startX += fieldW;
            zFieldWidget = new TextFieldWidget(client.textRenderer, startX, startY, fieldW, fieldH, Text.of("Vector"));
        }

        childrens.add(xFieldWidget);
        childrens.add(yFieldWidget);
        childrens.add(zFieldWidget);
        drawables.add(xFieldWidget);
        drawables.add(yFieldWidget);
        drawables.add(zFieldWidget);

        xFieldWidget.setText(String.valueOf(pos.getX()));
        yFieldWidget.setText(String.valueOf(pos.getY()));
        zFieldWidget.setText(String.valueOf(pos.getZ()));

        this.onSave = onSave;
    }

    @Override
    public void menuClose() {
        onSave.accept(new BlockPos(Integer.parseInt(xFieldWidget.getText()), Integer.parseInt(yFieldWidget.getText()), Integer.parseInt(zFieldWidget.getText())));
        super.menuClose();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delat) {
        BACKGROUND.draw(matrices, x, y, width, height);

        for (var drawable : drawables) {
            drawable.render(matrices, mouseX, mouseY, delat);
        }
    }

    @Override
    public final boolean isDragging() {
        return this.dragging;
    }

    @Override
    public final void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    @Override
    @Nullable
    public Element getFocused() {
        return this.focused;
    }

    @Override
    public void setFocused(@Nullable Element focused) {
        this.focused = focused;
    }

    @Override
    public List<? extends Element> children() {
        return childrens;
    }

}
