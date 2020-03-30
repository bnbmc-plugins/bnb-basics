package com.bananafalls.bnb.basics.Bnb;

import org.bukkit.Location;
import org.bukkit.Material;
import static org.bukkit.Material.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SafeChecker {

    //boolean topBlockSafe = true;
    //boolean bottomBlockSafe = true;
    //boolean blockBelowSafe = true;


    public boolean checkIfSafe(Location loc) {

        /*System.out.println(loc.getBlock().getType());


        ArrayList<Material> blocksBelowFeet = new ArrayList<Material>();
        blocksBelowFeet.add(Material.CHEST);
        blocksBelowFeet.add(Material.SOUL_SAND);
        blocksBelowFeet.add(Material.FARMLAND);
        blocksBelowFeet.add(Material.GRASS_PATH);
        blocksBelowFeet.add(Material.HOPPER);
        blocksBelowFeet.add(Material.ENCHANTING_TABLE);
        blocksBelowFeet.add(Material.LECTERN);
        blocksBelowFeet.add(Material.STONECUTTER);


        ArrayList<Material> blockStandingIn = new ArrayList<Material>();
        blockStandingIn.add(Material.IRON_BARS);*/

        //<editor-fold desc="temp">
        Location topBlockLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());
        Location bottomBlockLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        Location blockBelowLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ());

        Material topBlock = topBlockLoc.getBlock().getType();
        Material bottomBlock = bottomBlockLoc.getBlock().getType();
        Material blockBelow = blockBelowLoc.getBlock().getType();

        System.out.println(blockBelow);

        //</editor-fold>
        if(blockBelow != LAVA){
            if(topBlock == LAVA || bottomBlock == LAVA){
                return false;
            } else if (bottomBlock == SOUL_SAND && !topBlock.isOccluding()){
                return true;
            } else if (!topBlock.isOccluding() && !bottomBlock.isOccluding()){
                return true;
            }
        } else {
            return false;
        }
        return false;


        /*if(!blockStandingIn.contains(topBlock) || )
            topBlockSafe = false;

        if(!blockStandingIn.contains(bottomBlock))
            bottomBlockSafe = false;

        if(!blockBelow.isSolid() || !blocksBelowFeet.contains(blockBelow) || blockBelow == Material.LAVA)
            blockBelowSafe = false;


        return topBlockSafe && bottomBlockSafe && blockBelowSafe;*/

    }

}
