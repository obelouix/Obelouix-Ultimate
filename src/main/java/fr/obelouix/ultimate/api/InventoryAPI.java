package fr.obelouix.ultimate.api;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class InventoryAPI implements Inventory {

    private Inventory inventory;

    public InventoryAPI(int size, Component title) {
        inventory = createInventory(size, title);
    }

    private Inventory createInventory(int size, Component title) {
        return Bukkit.createInventory(null, size, title);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getMaxStackSize() {
        return 0;
    }

    @Override
    public void setMaxStackSize(int i) {
        inventory.setMaxStackSize(i);
    }

    @Override
    public @Nullable ItemStack getItem(int i) {
        return inventory.getItem(i);
    }

    @Override
    public void setItem(int i, @Nullable ItemStack itemStack) {
        inventory.setItem(i, itemStack);
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> addItem(@NotNull ItemStack... itemStacks) throws IllegalArgumentException {
        return inventory.addItem(itemStacks);
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> removeItem(@NotNull ItemStack... itemStacks) throws IllegalArgumentException {
        return inventory.removeItem(itemStacks);
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> removeItemAnySlot(@NotNull ItemStack... itemStacks) throws IllegalArgumentException {
        return inventory.removeItemAnySlot(itemStacks);
    }

    @Override
    public @org.checkerframework.checker.nullness.qual.Nullable ItemStack @NonNull [] getContents() {
        return inventory.getContents();
    }

    @Override
    public void setContents(@NotNull ItemStack[] itemStacks) throws IllegalArgumentException {
        inventory.setContents(itemStacks);
    }

    @Override
    public @NotNull ItemStack[] getStorageContents() {
        return inventory.getStorageContents();
    }

    @Override
    public void setStorageContents(@NotNull ItemStack[] itemStacks) throws IllegalArgumentException {
        inventory.setStorageContents(itemStacks);
    }

    @Override
    public boolean contains(@NotNull Material material) throws IllegalArgumentException {
        return inventory.contains(material);
    }

    @Override
    public boolean contains(@Nullable ItemStack itemStack) {
        return inventory.contains(itemStack);
    }

    @Override
    public boolean contains(@NotNull Material material, int i) throws IllegalArgumentException {
        return inventory.contains(material, i);
    }

    @Override
    public boolean contains(@Nullable ItemStack itemStack, int i) {
        return inventory.contains(itemStack, i);
    }

    @Override
    public boolean containsAtLeast(@Nullable ItemStack itemStack, int i) {
        return inventory.containsAtLeast(itemStack, i);
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(@NotNull Material material) throws IllegalArgumentException {
        return inventory.all(material);
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(@Nullable ItemStack itemStack) {
        return inventory.all(itemStack);
    }

    @Override
    public int first(@NotNull Material material) throws IllegalArgumentException {
        return inventory.first(material);
    }

    @Override
    public int first(@NotNull ItemStack itemStack) {
        return inventory.first(itemStack);
    }

    @Override
    public int firstEmpty() {
        return inventory.firstEmpty();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public void remove(@NotNull Material material) throws IllegalArgumentException {
        inventory.remove(material);
    }

    @Override
    public void remove(@NotNull ItemStack itemStack) {
        inventory.remove(itemStack);
    }

    @Override
    public void clear(int i) {
        inventory.clear(i);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public int close() {
        return inventory.close();
    }

    @Override
    public @NotNull List<HumanEntity> getViewers() {
        return inventory.getViewers();
    }

    @Override
    public @NotNull InventoryType getType() {
        return inventory.getType();
    }

    @Override
    public @Nullable InventoryHolder getHolder() {
        return inventory.getHolder();
    }

    @Override
    public @Nullable InventoryHolder getHolder(boolean bl) {
        return inventory.getHolder(bl);
    }

    @Override
    public @NotNull ListIterator<ItemStack> iterator() {
        return inventory.iterator();
    }

    @Override
    public @NotNull ListIterator<ItemStack> iterator(int i) {
        return inventory.iterator(i);
    }

    @Override
    public @Nullable Location getLocation() {
        return inventory.getLocation();
    }
}
