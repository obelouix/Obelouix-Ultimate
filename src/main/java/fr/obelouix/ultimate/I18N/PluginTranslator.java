package fr.obelouix.ultimate.I18N;

import fr.obelouix.ultimate.Main;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PluginTranslator {
    public static final GlobalTranslator globalTranslator = GlobalTranslator.translator();

    public static void init(){
        registerAllTranslations(List.of(new Locale("en", "US"), new Locale("fr", "FR")));
    }

    public static void registerAllTranslations(List<Locale> locales) {
        locales.forEach(
                locale -> {
                    final String language = locale.toLanguageTag();
                    final TranslationRegistry registry = TranslationRegistry.create(Key.key("obelouix", "main"));

                    registry.defaultLocale(Locale.ENGLISH);

                    final ResourceBundle resourceBundle = ResourceBundle.getBundle(
                            "lang_" + language, locale, UTF8ResourceBundleControl.get());

                    registry.registerAll(locale, resourceBundle, true);

                    if(globalTranslator.addSource(registry)) {
                        Main.getPlugin().getComponentLogger().info(Component.text("Registered locale: " + language, NamedTextColor.GREEN));
                    }
                }
        );

    }


}
