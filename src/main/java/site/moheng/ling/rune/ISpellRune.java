package site.moheng.ling.rune;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.SimpleRegistry;
import site.moheng.ling.LingMod;
import site.moheng.ling.components.IMagician;

import java.util.List;

public interface ISpellRune {
    SimpleRegistry<ISpellRune> RUNES = FabricRegistryBuilder
            .createSimple(ISpellRune.class, new Identifier(LingMod.MODID, "rune"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    static Identifier getRuneModelId(ISpellRune rune) {
        Identifier id = rune.getID();
        return getRuneModelId(id);
    }

    static Identifier getRuneModelId(Identifier id) {
        return new Identifier(id.getNamespace(), "item/rune/" + id.getPath());
    }

    default String getTranslationKey() {
        return Util.createTranslationKey("rune", getID());
    }

    default Text getRuneName() {
        return Text.translatable(getTranslationKey());
    }

    default Identifier getID() {
        return RUNES.getId(this);
    }

    default void appendRuneTooltip(final ItemStack stack, final List<Text> tooltip) {

    }

    default void exec(final IMagician magician) {

    }

    default Identifier getRuneModelId() {
        return getRuneModelId(this);
    }
}
