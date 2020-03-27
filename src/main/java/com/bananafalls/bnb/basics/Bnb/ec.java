package com.bananafalls.bnb.basics.Bnb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ec {

    basics plugin = basics.getPlugin(basics.class);

    // Check if warp exists
    public boolean warpExists(String name){
        try {
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM warps WHERE name=?");
            statement.setString(1, name.toLowerCase());

            ResultSet results = statement.executeQuery();
            if(results.next()){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Check if home exists
    public boolean homeExists(UUID owner, String name){
        try {
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM homes WHERE owner=? AND name=?");
            statement.setString(1, owner.toString());
            statement.setString(2, name.toLowerCase());

            ResultSet results = statement.executeQuery();
            if(results.next()){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
