package mechanicalarms;

import mechanicalarms.common.block.BlockArm;
import mechanicalarms.common.tile.BlockEntityArm;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MechanicalArmsMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	// an instance of our new item
	public static final Block BLOCK_ARM = new BlockArm();
	public static final BlockEntityType<BlockEntityArm> ARM_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier("mechanicalarms", "arm_basic_entity"),
			FabricBlockEntityTypeBuilder.create(BlockEntityArm::new, BLOCK_ARM).build()
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		Registry.register(Registries.BLOCK, new Identifier("mechanicalarms", "arm_basic"), BLOCK_ARM);
		Registry.register(Registries.ITEM, new Identifier("mechanicalarms", "arm_basic"), new BlockItem(BLOCK_ARM, new FabricItemSettings()));
	}
}
