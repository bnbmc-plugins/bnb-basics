package com.bananafalls.bnb.basics.Bnb;

import org.bukkit.Location;
import org.bukkit.Material;
import static org.bukkit.Material.*;

public class SafeChecker {

    public boolean isVoid(Location loc){
        int blocksToBottom = (int) loc.getY();

        Location blockToCheck = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());
        blockToCheck.setY(blockToCheck.getY() - 1);

        while(blocksToBottom > 0){
            if(blockToCheck.getBlock().getType() != AIR){
                return false;
            } else {
                blockToCheck.setY(blockToCheck.getY() - 1);
                blocksToBottom = blocksToBottom - 1;
            }
        }

        return true;
    }

    public boolean checkIfSafe(Location loc) {

        if (!isVoid(loc)) {
            //<editor-fold desc="temp">
            Location topBlockLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());
            Location bottomBlockLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
            Location blockBelowLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ());

            Material topBlock = topBlockLoc.getBlock().getType();
            Material bottomBlock = bottomBlockLoc.getBlock().getType();
            Material blockBelow = blockBelowLoc.getBlock().getType();

            //</editor-fold>
            if (blockBelow != LAVA) {
                if (topBlock == LAVA || bottomBlock == LAVA) {
                    return false;
                } else if (bottomBlock == SOUL_SAND && !topBlock.isOccluding()) {
                    return true;
                } else if (!topBlock.isOccluding() && !bottomBlock.isOccluding()) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;

    }

}
