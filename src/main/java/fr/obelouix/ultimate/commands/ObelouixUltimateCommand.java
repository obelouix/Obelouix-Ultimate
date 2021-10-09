package fr.obelouix.ultimate.commands;

import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.permissions.IPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObelouixUltimateCommand extends BukkitCommand {

    private static final List<String> subcommands = ImmutableList.of("reload", "version");

    public ObelouixUltimateCommand(String commandName) {
        super(commandName);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, String alias, String[] args) {
        final List<String> subcommandList = new ArrayList<>(Collections.emptyList());
        for (String subcommand : subcommands){
            if(sender.hasPermission("obelouix.command.obelouixultimate." + subcommand)){
                subcommandList.add(subcommand);
            }
        }
        return subcommandList;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String commandLabel, @NotNull String[] args) {
        if(IPermission.hasPermission(commandSender, "obelouix.command.obelouixultimate")){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("version") && IPermission.hasPermission(commandSender, "obelouix.command.obelouixultimate.version")){

                }
            }
        }
        return false;
    }

}
