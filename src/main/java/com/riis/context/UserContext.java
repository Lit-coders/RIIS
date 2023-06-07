package com.riis.context;

public class UserContext {

    private static String username;
    private static String lastLogin;
    private static UserContext instance;

    private UserContext() {

    }

    public static UserContext getInstance() {
        if (instance == null) {
            instance = new UserContext();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        UserContext.username = username;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        UserContext.lastLogin = lastLogin;
    }

}
