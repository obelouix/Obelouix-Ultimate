package fr.obelouix.ultimate.gui;

import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.inventory.ChestInventory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class OptionsGUI {

    private final Player player;
    private ChestInventory chestInventory;
    public OptionsGUI(Player player) {
        this.player = player;
        createInterface();
    }

    private void createInterface(){
        chestInventory = ChestInventory.builder()
                .rows(6)
//                .title(Component.text("test"))
                .setItem(0, new ItemStack(Material.COMPASS))
                .fill(new ItemStack(Material.BLACK_STAINED_GLASS_PANE))
                .setItem(2, new ItemStack(Material.COMPASS))
                .cancelClick()
                .update(true, 5)
                .action(0, new InventoryRunnable() {
                    @Override
                    public void run() {
                        MessagesAPI.sendMessage(player, Component.text("test", NamedTextColor.AQUA));
                        MessagesAPI.sendMessage(player,Component.text(new ItemStack(Objects.requireNonNull(chestInventory.getInventory().getItem(0))).hasItemMeta()));
                    }
                })
                .action(2, test())
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

    public void show(){
        chestInventory.open(player);
    }

}
