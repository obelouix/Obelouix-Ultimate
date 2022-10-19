package fr.obelouix.ultimate.gui.options;

import fr.obelouix.ultimate.api.InventoryBuilder;
import fr.obelouix.ultimate.api.InventoryType;
import fr.obelouix.ultimate.api.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OptionsInventory implements Listener {

    private final Inventory optionsInventory;
    private final ItemStack fillItem;
    private final ItemStack coloredEnchantTooltip;

    public OptionsInventory() {

        fillItem = new ItemBuilder(Material.SHULKER_SHELL)
                .displayname(Component.empty())
                .setCustomModelData(9000)
                .build();

        coloredEnchantTooltip = new ItemBuilder(Material.SHULKER_SHELL)
                .displayname(Component.text("TBD"))
                .setCustomModelData(9001)
                .build();

        final Component title = Component.translatable("options.title");
        optionsInventory = new InventoryBuilder(InventoryType.SIX_ROW, title)
                .setItem(10, coloredEnchantTooltip)
                .fillEmptySlots(fillItem)
                .build();
    }

    public void showInventory(@NotNull Player player) {
        player.openInventory(optionsInventory);
    }

    @EventHandler
    public void cancelClick(InventoryClickEvent event) {
        if (event.getView().title().equals(Component.translatable("options.title"))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getView().title().equals(Component.translatable("options.title"))) {
            if (event.getCurrentItem().equals(coloredEnchantTooltip)) {
                //TODO
            }
        }
    }
}
