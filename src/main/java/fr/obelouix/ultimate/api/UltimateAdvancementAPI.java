package fr.obelouix.ultimate.api;

import com.fren_gor.ultimateAdvancementAPI.AdvancementMain;
import com.fren_gor.ultimateAdvancementAPI.events.PlayerLoadingCompletedEvent;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.advancements.tabs.MinecraftTab;

import java.util.List;

public class UltimateAdvancementAPI {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI advancementAPI;


    public static com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI getAdvancementAPI() {
        return advancementAPI;
    }

    public synchronized void init() {
        AdvancementMain advancementMain = new AdvancementMain(plugin);
        advancementMain.load();
        advancementMain.enableInMemory();

        advancementAPI = com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI.getInstance(plugin);
        advancementAPI.disableVanillaAdvancements();

        plugin.getLogger().info("Initialized UltimateAdvancementAPI");

        MinecraftTab.registerAdvancements();

        List.of(MinecraftTab.getAdvancementTab())
                .forEach(advancementTab -> advancementTab.getEventManager().register(
                        advancementTab, PlayerLoadingCompletedEvent.class, event -> advancementTab.showTab(event.getPlayer())
                ));
    }
}
