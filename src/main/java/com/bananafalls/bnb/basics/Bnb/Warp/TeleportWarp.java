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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class TeleportWarp implements CommandExecutor {

    basics plugin = basics.getPlugin(basics.class);
    com.bananafalls.bnb.basics.Bnb.ec ec = new ec();

    //<editor-fold desc="Colour Variables">
    String red = ChatColor.RED + "";
    String green = ChatColor.GREEN + "";
    String bold = ChatColor.BOLD + "";
    String yellow = ChatColor.YELLOW + "";
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

        if(p.hasPermission("bnb.warps.warp")){
            if(args.length == 0){
                p.sendMessage(errors[new Random().nextInt(errors.length)] + "You need to enter a warp name!");
            } else {
                String name = args[0].toLowerCase();

                if(ec.warpExists(name)){
                    // Access the database asynchronously
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT x, y, z, yaw, pitch, world FROM warps where name=?");
                                statement.setString(1, name.toLowerCase());
                                ResultSet rs = statement.executeQuery();

                                double x = rs.getDouble(1);
                                double y = rs.getDouble(2);
                                double z = rs.getDouble(3);
                                float pitch = rs.getFloat(4);
                                float yaw = rs.getFloat(5);
                                String world = rs.getString(6);

                                Bukkit.getScheduler().runTask(plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        Location warpLocation = new Location(Bukkit.getWorld(world), x, y, z, pitch, yaw);
                                        if(basics.safeChecker.checkIfSafe(warpLocation)){
                                            p.teleport(warpLocation);
                                            p.sendMessage(green + bold + "WARPED!" + green + " You have been sent to the '" + name + "' warp!");
                                        } else {
                                            if(p.hasPermission("bnb.warps.unsafebypass")){
                                                p.teleport(warpLocation);
                                                p.sendMessage(yellow + bold + "WARNING!" + yellow + " The warp you just teleported to is unsafe! Normal players will not be able to teleport here. Please delete and re-set this warp.");
                                            } else {
                                                p.sendMessage(red + bold + "UNSAFE!" + red + " That warp is unsafe to teleport to! Please inform a staff member immediately.");
                                            }

                                        }

                                    }
                                });

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else { p.sendMessage(errors[new Random().nextInt(errors.length)] + "The warp '" + args[0].toLowerCase() + "' does not exist!"); }

            }
        } else {
            p.sendMessage(red + bold + "NO PERMISSION! " + red + "You do not have permission to use warps!");
        }


        return false;
    }


}