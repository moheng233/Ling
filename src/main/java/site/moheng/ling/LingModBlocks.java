package site.moheng.ling;

import io.wispforest.owo.registration.reflect.BlockRegistryContainer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import site.moheng.ling.blocks.ShelfBlock;
import site.moheng.ling.blocks.SpellEditTableBlock;

public class LingModBlocks implements BlockRegistryContainer {
    
    public static final SpellEditTableBlock SPELL_EDIT_TABLE_BLOCK = new SpellEditTableBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque());
    public static final ShelfBlock SHELF_BLOCK = new ShelfBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque());

    public static void init() {
        
    }

    @Override
    public BlockItem createBlockItem(Block block, String identifier) {
        return new BlockItem(block,new FabricItemSettings().group(LingMod.ITEM_GROUP));
    }
}
