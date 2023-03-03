package fr.obelouix.ultimate.inventory;

import com.google.common.annotations.Beta;
import fr.obelouix.ultimate.Main;
import fr.obelouix.ultimate.gui.InventoryRunnable;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;

public class ChestInventory implements Inventory {

    private static final ChestInventory INSTANCE = new ChestInventory();
    private org.bukkit.inventory.Inventory inventory;
    private org.bukkit.inventory.Inventory tempInventory;
    private int rows;
    //Default title
    private Component title = Component.translatable("container.chestDouble");

    @Override
    public ChestInventory build() {
        inventory = tempInventory;
        return this;
    }

    public static ChestInventory builder() {
        return INSTANCE;
    }

    @Override
    public ChestInventory rows(int rows) {
        this.rows = rows * 9;
        return this;
    }

    @Override
    public ChestInventory title(Component title) {
        this.title = title;
        return this;
    }

    @Override
    public ChestInventory setItem(int slot, ItemStack itemStack) {
        if(tempInventory == null) tempInventory = Bukkit.createInventory(null, rows, title);
        tempInventory.setItem(slot, itemStack);
        return this;
    }

    public ChestInventory update(boolean shouldUpdate, int ticks) {
        if(shouldUpdate) {
            Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), bukkitTask -> inventory.getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory()), 0, ticks);
        }
        return this;
    }

    @Override
    public ChestInventory cancelClick() {
        Bukkit.getPluginManager().registerEvent(InventoryClickEvent.class, new Listener() {
        }, EventPriority.NORMAL, (listener, event) -> {
            if (((InventoryClickEvent) event).getInventory().equals(inventory)) {
                ((InventoryClickEvent) event).setCancelled(true);
            }
        }, Main.getPlugin());
        return this;
    }

    public ChestInventory fill(ItemStack itemStack, boolean hideName) {

        if (hideName) {
            final ItemMeta meta = itemStack.getItemMeta();
            meta.displayName(Component.empty());
            meta.lore(Collections.singletonList(Component.empty()));
            itemStack.setItemMeta(meta);
        }

        if (tempInventory == null) tempInventory = Bukkit.createInventory(null, rows, title);
        for (int i = 0; i < tempInventory.getSize(); i++) {
            if (tempInventory.getItem(i) == null) tempInventory.setItem(i, itemStack);
        }
        return this;
    }

    public ChestInventory action(int slot, InventoryRunnable runnable) {
        Bukkit.getPluginManager().registerEvent(InventoryClickEvent.class, new Listener() {
        }, EventPriority.NORMAL, (listener, event) -> {
            if (((InventoryClickEvent) event).getInventory().equals(inventory) && (((InventoryClickEvent) event).getSlot() == slot)) {
                runnable.run();
            }
        }, Main.getPlugin());
        return this;
    }

    @Beta
    public ChestInventory actions(HashMap<Integer, InventoryRunnable> actionsMap) {
        actionsMap.forEach(this::action);
        return this;
    }

    @Override
    public void open(Player player) {
        Bukkit.getScheduler().runTask(Main.getPlugin(), () -> player.openInventory(inventory));
    }

    @Override
    public org.bukkit.inventory.Inventory getInventory() {
        return inventory;
    }

    public int first(ItemStack item) {
        return inventory.first(item);
    }

    public int first(Material material) {
        return inventory.first(material);
    }

    public ItemMeta getItemMeta(ItemStack itemStack){
        return  itemStack.getItemMeta();
    }

    public ItemStack getItem(int slot) {
        return inventory.getItem(slot);
    }

}
