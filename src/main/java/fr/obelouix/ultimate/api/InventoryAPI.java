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

    public InventoryAPI(int size, InventoryType inventoryType, Component title) {
        createInventory(size, inventoryType, title);
    }

    private void createInventory(int size, InventoryType inventoryType, Component title) {
        Bukkit.createInventory(null, inventoryType, title);
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

    }

    @Override
    public @Nullable ItemStack getItem(int i) {
        return null;
    }

    @Override
    public void setItem(int i, @Nullable ItemStack itemStack) {

    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> addItem(@NotNull ItemStack... itemStacks) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> removeItem(@NotNull ItemStack... itemStacks) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> removeItemAnySlot(@NotNull ItemStack... itemStacks) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @org.checkerframework.checker.nullness.qual.Nullable ItemStack @NonNull [] getContents() {
        return new ItemStack[0];
    }

    @Override
    public void setContents(@NotNull ItemStack[] itemStacks) throws IllegalArgumentException {

    }

    @Override
    public @NotNull ItemStack[] getStorageContents() {
        return new ItemStack[0];
    }

    @Override
    public void setStorageContents(@NotNull ItemStack[] itemStacks) throws IllegalArgumentException {

    }

    @Override
    public boolean contains(@NotNull Material material) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean contains(@Nullable ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean contains(@NotNull Material material, int i) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean contains(@Nullable ItemStack itemStack, int i) {
        return false;
    }

    @Override
    public boolean containsAtLeast(@Nullable ItemStack itemStack, int i) {
        return false;
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(@NotNull Material material) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(@Nullable ItemStack itemStack) {
        return null;
    }

    @Override
    public int first(@NotNull Material material) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public int first(@NotNull ItemStack itemStack) {
        return 0;
    }

    @Override
    public int firstEmpty() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void remove(@NotNull Material material) throws IllegalArgumentException {

    }

    @Override
    public void remove(@NotNull ItemStack itemStack) {

    }

    @Override
    public void clear(int i) {

    }

    @Override
    public void clear() {

    }

    @Override
    public int close() {
        return 0;
    }

    @Override
    public @NotNull List<HumanEntity> getViewers() {
        return null;
    }

    @Override
    public @NotNull InventoryType getType() {
        return null;
    }

    @Override
    public @Nullable InventoryHolder getHolder() {
        return null;
    }

    @Override
    public @Nullable InventoryHolder getHolder(boolean bl) {
        return null;
    }

    @Override
    public @NotNull ListIterator<ItemStack> iterator() {
        return null;
    }

    @Override
    public @NotNull ListIterator<ItemStack> iterator(int i) {
        return null;
    }

    @Override
    public @Nullable Location getLocation() {
        return null;
    }
}
