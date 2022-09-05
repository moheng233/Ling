package site.moheng.ling.client;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
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

public class ShelfModel implements UnbakedModel, BakedModel, FabricBakedModel {

    

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos,
            Supplier<Random> randomSupplier, RenderContext context) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<BakedQuad> getQuads(BlockState var1, Direction var2, Random var3) {
        return null;
    }

    @Override
    public boolean useAmbientOcclusion() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasDepth() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSideLit() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isBuiltin() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModelTransformation getTransformation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModelOverrideList getOverrides() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> var1,
            Set<Pair<String, String>> var2) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BakedModel bake(ModelLoader var1, Function<SpriteIdentifier, Sprite> var2, ModelBakeSettings var3,
            Identifier var4) {
        return this;
    }
    
}
