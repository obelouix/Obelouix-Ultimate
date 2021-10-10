package fr.obelouix.ultimate.utils;

import fr.obelouix.ultimate.ObelouixUltimate;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Unmodifiable;

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

    public static @NonNull @Unmodifiable Set<Group> getGroups() {
        return luckPermsAPI.getGroupManager().getLoadedGroups();
    }

}
