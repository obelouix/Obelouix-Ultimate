package fr.obelouix.ultimate.commands;

import fr.obelouix.ultimate.i18n.I18n;
import fr.obelouix.ultimate.messages.PluginMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NightCommand extends BukkitCommand {

    private static final I18n i18n = I18n.getInstance();
    private static Component message;
    private static CommandSender sender;

    public NightCommand(String name) {
        super(name);
        this.setUsage("/night [world]");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
        sender = commandSender;
        if (commandSender instanceof Player player && IPermission.hasPermission(commandSender, "obelouix.command.night")) {
            if (args.length == 0) {
                setNight(player.getWorld().getName());
            } else if (args.length == 1) {
                setNight(args[0]);
            } else {
                player.sendMessage(PluginMessages.wrongCommandUsage(this, player));
            }
            player.sendMessage(message);
        } else {
            if (args.length == 0) {
                commandSender.sendMessage(i18n.getTranslation(commandSender, "obelouix.command.day.console.too_few_arguments"));
            } else if (args.length == 1) {
                setNight(args[0]);
                commandSender.sendMessage(message);
            } else {
                commandSender.sendMessage(i18n.getTranslation(commandSender, "obelouix.command.day.console.too_many_arguments"));
            }
        }
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (sender.hasPermission("obelouix.command.night") && args.length == 1) {
            final List<String> worldList = new java.util.ArrayList<>(Collections.emptyList());
            for (final World world : Bukkit.getWorlds()) {
                // Only add overworlds as we don't care about changing the time in nether and ends dimensions
                if (world.getEnvironment() == World.Environment.NORMAL) {
                    worldList.add(world.getName());
                }
            }
            return worldList;
        }
        return super.tabComplete(sender, alias, args);
    }

    private void setNight(String world) {
        if (Bukkit.getWorld(world) != null) {
            Objects.requireNonNull(Bukkit.getWorld(world)).setTime(13188);
            if (sender instanceof Player player) {
                message = PluginMessages.playerTimeMessage(player, 13188);
            } else {
                message = PluginMessages.playerTimeMessage(sender, world, 13188);
            }

        } else {
            message = PluginMessages.nonExistentWorldMessage(sender, world);
        }

    }

}
