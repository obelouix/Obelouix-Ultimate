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

public class MiddayCommand extends BaseCommand {

    public void register() {
        CommandManager.getInstance().command(
                        CommandManager.getInstance()
                                .commandBuilder("midday")
                                .argument(StringArgument.optional("world"))
                                .handler(this::execute)
                                .build())
                .setCommandSuggestionProcessor(this::suggestions);
    }

    private List<String> suggestions(@NonNull CommandPreprocessingContext<CommandSender> commandSenderCommandPreprocessingContext, @NonNull List<String> strings) {
        final CommandSender sender = commandSenderCommandPreprocessingContext.getCommandContext().getSender();
        if (sender.hasPermission("obelouix.command.midday")) {
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
        if (IPermission.hasPermission(sender, "obelouix.command.midday")) {
            if (context.getOptional("world").isPresent()) {
                world = (String) context.getOptional("world").get();
            }
            if (world != null) {
                if (Bukkit.getWorld(world) != null) {
                    setMidday(world);
                    MessageSender.sendMessage(sender, PluginMessages.playerTimeMessage(sender, world, 6000));
                } else {
                    MessageSender.sendMessage(sender, PluginMessages.nonExistentWorldMessage(sender, world));
                }
            } else {
                if (sender instanceof Player player) {
                    setMidday(player.getWorld().getName());
                    MessageSender.sendMessage(player, PluginMessages.playerTimeMessage(player, player.getWorld().getName(), 6000));

                } else {
                    MessageSender.sendMessage(sender,
                            Component.text(i18n.getTranslation(sender, "obelouix.command.day.console.too_few_arguments"), NamedTextColor.DARK_RED));
                }
            }
        }
    }

    /**
     * We can't set the midday asynchronously, so we need to run this method
     *
     * @param world world name
     */
    private void setMidday(String world) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Objects.requireNonNull(Bukkit.getWorld(world)).setTime(6000);
            }
        }.runTask(ObelouixUltimate.getInstance());
    }
}
