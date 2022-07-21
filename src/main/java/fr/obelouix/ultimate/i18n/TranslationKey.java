package fr.obelouix.ultimate.i18n;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public enum TranslationKey {

    DIRECTION(Component.translatable("obelouix.direction")),
    NORTH(Component.translatable("obelouix.direction.north")),
    SOUTH(Component.translatable("obelouix.direction.south")),
    EAST(Component.translatable("obelouix.direction.east")),
    WEST(Component.translatable("obelouix.direction.west")),
    NORTH_WEST(Component.translatable("obelouix.direction.northwest")),
    NORTH_EAST(Component.translatable("obelouix.direction.northeast")),
    SOUTH_WEST(Component.translatable("obelouix.direction.southwest")),
    SOUTH_EAST(Component.translatable("obelouix.direction.southeast"));


    private final TranslatableComponent key;

    TranslationKey(@NotNull TranslatableComponent key) {
        this.key = key;
    }

    /**
     * Convert a {@link TranslatableComponent} to a {@link Component}
     *
     * @return a component
     */
    public Component toComponent() {
        return key;
    }

    /**
     * Convert a {@link TranslatableComponent} to a {@link Component} and add {@link TextColor color} it
     *
     * @param textColor a color
     * @return a colored component
     */
    public Component toComponent(TextColor textColor) {
        return key.color(textColor);
    }

    /**
     * Convert a {@link TranslatableComponent} to a {@link Component} and add {@link TextDecoration decorations} to it
     *
     * @param decorations a text decoration
     * @return a component with decoration(s)
     */
    public Component toComponent(TextDecoration... decorations) {
        return key.decorate(decorations);
    }

    /**
     * Convert a {@link TranslatableComponent} to a {@link Component} and add {@link TextColor color} and {@link TextDecoration decorations} to it
     *
     * @param textColor   a color
     * @param decorations a text decoration
     * @return a colored component with decoration(s)
     */
    public @NotNull Component toComponent(TextColor textColor, TextDecoration... decorations) {
        return key.color(textColor).decorate(decorations);
    }

    /**
     * Convert a {@link TranslatableComponent} to a {@link Component} with the first letter capitalized
     *
     * @return a component
     */
    public Component toCapitalizedComponent() {
        final @NotNull String component = PlainTextComponentSerializer.plainText().serialize(toComponent());
        return Component.text(StringUtils.capitalize(component));
    }

    /**
     * Convert a {@link TranslatableComponent} to a {@link Component} with the first letter capitalized and add {@link TextColor color} it
     *
     * @param textColor a color
     * @return a colored component
     */
    public Component toCapitalizedComponent(TextColor textColor) {
        final @NotNull String component = PlainTextComponentSerializer.plainText().serialize(toComponent());
        return Component.text(StringUtils.capitalize(component), textColor);
    }

    /**
     * Convert a {@link TranslatableComponent} to a {@link Component} with the first letter capitalized and add {@link TextDecoration decorations} to it
     *
     * @param decorations a text decoration
     * @return a component with decoration(s)
     */
    public Component toCapitalizedComponent(TextDecoration... decorations) {
        final @NotNull String component = PlainTextComponentSerializer.plainText().serialize(toComponent());
        return Component.text(StringUtils.capitalize(component), Style.style(decorations));
    }

    /**
     * Convert a {@link TranslatableComponent} to a {@link Component} with the first letter capitalized a
     * nd add {@link TextColor color} and {@link TextDecoration decorations} to it
     *
     * @param textColor   a color
     * @param decorations a text decoration
     * @return a colored component with decoration(s)
     */
    public Component toCapitalizedComponent(TextColor textColor, TextDecoration... decorations) {
        final @NotNull String component = PlainTextComponentSerializer.plainText().serialize(toComponent());
        return Component.text(StringUtils.capitalize(component), textColor, decorations);
    }


}
