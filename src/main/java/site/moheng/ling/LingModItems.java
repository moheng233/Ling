package site.moheng.ling;

import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import site.moheng.ling.items.SpellCardItem;
import site.moheng.ling.items.SpellRingItem;
import site.moheng.ling.items.SpellRuneItem;

public class LingModItems implements ItemRegistryContainer {
    
    public static final SpellRingItem SPELL_RING = new SpellRingItem(new FabricItemSettings().group(LingMod.ITEM_GROUP));
    public static final SpellCardItem SPELL_CARD = new SpellCardItem(new FabricItemSettings().group(LingMod.ITEM_GROUP));
    public static final SpellRuneItem SPELL_RUNE = new SpellRuneItem(new FabricItemSettings().group(LingMod.ITEM_GROUP));
    public static void init() {
        
    }
}
