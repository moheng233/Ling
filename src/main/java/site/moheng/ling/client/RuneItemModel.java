package site.moheng.ling.client;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import site.moheng.ling.LingModItems;
import site.moheng.ling.Runes;
import site.moheng.ling.rune.ISpellRune;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RuneItemModel implements UnbakedModel, BakedModel, FabricBakedModel {

    private final HashMap<ISpellRune, BakedModel> models = new HashMap<>();
    private BakedModel voidModel = null;

    @Override
    public Collection<Identifier> getModelDependencies() {
        return ISpellRune.RUNES.getIds().parallelStream().map(ISpellRune::getRuneModelId).collect(Collectors.toList());
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> find,
            Set<Pair<String, String>> var2) {
        var runes = getModelDependencies();
        Set<SpriteIdentifier> textures = Sets.newHashSetWithExpectedSize(runes.size());
        for (var id : runes) {
            var model = find.apply(id);
            if (model != null) {
                textures.addAll(model.getTextureDependencies(find, var2));
            }
        }
        return textures;
    }

    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter,
            ModelBakeSettings rotationContainer,
            Identifier modelId) {

        for (var rune : ISpellRune.RUNES) {
            models.put(rune, loader.bake(rune.getRuneModelId(), rotationContainer));
        }

        voidModel = models.get(Runes.VOID);

        return this;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState var1, Direction var2, Random var3) {
        return voidModel.getQuads(var1, var2, var3);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return voidModel.useAmbientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return voidModel.hasDepth();
    }

    @Override
    public boolean isSideLit() {
        return voidModel.isSideLit();
    }

    @Override
    public boolean isBuiltin() {
        return voidModel.isBuiltin();
    }

    @Override
    public Sprite getParticleSprite() {
        return voidModel.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return voidModel.getTransformation();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos,
            Supplier<Random> randomSupplier, RenderContext context) {
        context.fallbackConsumer().accept(voidModel);
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        context.fallbackConsumer().accept(models.getOrDefault(LingModItems.SPELL_RUNE.getRune(stack), voidModel));
    }

}
