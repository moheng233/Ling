package site.moheng.ling.spell.registry_container;

import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.util.registry.Registry;
import site.moheng.ling.spell.SpellNode;
import site.moheng.ling.spell.SpellRegistry;

public interface SpellBlockRegistryContainer extends AutoRegistryContainer<SpellNode> {
    @Override
    public default Class<SpellNode> getTargetFieldType() {
        return SpellNode.class;
    }

    @Override
    public default Registry<SpellNode> getRegistry() {
        return SpellRegistry.SPELL_NODE;
    }
}
