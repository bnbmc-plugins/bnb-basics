package com.bananafalls.bnb.basics.Bnb.back;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Back implements CommandExecutor {

    //<editor-fold desc="Colour Variables">
    String red = ChatColor.RED + "";
    String green = ChatColor.GREEN + "";
    String bold = ChatColor.BOLD + "";
    String italics = ChatColor.ITALIC + "";
    //</editor-fold>

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if(TeleportListener.locations.get(p.getUniqueId()) == null){
            p.sendMessage(red + bold + "NO LOCATION!" + red + " You have no where to return to!");
        } else {
            p.teleport(TeleportListener.locations.get(p.getUniqueId()));
            p.sendMessage(green + bold + "TELEPORTED!" + green + " Returned to your last location.");
        }


        return false;
    }


}
