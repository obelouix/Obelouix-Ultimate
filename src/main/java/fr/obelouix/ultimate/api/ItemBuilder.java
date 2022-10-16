package fr.obelouix.ultimate.api;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        itemMeta = item.getItemMeta();
    }

    /**
     * Sets the display name.
     *
     * @param displayname the display name.
     */
    public ItemBuilder displayname(@Nullable Component displayname) {
        itemMeta.displayName(displayname);
        return this;
    }

    /**
     * Sets the damage
     *
     * @param damage the damage
     */
    public ItemBuilder setDamage(int damage) {
        if (itemMeta instanceof Damageable damageable) {
            damageable.setDamage(damage);
        } else throw new ItemBuilderException(item.getType() + " 's meta is not an instance of Damageable");
        return this;
    }

    /**
     * Add Enchantment to the item.
     * The Enchantment is level 1
     *
     * @param enchantment the enchantment to add
     */
    public ItemBuilder addEnchantement(Enchantment enchantment) {
        itemMeta.addEnchant(enchantment, 1, false);
        return this;
    }

    /**
     * Add Enchantment to the item.
     *
     * @param enchantment the enchantment to add
     * @param level       the level of the enchantment to add
     */
    public ItemBuilder addEnchantement(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, false);
        return this;
    }

    /**
     * Add the enchantment glowing effect on the item.
     * <br> This applies {@link Enchantment#ARROW_INFINITE} if the item is not a bow or a crossbow
     * otherwise it applies {@link  Enchantment#PROTECTION_FALL}
     */
    public ItemBuilder addGlowingEffect() {
        if (itemMeta instanceof Damageable && (item.getType() != Material.BOW || item.getType() != Material.CROSSBOW)) {
            itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        } else {
            itemMeta.addEnchant(Enchantment.PROTECTION_FALL, 1, false);
        }
        itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Add Enchantment with an unsafe level to the item.
     *
     * @param enchantment the enchantment to add
     * @param level       the level of the enchantment to add
     */
    public ItemBuilder addUnsafeEnchantement(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Remove the enchantments from the item
     */
    public ItemBuilder clearEnchantments() {
        itemMeta.getEnchants().forEach((enchantment, integer) -> itemMeta.removeEnchant(enchantment));
        return this;
    }

    /**
     * Add Enchantments to the item.
     * The Enchantments are level 1
     * <br><b>This clear the enchantments before applying them</b>
     *
     * @param enchantments a list of enchantments to add
     */
    public ItemBuilder setEnchantement(Enchantment... enchantments) {
        itemMeta.getEnchants().clear();
        Arrays.stream(enchantments).forEach(
                enchantment -> itemMeta.addEnchant(enchantment, 1, false)

        );
        return this;
    }

    /**
     * Add Enchantments to the item.
     *
     * @param enchantments a list of enchantments to add
     * @param level        the level of all the enchantements
     */
    public ItemBuilder setEnchantement(int level, Enchantment... enchantments) {
        itemMeta.getEnchants().clear();
        Arrays.stream(enchantments).forEach(
                enchantment -> itemMeta.addEnchant(enchantment, level, false)
        );
        return this;
    }

    /**
     * Get the item's lore as an unmodifiable list
     *
     * @return the item's lore
     */
    public List<Component> getLore() {
        return itemMeta.hasLore() ? Collections.unmodifiableList(itemMeta.lore()) : Collections.emptyList();
    }

    /**
     * Set the lore of the item
     *
     * @param lore a list of components
     */
    public ItemBuilder setLore(@Nullable List<Component> lore) {
        itemMeta.lore(lore);
        return this;
    }

    /**
     * Set a custom {@link ItemMeta}
     *
     * @param meta the ItemMeta to set
     */
    public ItemBuilder setMeta(ItemMeta meta) {
        itemMeta = meta;
        return this;
    }

    /**
     * Get the {@link ItemMeta}
     *
     * @return the ItemMeta
     */
    public ItemMeta getItemMeta() {
        return itemMeta;
    }


    /**
     * Add Enchantments with an unsafe level to the item.
     * <br><b>This clear the enchantments before applying them</b>
     *
     * @param enchantments a list of enchantments to add
     * @param level        the level of all the enchantements
     */
    public ItemBuilder setUnsafeEnchantement(int level, Enchantment... enchantments) {
        itemMeta.getEnchants().clear();
        Arrays.stream(enchantments).forEach(
                enchantment -> itemMeta.addEnchant(enchantment, level, true)

        );
        return this;
    }

    /**
     * Build the item
     *
     * @return the item
     */
    public ItemStack build() {
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Build the item
     * <br> Same as {@link #build()} but for internal use
     *
     * @param item the itemstack to build
     * @return the item
     */
    ItemStack build(ItemStack item) {
        item.setItemMeta(itemMeta);
        return item;
    }

}
