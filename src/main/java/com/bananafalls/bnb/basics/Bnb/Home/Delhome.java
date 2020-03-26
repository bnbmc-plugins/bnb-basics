package com.bananafalls.bnb.basics.Bnb.Home;

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
import java.util.UUID;

public class Delhome implements CommandExecutor {

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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (p.hasPermission("bnb.homes.delete")) {
            if (args.length != 0) {
                UUID owner = p.getUniqueId();
                String name = args[0];

                if (ec.homeExists(owner, name)) {
                    // Access the database asynchronously
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                PreparedStatement statement = plugin.getConnection().prepareStatement(
                                        "DELETE FROM homes WHERE name =? AND owner =?");

                                statement.setString(1, name);
                                statement.setString(2, owner.toString());

                                statement.executeUpdate();
                                p.sendMessage(green + bold + "DELETED!" + green + " Home '" + name.toLowerCase() + "' was deleted!");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    p.sendMessage(errors[new Random().nextInt(errors.length)] + "You do not have a home called '" + args[0].toLowerCase() + "'!");
                }
            } else {
                p.sendMessage(errors[new Random().nextInt(errors.length)] + "You need to enter a home name!");
            }
        } else {
            p.sendMessage(red + bold + "NO PERMISSION! " + red + "You do not have permission to delete homes!");
        }


        return false;

    }
}
