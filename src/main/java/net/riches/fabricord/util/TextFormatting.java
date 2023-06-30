package net.riches.fabricord.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mutable;

/**
 * @author Projekt Valor
 * @since 6/26/2023
 */
public class TextFormatting {
    public static MutableText format(String message) {
        String[] words = message.split(" ");
        MutableText formattedMessage = (MutableText) Text.of("");

        for (String word : words) {
            // Check if the word starts with the color character '&'
            if (word.startsWith("&") && word.length() > 1) {
                char colorChar = word.charAt(1);
                Formatting formatting = Formatting.byCode(colorChar);

                // Skip if the color character is not valid
                if (formatting == null)
                    continue;

                // Apply the formatting to the formatted message
                formattedMessage = formattedMessage.append(((MutableText) Text.of(word.substring(2))).formatted(formatting));
            } else {
                // Append the word as it is (without any formatting)
                formattedMessage = formattedMessage.append(Text.of(word));
            }

            // Append a space after each word
            formattedMessage = formattedMessage.append(Text.of(" "));
        }

        // Remove the trailing space
        formattedMessage = formattedMessage.setStyle(formattedMessage.getStyle().withFormatting(Formatting.RESET));

        return formattedMessage;
    }
}
