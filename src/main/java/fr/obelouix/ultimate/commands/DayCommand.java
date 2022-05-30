package fr.obelouix.ultimate.commands;

import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.commands.manager.CommandManager;
import fr.obelouix.ultimate.messages.I18NMessages;
import fr.obelouix.ultimate.messages.PluginMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Objects;

public class DayCommand extends BaseCommand {

    @Override
    public void register() {
        CommandManager.getInstance().command(
                        CommandManager.getInstance()
                                .commandBuilder("day")
                                .argument(StringArgument.optional("world"))
                                .handler(this::execute)
                                .build())
                .setCommandSuggestionProcessor(this::suggestions);
    }

    private List<String> suggestions(@NonNull CommandPreprocessingContext<CommandSender> commandSenderCommandPreprocessingContext, @NonNull List<String> strings) {
        final CommandSender sender = commandSenderCommandPreprocessingContext.getCommandContext().getSender();
        if (sender.hasPermission("obelouix.command.day")) {
            Bukkit.getWorlds().forEach(world -> {
                if (world.getEnvironment().equals(World.Environment.NORMAL)) {
                    strings.add(world.getName());
                }
            });
        }
        return strings;
    }

    @Override
    protected void execute(@NonNull CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();
        String world = null;
        if (IPermission.hasPermission(sender, "obelouix.command.day")) {
            if (context.getOptional("world").isPresent()) {
                world = (String) context.getOptional("world").get();
            }
            if (world != null) {
                if (Bukkit.getWorld(world) != null) {
                    setDay(world);
                    MessagesAPI.sendMessage(sender, PluginMessages.playerTimeMessage(sender, world, 0));
                } else {
                    MessagesAPI.sendMessage(sender, PluginMessages.nonExistentWorldMessage(sender, world));
                }
            } else {
                if (sender instanceof Player player) {
                    setDay(player.getWorld().getName());
                    MessagesAPI.sendMessage(player, PluginMessages.playerTimeMessage(player, player.getWorld().getName(), 0));

                } else {
                    MessagesAPI.sendMessage(sender,
                            Component.text(I18NMessages.COMMAND_NOT_ENOUGH_ARGS.getSystemTranslation(), NamedTextColor.DARK_RED));
                }
            }
        }
    }

    /**
     * We can't set the day asynchronously, so we need to run this method
     *
     * @param world world name
     */
    private void setDay(String world) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Objects.requireNonNull(Bukkit.getWorld(world)).setTime(0);
            }
        }.runTask(ObelouixUltimate.getInstance());
    }

}
