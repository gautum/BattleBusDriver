package com.nwjefferies.battleBusDriver.databaseConnection;

public class LinkedUserLookup {

    private long guild_id;
    private long discord_id;
    private String display_name;

    LinkedUserLookup(long guild_id, long discord_id, String display_name) {
        this.guild_id = guild_id;
        this.discord_id = discord_id;
        this.display_name = display_name;
    }

    public long getGuild_id() {
        return guild_id;
    }

    public long getDiscord_id() {
        return discord_id;
    }

    public String getDisplay_name() {
        return display_name;
    }
}
