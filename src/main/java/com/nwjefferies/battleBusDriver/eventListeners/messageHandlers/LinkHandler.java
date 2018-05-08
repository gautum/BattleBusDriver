package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import com.nwjefferies.epicTools.EpicUser;
import sx.blah.discord.api.IDiscordClient;

import java.util.Calendar;

public class LinkHandler extends CommandHandler{

    public LinkHandler(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        super(client, databaseLookupService);
    }
    @Override
    public Response processCommand(Command command) {
        if(!command.isValidCommand()) {
            return null;
        }
        String text = "";
        System.out.println("[" + Calendar.getInstance().getTime() + "][LinkHandler] Processing link command");
        long guildID = command.getGuildId();

        if(command.getNumArguments() < 3) {
            text ="Unable to link account: Too few arguments passed";
        }
        else {
            // check if user already has a linked account
            if(databaseLookupService.getLinkedUser(guildID, command.getAuthorId()) != null) {
                text = "Unable to link account: You can only have one linked Epic";
            }
            else {
                String targetUsername = command.getArgumentsAfterCommandWithSpaces();
                EpicUser user = new EpicUser(targetUsername);
                // check if the user exists
                if(!user.isValidUser()) {
                    text = "Unable to link account: Could not find Epic username";
                }
                else {
                    // check if user has already been linked in the server
                    if(databaseLookupService.containsLinkedUser(guildID, user)) {
                        text = "Unable to link account: This Epic username has already been linked in this server";
                    }
                    else {
                        // from this point on, the user will be linked
                        text = "Success! Your Discord account is now linked to your Epic username";
                        databaseLookupService.addUser(user);
                        databaseLookupService.addLinkedUser(user, guildID, command.getAuthorId());
                        // remove users from unlinkedUsers and subscribedUsers
                        databaseLookupService.removeUnlinkedUser(guildID, user);
                        databaseLookupService.removeSubscribedUser(guildID, user);
                    }
                }
            }
        }

        return new Response(text);
    }
}
