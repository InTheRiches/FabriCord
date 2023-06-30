package net.riches.fabricord.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Formatting;
import net.riches.fabricord.FabriCord;
import net.riches.fabricord.FabriCordDiscord;
import net.riches.fabricord.util.TextFormatting;

import java.util.Random;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (FabriCord.getInstance().getDiscordClient().isDisabled()) return;

        if (event.getMessage().getAuthor() == event.getJDA().getSelfUser()) return;

        if (!event.getChannel().getId().equals("1122907482700922952")) {
            return;
        }

        Iterable<ServerPlayerEntity> players = FabriCord.getServer().getPlayerManager().getPlayerList();

        // use Fabric formatting to format this message

        for (ServerPlayerEntity player : players) {
            System.out.println(event.getMessage().getContentDisplay());
            player.sendMessage(TextFormatting.format("&9[Discord] "+ event.getAuthor().getName() + " &8» " + event.getMessage().getContentDisplay()), false);
        }
    }
}
