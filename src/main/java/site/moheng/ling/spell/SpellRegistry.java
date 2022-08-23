package site.moheng.ling.spell;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;
import site.moheng.ling.LingMod;

public class SpellRegistry {
    @SuppressWarnings("unchecked")
    public static final SimpleRegistry<SpellVariableType<?>> SPELL_VAR_TYPE = FabricRegistryBuilder
        .createSimple((Class<SpellVariableType<?>>) (Object) SpellVariableType.class,
                new Identifier(LingMod.MODID, "spell_stack_data"))
        .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static final SimpleRegistry<SpellNode> SPELL_NODE = FabricRegistryBuilder
        .createSimple(SpellNode.class, new Identifier(LingMod.MODID, "spell_block"))
        .attribute(RegistryAttribute.SYNCED).buildAndRegister();


}
