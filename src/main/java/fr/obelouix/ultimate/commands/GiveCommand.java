package fr.obelouix.ultimate.commands;

import cloud.commandframework.arguments.standard.IntegerArgument;
import cloud.commandframework.bukkit.parsers.MaterialArgument;
import cloud.commandframework.bukkit.parsers.PlayerArgument;
import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class GiveCommand extends BaseCommand {
    @Override
    protected void register() {
        COMMAND_MANAGER.command(BuildCommand("give")
                .permission("obelouix.command.give")
                .argument(PlayerArgument.of("player"))
                .argument(MaterialArgument.of("item"))
                .argument(IntegerArgument.of("quantity"))
                .build());
    }

    @Override
    protected void execute(@NonNull CommandContext<CommandSender> context) {

        final Player target = context.getOrDefault("player", null);
        final CommandSender sender = context.getSender();
        final ItemStack itemStack = new ItemStack(context.get("item"), context.get("quantity"));

        if (target != null && sender.hasPermission("obelouix.command.give." + context.get("item"))) {
            target.getInventory().addItem(itemStack);
        }

    }
}
