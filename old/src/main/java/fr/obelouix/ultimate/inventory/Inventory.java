package fr.obelouix.ultimate.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Inventory {

    Inventory build();
    Inventory rows(int rows);
    Inventory title(Component title);
    Inventory setItem(int slot, ItemStack itemStack);
    Inventory cancelClick();
    void open(Player player);
    org.bukkit.inventory.Inventory getInventory();
}
