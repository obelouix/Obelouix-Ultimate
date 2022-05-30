package fr.obelouix.ultimate.api;

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

public class TranslationAPI {

    private final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private String resourceBundleBaseName = "";

    public TranslationAPI() {
    }

    /**
     * Allows you to customize resources bundles base name
     *
     * @param resourceBundleBaseName
     */
    public void setResourceBundleBaseName(String resourceBundleBaseName) {
        this.resourceBundleBaseName = resourceBundleBaseName;
    }

    public String getTranslation(CommandSender target, String translation) {

        ResourceBundle bundle;
        CommentedConfigurationNode rootNode = null;

        if (target instanceof Player) {
            try {

                final HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                        .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", target.getName() + ".conf"))
                        .build();
                rootNode = playerFile.load();

                // CompletableFuture to not run this on the main thread
                final CompletableFuture<ResourceBundle> completableFuture = CompletableFuture
                        .supplyAsync(rootNode.node("language")::getString).thenApplyAsync(s -> ResourceBundle.getBundle(resourceBundleBaseName + s));

                bundle = completableFuture.get();
            } catch (MissingResourceException | InterruptedException | ExecutionException | NullPointerException |
                     ConfigurateException e) {
                if (Objects.requireNonNull(Objects.requireNonNull(rootNode).node("language").getString()).equalsIgnoreCase("br_fr")) {
                    bundle = ResourceBundle.getBundle(resourceBundleBaseName + "fr_FR");
                } else {
                    bundle = ResourceBundle.getBundle(resourceBundleBaseName + "en_US");
                }
            }
        } else if (target instanceof ConsoleCommandSender) {
            bundle = ResourceBundle.getBundle(resourceBundleBaseName + Locale.getDefault().toLanguageTag().replace("-", "_"));
        } else {
            bundle = ResourceBundle.getBundle(resourceBundleBaseName + "en_US");
        }

        if (!bundle.containsKey(translation)) {
            bundle = ResourceBundle.getBundle(resourceBundleBaseName + "en_US");
        }
        return bundle.getString(translation);
    }

    public String getTranslation(Locale locale, String message) {
        ResourceBundle translationBundle;

        try {
            translationBundle = ResourceBundle.getBundle(resourceBundleBaseName + locale.toLanguageTag().replaceAll("-", "_"));

        } catch (MissingResourceException e) {
            translationBundle = ResourceBundle.getBundle(resourceBundleBaseName + "en_US");
        }
        return translationBundle.getString(message);
    }

}
