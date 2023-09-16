package net.anvian.visualizerbookshelf;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookshelfVisualizer implements ModInitializer {
	public static final String MOD_ID = "visualizerbookshelf";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static MinecraftServer SERVER;

	@Override
	public void onInitialize() {
		LOGGER.info("Visual Chiseled Bookshelves Initialized");
	}
}