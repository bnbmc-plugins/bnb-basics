package com.bananafalls.bnb.basics.Bnb.chat;

import com.bananafalls.bnb.basics.Bnb.basics;
//import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.connorlinfoot.titleapi.TitleAPI;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_15_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.ChatColor.*;
import static org.bukkit.Sound.BLOCK_NOTE_BLOCK_GUITAR;
import static org.bukkit.Sound.ENTITY_PLAYER_LEVELUP;

public class Broadcast implements CommandExecutor, TabCompleter {

    //<editor-fold desc="Error Messages">
    String[] errors = {
            RED + "" + BOLD + "AW SHUCKS! " + RED,
            RED + "" +  BOLD + "OOPS! " + RED,
            RED + "" + BOLD + "ERROR! " + RED,
            RED + "" + BOLD + "FAIL! " + RED,
            RED + "" + BOLD + "UH OH! " + RED,
            RED + "" + BOLD + "WHOOPS! " + RED,
            RED + "" + BOLD + "NOPE! " + RED
    };
    //</editor-fold>

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if(p.hasPermission("bnb.announce")){
            if(args.length <= 1){
                p.sendMessage(errors[new Random().nextInt(errors.length)] + "Too few arguments! Usage: /broadcast [type] [message]");
            } else if(args[0].equalsIgnoreCase("chat")){
                String message = "";
                for(int i = 1; i < args.length; i++) {
                    message += args[i] + " ";
                }

                sendMessage(p, "chat", message);
            } else if(args[0].equalsIgnoreCase("actionbar")){
                String message = "";
                for(int i = 1; i < args.length; i++) {
                    message += args[i] + " ";
                }

                sendMessage(p, "actionbar", message);
            } else if(args[0].equalsIgnoreCase("title")){
            // Separate subtitle with two ::
                String message = "";
                for(int i = 1; i < args.length; i++) {
                    message += args[i] + " ";
                }
                sendMessage(p, "title", message);
            }
        }
        return false;
    }

    public void sendMessage(Player p, String type, String message){

        switch(type) {
            case "chat":
                for(Player online : Bukkit.getOnlinePlayers()){
                    String messageToSend = YELLOW + "" + BOLD + "BROADCAST! " + WHITE + message;
                    messageToSend = translateAlternateColorCodes('&', messageToSend);
                    online.sendMessage(messageToSend);

                    online.playSound(online.getLocation(), ENTITY_PLAYER_LEVELUP, 1, 0);
                }
                break;
            case "actionbar":
                message = translateAlternateColorCodes('&', message);
                for(Player online : Bukkit.getOnlinePlayers()){
                    ActionBarAPI.sendActionBar(p, message, 200);
                    online.playSound(online.getLocation(), ENTITY_PLAYER_LEVELUP, 1, 0);
                }
                break;
            case "title":
                String title;
                String subtitle;
                try{
                    String[] titleParts = message.split("``", 2);
                    title = titleParts[0];
                    subtitle = titleParts[1];
                } catch (Exception e){
                    title = message;
                    subtitle = "";
                }


                for(Player online : Bukkit.getOnlinePlayers()){
                    TitleAPI.sendTitle(p, 20, 200, 20, title, subtitle);
                    online.playSound(online.getLocation(), ENTITY_PLAYER_LEVELUP, 1, 0);
                }

                break;
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
            List<String> completions = new ArrayList<String>(); completions.add("chat"); completions.add("actionbar"); completions.add("title");
            return completions;
        } else {
            return new ArrayList<String>();
        }
    }

}
