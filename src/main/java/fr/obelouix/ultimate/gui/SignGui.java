package fr.obelouix.ultimate.gui;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntitySign;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftSign;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record SignGui(String[] lines) {

    public void openFakeGui(@NotNull Player player) {
        final var blockPosition = new BlockPosition(player.getLocation().getBlockX(), 255, player.getLocation().getBlockZ());

        var packet = new PacketPlayOutBlockChange(blockPosition, CraftMagicNumbers.getBlock(Material.OAK_WALL_SIGN).getBlockData());
        ((CraftPlayer) player).getHandle().b.sendPacket(packet);

        IChatBaseComponent[] components = CraftSign.sanitizeLines(lines);
        var sign = new TileEntitySign(blockPosition, Blocks.cg.getBlockData());
        sign.setColor(EnumColor.p);

        for (var i = 0; i < components.length; i++)
            sign.a(i, components[i]);

        ((CraftPlayer) player).getHandle().b.sendPacket(sign.getUpdatePacket());

        var outOpenSignEditor = new PacketPlayOutOpenSignEditor(blockPosition);
        ((CraftPlayer) player).getHandle().b.sendPacket(outOpenSignEditor);
    }

}
