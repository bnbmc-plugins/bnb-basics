package com.bananafalls.bnb.basics.Bnb.playerevents;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.UUID;

public class PlayerJoin implements Listener {

    public HashMap<UUID, Boolean> hasPlayed = new HashMap<UUID, Boolean>();
    TextComponent loginMessage = new TextComponent();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(ChatColor.GREEN + p.getDisplayName() + " has joined.");

        setNameTagColour(p);
        sendReminder(p);

    }

    // Don't worry about the T H I C C string, I'm removing it in a build in a few days.
    public void sendReminder(Player p){
        if(hasPlayed.get(p.getUniqueId()) == null){
            p.sendMessage(ChatColor.YELLOW + "Hey! This is Max, one of the main developers for the bits and Bytes Minecraft server. In order for us to fix bugs and create fun new features, we rely on bug reports! The difference between a good and bad report means the world, so when reporting a bug, please include what happened, steps to reproduce it, what should happen, and any other information you think is needed. Thank you! " + ChatColor.RED + "<3" + ChatColor.GRAY + "" + ChatColor.ITALIC + " This message will only show very occasionally on login, and will be removed soon. :) Public roadmap: https://bit.ly/39Gl1Yh");
            hasPlayed.put(p.getUniqueId(), true);
        }
    }

    Team op;
    public void setNameTagColour(Player p){
        /*if(p.isOp()){
            Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
            sb.getTeam("OP").addEntry();
        }*/
        if(p.isOp()){
            Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
            if(sb.getTeam("OP") == null){
                System.out.println("Does not exist, creating");
                op = sb.registerNewTeam("OP");
                op.setColor(ChatColor.RED);
                System.out.println(op.getColor());
            } else {
                System.out.println("Exists, adding player");
                op = sb.getTeam("OP");
            }
            op.addPlayer(p);


            System.out.println("Player is OP, adding");

        } else {
            op.removePlayer(p);
            System.out.println("Player not OP, removing");
        }
    }
}
