package com.bananafalls.bnb.basics.Bnb.Warp;

import com.bananafalls.bnb.basics.Bnb.basics;
import com.bananafalls.bnb.basics.Bnb.ec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class Delwarp implements CommandExecutor {
    basics plugin = basics.getPlugin(basics.class);

    com.bananafalls.bnb.basics.Bnb.ec ec = new ec();

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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { Player p = (Player) sender;


        if(p.hasPermission("bnb.warps.delete")) {
            if (args.length != 0) {
                String name = args[0].toLowerCase();
                if (ec.warpExists(name)) { // If the name exists in the DB
                    // Access the database asynchronously
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                PreparedStatement statement = plugin.getConnection().prepareStatement(
                                        "DELETE FROM warps WHERE name =?");

                                statement.setString(1, name);

                                statement.executeUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    p.sendMessage(green + bold + "SUCCESS!" + green + " Deleted warp '" + name + "'!");

                } else {
                    p.sendMessage(errors[new Random().nextInt(errors.length)] + "The warp '" + name + "' does not exist!");
                }

            } else {
                p.sendMessage(errors[new Random().nextInt(errors.length)] + "You need to enter a warp name!");
            }
        } else {
            p.sendMessage(red + bold + "NO PERMISSION! " + red + "You do not have permission to delete warps!");
        }


        return false;
    }
}


