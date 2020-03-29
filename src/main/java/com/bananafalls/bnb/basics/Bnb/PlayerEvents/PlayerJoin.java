package com.bananafalls.bnb.basics.Bnb.PlayerEvents;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerJoin implements Listener {

    public HashMap<UUID, Boolean> hasPlayed = new HashMap<UUID, Boolean>();
    TextComponent loginMessage = new TextComponent();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();



        e.setJoinMessage(ChatColor.GREEN + p.getDisplayName() + " has joined.");

        sendReminder(p);

    }

    // Don't worry about the T H I C C string, I'm removing it in a build in a few days.
    public void sendReminder(Player p){
        if(hasPlayed.get(p.getUniqueId()) == null){
            p.sendMessage(ChatColor.YELLOW + "Hey! This is Max, one of the main developers for the bits and Bytes Minecraft server. In order for us to fix bugs and create fun new features, we rely on bug reports! The difference between a good and bad report means the world, so when reporting a bug, please include what happened, steps to reproduce it, what should happen, and any other information you think is needed. Thank you! " + ChatColor.RED + "<3" + ChatColor.GRAY + "" + ChatColor.ITALIC + " This message will only show very occasionally on login, and will be removed soon. :)");
            hasPlayed.put(p.getUniqueId(), true);
        }
    }
}
