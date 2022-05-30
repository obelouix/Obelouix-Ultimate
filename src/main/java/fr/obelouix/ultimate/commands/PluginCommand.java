package fr.obelouix.ultimate.commands;

import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class PluginCommand extends Command {
    public PluginCommand(String name) {
        super(name);
        this.setUsage("/plugins");
        this.setAliases(ImmutableList.of("pl"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
        if (IPermission.hasPermission(commandSender, "bukkit.command.plugins") && label.equalsIgnoreCase("plugins")) {
            Component message = Component.text("Plugins (%i): ")
                    .replaceText(TextReplacementConfig.builder()
                            .matchLiteral("%i")
                            .replacement(Component.text(Bukkit.getPluginManager().getPlugins().length)
                                    .color(NamedTextColor.GREEN))
                            .build());
            for (Iterator<Plugin> plugin = Arrays.stream(Bukkit.getPluginManager().getPlugins()).iterator(); plugin.hasNext(); ) {
                String pluginName = plugin.next().getName();
                Component pluginComponent = Component.text(pluginName);
                if (Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(pluginName)).isEnabled()) {
                    message = message.append(pluginComponent.color(NamedTextColor.GREEN));
                } else {
                    message = message.append(pluginComponent.color(NamedTextColor.RED));
                }
                if (plugin.hasNext()) {
                    message = message.append(Component.text(", "));
                }
            }

            MessagesAPI.sendMessage(commandSender, message);
        }
        return false;
    }
}
