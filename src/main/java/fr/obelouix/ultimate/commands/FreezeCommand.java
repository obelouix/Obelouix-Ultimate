package fr.obelouix.ultimate.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.bukkit.parsers.PlayerArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.messages.I18NMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FreezeCommand extends BaseCommand implements Listener {

    @Override
    protected void register() {

        final CommandArgument<CommandSender, Player> playerAgument = PlayerArgument.<CommandSender>newBuilder("player")
                .asRequired()
                .withSuggestionsProvider(this::playerSuggestions)
                .build();


        COMMAND_MANAGER.command(BuildCommand("freeze")
                .permission("obelouix.command.freeze")
                .argument(playerAgument)
                .flag(COMMAND_MANAGER.flagBuilder("silent")
                        .withAliases("s")
                        .withDescription(ArgumentDescription.of("Freeze without alerting your target")))
                .build()).commandSuggestionProcessor(this::playerSuggestions);
    }

    private List<String> playerSuggestions(@NonNull CommandPreprocessingContext<CommandSender> PreprocessingContext, @NonNull List<String> strings) {
        return strings;
    }

    private List<String> playerSuggestions(@NonNull CommandContext<CommandSender> context, @NonNull String s) {
        final List<String> completion = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!player.hasPermission("obelouix.protection.freeze")) completion.add(player.getName());
        });
        return completion;
    }

    @Override
    protected void execute(@NonNull CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();
        final Player target = context.getOrDefault("player", null);
        final boolean silentFreeze = context.flags().isPresent("silent");

        if (target != null) {
            if (target.getName().equals(sender.getName())) {

                if (target.hasPermission("obelouix.protection.freeze")) {
                    MessagesAPI.sendMessage(sender, Component.text(I18NMessages.PROTECTION_FREEZE.getTranslation(sender), NamedTextColor.DARK_RED));
                    return;
                }

                if (!target.isFrozen() && target.getFreezeTicks() != 100000) {
                    target.setFreezeTicks(100000);
                    target.lockFreezeTicks(true);
                    if (!silentFreeze)
                        MessagesAPI.sendMessage(target, Component.text(I18NMessages.COMMAND_FREEZE_TARGET_FROZEN.getTranslation(target), NamedTextColor.DARK_RED));
                    MessagesAPI.sendMessage(target, Component.text(I18NMessages.COMMAND_FREEZE_SENDER_TARGET_FROZEN.getTranslation(sender))
                            .replaceText(TextReplacementConfig.builder()
                                    .matchLiteral("{player}")
                                    .replacement(target.getName())
                                    .build()));
                } else {
                    target.setFreezeTicks(0);
                    target.lockFreezeTicks(false);
                    if (!silentFreeze)
                        MessagesAPI.sendMessage(target, Component.text(I18NMessages.COMMAND_FREEZE_TARGET_UNFROZEN.getTranslation(target), NamedTextColor.GRAY));
                    MessagesAPI.sendMessage(target, Component.text(I18NMessages.COMMAND_FREEZE_SENDER_TARGET_UNFROZEN.getTranslation(sender))
                            .replaceText(TextReplacementConfig.builder()
                                    .matchLiteral("{player}")
                                    .replacement(target.getName())
                                    .build()));
                }
            } else {
                MessagesAPI.sendMessage(sender, Component.text(I18NMessages.COMMAND_FREEZE_TARGET_IS_SENDER.getTranslation(sender), NamedTextColor.DARK_RED));
            }

        }

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (player.isFrozen() && player.getFreezeTicks() == 100000) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamaged(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && player.isFrozen() && player.getFreezeTicks() == 100000) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (player.isFrozen() && player.getFreezeTicks() == 100000) {
            event.setCancelled(true);
        }
    }

}
