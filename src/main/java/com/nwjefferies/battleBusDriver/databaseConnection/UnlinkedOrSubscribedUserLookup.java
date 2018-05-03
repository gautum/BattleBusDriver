package com.nwjefferies.battleBusDriver.databaseConnection;

public class UnlinkedOrSubscribedUserLookup {

    private long guild_id;
    private String display_name;

    UnlinkedOrSubscribedUserLookup(long guild_id, String display_name) {
        this.guild_id = guild_id;
        this.display_name = display_name;
    }

    public long getGuild_id() {
        return guild_id;
    }

    public String getDisplay_name() {
        return display_name;
    }
}
