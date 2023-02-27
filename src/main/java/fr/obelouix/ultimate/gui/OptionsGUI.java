package fr.obelouix.ultimate.gui;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class OptionsGUI {

    private final Player player;
    private ChestInventory chestInventory;
    private final HashMap<Integer, InventoryRunnable> actions = new HashMap<>();

    public OptionsGUI(Player player) {
        this.player = player;
        createInterface();
        actions.put(0, coordinatesActions());
    }


    private void createInterface() {
        chestInventory = ChestInventory.builder()
                .rows(6)
//                .title(Component.text("test"))
                .setItem(0, new ItemStack(Material.COMPASS))
                .setItem(9, new ItemStack(Material.LIME_DYE)) //TODO: CHANGE
                .fill(new ItemStack(Material.BLACK_STAINED_GLASS_PANE))
                .setItem(2, new ItemStack(Material.COMPASS))
                .cancelClick()
                .update(true, 5)
                .actions(actions)
                /*.action(0, new InventoryRunnable() {
                    @Override
                    public void run() {
                        MessagesAPI.sendMessage(player, Component.text("test", NamedTextColor.AQUA));
                        MessagesAPI.sendMessage(player,Component.text(new ItemStack(Objects.requireNonNull(chestInventory.getInventory().getItem(0))).hasItemMeta()));
                    }
                })
                .action(2, test())*/
                .build();
    }

    public InventoryRunnable test(){
        return new InventoryRunnable() {
            @Override
            public void run() {
                MessagesAPI.sendMessage(player, Component.text("test", NamedTextColor.GREEN));
                final ItemMeta itemMeta = chestInventory.getItemMeta(chestInventory.getItem(0));
                itemMeta.lore(List.of(Component.text(LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ISO_TIME))));
                chestInventory.getItem(0).setItemMeta(itemMeta);
            }
        };
    }

    private InventoryRunnable coordinatesActions() {
        return new InventoryRunnable() {
            @Override
            public void run() {
                try {
                    final CommentedConfigurationNode playerConfig = PlayerConfig.getPlayerConfig(player).load();
                    final boolean coordsConfig = PlayerConfig.getBooleanNode(playerConfig.node("show-coordinates"));
                    final ItemMeta itemMeta = chestInventory.getItem(9).getItemMeta();

                    if (coordsConfig) {
                        if (player.performCommand("coords off")) {
                            itemMeta.displayName(GlobalTranslator.render(Component.translatable("obelouix.coordinates.off")
                                    .decoration(TextDecoration.ITALIC, false), player.locale()));

                            chestInventory.getItem(9).setType(Material.RED_DYE);
                            chestInventory.getItem(9).setItemMeta(itemMeta);
                        }

                    } else {
                        if (player.performCommand("coords on")) {
                            itemMeta.displayName(GlobalTranslator.render(Component.translatable("obelouix.coordinates.on")
                                    .decoration(TextDecoration.ITALIC, false), player.locale()));

                            chestInventory.getItem(9).setType(Material.LIME_DYE);
                            chestInventory.getItem(9).setItemMeta(itemMeta);
                        }
                    }

                } catch (ConfigurateException e) {
                    throw new RuntimeException(e);
                }

            }
        };
    }

    public void show() {
        chestInventory.open(player);
    }

}
