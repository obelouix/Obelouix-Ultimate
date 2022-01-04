package fr.obelouix.ultimate.api;

import com.fren_gor.ultimateAdvancementAPI.AdvancementMain;
import fr.obelouix.ultimate.ObelouixUltimate;

public class UltimateAdvancementAPI {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static AdvancementMain advancementMain;
    private static com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI advancementAPI;

    public static synchronized void init() {
        advancementMain = new AdvancementMain(plugin);
        advancementMain.load();
        advancementMain.enableInMemory();
        advancementAPI = com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI.getInstance(plugin);
        plugin.getLogger().info("Initialized UltimateAdvancementAPI");
    }

}
