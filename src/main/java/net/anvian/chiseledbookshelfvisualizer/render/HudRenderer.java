package net.anvian.chiseledbookshelfvisualizer.render;

import net.anvian.chiseledbookshelfvisualizer.ChiseledBookshelfVisualizerClient;
import net.anvian.chiseledbookshelfvisualizer.config.*;
import net.anvian.chiseledbookshelfvisualizer.data.BookData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class HudRenderer {
    private static boolean renderCrosshair = true;
    private static float scale = ChiseledBookshelfVisualizerClient.CONFIG.hudScale;

    public static void updateScale(float newScale) {
        scale = newScale;
    }

    public static void toggleCrosshair() {
        renderCrosshair = !renderCrosshair;
    }

    public static boolean shouldRenderCrosshair() {
        return renderCrosshair;
    }

    public static void hudRender(DrawContext context, MinecraftClient client) {
        if (shouldRenderCrosshair()) {
            if (!ChiseledBookshelfVisualizerClient.modAvailable) return;

            if (client.options.hudHidden) return;

            if (client.currentScreen != null) return;

            if (ChiseledBookshelfVisualizerClient.bookShelfData.isCurrentBookDataToggled) {
                final BookData currentBookData = ChiseledBookshelfVisualizerClient.currentBookData;
                int screenWidth = client.getWindow().getScaledWidth();
                int screenHeight = client.getWindow().getScaledHeight();
                int x = screenWidth / 2;
                int y = screenHeight / 2;
                final ItemStack itemStack = currentBookData.itemStack;
                int color = 0xFFFFFFFF;
                if (itemStack.getRarity().getFormatting().getColorValue() != null) {
                    color = itemStack.getRarity().getFormatting().getColorValue();
                }
                // Thanks to justanothercorpusguy on the Fabric project Discord
                // For explaining matrix scaling for text to multiple people :P
                context.getMatrices().push();
                context.getMatrices().scale(scale, scale, 1.0f);

                drawScaledCenteredText(context,client.textRenderer,itemStack.getName().getString(), x, y+10,color,scale);


                var storedComponents = itemStack.getComponents().get(DataComponentTypes.STORED_ENCHANTMENTS);
                if (storedComponents != null) {
                    int i = (int) (20 * ( scale > 1 ? scale : 1));
                    for (RegistryEntry<Enchantment> enchantment : storedComponents.getEnchantments()) {
                        drawScaledCenteredText(context, client.textRenderer, enchantment.value().description().getString(), x, y + i, 0xFFCECECE, scale);
                        i += 10 * ( scale > 1 ? scale : 1);
                    }
                }

                var writtenBookContentComponent = itemStack.getComponents().get(DataComponentTypes.WRITTEN_BOOK_CONTENT);

                if (writtenBookContentComponent != null) {
                    String authorText = Text.translatable("book.byAuthor", writtenBookContentComponent.author()).getString();
                    drawScaledCenteredText(context, client.textRenderer, authorText, x, y + 20, 0xFFCECECE, scale);
                }
                context.getMatrices().pop();
            }
        }
    }
    private static void drawScaledCenteredText(DrawContext context, TextRenderer textRenderer, String text, int x, int y, int color, float scale) {
        int textWidth = textRenderer.getWidth(text);
        float scaledX = x / scale - (textWidth / 2f);
        float scaledY = y / scale;

        textRenderer.draw(
                text,
                scaledX,
                scaledY,
                color,
                true,
                context.getMatrices().peek().getPositionMatrix(),
                context.getVertexConsumers(),
                TextRenderer.TextLayerType.NORMAL,
                0,
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                false
        );
    }
}
