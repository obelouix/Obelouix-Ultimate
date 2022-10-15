package fr.obelouix.ultimate.api;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Allows to create an item that will be a weapon
 */
public class WeaponBuilder extends ItemBuilder {

    private final ItemStack weapon;

    public WeaponBuilder(Material material) {
        super(material);
        weapon = new ItemBuilder(material).build();
    }

    public WeaponBuilder(Material material, int amount) {
        super(material, amount);
        weapon = new ItemBuilder(material, amount).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemBuilder displayname(@Nullable Component displayname) {
        return super.displayname(displayname);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemBuilder setDamage(int damage) {
        return super.setDamage(damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemBuilder addEnchantement(Enchantment enchantment) {
        return super.addEnchantement(enchantment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemBuilder addEnchantement(Enchantment enchantment, int level) {
        return super.addEnchantement(enchantment, level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemBuilder addUnsafeEnchantement(Enchantment enchantment, int level) {
        return super.addUnsafeEnchantement(enchantment, level);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ItemBuilder clearEnchantments() {
        return super.clearEnchantments();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemBuilder setEnchantement(Enchantment... enchantments) {
        return super.setEnchantement(enchantments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemBuilder setEnchantement(int level, Enchantment... enchantments) {
        return super.setEnchantement(level, enchantments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemBuilder setUnsafeEnchantement(int level, Enchantment... enchantments) {
        return super.setUnsafeEnchantement(level, enchantments);
    }

    /**
     * Build the weapon
     *
     * @return the weapon
     */
    public ItemStack build() {
        return weapon;
    }

}
