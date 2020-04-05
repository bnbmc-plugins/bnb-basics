package com.bananafalls.bnb.basics.Bnb.home;

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
import java.util.UUID;

public class Sethome implements CommandExecutor {

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

        System.out.println(p.getLocation());

        if(p.hasPermission("bnb.homes.set")) {
            if (args.length != 0) {
                if(args.length == 2 && args[1].equalsIgnoreCase("o")){
                    Location l = p.getLocation();
                    //<editor-fold desc="Home Data Variables">
                    String name = args[0].toLowerCase();
                    double x = l.getX();
                    double y = l.getY();
                    double z = l.getZ();
                    float yaw = l.getYaw();
                    float pitch = l.getPitch();
                    String world = l.getWorld().getName();
                    UUID owner = p.getUniqueId();
                    //</editor-fold>

                    // Access the database asynchronously
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                PreparedStatement deleteStatement = plugin.getConnection().prepareStatement(
                                        "DELETE FROM homes WHERE name=? AND owner=?"
                                );
                                deleteStatement.setString(1, name.toLowerCase());
                                deleteStatement.setString(2, owner.toString());

                                deleteStatement.executeUpdate();

                                PreparedStatement statement = plugin.getConnection().prepareStatement(
                                        "INSERT INTO homes (name, x, y, z, yaw, pitch, world, owner) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

                                //<editor-fold desc="Statement Variables">
                                statement.setString(1, name.toLowerCase());
                                statement.setDouble(2, x);
                                statement.setDouble(3, y);
                                statement.setDouble(4, z);
                                statement.setFloat(5, yaw);
                                statement.setFloat(6, pitch);
                                statement.setString(7, world);
                                statement.setString(8, owner.toString());
                                //</editor-fold>

                                statement.executeUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    p.sendMessage(green + bold + "SUCCESS!" + green + " Replaced home '" + name + "'!");
                } else {
                    if (!ec.homeExists(p.getUniqueId(), args[0])) { // If the name exists in the DB
                        Location l = p.getLocation();
                        //<editor-fold desc="Home Data Variables">
                        String name = args[0].toLowerCase();
                        double x = l.getX();
                        double y = l.getY();
                        double z = l.getZ();
                        float yaw = l.getYaw();
                        float pitch = l.getPitch();
                        String world = l.getWorld().getName();
                        UUID owner = p.getUniqueId();
                        //</editor-fold>

                        // Access the database asynchronously
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    PreparedStatement statement = plugin.getConnection().prepareStatement(
                                            "INSERT INTO homes (name, x, y, z, yaw, pitch, world, owner) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

                                    //<editor-fold desc="Statement Variables">
                                    statement.setString(1, name.toLowerCase());
                                    statement.setDouble(2, x);
                                    statement.setDouble(3, y);
                                    statement.setDouble(4, z);
                                    statement.setFloat(5, yaw);
                                    statement.setFloat(6, pitch);
                                    statement.setString(7, world);
                                    statement.setString(8, owner.toString());
                                    //</editor-fold>

                                    statement.executeUpdate();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        p.sendMessage(green + bold + "SUCCESS!" + green + " Created home '" + name + "'!");
                    } else {
                        p.sendMessage(errors[new Random().nextInt(errors.length)] + "You already have a home called '" + args[0].toLowerCase() + "'! Do \"/sethome [home] o\" to override it.");
                    }
                }






            } else {
                if (ec.homeExists(p.getUniqueId(), "home")) {
                    p.sendMessage(errors[new Random().nextInt(errors.length)] + "You already have a home called 'home'!");
                } else {

                    Location l = p.getLocation();
                    double x = l.getX();
                    double y = l.getY();
                    double z = l.getZ();
                    float yaw = l.getYaw();
                    float pitch = l.getPitch();
                    String world = l.getWorld().getName();
                    UUID owner = p.getUniqueId();

                    // Access the database asynchronously
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                PreparedStatement statement = plugin.getConnection().prepareStatement(
                                        "INSERT INTO homes (name, x, y, z, yaw, pitch, world, owner) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

                                //<editor-fold desc="Statement Variables">
                                statement.setString(1, "home");
                                statement.setDouble(2, x);
                                statement.setDouble(3, y);
                                statement.setDouble(4, z);
                                statement.setFloat(5, yaw);
                                statement.setFloat(6, pitch);
                                statement.setString(7, world);
                                statement.setString(8, owner.toString());
                                //</editor-fold>

                                statement.executeUpdate();

                                p.sendMessage(green + bold + "SUCCESS!" + green + " Set your home to this location!");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        } else {
            p.sendMessage(red + bold + "NO PERMISSION! " + red + "You do not have permission to set homes!");
        }

        return false;
    }
}
