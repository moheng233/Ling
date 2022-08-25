package site.moheng.ling.items;

import java.util.UUID;

import com.google.common.collect.Multimap;

import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.item.ItemStack;

public class SpellRingItem extends TrinketItem {

    public SpellRingItem(Settings settings) {
        super(settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot,
            LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);
        // modifiers.put(EntityAttributes.GENERIC_LUCK, arg1)
        // SlotAttributes.addSlotModifier(modifiers, "head/spell", uuid, 4, Operation.ADDITION);
        return modifiers;
    }
}
