package net.riches.fabricord.util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Projekt Valor
 * @since 6/30/2023
 */
public class FabriCordConfig {

    private static final Path CONFIG_PATH = Paths.get("config", "fabricord", "mod_config.yml");


    private static final YamlConfigurationLoader CONFIG_LOADER = YamlConfigurationLoader.builder()
            .path(CONFIG_PATH)
            .build();

    private static final ConfigurationNode CONFIG_NODE = CONFIG_LOADER.createNode();

    public static String token = "";

    public static void loadConfig() {
        try {
            CONFIG_NODE.load();
            enableFeature1 = CONFIG_NODE.node("token").getString();
        } catch (ConfigurateException e) {
            // Handle exceptions, e.g., file not found or invalid format
        }
    }

    public static void saveConfig() {
        try {
            CONFIG_NODE.node("token").set(token);
            CONFIG_LOADER.save(CONFIG_NODE);
        } catch (ConfigurateException e) {
            // Handle exceptions, e.g., file permission issues
        }
    }
}
