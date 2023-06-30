package net.riches.fabricord.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.riches.fabricord.events.PlayerJoinCallback;
import net.riches.fabricord.events.PlayerLeaveCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class MixinPlayerManager {

    @Inject(method = "onPlayerConnect", at = @At("RETURN"))
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerJoinCallback.EVENT.invoker().onPlayerJoin(connection, player);
    }

    @Inject(method = "remove", at = @At("HEAD"))
    private void remove(ServerPlayerEntity player, CallbackInfo ci) {
        PlayerLeaveCallback.EVENT.invoker().onPlayerLeave(player);
    }

}