package fr.obelouix.ultimate.events.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class DoubleDoors implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() instanceof Door door) {
            if (door.getMaterial() == Material.IRON_DOOR) event.setCancelled(true);
            Door.Hinge hinge = door.getHinge();
            event.getPlayer().sendMessage("coucou");
            Block secondaryDoor = getSecondaryDoor(door, hinge);
            if (event.getAction().isRightClick()) {
                if (door.isOpen() || ((Door) secondaryDoor).isOpen()) {
                    door.setOpen(false);
                    ((Door) secondaryDoor).setOpen(false);
                } else {
                    door.setOpen(true);
                    ((Door) secondaryDoor).setOpen(true);
                }
            }
        }
    }

    private Block getSecondaryDoor(Door door, Door.Hinge hinge) {
        return switch (door.getFacing()) {
            case NORTH ->
                    hinge.equals(Door.Hinge.LEFT) ? ((Block) door).getRelative(BlockFace.EAST) : ((Block) door).getRelative(BlockFace.WEST);
            case EAST ->
                    hinge.equals(Door.Hinge.LEFT) ? ((Block) door).getRelative(BlockFace.SOUTH) : ((Block) door).getRelative(BlockFace.NORTH);
            case SOUTH ->
                    hinge.equals(Door.Hinge.LEFT) ? ((Block) door).getRelative(BlockFace.WEST) : ((Block) door).getRelative(BlockFace.EAST);
            case WEST ->
                    hinge.equals(Door.Hinge.LEFT) ? ((Block) door).getRelative(BlockFace.NORTH) : ((Block) door).getRelative(BlockFace.SOUTH);
            default -> null;
        };
    }

}
