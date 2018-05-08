package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import com.nwjefferies.epicTools.EpicUser;
import sx.blah.discord.api.IDiscordClient;

import java.util.Calendar;

public class UnlinkHandler extends CommandHandler{

    public UnlinkHandler(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        super(client, databaseLookupService);
    }
    @Override
    public Response processCommand(Command command) {
        if(!command.isValidCommand()) {
            return null;
        }
        String text = "";
        System.out.println("[" + Calendar.getInstance().getTime() + "][Unlink] Processing unlink command");
        long guildID = command.getGuildId();

        if(command.getNumArguments() > 2) {
            text = "Unable to unlink account: Too many arguments passed";
        }
        else {
            // check if author has a linked account
            if(!databaseLookupService.containsLinkedUser(guildID, command.getAuthorId())) {
                text = "Unable to unlink account: You must have a linked Epic username first";
            }
            else {
                databaseLookupService.removeLinkedUser(guildID, command.getAuthorId());
                text = "Success! Your Discord account is now unlinked from your Epic username";
            }
        }

        return new Response(text);
    }
}
