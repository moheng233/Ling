package site.moheng.ling.items;

import java.util.List;

import io.wispforest.owo.nbt.NbtKey;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import site.moheng.ling.LingModClient;
import site.moheng.ling.LingModComponents;
import site.moheng.ling.LingModItems;
import site.moheng.ling.Runes;
import site.moheng.ling.rune.ISpellRune;

public class SpellRuneItem extends Item {
    private static final NbtKey<Identifier> KEY_RUNE = new NbtKey<>("rune", NbtKey.Type.IDENTIFIER);

    public SpellRuneItem(Settings settings) {
        super(settings);
    }

    public static ItemStack createRune(ISpellRune rune) {
        var stack = new ItemStack(LingModItems.SPELL_RUNE);
        stack.put(KEY_RUNE, rune.getID());
        return stack;
    }

    @Override
    public Text getName(ItemStack stack) {
        return getRune(stack).getRuneName();
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        getRune(stack).appendRuneTooltip(stack, tooltip);
        super.appendTooltip(stack, world, tooltip, context);
    }

    public ISpellRune getRune(ItemStack stack) {
        return ISpellRune.RUNES.get(stack.getOr(KEY_RUNE, Runes.VOID.getID()));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            for (var rune : ISpellRune.RUNES) {
                stacks.add(createRune(rune));
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            var magician = user.getComponent(LingModComponents.MAGICIAN);
            magician.execRune(getRune(user.getStackInHand(hand)));
        }
        return super.use(world, user, hand);
    }
}
