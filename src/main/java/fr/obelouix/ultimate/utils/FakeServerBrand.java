package fr.obelouix.ultimate.utils;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketDataSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Set;

public class FakeServerBrand {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

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
            channels.add("minecraft:brand");
            plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "minecraft:brand");
            player.sendPluginMessage(plugin, "minecraft:brand", new PacketDataSerializer(Unpooled.buffer()).a(ChatColor.translateAlternateColorCodes('&', Config.getCustomServerBrandName() + "&r")).array());
        }
    }

}
