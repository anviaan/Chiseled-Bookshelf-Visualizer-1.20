package net.anvian.chiseledbookshelfvisualizer.config;

import net.fabricmc.loader.api.*;
import org.spongepowered.include.com.google.gson.*;

import java.io.*;

public class ChiseledBookshelfVisualizerConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("chiseledbookshelfvisualizer.json").toFile();
    public float hudScale = 1.0f;

    public static ChiseledBookshelfVisualizerConfig load() {
        ChiseledBookshelfVisualizerConfig config;

        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                config = GSON.fromJson(reader, ChiseledBookshelfVisualizerConfig.class);
            } catch (IOException e) {
                System.err.println("Error reading config file: " + e.getMessage());
                config = new ChiseledBookshelfVisualizerConfig();
            }
        } else {
            config = new ChiseledBookshelfVisualizerConfig();
        }

        config.save();
        return config;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            System.err.println("Error writing config file: " + e.getMessage());
        }
    }
}
