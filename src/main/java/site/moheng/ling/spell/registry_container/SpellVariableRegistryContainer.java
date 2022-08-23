package site.moheng.ling.spell.registry_container;

import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.util.registry.Registry;
import site.moheng.ling.spell.SpellRegistry;
import site.moheng.ling.spell.SpellVariableType;

public interface SpellVariableRegistryContainer extends AutoRegistryContainer<SpellVariableType<?>> {
    @Override
    @SuppressWarnings("unchecked")
    public default Class<SpellVariableType<?>> getTargetFieldType() {
        return (Class<SpellVariableType<?>>) (Object) SpellVariableType.class;
    }

    @Override
    public default Registry<SpellVariableType<?>> getRegistry() {
        return SpellRegistry.SPELL_VAR_TYPE;
    }
    
}
