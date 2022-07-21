package fr.obelouix.ultimate.commands;

import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.minecraft.extras.RichDescription;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.api.TeleportAPI;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.messages.I18NMessages;
import fr.obelouix.ultimate.utils.LuckPermsUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SpawnCommand extends BaseCommand {

    private static CommentedConfigurationNode spawnConfig;

    public SpawnCommand() {
        try {
            final HoconConfigurationLoader spawnConfigLoader = HoconConfigurationLoader.builder()
                    .path(Path.of(plugin.getDataFolder().getPath(), "spawn.conf"))
                    .build();
            spawnConfig = spawnConfigLoader.load();
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void register() {

        final CommandArgument<CommandSender, String> playerAgument = StringArgument.<CommandSender>newBuilder("player")
                .greedy()
                .asOptional()
                .withSuggestionsProvider(this::suggestions)
                .build();

        COMMAND_MANAGER.command(
                BuildCommand("spawn")
                        .argument(playerAgument, RichDescription.of(Component.text("Player to teleport to spawn")))
                        .permission("obelouix.command.spawn")
                        .build()
        );
    }

    private List<String> suggestions(@NonNull CommandContext<CommandSender> context, @NonNull String s) {
        final List<String> players = new ArrayList<>();
        if (context.getSender().hasPermission("obelouix.command.spawn.others")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (context.getSender() instanceof Player playerSender) {
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
                    Objects.requireNonNull(sender.getServer().getPlayer(sender.getName())),
                    getSpawnLocation(LuckPermsUtils.getUserPrimaryGroup(player))));

            MessagesAPI.sendMessage(player, Component.text(I18NMessages.TELEPORTING.getTranslation(player), NamedTextColor.GOLD));

        } else {
            if (sender.hasPermission("obelouix.command.spawn.others")) {
                if (Objects.equals(targetName, "*")) {
                    Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getOnlinePlayers().forEach(player -> {
                                TeleportAPI.teleport(
                                        Objects.requireNonNull(sender.getServer().getPlayer(sender.getName())),
                                        getSpawnLocation(LuckPermsUtils.getUserPrimaryGroup(player)));

                                if (player == sender) {
                                    MessagesAPI.sendMessage(player, Component.text(I18NMessages.TELEPORTING_EVERYONE_SPAWN.getTranslation(player), NamedTextColor.GOLD));
                                } else {
                                    MessagesAPI.sendMessage(player, Component.text(I18NMessages.TELEPORTING.getTranslation(player), NamedTextColor.GOLD));
                                }
                            }

                    ));
                } else {

                    final Player target = Bukkit.getPlayer(targetName);
                    if (target != null && target.isOnline()) {
                        Bukkit.getScheduler().runTask(plugin, () -> TeleportAPI.teleport(
                                target,
                                Objects.requireNonNull(getSpawnLocation(LuckPermsUtils.getUserPrimaryGroup(target)))
                        ));

                        if (target == sender) {
//                            MessagesAPI.sendMessage(target, Component.text(I18NMessages.TELEPORTING.getTranslation(target), NamedTextColor.GOLD));
                            MessagesAPI.sendMessage(target, Component.translatable("obelouix.teleporting"));
                        } else {
                            MessagesAPI.sendMessage(sender, Component.text(I18NMessages.TELEPORTING_TARGET_SPAWN.getTranslation(sender), NamedTextColor.GOLD)
                                    .replaceText(TextReplacementConfig.builder()
                                            .matchLiteral("{player}")
                                            .replacement(Component.text(targetName, NamedTextColor.RED))
                                            .build()));

                            MessagesAPI.sendMessage(target, Component.text(I18NMessages.TELEPORTED_TO_SPAWN.getTranslation(target), NamedTextColor.GOLD));

                        }

                    }

                }
            }
        }
    }

    private @NotNull Location getSpawnLocation(String userGroup) {
        final UUID worldUUID = UUID.fromString(Objects.requireNonNull(spawnConfig.node("spawns", userGroup, "world").getString()));
        final World world = Bukkit.getWorld(worldUUID);
        return new Location(
                world,
                spawnConfig.node("spawns", userGroup, "x").getDouble(),
                spawnConfig.node("spawns", userGroup, "y").getDouble(),
                spawnConfig.node("spawns", userGroup, "z").getDouble(),
                spawnConfig.node("spawns", userGroup, "yaw").getFloat(),
                spawnConfig.node("spawns", userGroup, "pitch").getFloat()
        );
    }

}
