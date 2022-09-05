package site.moheng.ling.blocks;

import java.util.Optional;

import io.wispforest.owo.nbt.NbtKey;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import site.moheng.ling.LingModBlockEntitys;

public class ShelfBlockEntity extends BlockEntity {
    private static final NbtKey<NbtCompound> KEY_ITEM = new NbtKey<>("item", NbtKey.Type.COMPOUND);

    private ItemStack item = ItemStack.EMPTY;

    public ShelfBlockEntity(BlockPos pos, BlockState state) {
        super(LingModBlockEntitys.SHELF_BLOCK_ENTITY, pos, state);
    }

    /**
     * @return the item
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(ItemStack item) {
        if (world instanceof ServerWorld server) {
            this.item = item;
            markDirty();
            server.getChunkManager().markForUpdate(pos);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        item = ItemStack.fromNbt(nbt.get(KEY_ITEM));
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put(KEY_ITEM, item.writeNbt(new NbtCompound()));
        super.writeNbt(nbt);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
