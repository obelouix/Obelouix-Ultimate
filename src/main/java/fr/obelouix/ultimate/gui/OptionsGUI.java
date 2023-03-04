package fr.obelouix.ultimate.gui;

import fr.obelouix.ultimate.I18N.Translator;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.config.PlayerConfig;
import fr.obelouix.ultimate.inventory.ChestInventory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;

import java.util.Collections;

public class OptionsGUI {

    private final Player player;
    private ChestInventory chestInventory;

    public OptionsGUI(Player player) {
        this.player = player;
        MessagesAPI.sendMessage(player, Component.text(player.locale().toString()));
        createInterface();
    }


    private void createInterface() {

        chestInventory = ChestInventory.builder()
                .rows(6)
                .title(Component.translatable("options.title", NamedTextColor.DARK_BLUE))
                .setItem(0, setupCoordinatesItem())
                .setItem(9, setStateItem(isCoordinatesEnabled()))
                .fill(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), true)
                .setItem(2, new ItemStack(Material.COMPASS))
                .cancelClick()
                //.update(true, 20)
                .action(9, coordinatesActions())
                .build();
    }

    private ItemStack setupCoordinatesItem() {
        final ItemStack coordinatesItem = new ItemStack(Material.COMPASS);
        final ItemMeta meta = coordinatesItem.getItemMeta();
        meta.displayName(GlobalTranslator.render(Component.translatable("obelouix.coordinates")
                .color(NamedTextColor.AQUA)
                .decoration(TextDecoration.ITALIC, false), player.locale()));

        coordinatesItem.setItemMeta(meta);
        return coordinatesItem;
    }

    private ItemStack setStateItem(boolean state) {
        ItemStack item = new ItemStack(Material.LIME_DYE);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.lore(Collections.singletonList(Component.empty()));

        if (state) {
            item = new ItemStack(Material.LIME_DYE);
            itemMeta.displayName(Translator.translate(Component.translatable("obelouix.coordinates.on")
                    .color(NamedTextColor.GREEN)
                    .decoration(TextDecoration.ITALIC, false), player.locale()));
        } else {
            item = new ItemStack(Material.RED_DYE);
            itemMeta.displayName(Translator.translate(Component.translatable("obelouix.coordinates.off")
                    .color(NamedTextColor.RED)
                    .decoration(TextDecoration.ITALIC, false), player.locale()));
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    private InventoryRunnable coordinatesActions() {
        return new InventoryRunnable() {
            @Override
            public void run() {
                if (isCoordinatesEnabled()) {
                    if (player.performCommand("coords off")) {
                        chestInventory.setItem(9, setStateItem(false));
                    }
                } else {
                    if (player.performCommand("coords on")) {
                        chestInventory.setItem(9, setStateItem(true));
                    }
                }
            }
        };
    }

    private boolean isCoordinatesEnabled() {
        try {
            final CommentedConfigurationNode playerConfig = PlayerConfig.getPlayerConfig(player).load();
            return PlayerConfig.getBooleanNode(playerConfig.node("show-coordinates"));

        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }

    public void show() {
        chestInventory.open(player);
    }

}
