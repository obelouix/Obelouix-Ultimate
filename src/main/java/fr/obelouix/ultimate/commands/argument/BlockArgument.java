package fr.obelouix.ultimate.commands.argument;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.bukkit.BukkitCaptionKeys;
import cloud.commandframework.captions.CaptionVariable;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import cloud.commandframework.exceptions.parsing.ParserException;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;

public class BlockArgument<C> extends CommandArgument<C, Material> {

    protected BlockArgument(
            final boolean required,
            final @NonNull String name,
            final @NonNull String defaultValue,
            final @Nullable BiFunction<@NonNull CommandContext<C>, @NonNull String,
                    @NonNull List<@NonNull String>> suggestionsProvider,
            final @NonNull ArgumentDescription defaultDescription
    ) {
        super(required, name, new BlockArgument.BlockParser<>(), defaultValue, Material.class, suggestionsProvider, defaultDescription);
    }

    public static <C> Builder<C> newBuilder(final @NonNull String name) {
        return new BlockArgument.Builder<>(name);
    }


    public static <C> @NonNull CommandArgument<C, Material> of(final @NonNull String name) {
        return BlockArgument.<C>newBuilder(name).asRequired().build();
    }

    public static final class Builder<C> extends CommandArgument.Builder<C, Material> {

        private Builder(final @NonNull String name) {
            super(Material.class, name);
        }

        @Override
        public @NonNull CommandArgument<C, Material> build() {
            return new BlockArgument<>(
                    this.isRequired(),
                    this.getName(),
                    this.getDefaultValue(),
                    this.getSuggestionsProvider(),
                    this.getDefaultDescription()
            );
        }
    }

    public static final class BlockParser<C> implements ArgumentParser<C, Material> {

        @Override
        public @NonNull ArgumentParseResult<@NonNull Material> parse(@NonNull CommandContext<@NonNull C> commandContext, @NonNull Queue<@NonNull String> inputQueue) {
            String input = inputQueue.peek();
            if (input == null) {
                return ArgumentParseResult.failure(new NoInputProvidedException(
                        BlockArgument.BlockParser.class,
                        commandContext
                ));
            }

            try {
                final Material material = Material.valueOf(input.toUpperCase());
                inputQueue.remove();

                return material.isBlock() ? ArgumentParseResult.success(material) : ArgumentParseResult.failure(new BlockParseException(input, commandContext));
            } catch (final IllegalArgumentException exception) {
                return ArgumentParseResult.failure(new BlockParseException(input, commandContext));
            }
        }

        @Override
        public @NonNull List<@NonNull String> suggestions(@NonNull CommandContext<C> commandContext, @NonNull String input) {
            final List<String> completions = new ArrayList<>();
            for (Material material : Material.values()) {
                if (material.isBlock()) completions.add(material.name().toLowerCase());
            }
            return completions;
        }

    }

    public static final class BlockParseException extends ParserException {

        private final String input;

        /**
         * Construct a new MaterialParseException
         *
         * @param input   Input
         * @param context Command context
         */
        public BlockParseException(
                final @NonNull String input,
                final @NonNull CommandContext<?> context
        ) {
            super(
                    BlockArgument.BlockParser.class,
                    context,
                    BukkitCaptionKeys.ARGUMENT_PARSE_FAILURE_MATERIAL,
                    CaptionVariable.of("input", input)
            );
            this.input = input;
        }

        /**
         * Get the input
         *
         * @return Input
         */
        public @NonNull String getInput() {
            return this.input;
        }
    }

}
