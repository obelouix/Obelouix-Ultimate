package fr.obelouix.ultimate.components;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class PingFormat {

    public static Component getPingComponent(int ping) {
        Component pingComponent;
        if (ping <= 50) {
            pingComponent = Component.text(ping, NamedTextColor.GREEN);
        } else if (ping <= 150) {
            pingComponent = Component.text(ping, NamedTextColor.YELLOW);
        } else if (ping <= 300) {
            pingComponent = Component.text(ping, NamedTextColor.RED);
        } else {
            pingComponent = Component.text(ping, NamedTextColor.DARK_RED);
        }
        return pingComponent;
    }
}
