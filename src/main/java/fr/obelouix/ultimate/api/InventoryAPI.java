package fr.obelouix.ultimate.api;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import fr.obelouix.ultimate.utils.CustomHeadSkins;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

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
     * @param texture get the texture ID from https://freshcoal.com/
     * @param title   - the item name
     * @return Player Head with custom skin and name
     */
    public static ItemStack addCustomSkull(CustomHeadSkins texture, Component title) {
        final ItemStack skull = new ItemStack(Material.PLAYER_HEAD); // Create a new ItemStack of the Player Head type.
        final SkullMeta skullMeta = (SkullMeta) skull.getItemMeta(); // Get the created item's ItemMeta and cast it to SkullMeta so we can access the skull properties

        final PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
        profile.setProperty(new ProfileProperty("textures", texture.toString()));
        skullMeta.setPlayerProfile(profile);
        skullMeta.displayName(title.decoration(TextDecoration.ITALIC, false));
        skull.setItemMeta(skullMeta); // Apply the modified meta to the initial created item
        return skull;
    }

}
