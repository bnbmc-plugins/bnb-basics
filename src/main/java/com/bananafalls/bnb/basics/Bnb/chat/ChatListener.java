package com.bananafalls.bnb.basics.Bnb.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.getRecipients().clear();

        String message = e.getMessage();
        Player author = e.getPlayer();

        if (author.isOp()) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[M] " + ChatColor.RED + author.getDisplayName() + ChatColor.WHITE + " » " + message);
        } else {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[M] " + ChatColor.WHITE + author.getDisplayName() + " » " + message);
        }


    }
}
