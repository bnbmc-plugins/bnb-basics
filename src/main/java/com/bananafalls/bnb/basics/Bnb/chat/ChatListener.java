package com.bananafalls.bnb.basics.Bnb.chat;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import static org.bukkit.ChatColor.*;
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

        TextComponent prefix = new TextComponent(GOLD + "[M] ");
        TextComponent name = null;
        if(author.isOp()){
            if(!author.getName().equals(author.getDisplayName())){
                name = new TextComponent(RED + author.getDisplayName());
                name.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Real name: " + author.getName() ).create() ) );
            } else {
                name = new TextComponent(RED + author.getDisplayName());
            }
        } else {
            if(!author.getName().equals(author.getDisplayName())){
                name = new TextComponent(WHITE + author.getDisplayName());
                name.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Real name: " + author.getName() ).create() ) );
            } else {
                name = new TextComponent(WHITE + author.getDisplayName());
            }
        }
        TextComponent divider = new TextComponent(WHITE + " » ");
        TextComponent sentMessage = new TextComponent(message);

        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            player.sendMessage(new ComponentBuilder(prefix).append(name).append(divider).append(sentMessage).create());
        }


        /*if (author.isOp()) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[M] " + ChatColor.RED + author.getDisplayName() + ChatColor.WHITE + " » " + message);
        } else {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[M] " + ChatColor.WHITE + author.getDisplayName() + " » " + message);
        }*/


    }
}
