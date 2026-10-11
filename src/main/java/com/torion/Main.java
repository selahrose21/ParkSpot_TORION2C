package com.torion;

import com.torion.dao.DatabaseConnection;
import com.torion.dao.UserDao;
import com.torion.model.User;
import java.util.Scanner;

public class Main {
    private static final UserDao userDao = new UserDao();
    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();

        while (true) {
            if (currentUser == null) {
                showGuestMenu();
            } else {
                showSessionMenu();
            }
        }
    }

    private static void showGuestMenu() {
        System.out.println("\n");
        System.out.println("==================================================");
        System.out.println("         PARKSPOT - MAIN MENU                     ");
        System.out.println("==================================================");
        System.out.println("  [1] Register New Account");
        System.out.println("  [2] Login to Portal");
        System.out.println("  [3] Exit Application");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice (1-3): ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1" -> handleRegister();
            case "2" -> handleLogin();
            case "3" -> {
                System.out.println("\nThank you for using ParkSpot. Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please select 1, 2, or 3.");
        }
    }

    private static void handleRegister() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("             ACCOUNT REGISTRATION                 ");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter Full Name: ");
        String fullName = scanner.nextLine().trim();

        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        System.out.println("\nSelect Role:");
        System.out.println("  [1] TEACHER");
        System.out.println("  [2] STUDENT");
        System.out.print("Enter role choice: ");
        String roleChoice = scanner.nextLine().trim();

        String role = switch (roleChoice) {
            case "1" -> "TEACHER";
            case "2" -> "STUDENT";
            default -> null;
        };

        if (role == null) {
            System.out.println("Invalid role selection. Registration cancelled.");
            return;
        }

        boolean success = userDao.registerUser(fullName, username, password, role);
        if (success) {
            System.out.println("Registration successful! Status is set to PENDING.");
        } else {
            System.out.println("Registration failed (Username may already exist).");
        }
    }

    private static void handleLogin() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("                 USER LOGIN                       ");
        System.out.println("--------------------------------------------------");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        User user = userDao.loginUser(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("\nLogin successful! Welcome back, " + currentUser.username() + "!");
            System.out.println("Role: " + currentUser.role() + " | Status: " + currentUser.status());
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void showSessionMenu() {
        System.out.println("\n");
        System.out.println("==================================================");
        System.out.println("         DASHBOARD [" + currentUser.role() + "]   ");
        System.out.println("==================================================");
        System.out.println("  [1] View Profile Details");
        System.out.println("  [2] Logout");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice (1-2): ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1" -> {
                System.out.println("\n--------------------------------------------------");
                System.out.println("                 USER PROFILE                     ");
                System.out.println("--------------------------------------------------");
                System.out.println("  User ID    : " + currentUser.user_id());
                System.out.println("  Full Name  : " + currentUser.full_name());
                System.out.println("  Username   : " + currentUser.username());
                System.out.println("  Role       : " + currentUser.role());
                System.out.println("  Status     : " + currentUser.status());
                System.out.println("--------------------------------------------------");
            }
            case "2" -> {
                System.out.println("\nLogged out successfully. Returning to Main Menu.");
                currentUser = null;
            }
            default -> System.out.println("Invalid choice. Please select 1 or 2.");
        }
    }
}