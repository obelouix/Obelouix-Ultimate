package fr.obelouix.ultimate.blocks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class CompressedCobbleStone extends ItemStack {

    protected CompressedCobbleStone() {
        super(Material.COBBLESTONE);

        ItemMeta meta = this.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.displayName(Component.translatable("block.minecraft.cobblestone", Style.style(NamedTextColor.GREEN, TextDecoration.BOLD, TextDecoration.ITALIC.withState(false))));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.lore(Collections.singletonList(Component.translatable("obelouix.update.up_to_date")));
        this.setItemMeta(meta);
    }
}
