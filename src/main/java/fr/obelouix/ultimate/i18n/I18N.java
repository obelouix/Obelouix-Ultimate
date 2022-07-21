package fr.obelouix.ultimate.i18n;

import fr.obelouix.ultimate.ObelouixUltimate;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.translation.Translator;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18N implements Translator {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private final GlobalTranslator globalTranslator = GlobalTranslator.translator();
    private final Locale DEFAULT_LOCALE = new Locale("en", "US");
    private TranslationRegistry translationRegistry;

    public void init() {

        translationRegistry = TranslationRegistry.create(Key.key("obelouix", "translations"));
        translationRegistry.defaultLocale(DEFAULT_LOCALE);
        translationRegistry.registerAll(DEFAULT_LOCALE, ResourceBundle.getBundle("lang_" + DEFAULT_LOCALE, UTF8ResourceBundleControl.get()), false);

        registerOtherLanguages();
        globalTranslator.addSource(translationRegistry);

    }

    private void registerOtherLanguages() {
        List.of(
                        new Locale("en", "UK"),
                        new Locale("fr", "FR"),
                        new Locale("fr", "CA")
                )
                .forEach(locale -> {
                    try {
                        final ResourceBundle bundle = ResourceBundle.getBundle("lang_" + locale.toLanguageTag().replaceAll("-", "_"), UTF8ResourceBundleControl.get());
                        translationRegistry.registerAll(Locale.forLanguageTag(locale.toLanguageTag()), bundle, false);
                    } catch (MissingResourceException e) {
                        //throw new RuntimeException(e);
                    }
                });

    }

    public void unregister() {
        globalTranslator.removeSource(translationRegistry);
    }

    @Override
    public @NotNull Key name() {
        return Key.key("obelouix.main", "test");
    }

    @Override
    public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
        return new MessageFormat(key, locale);
    }

    public GlobalTranslator getGlobalTranslator() {
        return globalTranslator;
    }

    public TranslationRegistry getTranslationRegistry() {
        return translationRegistry;
    }
}
