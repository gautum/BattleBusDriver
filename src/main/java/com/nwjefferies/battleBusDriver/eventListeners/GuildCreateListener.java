package com.nwjefferies.battleBusDriver.eventListeners;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.obj.IGuild;

import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

public class GuildCreateListener implements IListener<GuildCreateEvent> {

    private IDiscordClient client;
    DatabaseLookupService databaseLookupService;

    public GuildCreateListener(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        this.client = client;
        this.databaseLookupService = databaseLookupService;
    }

    @Override
    public void handle(GuildCreateEvent event) {
        addGuild(event.getGuild());
    }

    private void addGuild(IGuild g) {
        if(databaseLookupService.addGuild(g.getLongID(), g.getChannels().get(0).getLongID())) {
            System.out.println("[" + Calendar.getInstance().getTime() + "][GuildCreateListener] New guild added: " + g.getLongID());
        }
        else {
            System.out.println("[" + Calendar.getInstance().getTime() + "][GuildCreateListener] New guild not added: " + g.getLongID());
        }
    }

}
