package fr.obelouix.ultimate.advancements;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GettingWoodAdvancement extends BaseAdvancement {

    private final List<Material> requirements = List.of(Material.OAK_LOG, Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG, Material.JUNGLE_LOG, Material.SPRUCE_LOG);

    public GettingWoodAdvancement(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, int maxProgression) {
        super(key, display, parent, maxProgression);

        registerEvent(BlockBreakEvent.class, e -> {
            Player player = e.getPlayer();
            if (isVisible(player) && requirements.contains(e.getBlock().getType())) {
                incrementProgression(player);
            }
        });

    }
}
