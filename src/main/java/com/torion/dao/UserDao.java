package com.torion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    public static boolean registerUser(String full_name, String username, String password_hash, String role) {

        String sql = "INSERT INTO User(full_name, username, password_hash, role, status) VALUES (?, ?, ?, ?, 'PENDING')";

        try(Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, full_name);
            pstmt.setString(2, username);
            pstmt.setString(3, password_hash);
            pstmt.setString(4, role.toUpperCase());
            pstmt.executeUpdate();
            System.out.println("-> Successful Registration, Waiting for Approval!");
            return true;

        } catch (SQLException e){
            System.err.println("Failed: "+ e.getMessage());
            return false;
        }
    }
}
