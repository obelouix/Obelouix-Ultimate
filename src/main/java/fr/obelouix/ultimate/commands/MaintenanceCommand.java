package fr.obelouix.ultimate.commands;

import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.i18n.I18n;
import fr.obelouix.ultimate.messages.PluginMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class MaintenanceCommand extends BukkitCommand {

    private I18n i18n = I18n.getInstance();

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
                                    player.kick(Component.text(i18n.getTranslation(commandSender, "obelouix.maintenance.started")), PlayerKickEvent.Cause.KICK_COMMAND);
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

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
        if (sender.hasPermission("obelouix.command.maintenance")) {
            return ImmutableList.of("on", "off");
        }
        return super.tabComplete(sender, alias, args, location);
    }
}
