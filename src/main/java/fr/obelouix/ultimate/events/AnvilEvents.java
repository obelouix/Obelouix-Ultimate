package fr.obelouix.ultimate.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryView;

public class AnvilEvents implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onAnvilPrepare(final PrepareAnvilEvent prepareAnvilEvent) {
        final AnvilInventory inv = prepareAnvilEvent.getInventory();
        if (!inv.getViewers().isEmpty()) {
            inv.setMaximumRepairCost(Integer.MAX_VALUE);
            int repairCost = inv.getRepairCost();
            final Player player = (Player) inv.getViewers().get(0);
            if (repairCost > 39) {
                inv.setRepairCost(39);
                prepareAnvilEvent.getView().setProperty(InventoryView.Property.REPAIR_COST, 39);
            }
            player.updateInventory();
        }
    }

}
