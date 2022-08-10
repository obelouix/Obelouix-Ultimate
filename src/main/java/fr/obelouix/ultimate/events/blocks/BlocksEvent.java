package fr.obelouix.ultimate.events.blocks;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BlocksEvent implements Listener {

    // Will be used later
    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerBreakBlock(BlockBreakEvent event) {

        final @NotNull Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.SURVIVAL) {
            final @NotNull Collection<ItemStack> drops = event.getBlock().getDrops();
            final Material blockType = event.getBlock().getType();
            // List of blacklisted blocks
            final List<Material> blacklist = List.of(Material.CHEST, Material.TRAPPED_CHEST, Material.FURNACE, Material.BLAST_FURNACE, Material.DISPENSER, Material.DROPPER, Material.HOPPER);

            // Cancel drop only if the broken block isn't a blacklisted block
            if (!blacklist.contains(blockType)) {
                // Player inventory is not full
                if (player.getInventory().firstEmpty() != -1) {
                    event.setDropItems(false);
                    drops.forEach(itemStack -> player.getInventory().addItem(itemStack));

                    // Player Inventory is full
                } else {
                    // filtered list with items that are not at their max stack size
                    final List<ItemStack> inventory = Arrays.stream(player.getInventory().getContents())
                            .filter(Objects::nonNull)
                            .filter(itemStack -> itemStack.getAmount() < itemStack.getMaxStackSize())
                            .toList();

                    for (final ItemStack itemStack : inventory) {
                        if (itemStack.getAmount() < itemStack.getMaxStackSize() && itemStack.getType().equals(blockType)) {
                            event.setDropItems(false);
                            final ItemStack tempItemStack = new ItemStack(itemStack.getType(), 1);
                            player.getInventory().addItem(tempItemStack);
                            break;
                        }
                    }
                }
            }
        }

    }
}
