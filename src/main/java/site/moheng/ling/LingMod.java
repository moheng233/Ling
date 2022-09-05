package site.moheng.ling;

import io.wispforest.owo.registration.reflect.FieldRegistrationHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LingMod implements ModInitializer {
    public static final String MODID = "lingmod";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MODID, "general"), () -> new ItemStack(Items.ANDESITE));

    public static final EntityAttribute MOD_MAX_MANA = new ClampedEntityAttribute(Util.createTranslationKey("attribute", new Identifier(LingMod.MODID, "max_mana")), 0, 0, 2000).setTracked(true);

    @Override
    public void onInitialize() {
        LOGGER.info("灵正在初始化!");

        Registry.register(Registry.ATTRIBUTE, new Identifier(MODID, "max_mana"), MOD_MAX_MANA);

        initGui();
        LingModNetwork.initServer();

        LingModItems.init();
        LingModBlocks.init();
        

        FieldRegistrationHandler.register(Runes.class, MODID, false);

        FieldRegistrationHandler.register(LingModItems.class, MODID, false);
        FieldRegistrationHandler.register(LingModBlocks.class, MODID, false);
        FieldRegistrationHandler.register(LingModBlockEntitys.class, MODID, false);
    }

    public void initGui() {
        LingModGui.initScreenHandler();
    }
}
