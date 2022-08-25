package site.moheng.ling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.wispforest.owo.registration.reflect.FieldRegistrationHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;


public class LingMod implements ModInitializer {
	public static final String MODID = "lingmod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
		new Identifier(MODID, "general"), () -> new ItemStack(Items.ANDESITE));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("灵正在初始化!");

		initGui();
		LingModNetwork.initServer();

		LingModBlockEntitys.init();
		LingModItems.init();
		LingModBlocks.init();

		
		FieldRegistrationHandler.register(LingModBlockEntitys.class, MODID, false);
		FieldRegistrationHandler.register(LingModItems.class, MODID, false);
		FieldRegistrationHandler.register(LingModBlocks.class, MODID, false);

		FieldRegistrationHandler.register(SpellVariableTypes.class, MODID, false);
		FieldRegistrationHandler.register(SpellNodes.class, MODID, false);
	}

	public void initGui() {
		LingModGui.initScreenHandler();
	}
}
