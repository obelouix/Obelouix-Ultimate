package fr.obelouix.ultimate.commands;

import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.bukkit.parsers.WorldArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.messages.I18NMessages;
import fr.obelouix.ultimate.messages.PluginMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DayCommand extends BaseCommand {


    protected List<String> suggestions(@NonNull CommandPreprocessingContext<CommandSender> commandSenderCommandPreprocessingContext, @NonNull List<String> strings) {
        final CommandSender sender = commandSenderCommandPreprocessingContext.getCommandContext().getSender();
        if (sender.hasPermission("obelouix.command.time.day")) {
            Bukkit.getWorlds().forEach(world -> {
                if (world.getEnvironment().equals(World.Environment.NORMAL)) {
                    strings.add(world.getName());
                }
            });
        }
        return strings;
    }

    @Override
    protected void register() {
        final CommandArgument<CommandSender, World> worldArgument = WorldArgument.<CommandSender>newBuilder("world")
                .asOptional()
                .withSuggestionsProvider(this::worldSuggestions)
                .build();

        COMMAND_MANAGER.command(BuildCommand("day")
                .permission("obelouix.command.time.day")
                .argument(worldArgument)
                .build());

    }

    private List<String> worldSuggestions(@NonNull CommandContext<CommandSender> context, @NonNull String s) {
        final List<String> worlds = new ArrayList<>();
        Bukkit.getWorlds().forEach(world -> {
            if (context.getSender().hasPermission("obelouix.command.time.world." + world.getName()))
                worlds.add(world.getName());
        });
        return worlds;
    }

    @Override
    protected void execute(@NonNull CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();
        World world = context.getOrDefault("world", null);

        if (world == null) {
            if (sender instanceof Player player) {
                world = player.getWorld();
            } else {
                MessagesAPI.sendMessage(sender, Component.text(I18NMessages.COMMAND_WORLD_REQUIRED.getTranslation(sender), NamedTextColor.DARK_RED));
                return;
            }
        }

        if (sender.hasPermission("obelouix.command.time.world." + world.getName())) {
            setDay(world);
            MessagesAPI.sendMessage(sender, PluginMessages.playerTimeMessage(sender, world.getName(), 0));
        } else {
            MessagesAPI.sendMessage(sender, Component.text(I18NMessages.COMMAND_TIME_NOT_ALLOWED_TO_CHANGE_THIS_WORLD.getTranslation(sender), NamedTextColor.DARK_RED)
                    .replaceText(TextReplacementConfig.builder()
                            .matchLiteral("{world}")
                            .replacement(Component.text(world.getName(), NamedTextColor.GOLD))
                            .build()));
        }
    }

    /**
     * We can't set the day asynchronously, so we need to run this method
     *
     * @param world a Bukkit {@link World World}
     */
    private void setDay(World world) {
        new BukkitRunnable() {
            @Override
            public void run() {
                world.setTime(0);
            }
        }.runTask(ObelouixUltimate.getInstance());
    }
}
