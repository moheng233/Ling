package site.moheng.ling.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import site.moheng.ling.blocks.ShelfBlockEntity;

public class ShelfEntityRenderer implements BlockEntityRenderer<ShelfBlockEntity> {

    /**
     * 
     */
    public ShelfEntityRenderer(Context context) {
    }

    @Override
    public void render(ShelfBlockEntity entity, float delta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
            int overlay) {
        matrices.push();
        
        double offset = Math.sin((entity.getWorld().getTime() + delta) / 8.0) / 8.0;
        int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
        matrices.translate(0.5, 1.2 + offset, 0.5);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + delta) * 4));

        MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getItem(), Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);

        matrices.pop();
    }

    
    
}
