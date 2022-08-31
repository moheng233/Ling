package site.moheng.ling.rune;

import java.util.List;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.SimpleRegistry;
import site.moheng.ling.LingMod;

public interface ISpellRune {
    public static final SimpleRegistry<ISpellRune> RUNES = FabricRegistryBuilder
            .createSimple(ISpellRune.class, new Identifier(LingMod.MODID, "rune"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public default Text getRuneName() {
        return Text.translatable(Util.createTranslationKey("rune", RUNES.getId(this)));
    };

    public default void appendRuneTooltip(ItemStack stack, List<Text> tooltip) {
        
    }
}
