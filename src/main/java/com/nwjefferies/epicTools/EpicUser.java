package com.nwjefferies.epicTools;

import com.xilixir.fortniteapi.v2.Configuration;
import com.xilixir.fortniteapi.v2.Credentials;
import com.xilixir.fortniteapi.v2.Epic.EpicLookup;
import com.xilixir.fortniteapi.v2.FortniteAPI;
import com.xilixir.fortniteapi.v2.Stats;

import java.io.IOException;

public class EpicUser {

    private static FortniteAPI api;
    static{
        initializeAPI();
    }
    private String displayName;
    private Stats stats;
    public EpicUser(String username) {
        try {
            EpicLookup lookup = api.getUserInfo(username);
            this.stats = api.getStats(lookup.getId());
            this.displayName = lookup.getDisplayName();
        }
        catch(IOException e) {
            stats = null;
        }
    }

    private static void initializeAPI() {
        Configuration login = new Configuration("epic_login", Credentials.class);
        Credentials credentials = login.read();
        api = new FortniteAPI(credentials);
        try {
            api.authenticate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidUser() {
        if(stats == null) {
            return false;
        }
        return true;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Stats getStats() {
        return stats;
    }

}