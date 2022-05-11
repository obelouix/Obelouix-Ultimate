package fr.obelouix.ultimate.api;

import fr.obelouix.ultimate.ObelouixUltimate;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class GlowingAPI {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public static void setPlayerGlowing(Player player, PotionEffectType potionEffect) {
        try {
            net.minecraft.world.entity.player.Player entityPlayer = ((CraftPlayer) player).getHandle();
            SynchedEntityData dataAccessor = entityPlayer.getEntityData();
            entityPlayer.setGlowingTag(!entityPlayer.isCurrentlyGlowing());
            Map<Integer, SynchedEntityData.DataItem<?>> map =
                    (Map<Integer, SynchedEntityData.DataItem<?>>) FieldUtils.readDeclaredField(dataAccessor, "d", true);
            SynchedEntityData.DataItem dataItem = map.get(0);
            byte initialBitMask = (Byte) dataItem.getValue();
            byte bitMask = 0x40;
            if (entityPlayer.isCurrentlyGlowing()) {
                dataItem.setValue((byte) (initialBitMask | 1 << bitMask));
            } else {
                dataItem.setValue((byte) (initialBitMask & ~(1 << bitMask)));
            }

            ClientboundSetEntityDataPacket metadataPacket = new ClientboundSetEntityDataPacket(entityPlayer.getId(), dataAccessor, true);

            ((CraftPlayer) player).getHandle().networkManager.send(metadataPacket);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
