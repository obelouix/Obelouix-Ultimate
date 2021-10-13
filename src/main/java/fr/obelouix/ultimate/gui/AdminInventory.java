package fr.obelouix.ultimate.gui;

import fr.obelouix.ultimate.api.InventoryAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class AdminInventory {

    private final Inventory inventory;

    public AdminInventory() {
        inventory = new InventoryAPI(54, InventoryType.CHEST, Component.text(""));
    }
}
