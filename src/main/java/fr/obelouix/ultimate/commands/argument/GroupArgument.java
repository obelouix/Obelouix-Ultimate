package fr.obelouix.ultimate.commands.argument;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.data.GroupInternal;
import fr.obelouix.ultimate.utils.LuckPermsUtils;
import net.luckperms.api.model.group.Group;
import net.minecraft.network.protocol.game.ServerboundChatPreviewPacket;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;

public class GroupArgument<C> extends CommandArgument<C, GroupInternal> {
    ServerboundChatPreviewPacket

    private GroupArgument(
            final boolean required,
            final String name,
            final String defaultValue,
            final @Nullable BiFunction<CommandContext<C>, String, List<String>> suggestionsProvider,
            final ArgumentDescription defaultDescription
    ) {
        super(required, name, new Parser<>(), defaultValue, GroupInternal.class, suggestionsProvider, defaultDescription);
    }

    public static class Parser<C> implements ArgumentParser<C, GroupInternal> {
        @Override
        public @NonNull ArgumentParseResult<GroupInternal> parse(@NonNull CommandContext<@NonNull C> commandContext, @NonNull Queue<@NonNull String> inputQueue) {
            return null;
        }

        @Override
        public @NonNull List<@NonNull String> suggestions(@NonNull CommandContext<C> commandContext, @NonNull String input) {
            final List<String> groups = new ArrayList<>();
            for (final Group group : LuckPermsUtils.getGroups()) {
                groups.add(group.getName());
            }
            return groups;
        }

        @Override
        public @NonNull <O> ArgumentParser<C, O> map(BiFunction<CommandContext<C>, GroupInternal, ArgumentParseResult<O>> mapper) {
            return ArgumentParser.super.map(mapper);
        }

        @Override
        public boolean isContextFree() {
            return ArgumentParser.super.isContextFree();
        }

        @Override
        public int getRequestedArgumentCount() {
            return ArgumentParser.super.getRequestedArgumentCount();
        }
    }
}
