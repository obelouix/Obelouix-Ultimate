package fr.obelouix.ultimate.commands;

import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.bukkit.parsers.WorldArgument;
import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.i18n.TranslationKey;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class DifficultyCommand extends BaseCommand {
    @Override
    protected void register() {

        final @NonNull CommandArgument<@NonNull CommandSender, @NonNull String> difficultyArgument = StringArgument.<CommandSender>newBuilder("difficulty")
                .asOptional()
                .withSuggestionsProvider(this::difficultySuggestions)
                .build();

        COMMAND_MANAGER.command(BuildCommand("difficulty")
                .permission("obelouix.command.difficulty")
                .argument(difficultyArgument)
                .argument(WorldArgument.optional("world"))
                .build());
    }

    private List<String> difficultySuggestions(@NonNull CommandContext<CommandSender> context, @NonNull String s) {
        return List.of("peaceful", "easy", "normal", "hard");
    }

    @Override
    protected void execute(@NonNull CommandContext<CommandSender> context) {

        if (context.getOptional("difficulty").isPresent()) {

            final Difficulty difficulty = switch (context.get("difficulty").toString().toLowerCase()) {
                case "peaceful" -> Difficulty.PEACEFUL;
                case "easy" -> Difficulty.EASY;
                case "normal" -> Difficulty.NORMAL;
                case "hard" -> Difficulty.HARD;
                default -> null;
            };

            final World world = (World) context.getOptional("world").orElse(null);

            if (difficulty == null) {
                Bukkit.getWorlds().forEach(world1 -> MessagesAPI.sendMessage(context.getSender(), Component.text("wrong difficulty")));
            } else {
                if (world == null) {
                    Bukkit.getWorlds().forEach(world1 -> world1.setDifficulty(difficulty));
                } else {
                    world.setDifficulty(difficulty);
                }
            }
        } else {
            MessagesAPI.sendMessage(context.getSender(), TranslationKey.WORLD_DIFFICULTIES_HEADER.toCenteredTextComponent("="));
//            Bukkit.getWorlds().forEach(world -> MessagesAPI.sendMessage(context.getSender(), TranslationKey.DIRECTION.toCenteredTextComponent("-")/*Component.translatable(world.getDifficulty().translationKey())*/));
        }
    }
}
