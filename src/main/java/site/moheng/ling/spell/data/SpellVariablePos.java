package site.moheng.ling.spell.data;

import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import site.moheng.ling.spell.SpellVariableType;

public class SpellVariablePos extends SpellVariableType<BlockPos> {

    @Override
    public Text getDisplayData(BlockPos data) {
        return Text.of(data.toString());
    }
    
}
