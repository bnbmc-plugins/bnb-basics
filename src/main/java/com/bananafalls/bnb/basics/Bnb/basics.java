package com.bananafalls.bnb.basics.Bnb;

import com.bananafalls.bnb.basics.Bnb.Chat.ChatListener;
import com.bananafalls.bnb.basics.Bnb.Home.Delhome;
import com.bananafalls.bnb.basics.Bnb.Home.ListHomes;
import com.bananafalls.bnb.basics.Bnb.Home.Sethome;
import com.bananafalls.bnb.basics.Bnb.Home.TeleportHome;
import com.bananafalls.bnb.basics.Bnb.Warp.*;
import com.bananafalls.bnb.basics.Bnb.back.BackManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.Objects;

public class basics extends JavaPlugin {

    private Connection connection;

    public static basics plugin;

    public static BackManager backManager = new BackManager();
    public static SafeChecker safeChecker = new SafeChecker();

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;

        Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                mysqlSetup();
            }
        });

        // Warp Commands
        Objects.requireNonNull(this.getCommand("setwarp")).setExecutor(new Setwarp());
        Objects.requireNonNull(this.getCommand("warp")).setExecutor(new TeleportWarp());
        Objects.requireNonNull(this.getCommand("delwarp")).setExecutor(new Delwarp());
        Objects.requireNonNull(this.getCommand("warps")).setExecutor(new ListWarps());

        // Home Commands
        Objects.requireNonNull(this.getCommand("sethome")).setExecutor(new Sethome());
        Objects.requireNonNull(this.getCommand("delhome")).setExecutor(new Delhome());
        Objects.requireNonNull(this.getCommand("home")).setExecutor(new TeleportHome());
        Objects.requireNonNull(this.getCommand("homes")).setExecutor(new ListHomes());
        Objects.requireNonNull(this.getCommand("back")).setExecutor(new BackManager());

        Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SignWarp(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BackManager(), this);
    }

    private void registerCommands() {

    }

    @Override
    public void onDisable() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Plugin shutdown logic
    }

    public void mysqlSetup(){

        try {
            synchronized (this){
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }

                setConnection(DriverManager.getConnection("jdbc:sqlite:basics.db"));

                Statement warpStatement = this.getConnection().createStatement();
                warpStatement.executeUpdate(

                        "CREATE TABLE IF NOT EXISTS warps (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "name VARCHAR(255)," +
                                "x INTEGER," +
                                "y INTEGER," +
                                "z INTEGER," +
                                "yaw FLOAT," +
                                "pitch FLOAT," +
                                "world VARCHAR(255));"
                );

                Statement homeStatement = this.getConnection().createStatement();
                homeStatement.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS homes (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "name VARCHAR(255)," +
                                "x INTEGER," +
                                "y INTEGER," +
                                "z INTEGER," +
                                "yaw FLOAT," +
                                "pitch FLOAT," +
                                "world VARCHAR(255)," +
                                "owner VARCHAR(36));"
                );

                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "SQLite Connected Successfully!");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
