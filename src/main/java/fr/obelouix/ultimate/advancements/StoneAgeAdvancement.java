package fr.obelouix.ultimate.advancements;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.visibilities.ParentGrantedVisibility;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.List;

public class StoneAgeAdvancement extends BaseAdvancement implements ParentGrantedVisibility {

    private final List<Material> requirements = List.of(Material.COBBLESTONE, Material.COBBLED_DEEPSLATE, Material.BLACKSTONE);

    public StoneAgeAdvancement(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, @Range(from = 1L, to = 2147483647L) int maxProgression) {
        super(key, display, parent, maxProgression);

        registerEvent(BlockBreakEvent.class, e -> {
            if (isVisible(e.getPlayer()) && requirements.contains(e.getBlock().getType())) {
                incrementProgression(e.getPlayer());
            }
        });

    }
}
