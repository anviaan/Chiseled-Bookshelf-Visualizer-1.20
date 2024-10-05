package net.anvian.chiseledbookshelfvisualizer;

import net.anvian.chiseledbookshelfvisualizer.config.*;
import net.anvian.chiseledbookshelfvisualizer.data.BookData;
import net.anvian.chiseledbookshelfvisualizer.data.BookShelfData;
import net.anvian.chiseledbookshelfvisualizer.network.BookShelfInventoryPayload;
import net.anvian.chiseledbookshelfvisualizer.network.ModCheckPayload;
import net.anvian.chiseledbookshelfvisualizer.util.KeyInput;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.Items;

@Environment(EnvType.CLIENT)
public class ChiseledBookshelfVisualizerClient implements ClientModInitializer {
    public static BookData currentBookData = BookData.empty();
    public static BookShelfData bookShelfData = new BookShelfData();
    public static boolean modAvailable = false;
    public static ChiseledBookshelfVisualizerConfig CONFIG;

    @Override
    public void onInitializeClient() {
        CONFIG = ChiseledBookshelfVisualizerConfig.load();

        KeyInput.register();

        ClientPlayNetworking.registerGlobalReceiver(BookShelfInventoryPayload.ID,
                ((payload, context) ->
                        context.client().execute(() ->{
                            bookShelfData.requestSent = false;
                            if(payload.itemStack().isOf(Items.AIR)){
                                bookShelfData.isCurrentBookDataToggled = false;
                                currentBookData = BookData.empty();
                                currentBookData.slotId = -2;
                            }
                            else{
                                bookShelfData.isCurrentBookDataToggled = true;
                                currentBookData = new BookData(payload.itemStack(),payload.pos(),payload.slotNum());
                            }
                        })));

        ClientPlayNetworking.registerGlobalReceiver(ModCheckPayload.ID,
                (payload, context) -> context.client().execute(() ->{
                    ChiseledBookshelfVisualizer.LOGGER.info("[ChiseledBookshelfVisualizer] Connected to server");
                    modAvailable = true;
                }));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->  modAvailable = false);
    }
}
