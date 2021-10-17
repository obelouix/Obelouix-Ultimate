package fr.obelouix.ultimate.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

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

}
