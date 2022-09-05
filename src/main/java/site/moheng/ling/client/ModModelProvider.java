package site.moheng.ling.client;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ModModelProvider implements ModelResourceProvider {
    public static final Identifier RUNE_ID = new Identifier("lingmod:item/spell_rune");
    public static final Identifier SHELF_ID = new Identifier("lingmod:block/shelf_block");
    
    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) {
        if (RUNE_ID.equals(resourceId)) {
            return new RuneItemModel();
        }
        // if (RUNE_ID.equals(resourceId)) {
        //     return new ShelfModel();
        // }

        return null;
    }

}
