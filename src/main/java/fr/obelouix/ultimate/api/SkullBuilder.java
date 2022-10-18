package fr.obelouix.ultimate.api;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SkullBuilder {

    private final ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
    private final SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

    /**
     * Set the displayname of the skull
     *
     * @param displayname the displayname to set
     */
    public SkullBuilder displayname(Component displayname) {
        skullMeta.displayName(displayname);
        return this;
    }

    /**
     * Add the enchantment effect to the skull item
     */
    public SkullBuilder glowing() {
        skullMeta.getEnchantLevel(Enchantment.ARROW_INFINITE);
        skullMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Set the lore of the skull
     * <br> It keep the previous lore if there is one
     *
     * @param lore the lore to set
     */
    public SkullBuilder lore(Component[] lore) {
        final List<Component> components = skullMeta.hasLore() ? skullMeta.lore() : new ArrayList<>();
        components.addAll(Arrays.asList(lore));
        skullMeta.lore(components);
        return this;
    }

    /**
     * Set the lore of the skull
     * <br> It keep the previous lore if there is one
     *
     * @param lore the lore to set
     */
    public SkullBuilder lore(List<Component> lore) {
        final List<Component> components = skullMeta.hasLore() ? skullMeta.lore() : new ArrayList<>();
        components.addAll(lore);
        skullMeta.lore(components);
        return this;
    }

    /**
     * Get the lore of the skull
     *
     * @return the lore of the skull
     */
    public List<Component> lore() {
        return skullMeta.lore();
    }

    /**
     * Set the lore of the skull
     * <br> Unlike {@link #lore(List)} It doesn't keep the previous lore if there is one
     *
     * @param lore the lore to set
     */
    public SkullBuilder setLore(Component[] lore) {
        final List<Component> components = new ArrayList<>();
        components.addAll(Arrays.asList(lore));
        skullMeta.lore(components);
        return this;
    }

    /**
     * Set the lore of the skull
     * <br> Unlike {@link #lore(List)} It doesn't keep the previous lore if there is one
     *
     * @param lore the lore to set
     */
    public SkullBuilder setLore(List<Component> lore) {
        final List<Component> components = new ArrayList<>();
        components.addAll(lore);
        skullMeta.lore(components);
        return this;
    }

    /**
     * Set the owner of the head
     *
     * @param player the owner of the head to use
     */
    public SkullBuilder owner(Player player) {
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        return this;
    }

    /**
     * Set the owner of the head from their uuid
     *
     * @param uuid the uuid of the skull owner to use
     */
    public SkullBuilder owner(UUID uuid) {
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        return this;
    }

    /**
     * Set the owner of the head from their name
     *
     * @param name the name of the skull owner to use
     */
    public SkullBuilder owner(String name) {
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
        return this;
    }

    /**
     * Set the texture of the head
     * You can get the <b>Texture value</b> from websites like mineskin
     * <br>See more details <a href="https://custom-ores.fandom.com/wiki/Getting_a_Texture_Value">here</a>
     * <br><br><b>Using {@link #owner(UUID)}/{@link #owner(String)}/{@link #owner(Player)} is not needed when calling this method</b>
     *
     * @param textureID the texture Data/Value to set
     */
    public SkullBuilder texture(String textureID) {
        final PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
        profile.setProperty(new ProfileProperty("textures", textureID));
        skullMeta.setPlayerProfile(profile);
        return this;
    }

    /**
     * Build the player head
     *
     * @return the player head
     */
    public ItemStack build() {
        skull.setItemMeta(skullMeta);
        return skull;
    }

}
