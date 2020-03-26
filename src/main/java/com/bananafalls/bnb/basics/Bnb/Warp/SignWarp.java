package com.bananafalls.bnb.basics.Bnb.Warp;

import com.bananafalls.bnb.basics.Bnb.basics;
import com.bananafalls.bnb.basics.Bnb.ec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class SignWarp implements Listener {

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

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        BlockState state;
        try{
            state = b.getState();
        } catch (NullPointerException error){
            return;
        }

        Action a = e.getAction();

        if(a == Action.RIGHT_CLICK_BLOCK){

            if (state instanceof Sign) {
                Sign sign = (Sign) state;
                if (sign.getLine(0).equals(ChatColor.DARK_BLUE + "[Warp]")) {
                    if(p.hasPermission("bnb.warps.signs.use")) {
                        String warpName = sign.getLine(1);
                        if (ec.warpExists(warpName)) {
                            // Access the database asynchronously
                            Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT x, y, z, yaw, pitch, world FROM warps where name=?");
                                        statement.setString(1, warpName.toLowerCase());
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
                                                p.sendMessage(green + bold + "WARPED!" + green + " You have been sent to the '" + warpName + "' warp!");
                                            }
                                        });

                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } else {
                            p.sendMessage(errors[new Random().nextInt(errors.length)] + "The warp '" + warpName.toLowerCase() + "' does not exist! Please tell an administrator right away!");
                        }
                    } else {
                        p.sendMessage(red + bold + "NO PERMISSION! " + red + "You do not have permission to use warp signs!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e){
        Player p = e.getPlayer();
        BlockState state = e.getBlock().getState();

        if(state instanceof Sign){

            if(e.getLine(0).equals("[Warp]")){
                if(p.hasPermission("bnb.warps.sign.create")){
                    if(!e.getLine(1).isEmpty()) {
                            if(ec.warpExists(e.getLine(1))) {
                                e.setLine(0, ChatColor.DARK_BLUE + "[Warp]");
                                p.sendMessage(green + bold + "WARPED!" + green + " The warp sign to '" + e.getLine(1).toLowerCase() + "' has been created!");
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(errors[new Random().nextInt(errors.length)] + "The warp '" + e.getLine(1).toLowerCase() + "' does not exist!"); }
                    } else {
                        p.sendMessage(red + bold + "BLANK! " + red + "You must put a warp name on the second line!");
                    }
                } else {
                    p.sendMessage(red + bold + "NO PERMISSION!" + red + " You do not have permission to make warp signs!");
                    e.setCancelled(true);
                }
            }
        }
    }



    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        Material b = e.getBlock().getType();
        BlockState state = e.getBlock().getState();

        if(state instanceof Sign){
            if(((Sign) state).getLine(0).equals(ChatColor.DARK_BLUE + "[Warp]")){
                if(p.hasPermission("bnb.warps.sign.break")){
                    return;
                } else {
                    e.setCancelled(true);
                    p.sendMessage(red + bold + "NO PERMISSION!" + red + " You do not have permission to break warp signs! If you" +
                            " need this removed, please contact an administrator.");
                }

            }
        }

    }

}
