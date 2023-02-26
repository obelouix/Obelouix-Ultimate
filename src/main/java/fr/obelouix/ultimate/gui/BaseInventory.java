package fr.obelouix.ultimate.gui;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public abstract class BaseInventory {

    public static final int ONE_ROW = 9;
    public static final int TWO_ROWS = ONE_ROW * 2;
    public static final int THREE_ROWS = ONE_ROW * 3;
    public static final int FOUR_ROWS = ONE_ROW * 4;
    public static final int FIVE_ROWS = ONE_ROW * 5;
    public static final int SIX_ROWS = ONE_ROW * 6;

    public Inventory inventory;
    private int inventorySize;

    public void rows(int rows){
        inventorySize = rows;
    }

    public void build(){
        inventory = Bukkit.createInventory(null, inventorySize);
    }

}
