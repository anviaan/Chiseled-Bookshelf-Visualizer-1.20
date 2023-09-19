package net.anvian.visualizerbookshelf.event;

import net.anvian.visualizerbookshelf.render.InGameHudBookPreview;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInput {
    public static KeyBinding keyBinding;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                InGameHudBookPreview.toggleCrosshair();

                assert client.player != null;
                if (InGameHudBookPreview.shouldRenderCrosshair()) {
                    client.player.sendMessage(net.minecraft.text.Text.of("VisualizerBookshelf: Enabled"), false);
                } else {
                    client.player.sendMessage(net.minecraft.text.Text.of("VisualizerBookshelf: Disabled"), false);
                }
            }
        });
    }

    public static void register(){
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Enabled/Disabled the visualizer",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "Chiseled Bookshelf Visualizer Mod"
        ));
        registerKeyInputs();
    }
}
