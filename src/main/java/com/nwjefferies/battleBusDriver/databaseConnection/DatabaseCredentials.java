package com.nwjefferies.battleBusDriver.databaseConnection;

public class DatabaseCredentials {
    private String username;
    private String password;
    private String dbUrl;
    public DatabaseCredentials(String username, String password, String dbUrl) {
        this.username = username;
        this.password = password;
        this.dbUrl = dbUrl;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() { return password; }
    public String getDbUrl() { return dbUrl; }
}
