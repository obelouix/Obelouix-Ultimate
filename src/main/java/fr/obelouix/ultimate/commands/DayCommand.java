package fr.obelouix.ultimate.commands;

import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.commands.manager.CommandManager;
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

    private static Component message;
    private static CommandSender sender;


    /*@Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
        sender = commandSender;
        if (commandSender instanceof Player player && IPermission.hasPermission(commandSender, "obelouix.command.day")) {
            if (args.length == 0) {
                setDay(player.getWorld().getName());
            } else if (args.length == 1) {
                setDay(args[0]);
            } else {
                MessageSender.sendMessage(player, PluginMessages.wrongCommandUsage(this, player));
            }
            MessageSender.sendMessage(sender, message);
        } else if (commandSender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                commandSender.sendMessage(i18n.getTranslation(commandSender, "obelouix.command.day.console.too_few_arguments"));
            } else if (args.length == 1) {
                setDay(args[0]);
                MessageSender.sendMessage(sender, message);
            } else {
                commandSender.sendMessage(i18n.getTranslation(commandSender, "obelouix.command.day.console.too_many_arguments"));
            }
        }
        return false;
    }

    @NotNull
    protected static List<String> getNormalWorlds() {
        final List<String> worldList = new java.util.ArrayList<>(Collections.emptyList());
        for (final World world : Bukkit.getWorlds()) {
            // Only add overworlds as we don't care about changing the time in nether and ends dimensions
            if (world.getEnvironment() == World.Environment.NORMAL) {
                worldList.add(world.getName());
            }
        }
        return worldList;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (sender.hasPermission("obelouix.command.day") && args.length == 1) {
            return getNormalWorlds();
        }
        return super.tabComplete(sender, alias, args);
    }

    private void setDay(String world) {
        if (Bukkit.getWorld(world) != null) {
            Objects.requireNonNull(Bukkit.getWorld(world)).setTime(0);
            if (sender instanceof Player player) {
                message = PluginMessages.playerTimeMessage(player, 0);
            } else {
                message = PluginMessages.playerTimeMessage(sender, world, 0);
            }

        } else {
            message = PluginMessages.nonExistentWorldMessage(sender, world);
        }

    }*/

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
                    MessageSender.sendMessage(sender, PluginMessages.playerTimeMessage(sender, world, 0));
                }
            } else {
                if (sender instanceof Player player) {
                    setDay(player.getWorld().getName());
                    MessageSender.sendMessage(player, PluginMessages.playerTimeMessage(player, player.getWorld().getName(), 0));

                } else {
                    MessageSender.sendMessage(sender,
                            Component.text(i18n.getTranslation(sender, "obelouix.command.day.console.too_few_arguments"), NamedTextColor.DARK_RED));
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
