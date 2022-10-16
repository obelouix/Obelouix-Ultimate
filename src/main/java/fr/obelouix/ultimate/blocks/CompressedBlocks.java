package fr.obelouix.ultimate.blocks;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CompressedBlocks {

    public static final ItemStack COMPRESSED_COBBLESTONE = new CompressedCobbleStone();

    public void setup() {
        /*enchantBlock(COMPRESSED_COBBLESTONE);*/
    }

    private void enchantBlock(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemStack.setItemMeta(meta);
    }

}
