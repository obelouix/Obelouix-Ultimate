package fr.obelouix.ultimate.recipes;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CustomCraftingTableRecipes {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final List<Recipe> recipeList = new ArrayList<>();

    public CustomCraftingTableRecipes() {
        final HashMap<Character, ItemStack> repairableAnvilIngredients = new HashMap<>();
        repairableAnvilIngredients.put('I', new ItemStack(Material.IRON_INGOT));
        repairableAnvilIngredients.put('A', new ItemStack(Material.DAMAGED_ANVIL));
        final ShapedRecipe repairable_anvil = registerRecipe(new NamespacedKey(plugin, "repairable_anvil"), new ItemStack(Material.ANVIL), repairableAnvilIngredients, "III", "IAI", "III");

        register(repairable_anvil);

    }

    private ShapedRecipe registerRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, Map<Character, ItemStack> ingredients, String... shape) {
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(shape);
        ingredients.forEach(recipe::setIngredient);
        return recipe;
    }

    private void register(ShapedRecipe shapedRecipe) {
        try {
            plugin.getServer().addRecipe(shapedRecipe);
        } catch (IllegalStateException ignored){

        }
    }

}
