package fr.obelouix.ultimate.advancements;

import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import fr.obelouix.ultimate.advancements.tabs.MinecraftTab;
import fr.obelouix.ultimate.messages.I18NMessages;
import org.bukkit.Material;

public class MinecraftRootAdvancement {

    private static final AdvancementDisplay rootDisplay = new AdvancementDisplay(
            Material.GRASS_BLOCK,
            // I think I'll need to rewrite this api to handle multi-language advancements
            I18NMessages.ADVANCEMENTS_ROOT.getSystemTranslation(),
            AdvancementFrameType.TASK,
            true,
            false,
            0,
            0,
            I18NMessages.ADVANCEMENTS_ROOT_DESC.getSystemTranslation());

    private static final RootAdvancement rootAdvancement = new RootAdvancement(
            MinecraftTab.getAdvancementTab(), "root", rootDisplay, "textures/block/stone.png");

    public static RootAdvancement getRootAdvancement() {
        return rootAdvancement;
    }
}
