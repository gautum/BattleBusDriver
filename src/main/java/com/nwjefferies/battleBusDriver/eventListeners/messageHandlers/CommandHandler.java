package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import sx.blah.discord.api.IDiscordClient;


public abstract class CommandHandler {

    protected IDiscordClient client;
    protected DatabaseLookupService databaseLookupService;

    public CommandHandler(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        this.client = client;
        this.databaseLookupService = databaseLookupService;
    }

    public abstract Response processCommand(Command command);
}
