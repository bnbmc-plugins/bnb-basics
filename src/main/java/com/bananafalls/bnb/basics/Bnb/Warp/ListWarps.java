package com.bananafalls.bnb.basics.Bnb.Warp;

import com.bananafalls.bnb.basics.Bnb.basics;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListWarps implements CommandExecutor {

    //<editor-fold desc="Colour Variables">
    String red = ChatColor.RED + "";
    String green = ChatColor.GREEN + "";
    String bold = ChatColor.BOLD + "";
    String italics = ChatColor.ITALIC + "";
    //</editor-fold>

    //<editor-fold desc="Error Messages">
    String[] errors = {
            red + bold + "AW SHUCKS! " + red,
            red + bold + "OOPS! " + red,
            red + bold + "ERROR! " + red,
            red + bold + "FAIL! " + red,
            red + bold + "UH OH! " + red,
            red + bold + "WHOOPS! " + red,
            red + bold + "NOPE! " + red
    };
    //</editor-fold>

    //<editor-fold desc="Success Messages">
    String[] successes = {
            green + bold + "A LIST! " + green,
            green + bold + "WARPS! " + green,
            green + bold + "ITEMS! " + green,
            green + bold + "AN ARRAY! " + green,
            green + bold + "LOCATIONS! " + green
    };
    //</editor-fold>

    basics plugin = basics.getPlugin(basics.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { Player p = (Player) sender;

        if (p.hasPermission("bnb.warps.list")){
            if(!listWarps().isEmpty()){
                p.sendMessage(successes[new Random().nextInt(successes.length)] + "The available warps are: " + listWarps().toString().substring(1, listWarps().toString().length()-1).toLowerCase());
            } else {
                p.sendMessage(errors[new Random().nextInt(errors.length)] + "There are no available warps :(");
            }

        } else {
            p.sendMessage(red + bold + "NO PERMISSION! " + red + "You do not have permission to list warps!");
        }

        return false;
    }

    public List listWarps(){

        List<String> warps = new ArrayList<>();
        try {
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT name FROM warps");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                warps.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warps;

    }


}
