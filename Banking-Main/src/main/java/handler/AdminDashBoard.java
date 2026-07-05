package handler;

import auth.Session;

public class AdminDashBoard {

    public void displayAdminDash() {
        while (Session.isLoggedIn()) {
            System.out.println("""
                    ADMIN DASHBOARD
                    1. View Users
                    2. View Transactions
                    3. Freeze Account
                    4. Logout
                  
                    """);
        }
    }
}
