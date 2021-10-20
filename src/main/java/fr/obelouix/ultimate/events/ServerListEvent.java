package fr.obelouix.ultimate.events;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import fr.obelouix.ultimate.config.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ServerListEvent implements Listener {

    @EventHandler
    public void modifyServerList(PaperServerListPingEvent event) {
        if (Config.isServerInMaintenance()) {
            event.setProtocolVersion(1);
            event.setVersion("Maintenance");
        }
    }

}
