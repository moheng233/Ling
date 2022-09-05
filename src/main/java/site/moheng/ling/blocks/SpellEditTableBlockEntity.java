package site.moheng.ling.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import site.moheng.ling.LingModBlockEntitys;

public class SpellEditTableBlockEntity extends BlockEntity {

    public SpellEditTableBlockEntity(BlockPos pos, BlockState state) {
        super(LingModBlockEntitys.SPELL_EDIT_TABLE_BLOCK, pos, state);
    }
}
