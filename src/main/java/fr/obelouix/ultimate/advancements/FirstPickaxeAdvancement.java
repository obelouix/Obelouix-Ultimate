package fr.obelouix.ultimate.advancements;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.visibilities.ParentGrantedVisibility;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.CraftItemEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class FirstPickaxeAdvancement extends BaseAdvancement implements ParentGrantedVisibility {
    public FirstPickaxeAdvancement(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, @Range(from = 1L, to = 2147483647L) int maxProgression) {
        super(key, display, parent, maxProgression);

        registerEvent(CraftItemEvent.class, e -> {
            if (isVisible(e.getWhoClicked().getUniqueId()) && e.getRecipe().getResult().getType().equals(Material.WOODEN_PICKAXE)) {
                @NotNull HumanEntity player = e.getWhoClicked();

                incrementProgression(player.getUniqueId());
            }
        });
    }
}
