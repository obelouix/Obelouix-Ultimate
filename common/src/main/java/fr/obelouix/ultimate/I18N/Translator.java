package fr.obelouix.ultimate.I18N;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {

    public static final GlobalTranslator globalTranslator = GlobalTranslator.translator();
    private static JavaPlugin javaPlugin;

    public static void init(JavaPlugin plugin) {

        javaPlugin = plugin;
        registerAllTranslations(
                List.of(
                        Locale.US,
                        Locale.FRANCE,
                        Locale.CANADA_FRENCH
       /*                 new Locale("en", "US"),
                        new Locale("fr", "FR"),
                        new Locale("fr", "CA")*/
                )
        );
    }

    private static void registerAllTranslations(List<Locale> locales) {
        final TranslationRegistry registry = TranslationRegistry.create(Key.key("obelouix", "main"));

        locales.forEach(
                locale -> {
                    final String language = locale.toLanguageTag().replace("-", "_");

                    final ResourceBundle resourceBundle = ResourceBundle.getBundle(
                            "translations." + language, locale, UTF8ResourceBundleControl.get());

                    registry.registerAll(locale, resourceBundle, true);

                    if (globalTranslator.addSource(registry)) {
                        javaPlugin.getComponentLogger().info(Component.text("Registered locale: " + language, NamedTextColor.GREEN));
                    }
                }
        );

        registry.defaultLocale(Locale.US);

    }

    public static Component translate(TranslatableComponent component, Locale locale) {
        return GlobalTranslator.render(component, locale);
    }

    public static Component translate(Component component, Locale locale) {
        return GlobalTranslator.render(component, locale);
    }

}
