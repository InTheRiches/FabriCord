package net.riches.fabricord;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.riches.fabricord.listeners.MessageListener;
import net.riches.fabricord.util.FabriCordConfig;
import org.jetbrains.annotations.NotNull;
import org.apache.commons.codec.binary.Base64;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Projekt Valor
 * @since 6/26/2023
 */
public class FabriCordDiscord implements EventListener {

    private JDA jda;
    private boolean disabled;
    private MessageChannel channel;

    public FabriCordDiscord() {
        System.out.println("Discord Client initialized");
        JDABuilder builder = JDABuilder.createDefault(FabriCordConfig.token);

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(true);
        builder.setActivity(Activity.watching("Shrek"));

        builder.enableIntents(GatewayIntent.GUILD_MESSAGES);

        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        jda.addEventListener(this);
        jda.addEventListener(new MessageListener());
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof DisconnectEvent) {
            System.out.println("Discord Bot Disconnected");
        }
        if (event instanceof ReadyEvent) {
            System.out.println("Discord Bot Ready");
            this.channel = jda.getTextChannelById(1122907482700922952L);
        }
    }

    public void shutdown() {
        disabled = true;
        jda.shutdown();
    }

    public JDA getJDA() {
        return jda;
    }

    public void playerChatMessage(ServerPlayerEntity player, String rawMessage) {
        if (this.disabled) return;

        channel.sendMessage("**" + player.getEntityName() + ":** " + rawMessage).queue();
    }

    public void playerJoined(ServerPlayerEntity player) {
        if (this.disabled) return;

        channel.sendMessageEmbeds(new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTimestamp(Instant.now())
                .setAuthor(player.getEntityName() + " joined the game.", null, "https://minotar.net/armor/bust/" + player.getUuid() + "/100.png")
                .build()).queue();
    }

    public void playerLeft(ServerPlayerEntity player) {
        if (this.disabled) return;

        channel.sendMessageEmbeds(new EmbedBuilder()
                .setColor(Color.RED)
                .setTimestamp(Instant.now())
                .setAuthor(player.getEntityName() + " left the game.", null, "https://minotar.net/armor/bust/" + player.getUuid() + "/100.png")
                .build()).queue();
    }

    public boolean isDisabled() {
        return disabled;
    }
}
