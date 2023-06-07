package com.riis.context;

public class UserContext {
    private static UserContext instance;
    private String username;
    private String lastlogin;

    private UserContext() {
    }

    public static UserContext getInstance() {
        if (instance == null) {
            synchronized (UserContext.class) {
                if (instance == null) {
                    instance = new UserContext();
                }
            }
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getLastlogin() {
        return lastlogin;
    }
}