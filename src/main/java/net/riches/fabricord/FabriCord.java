package net.riches.fabricord;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.riches.fabricord.listeners.ChatEventListener;

/**
 * @author Projekt Valor
 * @since 6/25/2023
 */
public class FabriCord implements ModInitializer {

    private FabriCordDiscord discordClient;

    private static FabriCord instance;
    private static MinecraftServer server;

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        instance = this;

        this.discordClient = new FabriCordDiscord();

        new ChatEventListener();

        ServerLifecycleEvents.SERVER_STARTED.register((s -> server = s));

        ServerLifecycleEvents.SERVER_STOPPING.register(this::serverStopping);
    }

    private void serverStopping(MinecraftServer minecraftServer) {
        this.getDiscordClient().shutdown();
    }

    public FabriCordDiscord getDiscordClient() {
        return this.discordClient;
    }

    public static FabriCord getInstance() {
        return instance;
    }

    public static MinecraftServer getServer() {
        return server;
    }
}
