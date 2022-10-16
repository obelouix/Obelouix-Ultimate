package fr.obelouix.ultimate.recipes.blocks;

import fr.obelouix.ultimate.blocks.CompressedBlocks;
import fr.obelouix.ultimate.recipes.RecipeRegistry;
import org.bukkit.NamespacedKey;

public class SuperCompressedCobblestone extends RecipeRegistry {

    @Override
    public void addRecipe() {
        shapedRecipe.put('C', CompressedBlocks.COMPRESSED_COBBLESTONE);
        registerShapedRecipe(new NamespacedKey(plugin, "compressed_cobblestone"), CompressedBlocks.COMPRESSED_COBBLESTONE, shapedRecipe, "CCC", "CCC", "CCC");
    }
}
