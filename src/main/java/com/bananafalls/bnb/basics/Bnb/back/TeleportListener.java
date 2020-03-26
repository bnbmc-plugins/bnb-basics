package com.bananafalls.bnb.basics.Bnb.back;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportListener implements Listener {

    public static HashMap<UUID, Location> locations = new HashMap<>();

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        e.setCancelled(true);
        locations.put(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());
        e.setCancelled(false);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        locations.put(e.getEntity().getUniqueId(), e.getEntity().getLocation());
    }

}
