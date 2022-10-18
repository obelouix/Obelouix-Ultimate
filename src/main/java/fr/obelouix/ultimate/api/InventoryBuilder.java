package fr.obelouix.ultimate.api;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryBuilder {

    private final Inventory inventory;

    public InventoryBuilder(InventoryType type) {
        this.inventory = Bukkit.createInventory(null, type.get());
    }

    public InventoryBuilder(InventoryType type, Component title) {
        this.inventory = Bukkit.createInventory(null, type.get(), title);
    }

    public InventoryBuilder(Player owner, InventoryType type) {
        this.inventory = Bukkit.createInventory(owner, type.get());
    }

    public InventoryBuilder(Player owner, InventoryType type, Component title) {
        this.inventory = Bukkit.createInventory(owner, type.get(), title);
    }

    public InventoryBuilder addItem(ItemStack[] itemStacks) {
        inventory.addItem(itemStacks);
        return this;
    }

    public InventoryBuilder addItem(List<ItemStack> itemStacks) {
        itemStacks.forEach(inventory::addItem);
        return this;
    }

    public ItemStack getItem(int slot) {
        return inventory.getItem(slot);
    }

    public Inventory build() {
        return inventory;
    }
}
