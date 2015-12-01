package com.brynhildr.asgard.global;

/**
 * Created by lqshan on 11/30/15.
 */
public class SimplifiedUserAuthentication {
    private static boolean isLoggedIn = false;
    private static String username = null;

    private static void setUsername(String username) {
        SimplifiedUserAuthentication.username = username;
    }

    public static String getUsername() {
        if (isLoggedIn)
            return username;
        else return "You have not logged in.";
    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static boolean login(String username, String pwd) {
        if (isLoggedIn) // You have already logged in.
            return false;
        /**
         *  TODO: Send Post request to server
         */

        if (true) { // Login succeeded
            setUsername(username);
            isLoggedIn = true;
        } else {
            // TODO: login failed;
        }
        return true;
    }

    public static void logout() {
        isLoggedIn = false;
        username = null;
    }
}
