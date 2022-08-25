package site.moheng.ling.components;

import java.util.HashSet;

import com.mojang.datafixers.types.templates.List;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import io.wispforest.owo.nbt.NbtKey;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import site.moheng.ling.spell.SpellStream;

public class MagicianComponents implements ComponentV3, ServerTickingComponent {
    private final Entity entity;
    private final HashSet<SpellStream> streams = new HashSet<>();

    public MagicianComponents(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        
    }

    @Override
    public void serverTick() {
        
    }
    
}
