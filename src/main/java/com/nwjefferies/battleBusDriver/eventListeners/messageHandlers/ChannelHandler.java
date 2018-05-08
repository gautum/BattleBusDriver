package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import com.nwjefferies.epicTools.EpicUser;
import sx.blah.discord.api.IDiscordClient;

import java.util.Calendar;

public class ChannelHandler extends CommandHandler{

    public ChannelHandler(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        super(client, databaseLookupService);
    }
    @Override
    public Response processCommand(Command command) {
        if(!command.isValidCommand()) {
            return null;
        }
        String text = "";
        System.out.println("[" + Calendar.getInstance().getTime() + "][RemoveHandler] Processing remove command");
        long guildID = command.getGuildId();

        if(command.getNumArguments() > 2) {
            text = "Unable to set channel: Too many arguments passed";
        }
        else {
            // check if server owner
            if(!command.isServerOwner()) {
                text = "Unable to set channel: You must be the server owner to use this command";
            }
            else {
                text = "Success! This channel has been set to #" + command.getChannelName();
                databaseLookupService.setChannel(guildID, command.getChannelId());
            }
        }

        return new Response(text);
    }
}
