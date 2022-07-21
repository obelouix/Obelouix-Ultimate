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

    public Component toComponent() {
        return key;
    }

    public Component toComponent(TextColor textColor) {
        return key.color(textColor);
    }

    public Component toComponent(TextDecoration... decorations) {
        return key.decorate(decorations);
    }

    public @NotNull Component toComponent(TextColor textColor, TextDecoration... decoration) {
        return key.color(textColor).decorate(decoration);
    }


    public Component toCapitalizedComponent() {
        @NotNull String component = PlainTextComponentSerializer.plainText().serialize(toComponent());
        return Component.text(StringUtils.capitalize(component));
    }

    public Component toCapitalizedComponent(TextColor textColor) {
        @NotNull String component = PlainTextComponentSerializer.plainText().serialize(toComponent());
        return Component.text(StringUtils.capitalize(component), textColor);
    }

    public Component toCapitalizedComponent(TextDecoration... decoration) {
        @NotNull String component = PlainTextComponentSerializer.plainText().serialize(toComponent());
        return Component.text(StringUtils.capitalize(component), Style.style(decoration));
    }

    public Component toCapitalizedComponent(TextColor textColor, TextDecoration... decoration) {
        @NotNull String component = PlainTextComponentSerializer.plainText().serialize(toComponent());
        return Component.text(StringUtils.capitalize(component), textColor, decoration);
    }


}
