package fr.obelouix.ultimate.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class ConfirmationInventory extends BaseGUI {

    public ConfirmationInventory() {
    }

    public ConfirmationInventory(Player player) {
        super(player);
        inventory = Bukkit.createInventory(null, 9, title(player));
        showInventory(player);
    }

    public ConfirmationInventory(Player player, ClickEvent confirmationClickEvent) {
        this(player);
        inventory = Bukkit.createInventory(null, 9, title(player));
        showInventory(player);
    }

    @Override
    protected Component title(Player player) {
        return Component.text("Confirmation");
    }

    @Override
    protected void setupInventory() {
    }

    @Override
    public void showInventory(@NotNull Player player) {
        player.openInventory(inventory);
    }

    @Override
    public void cancelClick(InventoryClickEvent event) {
        final InventoryView view = event.getView();
        final Player player = (Player) event.getWhoClicked();
        if (view.title().equals(title(player))) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onInventoryClickEvent(InventoryClickEvent event) {

    }
}
