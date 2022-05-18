package fr.obelouix.ultimate.advancements.tabs;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import fr.obelouix.ultimate.advancements.*;
import fr.obelouix.ultimate.api.UltimateAdvancementAPI;
import org.bukkit.Material;

public class MinecraftTab {

    private static final AdvancementTab ADVANCEMENT_TAB = UltimateAdvancementAPI.getAdvancementAPI().createAdvancementTab("obelouix_ultimate");
    private static final GettingWoodAdvancement GETTING_WOOD = new GettingWoodAdvancement("getting_wood",
            new AdvancementDisplay(
                    Material.OAK_LOG,
                    "",
                    AdvancementFrameType.TASK,
                    true,
                    true,
                    1,
                    0,
                    ""),
            MinecraftRootAdvancement.getRootAdvancement(), 3);

    private static final CraftingBaseAdvancement CRAFTING_BASE = new CraftingBaseAdvancement("crafting_base",
            new AdvancementDisplay(
                    Material.CRAFTING_TABLE,
                    "",
                    AdvancementFrameType.TASK,
                    true,
                    true,
                    2,
                    0,
                    ""),
            GETTING_WOOD, 1);

    private static final FirstPickaxeAdvancement FIRST_PICKAXE = new FirstPickaxeAdvancement("first_pickaxe",
            new AdvancementDisplay(
                    Material.WOODEN_PICKAXE,
                    "",
                    AdvancementFrameType.TASK,
                    true,
                    true,
                    2.5f,
                    2,
                    ""),
            CRAFTING_BASE, 1);

    private static final StoneAgeAdvancement STONE_AGE = new StoneAgeAdvancement("mine_stone",
            new AdvancementDisplay(
                    Material.WOODEN_PICKAXE,
                    "",
                    AdvancementFrameType.TASK,
                    true,
                    true,
                    4,
                    3,
                    ""),
            FIRST_PICKAXE, 1);

    private static final GettingAnUpgradeAdvancement.StonePickaxeUpgrade GETTING_AN_UPGRADE = new GettingAnUpgradeAdvancement.StonePickaxeUpgrade("upgrade_tools_stone",
            new AdvancementDisplay(
                    Material.STONE_PICKAXE,
                    "",
                    AdvancementFrameType.TASK,
                    true,
                    true,
                    2.5f,
                    3,
                    ""),
            FIRST_PICKAXE, 1);

    private static final GettingAnUpgradeAdvancement.IronPickaxeUpgrade IRON_PICKAXE_UPGRADE = new GettingAnUpgradeAdvancement.IronPickaxeUpgrade(
            "upgrade_tools_iron",
            new AdvancementDisplay(
                    Material.IRON_PICKAXE,
                    "",
                    AdvancementFrameType.TASK,
                    true,
                    true,
                    2.5f,
                    4,
                    ""),
            GETTING_AN_UPGRADE, 1
    );

    private static final GettingAnUpgradeAdvancement.GoldPickaxeUpgrade GOLD_PICKAXE_UPGRADE = new GettingAnUpgradeAdvancement.GoldPickaxeUpgrade(
            "upgrade_tools_gold",
            new AdvancementDisplay(
                    Material.GOLDEN_PICKAXE,
                    "",
                    AdvancementFrameType.TASK,
                    true,
                    true,
                    2.5f,
                    4,
                    ""),
            IRON_PICKAXE_UPGRADE, 1
    );

    private static final GettingAnUpgradeAdvancement.DiamondPickaxeUpgrade DIAMOND_PICKAXE_UPGRADE = new GettingAnUpgradeAdvancement.DiamondPickaxeUpgrade(
            "upgrade_tools_diamond",
            new AdvancementDisplay(
                    Material.DIAMOND_PICKAXE,
                    "",
                    AdvancementFrameType.TASK,
                    true,
                    true,
                    2.5f,
                    4,
                    ""),
            IRON_PICKAXE_UPGRADE, 1
    );

    /**
     * @return the advancement tab
     */
    public static AdvancementTab getAdvancementTab() {
        return ADVANCEMENT_TAB;
    }

    /**
     * Register all created advancements into the default advancement tab
     */
    public static void registerAdvancements() {
        ADVANCEMENT_TAB.registerAdvancements(
                MinecraftRootAdvancement.getRootAdvancement(),
                GETTING_WOOD,
                CRAFTING_BASE,
                FIRST_PICKAXE,
                STONE_AGE,
                GETTING_AN_UPGRADE,
                IRON_PICKAXE_UPGRADE,
                GOLD_PICKAXE_UPGRADE,
                DIAMOND_PICKAXE_UPGRADE
        );
    }

}
