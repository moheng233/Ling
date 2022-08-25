package site.moheng.ling;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;
import site.moheng.ling.components.MagicianComponents;
import site.moheng.ling.components.ManaContainerComponents;

public class LingModComponents implements EntityComponentInitializer {

    public static final ComponentKey<MagicianComponents> MAGICIAN = ComponentRegistry.getOrCreate(new Identifier(LingMod.MODID, "magician"), MagicianComponents.class);
    public static final ComponentKey<ManaContainerComponents> MANA_CONTAINER = ComponentRegistry.getOrCreate(new Identifier(LingMod.MODID, "mana_container"), ManaContainerComponents.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(MAGICIAN, player -> new MagicianComponents(player), RespawnCopyStrategy.ALWAYS_COPY);  
        registry.registerForPlayers(MANA_CONTAINER, player -> new ManaContainerComponents(), RespawnCopyStrategy.ALWAYS_COPY);      
    }
    
}
