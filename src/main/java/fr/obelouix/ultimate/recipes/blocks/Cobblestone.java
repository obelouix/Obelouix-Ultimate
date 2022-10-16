package fr.obelouix.ultimate.recipes.blocks;

import fr.obelouix.ultimate.blocks.CompressedBlocks;
import fr.obelouix.ultimate.recipes.RecipeRegistry;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class Cobblestone extends RecipeRegistry {
    @Override
    public void addRecipe() {
        shapelessRecipe.put(CompressedBlocks.COMPRESSED_COBBLESTONE, 1);
        registerShapelessRecipe(new NamespacedKey(plugin, "cobblestone_from_compressed"), "cobblestone", new ItemStack(Material.COBBLESTONE, 9), shapelessRecipe);
    }
}
