package site.moheng.ling.gui.menu;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;

public abstract class AbsEditMenu extends DrawableHelper implements Element, Drawable {
    protected MinecraftClient client;
    public int x;
    public int y;
    
    public AbsEditMenu(MinecraftClient client,int x, int y) {
        this.client = client;
        this.x = x;
        this.y = y;
    }

    public void menuClose() {

    }
}
