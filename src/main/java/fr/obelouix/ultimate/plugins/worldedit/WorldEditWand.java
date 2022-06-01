package fr.obelouix.ultimate.plugins.worldedit;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.command.tool.InvalidToolBindException;
import fr.obelouix.ultimate.components.Components;
import fr.obelouix.ultimate.permissions.IPermission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class WorldEditWand implements Listener {

    @EventHandler
    public void onGiveWand(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();
        if (event.getMessage().equalsIgnoreCase("//wand")) {
            if (IPermission.hasPermission(player, "worldedit.wand")) {
                event.setCancelled(true);

                ItemStack wandItem = new ItemStack(Material.WOODEN_AXE);
                ItemMeta meta = wandItem.getItemMeta();
                meta.displayName(new Components(player).worldEditWand);

                wandItem.setItemMeta(meta);

                PlayerInventory playerInventory = player.getInventory();
                playerInventory.addItem(wandItem);

            }
        }

    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) throws IncompleteRegionException {
        Player player = event.getPlayer();
        @NotNull Action action = event.getAction();
        LocalSession session = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));

        if ((action.isLeftClick() || action.isRightClick())) {

            ItemStack item = event.getItem();

            if (item != null && item.getType().equals(Material.WOODEN_AXE)) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasDisplayName() && meta.displayName().equals(new Components(player).worldEditWand)) {
                    if (action.isLeftClick()) {
                        //session.getSelection(BukkitAdapter.adapt(player.getWorld())).
                        new WorldRegenerator().RegenBelowZero(player);
                    }
                } else {
                    try {
                        session.setTool(BukkitAdapter.adapt(item).getType(), null);
                    } catch (InvalidToolBindException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        // Bukkit.getServer().dispatchCommand(player, "/tool selwand");

    }


}
