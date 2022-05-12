package fr.obelouix.ultimate.commands;

import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.commands.manager.CommandManager;
import fr.obelouix.ultimate.i18n.I18n;
import fr.obelouix.ultimate.permissions.IPermission;
import fr.obelouix.ultimate.renderer.ImageMapRenderder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class MapImageCommand extends BaseCommand {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    @Override
    public void register() {
        CommandManager.getInstance().command(
                CommandManager.getInstance().commandBuilder("mapimg")
                        .argument(StringArgument.of("action"))
                        .handler(this::execute)
                        .build()
        ).setCommandSuggestionProcessor(this::suggestions);
    }

    private List<String> suggestions(@NonNull CommandPreprocessingContext<CommandSender> commandSenderCommandPreprocessingContext, @NonNull List<String> strings) {
        return List.of("set", "clear");
    }

    @Override
    protected void execute(@NonNull CommandContext<CommandSender> context) {
        if (context.getSender() instanceof ConsoleCommandSender consoleCommandSender) {
            MessageSender.sendMessage(consoleCommandSender, Component.text(
                    I18n.getInstance().getTranslation(consoleCommandSender, "obelouix.commands.not_for_console"),
                    NamedTextColor.DARK_RED));
        } else if (context.getSender() instanceof Player player) {
            if (IPermission.hasPermission(context.getSender(), "obelouix.command.mapimg")) {
                ItemStack mainHandItem = player.getInventory().getItemInMainHand();
                ItemStack offHandItem = player.getInventory().getItemInOffHand();
                boolean isPlayerHoldingMap = mainHandItem.equals(new ItemStack(Material.MAP)) || offHandItem.equals(new ItemStack(Material.MAP));
                if (isPlayerHoldingMap) {
                    if (context.get("action").equals("set")) {
                        MapView map = plugin.getServer().createMap(player.getWorld());

                        for (MapRenderer mapRenderer : map.getRenderers()) {
                            map.removeRenderer(mapRenderer);
                            map.setUnlimitedTracking(true);
                            map.addRenderer(new ImageMapRenderder());
                        }

                    }
                }
            }
        }
    }
}