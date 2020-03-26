package com.bananafalls.bnb.basics.Bnb.Home;

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
import java.util.Arrays;
import java.util.Random;

public class TeleportHome implements CommandExecutor {

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
        if(p.hasPermission("bnb.homes.teleport")) {
            if (args.length == 0) {
                if (ec.homeExists(p.getUniqueId(), "home")) {
                    p.performCommand("home home");
                } else {
                    p.sendMessage(errors[new Random().nextInt(errors.length)] + "You do not have a home! Set one with /sethome");
                }
            } else {
                String name = args[0].toLowerCase();


                if (ec.homeExists(p.getUniqueId(), name)) {
                    // Access the database asynchronously
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT x, y, z, yaw, pitch, world FROM homes where owner=? AND name=?");
                                statement.setString(1, p.getUniqueId().toString());
                                statement.setString(2, name);
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
                                        p.teleport(new Location(Bukkit.getWorld(world), x, y, z, pitch, yaw));
                                        if (name.equals("home")) {
                                            p.sendMessage(green + bold + "WARPED!" + green + " You have been sent to your home!");
                                        } else {
                                            p.sendMessage(green + bold + "WARPED!" + green + " You have been sent to your home, '" + name + "'!");
                                        }

                                    }
                                });

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    p.sendMessage(errors[new Random().nextInt(errors.length)] + "You do not have a home called '" + args[0].toLowerCase() + "'!");
                }
            }
        } else {
            p.sendMessage(red + bold + "NO PERMISSION! " + red + "You do not have permission to use homes!");
        }

        return false;
    }

}
