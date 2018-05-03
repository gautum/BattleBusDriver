package com.nwjefferies.battleBusDriver.databaseConnection;

public class GuildLookup {
    private long guild_id;
    private long channel_id;
    GuildLookup(long guild_id, long channel_id) {
        this.guild_id = guild_id;
        this.channel_id = channel_id;
    }

    public long getGuildID() { return guild_id; }
    public long getChannelID() {return channel_id; }

    public String toString() {
        return "guild_id: " + guild_id + "\nchannel_id: " + channel_id;
    }
}
