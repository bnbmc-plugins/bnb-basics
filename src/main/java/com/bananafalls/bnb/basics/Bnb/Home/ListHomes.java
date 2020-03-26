package com.bananafalls.bnb.basics.Bnb.Home;

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
import java.util.UUID;

public class ListHomes implements CommandExecutor {

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

        if (p.hasPermission("bnb.homes.list")){
            if(!listHomes(p.getUniqueId()).isEmpty()){
                p.sendMessage(successes[new Random().nextInt(successes.length)] + "Your available homes are: " + listHomes(p.getUniqueId()).toString().substring(1, listHomes(p.getUniqueId()).toString().length()-1).toLowerCase());
            } else {
                p.sendMessage(errors[new Random().nextInt(errors.length)] + "You have no homes :( Set one with /sethome!");
            }

        } else {
            p.sendMessage(red + bold + "NO PERMISSION! " + red + "You do not have permission to list homes!");
        }

        return false;
    }

    public List listHomes(UUID owner){

        List<String> homes = new ArrayList<>();

        try {
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT name FROM homes WHERE owner =?");
            statement.setString(1, owner.toString());

            ResultSet rs = statement.executeQuery();


            while (rs.next()) {
                homes.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return homes;

    }

}
