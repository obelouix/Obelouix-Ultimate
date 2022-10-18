package fr.obelouix.ultimate.api;

import com.destroystokyo.paper.Namespaced;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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

    public ItemBuilder addColoredEnchantmentLore() {
        final Map<Enchantment, Integer> enchantments = itemMeta.getEnchants();
        //Create a new List if there is no lore
        final List<Component> lore = itemMeta.lore() != null ? itemMeta.lore() : new ArrayList<>();
        enchantments.forEach((enchantment, integer) -> {
            switch (enchantment.getKey().value()) {
                // All purposes
                case "mending" -> lore.add(Component.translatable("enchantment.minecraft.mending", NamedTextColor.GOLD)
                        .append(Component.text(" "))
                        .append(setEnchantLevelToComponent(integer)));
                case "vanishing_curse" ->
                        lore.add(Component.translatable("enchantment.minecraft.vanishing_curse", NamedTextColor.DARK_RED)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "unbreaking" ->
                        lore.add(Component.translatable("enchantment.minecraft.unbreaking", NamedTextColor.DARK_PURPLE)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));

                // Armor
                case "aqua_affinity" ->
                        lore.add(Component.translatable("enchantment.minecraft.waterWorker", NamedTextColor.AQUA)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "blast_protection" ->
                        lore.add(Component.translatable("enchantment.minecraft.protect.explosion", NamedTextColor.GREEN)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "binding_curse" ->
                        lore.add(Component.translatable("enchantment.minecraft.binding_curse", NamedTextColor.DARK_RED)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "depth_strider" ->
                        lore.add(Component.translatable("enchantment.minecraft.waterWalker", NamedTextColor.AQUA)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "feather_falling" ->
                        lore.add(Component.translatable("enchantment.minecraft.protect.fall", NamedTextColor.GREEN)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "fire_protection" ->
                        lore.add(Component.translatable("enchantment.minecraft.protect.fire", NamedTextColor.GOLD)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "frost_walker" ->
                        lore.add(Component.translatable("enchantment.minecraft.frostWalker", NamedTextColor.AQUA)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "projectile_protection" ->
                        lore.add(Component.translatable("enchantment.minecraft.protect.projectile", NamedTextColor.GREEN)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "protection" ->
                        lore.add(Component.translatable("enchantment.minecraft.protect.all", NamedTextColor.GREEN)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "respiration" ->
                        lore.add(Component.translatable("enchantment.minecraft.oxygen", NamedTextColor.AQUA)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "soul_speed" ->
                        lore.add(Component.translatable("enchantment.minecraft.soul_speed", NamedTextColor.GREEN)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "swift_sneak" ->
                        lore.add(Component.translatable("enchantment.minecraft.swift_sneak", NamedTextColor.GREEN)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "thorns" ->
                        lore.add(Component.translatable("enchantment.minecraft.thorns", NamedTextColor.DARK_PURPLE)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));

                // Melee Weapons
                case "bane_of_arthropods" ->
                        lore.add(Component.translatable("enchantment.minecraft.bane_of_arthropods", NamedTextColor.LIGHT_PURPLE)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "fire_aspect" ->
                        lore.add(Component.translatable("enchantment.minecraft.fire_aspect", TextColor.color(255, 128, 0))
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "looting" -> lore.add(Component.translatable("enchantment.minecraft.looting", NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(" "))
                        .append(setEnchantLevelToComponent(integer)));
                case "knockback" ->
                        lore.add(Component.translatable("enchantment.minecraft.knockback", NamedTextColor.YELLOW)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "sharpness" ->
                        lore.add(Component.translatable("enchantment.minecraft.sharpness", NamedTextColor.LIGHT_PURPLE)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "smite" ->
                        lore.add(Component.translatable("enchantment.minecraft.smite", NamedTextColor.LIGHT_PURPLE)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "sweeping" ->
                        lore.add(Component.translatable("enchantment.minecraft.sweeping", NamedTextColor.BLUE)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));

                // Ranged Weapons
                case "channeling" ->
                        lore.add(Component.translatable("enchantment.minecraft.channeling", NamedTextColor.GREEN)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "flame" ->
                        lore.add(Component.translatable("enchantment.minecraft.flame", TextColor.color(255, 128, 0))
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "infinity" ->
                        lore.add(Component.translatable("enchantment.minecraft.infinity", NamedTextColor.GOLD)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "loyalty" ->
                        lore.add(Component.translatable("enchantment.minecraft.loyalty", NamedTextColor.YELLOW)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "riptide" -> lore.add(Component.translatable("enchantment.minecraft.riptide", NamedTextColor.GREEN)
                        .decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(" "))
                        .append(setEnchantLevelToComponent(integer)));
                case "multishot" ->
                        lore.add(Component.translatable("enchantment.minecraft.multishot", NamedTextColor.GOLD)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "piercing" ->
                        lore.add(Component.translatable("enchantment.minecraft.piercing", NamedTextColor.GREEN)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "power" ->
                        lore.add(Component.translatable("enchantment.minecraft.power", NamedTextColor.LIGHT_PURPLE)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "punch" ->
                        lore.add(Component.translatable("enchantment.minecraft.punch", NamedTextColor.LIGHT_PURPLE)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "quick_charge" ->
                        lore.add(Component.translatable("enchantment.minecraft.quick_charge", NamedTextColor.GREEN)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));

                // Melee & Ranged Weapons
                case "impaling" ->
                        lore.add(Component.translatable("enchantment.minecraft.impaling", NamedTextColor.LIGHT_PURPLE)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));

                // Tools
                case "fortune" -> lore.add(Component.translatable("enchantment.minecraft.fortune", NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(" "))
                        .append(setEnchantLevelToComponent(integer)));
                case "luck_of_the_sea" ->
                        lore.add(Component.translatable("enchantment.minecraft.luck_of_the_sea", NamedTextColor.LIGHT_PURPLE)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
                case "lure" -> lore.add(Component.translatable("enchantment.minecraft.lure", NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(" "))
                        .append(setEnchantLevelToComponent(integer)));
                case "silk_touch" ->
                        lore.add(Component.translatable("enchantment.minecraft.silk_touch", NamedTextColor.DARK_PURPLE)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));

                // Tools & Melee Weapons
                case "efficiency" ->
                        lore.add(Component.translatable("enchantment.minecraft.efficiency", NamedTextColor.YELLOW)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(" "))
                                .append(setEnchantLevelToComponent(integer)));
            }
        });
        itemMeta.lore(lore);
        return this;
    }

    private Component setEnchantLevelToComponent(int level) {
        return switch (level) {
            case 1 -> Component.text("I");
            case 2 -> Component.text("II");
            case 3 -> Component.text("III");
            case 4 -> Component.text("IV");
            case 5 -> Component.text("V");
            case 6 -> Component.text("VI");
            case 7 -> Component.text("VII");
            case 8 -> Component.text("VIII");
            case 9 -> Component.text("IX");
            case 10 -> Component.text("X");
            default -> Component.text(level);
        };
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
        hideEnchantments();
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
     * Set the materials where the item can be placed using their keys
     * (e.g. Material.GRASS_BLOCK.getKey() )
     *
     * @param materials a list of materials keys
     */
    public ItemBuilder canBePlacedOn(List<Namespaced> materials) {
        itemMeta.setPlaceableKeys(materials);
        return this;
    }

    /**
     * Set the materials that can be destroyed by the item
     * (e.g. Material.GRASS_BLOCK.getKey() )
     *
     * @param materials a list of materials keys
     */
    public ItemBuilder canDestoy(List<Namespaced> materials) {
        itemMeta.setDestroyableKeys(materials);
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

    public ItemBuilder setEnchantement(List<Enchantment> enchantments) {
        if (itemMeta.hasEnchants()) itemMeta.getEnchants().clear();
        enchantments.forEach(enchantment -> itemMeta.addEnchant(enchantment, 1, false));
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
     * Set the item defense points.
     *
     * @param defense the item defense point to set
     */
    public ItemBuilder setArmor(double defense) {
        final AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "generic.Damage", defense, AttributeModifier.Operation.ADD_NUMBER);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);
        return this;
    }

    /**
     * Set the item's attack damage
     *
     * @param damage the attack damage to set
     */
    public ItemBuilder setAttackDamage(double damage) {
        final AttributeModifier attackDamage = new AttributeModifier(UUID.randomUUID(), "generic.Damage", damage, AttributeModifier.Operation.ADD_NUMBER);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamage);
        return this;
    }

    /**
     * Set the item's attack speed
     *
     * @param speed the attack speed to set
     */
    public ItemBuilder setAttackSpeed(double speed) {
        final AttributeModifier attackSpeed = new AttributeModifier(UUID.randomUUID(), "generic.AttackSpeed", speed, AttributeModifier.Operation.ADD_NUMBER);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeed);
        return this;
    }

    /**
     * Set the item's attack damage and attack speed
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
     * Set the knockback resistance of the item
     *
     * @param resistance the knockback resistance to set
     */
    public ItemBuilder setKnockbackResistance(double resistance) {
        final AttributeModifier knockbackResistance = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", resistance, AttributeModifier.Operation.ADD_NUMBER);
        itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistance);
        return this;
    }

    /**
     * Set the movement speed of the item that will be applied to the entity holding it
     *
     * @param speed the movement speed to set
     */
    public ItemBuilder setMovementSpeed(double speed) {
        final AttributeModifier movementSpeed = new AttributeModifier(UUID.randomUUID(), "generic.movement_speed", speed, AttributeModifier.Operation.ADD_NUMBER);
        itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, movementSpeed);
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

    /**
     * Hide all {@link ItemFlag}'s
     */
    public ItemBuilder hideAllFlags() {
        Arrays.stream(ItemFlag.values()).forEach(flag -> itemMeta.addItemFlags(flag));
        return this;
    }

}
