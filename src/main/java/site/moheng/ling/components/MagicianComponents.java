package site.moheng.ling.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;

public class MagicianComponents implements ComponentV3, ServerTickingComponent {
    private final Entity entity;


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
