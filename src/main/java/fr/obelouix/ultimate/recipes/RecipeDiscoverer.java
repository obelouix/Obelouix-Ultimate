package fr.obelouix.ultimate.recipes;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.TranslationAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class RecipeDiscoverer implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final TranslationAPI translationAPI = plugin.getTranslationAPI();

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent entityPickupItemEvent) {
        if (entityPickupItemEvent.getEntityType().equals(EntityType.PLAYER)) {
            final Player player = (Player) entityPickupItemEvent.getEntity();
            final ItemStack item = entityPickupItemEvent.getItem().getItemStack();

            // switch cause a lot more recipes will be added
            switch (item.getType()) {
                case ROTTEN_FLESH -> player.discoverRecipe(Objects.requireNonNull(NamespacedKey.fromString("rottenflesh_to_leather", plugin)));
                case ANVIL -> player.discoverRecipe(Objects.requireNonNull(NamespacedKey.fromString("repairable_anvil", plugin)));
            }
        }
    }

    @EventHandler
    public void onPlayerCompleteCraft(CraftItemEvent craftItemEvent) {

        final Player player = (Player) craftItemEvent.getWhoClicked();

        switch (craftItemEvent.getRecipe().getResult().getType()) {
            case ANVIL -> player.discoverRecipe(new NamespacedKey(plugin, "repairable_anvil"));
        }
    }
}
