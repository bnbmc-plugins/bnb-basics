package com.bananafalls.bnb.basics.Bnb;

import org.bukkit.Location;
import org.bukkit.Material;

public class SafeChecker {

    public boolean checkIfSafe(Location loc){

        Location blockAbove = loc.add(0, 1, 0);
        Location blockBelow = loc.subtract(0, 1, 0);

        System.out.println(blockAbove + " /////////////// " + blockBelow);

        if(loc.getBlock().getType() != Material.AIR || loc.getBlock().getType() == Material.LAVA ||
                blockAbove.getBlock().getType() != Material.AIR || blockAbove.getBlock().getType() == Material.LAVA ||
        blockBelow.getBlock().getType() == Material.LAVA){
            return false;
        }

        return true;
    }

}
