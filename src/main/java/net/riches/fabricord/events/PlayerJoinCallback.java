package net.riches.fabricord.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Optional;

/**
 * @author Projekt Valor
 * @since 6/26/2023
 */
public interface PlayerJoinCallback {
    Event<PlayerJoinCallback> EVENT = EventFactory.createArrayBacked(PlayerJoinCallback.class, callbacks -> (clientConnection, playerEntity) -> {
        Optional<Text> msg = Optional.empty();
        for (PlayerJoinCallback callback : callbacks) {
            Optional<Text> callbackResult = callback.onPlayerJoin(clientConnection, playerEntity);
            if (callbackResult.isPresent()) msg = callbackResult;
        }
        return msg;
    });

    Optional<Text> onPlayerJoin(ClientConnection clientConnection, ServerPlayerEntity playerEntity);
}
