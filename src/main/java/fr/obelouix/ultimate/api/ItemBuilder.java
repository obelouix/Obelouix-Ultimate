package fr.obelouix.ultimate.api;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private final ItemStack item;
    protected ItemMeta itemMeta;

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
     * Sets the damage (durability)
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
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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
     * Set the weapon's attack damage
     *
     * @param damage the attack damage to set
     */
    public ItemBuilder setAttackDamage(double damage) {
        final AttributeModifier attackDamage = new AttributeModifier(UUID.randomUUID(), "generic.Damage", damage, AttributeModifier.Operation.ADD_NUMBER);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamage);
        return this;
    }

    /**
     * Set the weapon's attack speed
     *
     * @param speed the attack speed to set
     */
    public ItemBuilder setAttackSpeed(double speed) {
        final AttributeModifier attackSpeed = new AttributeModifier(UUID.randomUUID(), "generic.AttackSpeed", speed, AttributeModifier.Operation.ADD_NUMBER);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeed);
        return this;
    }

    /**
     * Set the weapon's attack damage and attack speed
     *
     * @param attackDamage the attack damage to set
     * @param attackSpeed  the attack speed to set
     */
    public ItemBuilder setAttackDamageAndSpeed(double attackDamage, double attackSpeed) {
        setAttackDamage(attackDamage);
        setAttackSpeed(attackSpeed);
        return this;
    }

    /**
     * Hide attributes like Damage
     */
    public ItemBuilder hideAttributes() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }

    /**
     * Hide enchantments
     */
    public ItemBuilder hideEnchantments() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Hide what the ItemStack can break/destroy
     */
    public ItemBuilder hideDestroy() {
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        return this;
    }

    /**
     * Hide dyes from coloured leather armour
     */
    public ItemBuilder hideDyes() {
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        return this;
    }

    /**
     * Hide where this ItemStack can be build/placed on
     */
    public ItemBuilder hidePlacedOn() {
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        return this;
    }

    /**
     * Hide d potion effects, book and firework information, map tooltips, patterns of banners, and enchantments of enchanted books.
     */
    public ItemBuilder hidePotionEffects() {
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        return this;
    }

    /**
     * Hide the unbreakable State
     */
    public ItemBuilder hideUnbreakble() {
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

}
