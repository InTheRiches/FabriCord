package net.riches.fabricord.listeners;

import net.riches.fabricord.FabriCord;
import net.riches.fabricord.FabriCordDiscord;
import net.riches.fabricord.events.PlayerJoinCallback;
import net.riches.fabricord.events.PlayerLeaveCallback;
import net.riches.fabricord.events.ServerChatCallback;

import java.util.Optional;

/**
 * @author Projekt Valor
 * @since 6/26/2023
 */
public class ChatEventListener {
    public ChatEventListener() {
        ServerChatCallback.EVENT.register((playerEntity, rawMessage) -> {
            FabriCord.getInstance().getDiscordClient().playerChatMessage(playerEntity, rawMessage);

            return Optional.empty();
        });

        PlayerJoinCallback.EVENT.register((clientConnection, playerEntity) -> {
            FabriCord.getInstance().getDiscordClient().playerJoined(playerEntity);

            return Optional.empty();
        });

        PlayerLeaveCallback.EVENT.register((playerEntity) -> {
            FabriCord.getInstance().getDiscordClient().playerLeft(playerEntity);

            return Optional.empty();
        });
    }
}
