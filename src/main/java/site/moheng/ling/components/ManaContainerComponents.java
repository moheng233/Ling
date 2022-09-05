package site.moheng.ling.components;

import io.wispforest.owo.nbt.NbtKey;
import net.minecraft.nbt.NbtCompound;

/**
 * 魔力容器组件
 */
public class ManaContainerComponents implements IManaContainer {
    private static final NbtKey<Integer> KEY_MANA_MAX = new NbtKey<>("mana_max", NbtKey.Type.INT);
    private static final NbtKey<Integer> KEY_MANA = new NbtKey<>("mana", NbtKey.Type.INT);

    private int manaMax = 0;
    private int mana = 0;

    @Override
    public void readFromNbt(NbtCompound tag) {
        manaMax = tag.getOr(KEY_MANA_MAX, 0);
        mana = tag.getOr(KEY_MANA, 0);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.put(KEY_MANA_MAX, manaMax);
        tag.put(KEY_MANA, mana);
    }

    @Override
    public int getManaMax() {
        return manaMax;
    }

    @Override
    public int getMana() {
        return mana;
    }
    
}
