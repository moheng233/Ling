package site.moheng.ling.mixin;

import com.google.gson.JsonElement;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Mixin(ItemModelGenerator.class)
public interface ItemModelGeneratorMixin {
    @Accessor
    BiConsumer<Identifier, Supplier<JsonElement>> getWriter();
}
