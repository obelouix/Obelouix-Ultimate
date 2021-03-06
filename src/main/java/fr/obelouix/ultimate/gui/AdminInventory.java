package fr.obelouix.ultimate.gui;

import fr.obelouix.ultimate.api.InventoryAPI;
import fr.obelouix.ultimate.utils.CustomHeadSkins;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.incendo.interfaces.core.click.ClickHandler;
import org.incendo.interfaces.paper.PlayerViewer;
import org.incendo.interfaces.paper.element.ItemStackElement;
import org.incendo.interfaces.paper.transform.PaperTransform;
import org.incendo.interfaces.paper.type.ChestInterface;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class AdminInventory extends BaseGUI {

    // title of the item that will show the inventory that manage online players
    private static Component playerManagementComponent;
    private static Component playerManagementDescriptionComponent;
    // title of the item that will show the inventory that manage server worlds
    private static Component worldManagementComponent;
    private static Component maintenanceComponent;
    private static Component maintenanceDescComponent;
    // the player who see the inventory
    private Player viewer;

    /**
     * This constructor is only for registering the event
     */
    public AdminInventory() {
    }

    /**
     * This constructor set up the inventory to open when called
     *
     * @param player - the player that will see the inventory
     */
    public AdminInventory(Player player) {

        inventory = Bukkit.createInventory(null, 54, title(player)); //Bukkit.createInventory(null, 54, title(player));
        this.viewer = player;
        playerManagementComponent = Component.text(translationAPI.getTranslation(player, "obelouix.inventory.admin_center.player_management"), NamedTextColor.GREEN);
        playerManagementDescriptionComponent = Component.text(translationAPI.getTranslation(player, "obelouix.inventory.admin_center.player_management.description"), NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false);
        worldManagementComponent = Component.text(translationAPI.getTranslation(player, "obelouix.inventory.admin_center.world_management"), NamedTextColor.GOLD);
        Component worldManagementDescriptionComponent = Component.text(translationAPI.getTranslation(player, "obelouix.inventory.admin_center.world_management.description"))
                .decoration(TextDecoration.ITALIC, false);

        maintenanceComponent = Component.text(translationAPI.getTranslation(player, "obelouix.inventory.maintenance_center"), NamedTextColor.AQUA);
        maintenanceDescComponent = Component.text(translationAPI.getTranslation(player, "obelouix.inventory.maintenance_center.description"))
                .decoration(TextDecoration.ITALIC, false);

        setupInventory(player);
        showInventory(player);
    }

    @Override
    protected Component title(Player player) {
        return Component.text(translationAPI.getTranslation(player, "obelouix.inventory.admin_center"), NamedTextColor.DARK_RED);
    }

    @Override
    protected void setupInventory() {

    }

    protected void setupInventory(Player player) {

        chestInterface = ChestInterface.builder()
                .rows(6)
                .updates(true, 5)
                .clickHandler(ClickHandler.cancel())
                .addTransform(PaperTransform.chestItem(ItemStackElement.of(InventoryAPI.addCustomSkull(viewer, playerManagementComponent)), 1, 1))
                .addTransform(PaperTransform.chestFill(ItemStackElement.of(new ItemStack(Material.BLACK_STAINED_GLASS_PANE))))
                .title(title(viewer))
                .build();

        inventory.setItem(0, InventoryAPI.addCustomSkull(viewer, playerManagementComponent));
        InventoryAPI.addLore(Objects.requireNonNull(inventory.getItem(0)),
                List.of(playerManagementDescriptionComponent));
        inventory.setItem(4, InventoryAPI.addCustomSkull(CustomHeadSkins.GLOBE, worldManagementComponent));
        // Maintenance
        inventory.setItem(11, InventoryAPI.addCustomSkull(CustomHeadSkins.SHELL_COMPUTER, maintenanceComponent));
        InventoryAPI.addLore(Objects.requireNonNull(inventory.getItem(11)), List.of(maintenanceDescComponent));
    }

    @Override
    public void showInventory(@NotNull Player player) {
        //player.openInventory(inventory);
        chestInterface.open(PlayerViewer.of(player));
    }

    @Override
    @EventHandler
    public void cancelClick(InventoryClickEvent event) {
        final InventoryView view = event.getView();
        final Player player = (Player) event.getWhoClicked();
        if (view.title().equals(title(player))) {
            event.setCancelled(true);
        }
    }

    @Override
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

/*        final ItemStack clickedItem = event.getCurrentItem();
        final Player clicker = (Player) event.getWhoClicked();
        if (clickedItem != null) {
            final String clickedItemTitle = PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(clickedItem.getItemMeta().displayName()));
            if (clickedItemTitle.equals(PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(clickedItem.getItemMeta().displayName())))) {
//                new SignGui(new String[]{"",  "^^^^^^^^^^^^^^^", "date de debut", "format: jj/mm/aaaa"}).openFakeGui((Player) event.getWhoClicked());
                clicker.openBook(new MaintenanceBook(clicker).show());
            }
        }*/
    }
}
