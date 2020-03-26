package com.bananafalls.bnb.basics.Bnb.Warp;

import com.bananafalls.bnb.basics.Bnb.basics;
import com.bananafalls.bnb.basics.Bnb.ec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class Setwarp implements CommandExecutor {

    com.bananafalls.bnb.basics.Bnb.ec ec = new ec();

    basics plugin = basics.getPlugin(basics.class);

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
        if(p.hasPermission("bnb.warps.set")) {
            if(args.length != 0){
                    if(!ec.warpExists(args[0])) { // If the name exists in the DB
                        Location l = p.getLocation();
                        //<editor-fold desc="Warp Data Variables">
                        String name = args[0].toLowerCase();
                        double x = l.getX();
                        double y = l.getY();
                        double z = l.getZ();
                        float yaw = l.getYaw();
                        float pitch = l.getPitch();
                        String world = l.getWorld().getName();
                        //</editor-fold>

                        // Access the database asynchronously
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    PreparedStatement statement = plugin.getConnection().prepareStatement(
                                            "INSERT INTO warps (name, x, y, z, yaw, pitch, world) VALUES(?, ?, ?, ?, ?, ?, ?)");

                                    //<editor-fold desc="Statement Variables">
                                    statement.setString(1, name.toLowerCase());
                                    statement.setDouble(2, x);
                                    statement.setDouble(3, y);
                                    statement.setDouble(4, z);
                                    statement.setFloat(5, yaw);
                                    statement.setFloat(6, pitch);
                                    statement.setString(7, world);
                                    //</editor-fold>

                                    statement.executeUpdate();
                                } catch (SQLException e){
                                    e.printStackTrace();
                                }
                            }
                        });

                        p.sendMessage(green + bold + "SUCCESS!" + green + " Created warp '" + name + "'!");
                    } else { p.sendMessage(errors[new Random().nextInt(errors.length)] + "The warp '" + args[0].toLowerCase() + "' already exists!"); }



            } else {
                p.sendMessage(errors[new Random().nextInt(errors.length)] + "You need to enter a warp name!");
            }
        } else {
            p.sendMessage(red + bold + "NO PERMISSION! " + red + "You do not have permission to set warps!");
        }


        return false;
    }

}
