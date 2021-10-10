package fr.obelouix.ultimate.commands;

import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.i18n.I18n;
import fr.obelouix.ultimate.messages.PluginMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObelouixUltimateCommand extends BukkitCommand {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final I18n i18n = I18n.getInstance();
    private static final List<String> subcommands = ImmutableList.of("reload", "version");

    public ObelouixUltimateCommand(String commandName) {
        super(commandName);
        this.setUsage("/obelouixultimate <reload | version>");
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) {
        final List<String> subcommandList = new ArrayList<>(Collections.emptyList());
        for (final String subcommand : subcommands) {
            if (sender.hasPermission("obelouix.command.obelouixultimate." + subcommand)) {
                subcommandList.add(subcommand);
            }
        }
        return subcommandList;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String commandLabel, @NotNull String[] args) {
        if (IPermission.hasPermission(commandSender, "obelouix.command.obelouixultimate")) {
            Component message = Component.text("");
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("version") && IPermission.hasPermission(commandSender, "obelouix.command.obelouixultimate.version")) {
                    message = Component.text(i18n.getTranslation(commandSender, "obelouix.plugin.version")).color(NamedTextColor.GREEN)
                            .replaceText(TextReplacementConfig.builder()
                                    .matchLiteral("{0}")
                                    .replacement(Component.text(plugin.getDescription().getVersion()).color(NamedTextColor.AQUA))
                                    .build());

                } else if (args[0].equalsIgnoreCase("reload")) {
                    Config.loadConfig();
                    if(Config.isConfigReloaded()) {
                        message = Component.text(i18n.getTranslation(commandSender, "obelouix.command.obelouixultimate.reload"))
                                .color(NamedTextColor.AQUA);
                    } else {
                        message = Component.text(i18n.getTranslation(commandSender, "obelouix.command.obelouixultimate.reload.failed"))
                                .color(NamedTextColor.DARK_RED);
                    }

                } else {
                    message = PluginMessages.wrongCommandUsage(this, commandSender);
                }
            } else {
                message = PluginMessages.wrongCommandUsage(this, commandSender);
            }
            commandSender.sendMessage(message);
        }
        return false;
    }

}
