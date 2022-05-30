package fr.obelouix.ultimate.api;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import fr.obelouix.ultimate.utils.CustomHeadSkins;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class InventoryAPI {

    private InventoryAPI() {

    }

    public static ItemStack addCustomSkull(Player player, Component itemName) {
        final ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        final SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        skullMeta.displayName(itemName.decoration(TextDecoration.ITALIC, false));
        skull.setItemMeta(skullMeta);

        return skull;
    }

    /**
     * Get a Skull with the skin of a player that is connected on the server
     *
     * @param player The Online player
     * @return Player Skull with a skin
     */
    public static ItemStack getOnlineSkull(Player player) {
        final ItemStack skull = new ItemStack(Material.PLAYER_HEAD); // Create a new ItemStack of the Player Head type.
        final SkullMeta skullMeta = (SkullMeta) skull.getItemMeta(); // Get the created item's ItemMeta and cast it to SkullMeta, so we can access the skull properties
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())); // Set the skull's owner, so it will adapt the skin of the provided username (case-sensitive).
        skullMeta.displayName(Component.text(player.getName()).decoration(TextDecoration.ITALIC, false));
        skull.setItemMeta(skullMeta); // Apply the modified meta to the initial created item
        return skull;
    }

    /**
     * Get a player head with a custom texture ID
     *
     * @param texture get the texture ID from <a href="https://freshcoal.com/">https://freshcoal.com/</a>
     * @param title   - the item name
     * @return Player Head with custom skin and name
     */
    public static ItemStack addCustomSkull(CustomHeadSkins texture, @org.jetbrains.annotations.NotNull Component title) {
        final ItemStack skull = new ItemStack(Material.PLAYER_HEAD); // Create a new ItemStack of the Player Head type.
        final SkullMeta skullMeta = (SkullMeta) skull.getItemMeta(); // Get the created item's ItemMeta and cast it to SkullMeta so we can access the skull properties

        final PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
        profile.setProperty(new ProfileProperty("textures", texture.toString()));
        skullMeta.setPlayerProfile(profile);
        skullMeta.displayName(title.decoration(TextDecoration.ITALIC, false));
        skull.setItemMeta(skullMeta); // Apply the modified meta to the initial created item
        return skull;
    }

    /**
     * allows to add an {@link Enchantment}
     *
     * @param item         the item that will receive the enchantment
     * @param enchantment  the {@link Enchantment} to add
     * @param enchantLevel an {@link Integer} between {@code 0} and {@link 255}
     */
    public static void addEnchantment(ItemStack item, Enchantment enchantment, int enchantLevel) {
        final ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(enchantment, enchantLevel, true);
        item.setItemMeta(meta);
    }

    /**
     * allows to add multiple {@link Enchantment} at once
     *
     * @param item         the item that will receive the enchantments
     * @param enchantments a {@link List} of {@link Enchantment}
     * @param enchantLevel an {@link Integer} between {@code 0} and {@link 255}
     */
    public static void addEnchantments(ItemStack item, List<Enchantment> enchantments, int enchantLevel) {
        final ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        for (final Enchantment enchantment : enchantments) {
            meta.addEnchant(enchantment, enchantLevel, true);
        }
        item.setItemMeta(meta);
    }

    /**
     * change the name of the item
     *
     * @param item     the item were we change the name
     * @param itemName a {@link Component} that will be the name if this item
     */
    public static void setItemName(ItemStack item, Component itemName) {
        final ItemMeta meta = item.getItemMeta();
        meta.displayName(itemName.decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(meta);
    }

    /**
     * add lore (aka description) to the item
     *
     * @param item       the item were whe add the lore
     * @param components a {@link List} of {@link Component} to show
     */
    public static void addLore(ItemStack item, List<Component> components) {
        final ItemMeta meta = item.getItemMeta();
        meta.lore(components);
        item.setItemMeta(meta);
    }

}
