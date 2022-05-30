package fr.obelouix.ultimate.i18n;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class I18n {
    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static I18n instance;

    private I18n() {
    }

    public static I18n getInstance() {
        if (instance == null) {
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
        ResourceBundle playerMessages;
        if (commandSender instanceof Player) {
            CommentedConfigurationNode root = null;
            try {

                final HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                        .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", commandSender.getName() + ".conf"))
                        .build();
                root = playerFile.load();

                // CompletableFuture to not run this on the main thread
                final CompletableFuture<ResourceBundle> completableFuture = CompletableFuture
                        .supplyAsync(root.node("language")::getString).thenApplyAsync(s -> ResourceBundle.getBundle("messages_" + s));

                playerMessages = completableFuture.get();
            } catch (MissingResourceException | InterruptedException | ExecutionException | NullPointerException | ConfigurateException e) {
                if (Objects.requireNonNull(Objects.requireNonNull(root).node("language").getString()).equalsIgnoreCase("br_fr")) {
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

        if (!playerMessages.containsKey(message)) {
            playerMessages = ResourceBundle.getBundle("messages_en_US");
        }

        return playerMessages.getString(message);
    }

    public String getTranslation(Locale locale, String message) {
        ResourceBundle translationBundle;
        try {
            translationBundle = ResourceBundle.getBundle("messages_" + locale.toLanguageTag().replaceAll("-", "_"));

        } catch (MissingResourceException e) {
            translationBundle = ResourceBundle.getBundle("messages_en_US");
        }
        return translationBundle.getString(message);
    }

}
