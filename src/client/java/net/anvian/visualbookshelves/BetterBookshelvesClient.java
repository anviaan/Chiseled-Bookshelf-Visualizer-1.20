package net.anvian.visualbookshelves;

import net.anvian.visualbookshelves.networking.client.Networking;
import net.anvian.visualbookshelves.render.InGameHudBookPreview;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterBookshelvesClient implements ClientModInitializer {
	public static final String MOD_ID = "visualbookshelves";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		Networking.init();

		HudRenderCallback.EVENT.register(InGameHudBookPreview::renderCrosshair);

		LOGGER.info("Visual Chiseled Bookshelves Client Initialized");
	}
}