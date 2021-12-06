package fr.obelouix.ultimate.utils;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Set;

public class FakeServerBrand {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final String minecraftBrandChannel = "minecraft:brand";

    public static void sendFakeBrand(Player player) {
        Set<String> channels = null;
        try {
            final Field playerChannels = player.getClass().getDeclaredField("channels");
            playerChannels.setAccessible(true);
            channels = (Set<String>) playerChannels.get(player);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (channels != null && !channels.contains("minecraft:brand")) {
            channels.add(minecraftBrandChannel);
            plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, minecraftBrandChannel);
            //player.sendPluginMessage(plugin, minecraftBrandChannel, new PacketDecoder(PacketFlow.SERVERBOUND).channelActive(ChatColor.translateAlternateColorCodes('&', Config.getCustomServerBrandName() + "&r")).array());
        }
    }

}
