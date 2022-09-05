package site.moheng.ling;

import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import site.moheng.ling.blocks.ShelfBlockEntity;
import site.moheng.ling.blocks.SpellEditTableBlockEntity;

public class LingModBlockEntitys implements AutoRegistryContainer<BlockEntityType<?>> {
    public static final BlockEntityType<SpellEditTableBlockEntity> SPELL_EDIT_TABLE_BLOCK = FabricBlockEntityTypeBuilder
            .create(SpellEditTableBlockEntity::new,
                    LingModBlocks.SPELL_EDIT_TABLE_BLOCK)
            .build();
    public static final BlockEntityType<ShelfBlockEntity> SHELF_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(ShelfBlockEntity::new, LingModBlocks.SHELF_BLOCK).build();


    @Override
    @SuppressWarnings("unchecked")
    public Class<BlockEntityType<?>> getTargetFieldType() {
        return (Class<BlockEntityType<?>>) (Object) BlockEntityType.class;
    }

    @Override
    public Registry<BlockEntityType<?>> getRegistry() {
        return Registry.BLOCK_ENTITY_TYPE;
    }

}
