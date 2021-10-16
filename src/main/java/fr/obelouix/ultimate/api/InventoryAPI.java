package fr.obelouix.ultimate.api;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class InventoryAPI {

    private Inventory inventory;
    private int size;
    private Component title = Component.text("");

    protected InventoryAPI() {
        inventory = createInventory(size, this.title);
    }

    public InventoryAPI(int size, @NotNull Component title) {
        this.size = size;
        this.title = title;
    }

    public Inventory createInventory(int size, @NotNull Component title) {
        return Bukkit.createInventory(null, size, title);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
