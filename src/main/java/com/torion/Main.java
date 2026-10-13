package com.torion;

import com.torion.dao.UserDao;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserDao userDao = new UserDao();

    public static void main (String args[]){

        System.out.println("\n======================================");
        System.out.println("\t   PARKSPOT SYSTEM MENU              ");
        System.out.println("======================================");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        String choice = sc.nextLine();

        switch(choice){
            case "1":
                break;
            case "2":
                System.out.print("Enter full name: ");
                String fname = sc.nextLine();
                System.out.print("Enter username: ");
                String user = sc.nextLine();
                System.out.print("Enter password: ");
                String pass = sc.nextLine();

                System.out.print("Select Role [1] Admin [2] Attendant [3] Customer: ");
                int roleChoice = sc.nextInt();
                String role;
                switch (roleChoice){
                    case 1:
                        role = "ADMIN";
                        break;
                    case 2:
                        role = "ATTENDANT";
                        break;
                    case 3:
                        role = "CUSTOMER";
                        break;
                    default:
                        role = "null";

                }

                if(role != null){

                    UserDao.registerUser(fname, user, pass, role);

                }else{
                    System.out.println("Invalid role selection!");
                }

        }

    }

}