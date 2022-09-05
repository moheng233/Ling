package site.moheng.ling;

import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.util.registry.Registry;
import site.moheng.ling.rune.ISpellRune;
import site.moheng.ling.rune.RuneGetCrosshaireTarget;
import site.moheng.ling.rune.RuneGetMe;
import site.moheng.ling.rune.RuneGetPos;
import site.moheng.ling.rune.RuneRemoveFirst;
import site.moheng.ling.rune.RuneVoid;

public class Runes implements AutoRegistryContainer<ISpellRune> {
    public static final RuneVoid VOID = new RuneVoid();
    public static final RuneGetMe GET_ME = new RuneGetMe();
    public static final RuneRemoveFirst REMOVE_FIRST = new RuneRemoveFirst();
    public static final RuneGetPos GET_POS = new RuneGetPos();
    public static final RuneGetCrosshaireTarget GET_CROSSHAIRE_TARGET = new RuneGetCrosshaireTarget();

    @Override
    public Class<ISpellRune> getTargetFieldType() {
        return ISpellRune.class;
    }

    @Override
    public Registry<ISpellRune> getRegistry() {
        return ISpellRune.RUNES;
    }
    
}
