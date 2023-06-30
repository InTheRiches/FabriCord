package net.riches.fabricord.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Optional;

/**
 * @author Projekt Valor
 * @since 6/26/2023
 */
public interface PlayerLeaveCallback {
    Event<PlayerLeaveCallback> EVENT = EventFactory.createArrayBacked(PlayerLeaveCallback.class, callbacks -> (playerEntity) -> {
        Optional<Text> msg = Optional.empty();
        for (PlayerLeaveCallback callback : callbacks) {
            Optional<Text> callbackResult = callback.onPlayerLeave(playerEntity);
            if (callbackResult.isPresent()) msg = callbackResult;
        }
        return msg;
    });

    Optional<Text> onPlayerLeave(ServerPlayerEntity playerEntity);
}
