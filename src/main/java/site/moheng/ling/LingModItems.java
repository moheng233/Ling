package site.moheng.ling;

import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import site.moheng.ling.items.SpellCodeItem;

public class LingModItems implements ItemRegistryContainer {
    
    public static final SpellCodeItem SPELL_CODE = new SpellCodeItem(new FabricItemSettings().group(LingMod.ITEM_GROUP)); 

    public static void init() {
        
    }
}
