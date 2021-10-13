package fr.obelouix.ultimate.gui;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.i18n.I18n;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public abstract class BaseGUI implements Listener {

    protected ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    protected I18n i18n = I18n.getInstance();

    /**
     * Used to show a custom inventory to a player
     *
     * @param player The player who will see the custom inventory
     */
    public abstract void showInventory(@NotNull Player player);

    /**
     * Used to cancel the click event to avoid player stealing items in the
     * custom inventory
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public abstract void cancelClick(InventoryClickEvent event);

    /**
     * Control what to do on each clicked objects
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public abstract void onInventoryClickEvent(InventoryClickEvent event);
}
