package com.bananafalls.bnb.basics.Bnb.back;

import com.bananafalls.bnb.basics.Bnb.basics;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.UUID;

public class BackManager implements CommandExecutor, Listener {

    public static HashMap<UUID, Location> backLocations = new HashMap<UUID, Location>(); // Create a HashMap to store the location of players when they teleport

    //<editor-fold desc="Colour Variables">
    String red = ChatColor.RED + "";
    String green = ChatColor.GREEN + "";
    String gray = ChatColor.GRAY + "";
    String bold = ChatColor.BOLD + "";
    String italics = ChatColor.ITALIC + "";
    //</editor-fold>

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { Player p = (Player) sender;
        if(backLocations.containsKey(p.getUniqueId())){ // Check if the HashMap contains a location for the player
            if(basics.safeChecker.checkIfSafe(backLocations.get(p.getUniqueId()))){
                p.teleport(backLocations.get(p.getUniqueId())); // If so, teleport them to that location
                backLocations.remove(p.getUniqueId()); // And remove their HashMap entry
                p.sendMessage(green + bold + "TELEPORTED!" + green + " You returned to your previous location!");
            } else {
                p.sendMessage(red + bold + "UNSAFE!" + red + " Your previous location is now unsafe to teleport to!");
            }

        } else { // If the HashMap does not contain an entry for the player, send an error
            p.sendMessage(red + bold + "NO LOCATION!" + red + " You have not teleported anywhere, so you have nowhere to return to!");
        }
        return false;
    }

    // When the player leaves, clear their HashMap
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        backLocations.remove(e.getPlayer().getUniqueId());
    }

    // Whenever the player teleports, set the location they teleported from to an entry in the HashMap.
    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) { Player p = e.getPlayer();
        // Only save the value if the teleport is caused by a plugin or command, this prevents multiple entries being added (as the event fires multiple times per teleport)
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN || e.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
            backLocations.put(p.getUniqueId(), p.getLocation());
        }
    }

    // On death, save the death location to the HashMap.
    @EventHandler
    public void onDeath(PlayerDeathEvent e) { Player p = e.getEntity().getPlayer();
        if(p != null)
            p.sendMessage(gray + italics + "Do /back to return to where you died.");
            backLocations.put(p.getUniqueId(), p.getLocation());
    }

}
