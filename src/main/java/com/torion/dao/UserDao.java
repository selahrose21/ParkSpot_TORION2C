package com.torion.dao;

import java.sql.*;

public class UserDao {

    public UserDao(){
        try(Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement()){
            defaultAdminGenerator(conn);
        }catch(SQLException e){
            System.err.println("Error seeding default admin: " + e.getMessage());
        }
    }

    public void defaultAdminGenerator(Connection conn){
        String checkSql = "SELECT COUNT(*) FROM User WHERE role = 'ADMIN'";
        String insertSql = "INSERT INTO User(fullname, username, password_hash, role, status) VALUES ('adminuser', 'admin', 'admin123', 'ADMIN', 'APPROVED')";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.executeUpdate(insertSql);
                System.out.println("-> Default Admin created (Username: admin | Password: admin123)");
            }
        } catch (SQLException e) {
            System.err.println("Error seeding default admin: " + e.getMessage());
        }
    }

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