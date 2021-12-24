package fr.obelouix.ultimate.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.commands.manager.CommandManager;
import fr.obelouix.ultimate.i18n.I18n;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class ObelouixUltimateCommand /*extends BukkitCommand*/ {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final I18n i18n = I18n.getInstance();
    private static final List<String> subcommands = ImmutableList.of("reload", "version");

/*    public ObelouixUltimateCommand(String commandName) {
        super(commandName);
        this.setUsage("/obelouixultimate <reload | version>");
    }*/

    public void register() throws Exception {
        @NonNull CommandMeta meta = CommandMeta.simple().build();
        CommandManager.getInstance().command(
                CommandManager.getInstance().commandBuilder("obelouixultimate")
                        .argument(StringArgument.optional("world"), ArgumentDescription.of("world"))
                        .handler(this::execute)
                        .build()
        );
    }

    private void execute(@NonNull CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();

        if (IPermission.hasPermission(sender, "obelouix.command.obelouixultimate")) {
            Component message;
            final String world;

            if (context.getOptional("world").isPresent()) {
                world = String.valueOf(context.getOptional("world").get());
                sender.sendMessage(world);
            }
        }
    }

   /* @Override
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
            Component message;
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
    }*/

}
