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

public class GettingAnUpgradeAdvancement {

    public static class StonePickaxeUpgrade extends BaseAdvancement implements ParentGrantedVisibility {
        public StonePickaxeUpgrade(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, @Range(from = 1L, to = 2147483647L) int maxProgression) {
            super(key, display, parent, maxProgression);

            registerEvent(CraftItemEvent.class, e -> {
                if (isVisible(e.getWhoClicked().getUniqueId()) && e.getRecipe().getResult().getType().equals(Material.STONE_PICKAXE)) {
                    @NotNull HumanEntity player = e.getWhoClicked();
                    incrementProgression(player.getUniqueId());
                }
            });
        }
    }

    public static class IronPickaxeUpgrade extends BaseAdvancement implements ParentGrantedVisibility {
        public IronPickaxeUpgrade(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, @Range(from = 1L, to = 2147483647L) int maxProgression) {
            super(key, display, parent, maxProgression);

            registerEvent(CraftItemEvent.class, e -> {
                if (isVisible(e.getWhoClicked().getUniqueId()) && e.getRecipe().getResult().getType().equals(Material.IRON_PICKAXE)) {
                    @NotNull HumanEntity player = e.getWhoClicked();
                    incrementProgression(player.getUniqueId());
                }
            });
        }
    }

    public static class GoldPickaxeUpgrade extends BaseAdvancement implements ParentGrantedVisibility {
        public GoldPickaxeUpgrade(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, @Range(from = 1L, to = 2147483647L) int maxProgression) {
            super(key, display, parent, maxProgression);

            registerEvent(CraftItemEvent.class, e -> {
                if (isVisible(e.getWhoClicked().getUniqueId()) && e.getRecipe().getResult().getType().equals(Material.GOLDEN_PICKAXE)) {
                    @NotNull HumanEntity player = e.getWhoClicked();
                    incrementProgression(player.getUniqueId());
                }
            });
        }
    }

    public static class DiamondPickaxeUpgrade extends BaseAdvancement implements ParentGrantedVisibility {
        public DiamondPickaxeUpgrade(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, @Range(from = 1L, to = 2147483647L) int maxProgression) {
            super(key, display, parent, maxProgression);

            registerEvent(CraftItemEvent.class, e -> {
                if (isVisible(e.getWhoClicked().getUniqueId()) && e.getRecipe().getResult().getType().equals(Material.DIAMOND_PICKAXE)) {
                    @NotNull HumanEntity player = e.getWhoClicked();
                    incrementProgression(player.getUniqueId());
                }
            });
        }
    }

}

