package fr.obelouix.ultimate.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class MaintenanceInventory extends BaseGUI {

    public MaintenanceInventory(Player player) {
        super(player);
    }

    @Override
    protected Component title(Player player) {
        return null;
    }

    @Override
    protected void setupInventory() {

    }

    @Override
    public void showInventory(@NotNull Player player) {

    }

    @Override
    public void cancelClick(InventoryClickEvent event) {

    }

    @Override
    public void onInventoryClickEvent(InventoryClickEvent event) {

    }
}
