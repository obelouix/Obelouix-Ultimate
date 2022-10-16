package fr.obelouix.ultimate.recipes;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class RecipeRegistry {

    protected static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    protected final HashMap<Character, ItemStack> shapedRecipe = new HashMap<>();
    protected final HashMap<ItemStack, Integer> shapelessRecipe = new HashMap<>();

    protected void registerShapedRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, Map<Character, ItemStack> ingredients, String... shape) {
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(shape);
        ingredients.forEach(recipe::setIngredient);
        register(recipe);
    }

    protected void registerShapelessRecipe(@NotNull NamespacedKey key, String group, @NotNull ItemStack result, Map<ItemStack, Integer> ingredients) {
        ShapelessRecipe recipe = new ShapelessRecipe(key, result);
        recipe.setGroup(group);
        ingredients.forEach((itemStack, quantity) -> recipe.addIngredient(quantity, itemStack));
        register(recipe);
    }

    private void register(Recipe recipe) {
        try {
            plugin.getServer().addRecipe(recipe);
        } catch (IllegalStateException ignored) {

        }
    }

    public abstract void addRecipe();

}
