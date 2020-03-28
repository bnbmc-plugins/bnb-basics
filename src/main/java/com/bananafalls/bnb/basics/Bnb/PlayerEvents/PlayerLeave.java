package com.bananafalls.bnb.basics.Bnb.PlayerEvents;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        e.setQuitMessage(ChatColor.RED + player.getDisplayName() + " has left.");

    }

}
