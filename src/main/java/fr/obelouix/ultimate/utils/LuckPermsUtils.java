package fr.obelouix.ultimate.utils;

import fr.obelouix.ultimate.ObelouixUltimate;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Objects;
import java.util.Set;

public class LuckPermsUtils {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static RegisteredServiceProvider<LuckPerms> luckPermsProvider;
    private static LuckPerms luckPermsAPI;

    public static void checkForLuckPerms() {
        if (plugin.getClass("net.luckperms.api.LuckPerms")) {
            plugin.getLogger().info("Found LuckPerms");
            final RegisteredServiceProvider<LuckPerms> lpProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (lpProvider != null) {
                luckPermsProvider = lpProvider;
                luckPermsAPI = luckPermsProvider.getProvider();
            }
        }
    }

    public static RegisteredServiceProvider<LuckPerms> getLuckPermsProvider() {
        return luckPermsProvider;
    }

    public static LuckPerms getLuckPermsAPI() {
        return luckPermsAPI;
    }

    /**
     * Get the list of groups
     * @return list of groups
     */
    public static @NonNull @Unmodifiable Set<Group> getGroups() {
        return luckPermsAPI.getGroupManager().getLoadedGroups();
    }

    /**
     * Get the Primary group of the given player
     * @param player - the player to check
     * @return primary group of the player
     */
    public static @NotNull String getUserPrimaryGroup(@NotNull Player player){
        return Objects.requireNonNull(luckPermsAPI.getUserManager().getUser(player.getUniqueId())).getPrimaryGroup();
    }

    /**
     * Set the primary group of the given player
     * @param player - the player to set the primary group
     * @param group - the primary group
     */
    public static void setUserPrimaryGroup(@NotNull Player player, String group){
        Objects.requireNonNull(luckPermsAPI.getUserManager().getUser(player.getUniqueId())).setPrimaryGroup(group);
    }

    /**
     * Retrieve {@link Player} prefixes from LuckPerms
     *
     * @param player the player
     * @return player prefixes
     */
    public static String getPrefix(Player player) {
        if (luckPermsAPI != null) {
            final User user = luckPermsAPI.getPlayerAdapter(Player.class).getUser(player);
            final String prefix = user.getCachedData().getMetaData().getPrefix();
            return prefix != null ? prefix : "";
        }
        return "";
    }

    /**
     * Retrieve {@link Player} suffixes from LuckPerms
     *
     * @param player the player
     * @return player suffixes
     */
    public static String getSuffix(Player player) {
        if (luckPermsAPI != null) {
            final User user = luckPermsAPI.getPlayerAdapter(Player.class).getUser(player);
            final String suffix = user.getCachedData().getMetaData().getSuffix();
            return suffix != null ? suffix : "";
        }
        return "";
    }
}
