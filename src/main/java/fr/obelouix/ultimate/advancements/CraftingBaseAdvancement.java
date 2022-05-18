package fr.obelouix.ultimate.advancements;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.visibilities.ParentGrantedVisibility;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.CraftItemEvent;
import org.jetbrains.annotations.NotNull;

public class CraftingBaseAdvancement extends BaseAdvancement implements ParentGrantedVisibility {
    public CraftingBaseAdvancement(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, int maxProgression) {
        super(key, display, parent, maxProgression);

        registerEvent(CraftItemEvent.class, e -> {
            if (isVisible(e.getWhoClicked().getUniqueId()) && e.getRecipe().getResult().getType().equals(Material.CRAFTING_TABLE)) {
                @NotNull HumanEntity player = e.getWhoClicked();
                incrementProgression(player.getUniqueId());
            }
        });

    }
}
