package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.TypeOfCommand;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import sx.blah.discord.api.IDiscordClient;

public class CommandHandlerContainer {

    AddHandler addHandler;
    ChannelHandler channelHandler;
    HelpHandler helpHandler;
    LeadersHandler leadersHandler;
    LinkHandler linkHandler;
    RemoveHandler removeHandler;
    StatsHandler statsHandler;
    SubscribeHandler subscribeHandler;
    UnlinkHandler unlinkHandler;
    UnsubscribeHandler unsubscribeHandler;

    public CommandHandlerContainer(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        addHandler = new AddHandler(client, databaseLookupService);
        channelHandler = new ChannelHandler(client, databaseLookupService);
        helpHandler = new HelpHandler(client, databaseLookupService);
        leadersHandler = new LeadersHandler(client, databaseLookupService);
        linkHandler = new LinkHandler(client, databaseLookupService);
        removeHandler = new RemoveHandler(client, databaseLookupService);
        statsHandler = new StatsHandler(client, databaseLookupService);
        subscribeHandler = new SubscribeHandler(client, databaseLookupService);
        unlinkHandler = new UnlinkHandler(client, databaseLookupService);
        unsubscribeHandler = new UnsubscribeHandler(client, databaseLookupService);
    }

    public Response processCommand(Command command) {
        TypeOfCommand typeOfCommand = command.getCommandType();
        if(typeOfCommand == null) {
            return new Response("Unable to process command: Enter a valid command");
        }
        switch(typeOfCommand) {
            case ADD:           return addHandler.processCommand(command);
            case REMOVE:        return removeHandler.processCommand(command);
            case LINK:          return linkHandler.processCommand(command);
            case UNLINK:        return unlinkHandler.processCommand(command);
            case SUBSCRIBE:     return subscribeHandler.processCommand(command);
            case UNSUBSCRIBE:   return unsubscribeHandler.processCommand(command);
            case HELP:          return helpHandler.processCommand(command);
            case STATS:         return statsHandler.processCommand(command);
            case LEADERS:       return leadersHandler.processCommand(command);
            case CHANNEL:       return channelHandler.processCommand(command);
            default:
                return null;
        }
    }

}
