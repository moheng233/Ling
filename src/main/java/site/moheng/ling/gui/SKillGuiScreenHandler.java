package site.moheng.ling.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import site.moheng.ling.LingModGui;

public class SKillGuiScreenHandler extends ScreenHandler {

    public SKillGuiScreenHandler(int syncId, PlayerInventory inventory) {
        super(LingModGui.SKILL_GUI_SCREEN_HANDLER_TYPE, syncId);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity var1, int var2) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity var1) {
        return false;
    }

}
