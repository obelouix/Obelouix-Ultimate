package fr.obelouix.ultimate.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.bukkit.parsers.location.LocationArgument;
import cloud.commandframework.context.CommandContext;
import com.google.common.collect.Lists;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.commands.argument.BlockArgument;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Objects;

public class FillCommand extends BaseCommand {

    @Override
    protected void register() {

        COMMAND_MANAGER.command(BuildCommand("fill")
                .argument(LocationArgument.newBuilder("pos1"))
                .argument(LocationArgument.newBuilder("pos2"))
                .argument(BlockArgument.of("block"), ArgumentDescription.of(""))
                .permission("obelouix.command.fill")
                .senderType(Player.class)
                .build()
        );
    }

    @Override
    protected void execute(@NonNull CommandContext<CommandSender> context) {
        final Location pos1 = context.get("pos1");
        final Location pos2 = context.get("pos2");
        final World world = Objects.requireNonNull(Bukkit.getPlayer(context.getSender().getName())).getWorld();
        final Material block = context.get("block");
        final List<Location> blocksPos = Lists.newArrayList();

        int topBlockX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int topBlockY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int topBlockZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        int bottomBlockX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int bottomBlockY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int bottomBlockZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    blocksPos.add(new Location(world, x, y, z));
                }
            }
        }

        blocksPos.forEach(location -> Bukkit.getScheduler().runTask(plugin, () -> world.setBlockData(location, block.createBlockData())));
        MessagesAPI.sendMessage(context.getSender(), Component.text(blocksPos.size()));

    }
}
