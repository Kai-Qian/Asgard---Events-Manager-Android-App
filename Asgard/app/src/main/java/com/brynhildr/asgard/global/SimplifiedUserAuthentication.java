package com.brynhildr.asgard.global;

import com.brynhildr.asgard.local.AuthenticationWithRemote;

/**
 * Created by lqshan on 11/30/15.
 */
public class SimplifiedUserAuthentication {
    private static boolean isLoggedIn = false;
    private static String username;

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
        boolean loginSucceeded = false;
        try {
            loginSucceeded = new AuthenticationWithRemote().execute(username, pwd).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (loginSucceeded) { // Login succeeded
            setUsername(username);
            isLoggedIn = true;
        } else {
            return false;
        }
        return true;
    }

    public static void logout() {
        isLoggedIn = false;
        username = null;
    }
}
