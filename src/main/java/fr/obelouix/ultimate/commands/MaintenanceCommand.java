package fr.obelouix.ultimate.commands;

import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.messages.PluginMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class MaintenanceCommand extends BukkitCommand {
    public MaintenanceCommand(String name) {
        super(name);
        this.setUsage("/maintenance <on|off>");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
        if (IPermission.hasPermission(commandSender, "obelouix.command.maintenance")) {
            if (args.length == 1) {
                switch (args[0].toLowerCase(Locale.ROOT)) {
                    case "on" -> {
                        if (!Config.isServerInMaintenance()) {
                            Config.setServerInMaintenance(true);
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (!player.hasPermission("obelouix.maintenance.bypass")) {
                                    player.kick(Component.text("Server in maintenance"), PlayerKickEvent.Cause.TIMEOUT);
                                }
                            }
                        } else {
                            commandSender.sendMessage("TBD");
                        }
                    }
                    case "off" -> {
                        if (Config.isServerInMaintenance()) {
                            Config.setServerInMaintenance(false);
                        } else {
                            commandSender.sendMessage("TBD");
                        }
                    }
                    default -> commandSender.sendMessage(PluginMessages.wrongCommandUsage(this, commandSender));
                }
            } else {
                commandSender.sendMessage(PluginMessages.wrongCommandUsage(this, commandSender));
            }
        }
        return false;
    }
}
