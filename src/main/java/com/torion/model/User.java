package com.torion.model;

public record User (int user_id, String full_name, String username, String password_hash, String role, String status) {
    @Override
    public String toString(){
        return String.format("[%d] %s %s [%s] (%s) - Status: %s", user_id, full_name, username, password_hash, role, status);

    }
}