package com.bananafalls.bnb.basics.Bnb;

import org.bukkit.Location;
import org.bukkit.Material;

public class SafeChecker {

    public boolean checkIfSafe(Location loc) {

        Location topBlock = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());
        Location blockBelow = new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ());

        System.out.println(topBlock.getY() + " < block top (head)");
        System.out.println(blockBelow.getY() + " < block below");
        System.out.println(loc.getY() + " < block bottom (feet)");

        System.out.println(topBlock.getBlock().getType() + " top block type");
        System.out.println(loc.getBlock().getType() + " below block type");
        System.out.println(blockBelow.getBlock().getType() + " block under feet type");

        if (loc.getBlock().getType().isSolid() || topBlock.getBlock().getType().isSolid()) {
            return false;
        } else {
            if (loc.getBlock().getType() == Material.LAVA || topBlock.getBlock().getType() == Material.LAVA || blockBelow.getBlock().getType() == Material.LAVA || blockBelow.getBlock().getType() == Material.CACTUS) {
                return false;
            } else {
                return true;
            }
        }

    }

}
