package fr.obelouix.ultimate.commands;

import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import cloud.commandframework.minecraft.extras.RichDescription;
import com.google.common.base.Preconditions;
import fr.obelouix.ultimate.api.TeleportAPI;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpawnCommand extends BaseCommand {
    @Override
    protected void register() {
        COMMAND_MANAGER.command(
                BuildCommand("spawn")
                        .argument(StringArgument.optional("player"), RichDescription.of(Component.text("Player to teleport to spawn")))
                        .permission("obelouix.command.spawn")
                        .build()
        ).commandSuggestionProcessor(this::suggestions);
    }

    private List<String> suggestions(@NonNull CommandPreprocessingContext<CommandSender> context, @NonNull List<String> strings) {
        final List<String> players = new ArrayList<>();
        if (context.getCommandContext().hasPermission("obelouix.command.spawn.others")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (context.getCommandContext().getSender() instanceof Player playerSender) {
                    //Don't include players that the sender cannot see
                    if (playerSender.canSee(player)) players.add(player.getName());
                } else players.add(player.getName());
            });
            players.add("*");
        }
        return players;
    }

    @Override
    protected void execute(@NonNull CommandContext<CommandSender> context) {
        @NonNull final CommandSender sender = context.getSender();
        final String targetName = (String) context.getOptional("player").orElse(null);

        if (targetName == null && sender instanceof Player player) {
            Bukkit.getScheduler().runTask(plugin, () -> TeleportAPI.teleport(
                    Objects.requireNonNull(player.getServer().getPlayer(player.getName())),
                    Objects.requireNonNull(player.getServer().getPlayer(player.getName())).getWorld().getSpawnLocation())
            );
        } else {
            if (sender.hasPermission("obelouix.command.spawn.others")) {
                if (Objects.equals(targetName, "*")) {
                    Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getOnlinePlayers().forEach(player ->
                            TeleportAPI.teleport(
                                    Objects.requireNonNull(sender.getServer().getPlayer(sender.getName())),
                                    Objects.requireNonNull(sender.getServer().getPlayer(sender.getName())).getWorld().getSpawnLocation())
                    ));
                } else {
                    // Allow to partially type a player name
                    final String match = String.valueOf(Bukkit.getOnlinePlayers().stream().filter(player -> player.getName().contains(targetName)).findFirst());
                    Preconditions.checkNotNull(Bukkit.getPlayer(match));
                    final Player target = Bukkit.getPlayer(match);

                    Bukkit.getScheduler().runTask(plugin, () -> TeleportAPI.teleport(
                            Objects.requireNonNull(target.getServer().getPlayer(target.getName())),
                            Objects.requireNonNull(target.getServer().getPlayer(target.getName())).getWorld().getSpawnLocation())
                    );
                }
            }
        }

        //plugin.getLogger().info("ntm le serveur, tp: " + target);
        //if(target != null) TeleportAPI.teleportTask(Bukkit.getPlayer(target), Objects.requireNonNull(Bukkit.getPlayer(target)).getWorld().getSpawnLocation());
        //else if(context.getSender() instanceof Player player) TeleportAPI.teleportTask(player, player.getWorld().getSpawnLocation());
        /*EssentialsSpawn essentialsSpawn = null;
        IEssentials essentials = null;
        if(PluginDetector.getEssentialsSpawn() != null) essentialsSpawn = PluginDetector.getEssentialsSpawn();
        if(PluginDetector.getIEssentialsAPI() != null) essentials = PluginDetector.getIEssentialsAPI();*/
        // final AtomicReference<Location> spawnLoc = new AtomicReference<>(Objects.requireNonNull(Bukkit.getWorld(Bukkit.getWorlds().get(0).getKey())).getSpawnLocation());
        //TeleportAPI.TeleportAll((Player) context.getSender(), ((Player) context.getSender()).getWorld().getSpawnLocation());
        /*if (context.hasPermission("obelouix.command.spawn.others") && target != null) {
            if(target.equals("*")) {
                //EssentialsSpawn finalEssentialsSpawn = essentialsSpawn;
                //IEssentials finalEssentials = essentials;
                Bukkit.getOnlinePlayers().forEach(player -> {
                  //  if(finalEssentialsSpawn != null) spawnLoc.set(finalEssentialsSpawn.getSpawn(finalEssentials.getUser(Bukkit.getPlayer(target)).getGroup()));
                    TeleportAPI.TeleportAll(player.getWorld().getSpawnLocation());
                });
            } else if (Bukkit.getPlayer(target) != null) {
                final Player player = Bukkit.getPlayer(target);
                //if(essentialsSpawn != null) spawnLoc.set(essentialsSpawn.getSpawn(essentials.getUser(Bukkit.getPlayer(target)).getGroup()));
                TeleportAPI.TeleportAll(player, player.getWorld().getSpawnLocation());
            }
        } else if (context.hasPermission("obelouix.command.spawn") && target == null) {
            if(context.getSender() instanceof Player player) {
                //if(essentialsSpawn != null) spawnLoc.set(essentialsSpawn.getSpawn(essentials.getUser(player).getGroup()));
                TeleportAPI.TeleportAll(player, player.getWorld().getSpawnLocation());
            }
        }*/
    }
}
