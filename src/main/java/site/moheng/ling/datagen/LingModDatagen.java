package site.moheng.ling.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import site.moheng.ling.LingMod;
import site.moheng.ling.LingModBlocks;
import site.moheng.ling.LingModItems;
import site.moheng.ling.mixin.ItemModelGeneratorMixin;
import site.moheng.ling.rune.ISpellRune;

public class LingModDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        LingMod.LOGGER.info("datagen");
        fabricDataGenerator.addProvider(TagDatagen::new);
        fabricDataGenerator.addProvider(ItemDatagen::new);
    }

    public static class ItemDatagen extends FabricModelProvider {

        public ItemDatagen(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            blockStateModelGenerator.registerSimpleCubeAll(LingModBlocks.SPELL_EDIT_TABLE_BLOCK);
            blockStateModelGenerator.registerSimpleState(LingModBlocks.SHELF_BLOCK);
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            itemModelGenerator.register(LingModItems.SPELL_RUNE, Models.GENERATED);
            for (var rune : ISpellRune.RUNES) {
                Models.GENERATED.upload(ISpellRune.getRuneModelId(rune),
                        TextureMap.layer0(ISpellRune.getRuneModelId(rune)),
                        ((ItemModelGeneratorMixin) itemModelGenerator).getWriter());
            }
        }

    }

    public static class TagDatagen extends FabricTagProvider<ISpellRune> {

        public TagDatagen(FabricDataGenerator dataGenerator) {
            super(dataGenerator, ISpellRune.RUNES);
        }

        @Override
        protected void generateTags() {

        }

    }
}
