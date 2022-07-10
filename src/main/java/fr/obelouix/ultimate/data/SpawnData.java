package fr.obelouix.ultimate.data;

import fr.obelouix.ultimate.ObelouixUltimate;
import net.kyori.adventure.text.Component;

public class SpawnData {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public static void setup() {
        plugin.getComponentLogger().info(Component.text("hi"));
    }

}
