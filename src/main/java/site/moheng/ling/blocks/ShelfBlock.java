package site.moheng.ling.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

public class ShelfBlock extends BlockWithEntity {
    VoxelShape shape = null;

    public ShelfBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShelfBlockEntity(pos, state);
    }

    public VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0.9375, 0.0625, 0.8125, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 0.0625, 0.8125, 0.0625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.8125, 0, 1, 0.9375, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.9375, 0, 0.9375, 1, 0.8125, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.9375, 0, 0, 1, 0.8125, 0.0625));
    
        return shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {
        
        var stack = player.getStackInHand(hand);
        var entity = (ShelfBlockEntity)world.getBlockEntity(pos);

        if(!stack.isEmpty() && entity.getItem().isEmpty()) {
            if(!world.isClient()) {
                player.setStackInHand(hand, ItemStack.EMPTY);
                entity.setItem(stack);
            }

            return ActionResult.SUCCESS;
        }

        if(!entity.getItem().isEmpty() && player.isSneaking()) {
            if(!world.isClient()) {
                BlockPos dpos = pos.up();
                ItemEntity itemEntity = new ItemEntity(world, (double)dpos.getX() + 0.5, (double)dpos.getY(), (double)dpos.getZ() + 0.5, entity.getItem(), 0, 0.1, 0);
                entity.setItem(ItemStack.EMPTY);
                world.spawnEntity(itemEntity);
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
