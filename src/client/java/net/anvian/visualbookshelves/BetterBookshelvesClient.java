package net.anvian.visualbookshelves;

import net.anvian.visualbookshelves.config.client.ConfigManager;
import net.anvian.visualbookshelves.networking.client.Networking;
import net.anvian.visualbookshelves.render.BetterBookshelvesModelProvider;
import net.anvian.visualbookshelves.render.ChiseledBookshelfBlockEntityRenderer;
import net.anvian.visualbookshelves.render.InGameHudBookPreview;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterBookshelvesClient implements ClientModInitializer {
	public static final String MOD_ID = "betterbookshelves";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		Networking.init();

		ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new BetterBookshelvesModelProvider());
		BlockEntityRendererFactories.register(BlockEntityType.CHISELED_BOOKSHELF, context -> new ChiseledBookshelfBlockEntityRenderer());
		HudRenderCallback.EVENT.register(InGameHudBookPreview::renderCrosshair);

		ConfigManager.loadConfig();
		LOGGER.info("BetterBookshelves Client Initialized");
	}
}