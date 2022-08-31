package site.moheng.ling.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import site.moheng.ling.LingMod;
import site.moheng.ling.LingModItems;
import site.moheng.ling.rune.ISpellRune;

public class ItemDatagen extends FabricModelProvider {

    public ItemDatagen(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (var rune : ISpellRune.RUNES) {
            itemModelGenerator.register(LingModItems.SPELL_RUNE, ISpellRune.RUNES.getId(rune).toString(), Models.GENERATED);
        }
    }
    
}
