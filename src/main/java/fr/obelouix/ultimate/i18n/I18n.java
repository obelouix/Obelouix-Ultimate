package fr.obelouix.ultimate.i18n;

import fr.obelouix.ultimate.data.PlayerData;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class I18n {
    private static I18n instance;

    private I18n() {
    }

    public static I18n getInstance(){
        if(instance == null){
            instance = new I18n();
        }
        return instance;
    }

    /**
     * Method for sending a message based on player's Locale
     *
     * @param commandSender the player that will receive the message - required to send th message in the right language
     * @param message       a {@link String} ID of the message
     * @return the @param message
     */
    public String getTranslation(CommandSender commandSender, String message) {
        final PlayerData playerData = new PlayerData();
        ResourceBundle playerMessages;
        if (commandSender instanceof Player) {
            try {
                // CompletableFuture to not run this on the main thread
                final CompletableFuture<ResourceBundle> completableFuture = CompletableFuture
                        .supplyAsync(playerData::getPlayerLocale).thenApplyAsync(s -> ResourceBundle.getBundle("messages_" + s));

                playerMessages = completableFuture.get();
            } catch (MissingResourceException | InterruptedException | ExecutionException e) {
                if (playerData.getPlayerLocale().equalsIgnoreCase("br_fr")) {
                    playerMessages = ResourceBundle.getBundle("messages_fr_FR");
                } else {
                    playerMessages = ResourceBundle.getBundle("messages_en_US");
                }
            }

        } else if (commandSender instanceof ConsoleCommandSender) {
            playerMessages = ResourceBundle.getBundle("messages_" + Locale.getDefault().toLanguageTag().replace("-", "_"));
        } else {
            playerMessages = ResourceBundle.getBundle("messages_en_US");
        }

        return playerMessages.getString(message);
    }
}
