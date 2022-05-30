package fr.obelouix.ultimate.commands;

import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.api.TranslationAPI;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.messages.PluginMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class MaintenanceCommand extends BukkitCommand {

    protected final TranslationAPI translationAPI = ObelouixUltimate.getInstance().getTranslationAPI();

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
                                player.hasPermission("obelouix.maintenance.bypass");//TODO: FIX THIS ->  player.kickPlayer(Component.text(i18n.getTranslation(commandSender, "obelouix.maintenance.started")));
                            }
                        } else {
                            MessagesAPI.sendMessage(commandSender, Component.text(translationAPI.getTranslation(commandSender, "obelouix.maintenance.already_enabled")));
                        }
                    }
                    case "off" -> {
                        if (Config.isServerInMaintenance()) {
                            Config.setServerInMaintenance(false);
                        } else {

                            MessagesAPI.sendMessage(commandSender, Component.text(translationAPI.getTranslation(commandSender, "obelouix.maintenance.already_disabled")));
                        }
                    }
                    default ->
                            MessagesAPI.sendMessage(commandSender, PluginMessages.wrongCommandUsage(this, commandSender));
                }
            } else {
                MessagesAPI.sendMessage(commandSender, PluginMessages.wrongCommandUsage(this, commandSender));
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
