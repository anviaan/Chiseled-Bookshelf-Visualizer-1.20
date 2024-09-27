package net.anvian.chiseledbookshelfvisualizer;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChiseledBookshelfVisualizer implements ModInitializer {
	public static final String MOD_ID = "chiseled-bookshelf-visualizer";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello from Chiseled Bookshelf Visualizer!");
	}
}