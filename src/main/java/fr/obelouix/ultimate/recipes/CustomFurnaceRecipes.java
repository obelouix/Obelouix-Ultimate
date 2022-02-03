package fr.obelouix.ultimate.recipes;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

public class CustomFurnaceRecipes {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private final FurnaceRecipe rottenfleshToLeather = registerRecipe(new NamespacedKey(plugin, "rottenflesh_to_leather"), new ItemStack(Material.LEATHER), new RecipeChoice.ExactChoice(new ItemStack(Material.ROTTEN_FLESH)), 0.25f, 250);


    public CustomFurnaceRecipes() {
        //Rotten flesh to leather
        rottenfleshToLeather.setGroup("stuff");
        register(rottenfleshToLeather);
    }


    private FurnaceRecipe registerRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull RecipeChoice input, float experience, int cookingTime) {
        return new FurnaceRecipe(key, result, input, experience, cookingTime);
    }

    private void register(FurnaceRecipe furnaceRecipe) {
        try{
            plugin.getServer().addRecipe(furnaceRecipe);
        } catch (IllegalStateException ignored){

        }
    }

}
