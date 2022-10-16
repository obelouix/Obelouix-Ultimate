package fr.obelouix.ultimate.recipes.blocks;

import fr.obelouix.ultimate.blocks.CompressedBlocks;
import fr.obelouix.ultimate.recipes.RecipeRegistry;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class CompressedCobblestone extends RecipeRegistry {

    @Override
    public void addRecipe() {
        shapedRecipe.put('C', new ItemStack(Material.COBBLESTONE));
        registerShapedRecipe(new NamespacedKey(plugin, "compressed_cobblestone"), CompressedBlocks.COMPRESSED_COBBLESTONE, shapedRecipe, "CCC", "CCC", "CCC");
    }
}
