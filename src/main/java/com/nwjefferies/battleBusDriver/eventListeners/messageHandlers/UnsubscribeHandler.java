package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import com.nwjefferies.epicTools.EpicUser;
import sx.blah.discord.api.IDiscordClient;

import java.util.Calendar;

public class UnsubscribeHandler extends CommandHandler{

    public UnsubscribeHandler(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        super(client, databaseLookupService);
    }
    @Override
    public Response processCommand(Command command) {
        if(!command.isValidCommand()) {
            return null;
        }
        String text = "";
        System.out.println("[" + Calendar.getInstance().getTime() + "][UnsubscribeHandler] Processing unsubscribe command");
        long guildID = command.getGuildId();

        if(command.getNumArguments() < 3) {
            text = "Unable to unsubscribe to username: Too few arguments passed";
        }
        else {
            // check if server owner
            if(!command.isServerOwner()) {
                text = "Unable to unsubscribe to username: You must be the server owner to use this command";
            }
            else {
                String targetUsername = command.getArgumentsAfterCommandWithSpaces();
                EpicUser user = new EpicUser(targetUsername);
                // not checking to see if it's a valid user or not because this handler doesn't need to know that

                // check if user is in the unlinked users
                if(!databaseLookupService.containsSubscribedUser(guildID, user)) {
                    text = "Unable to unsubscribe to username: This subscribed username was not found in the server";
                    if(databaseLookupService.containsLinkedUser(guildID, user)) {
                        text += "\nNote: Linked usernames cannot be unsubscribed to, " +
                                "the users that linked them will have to unlink them to stop receiving win notifications";
                    }
                }
                else {
                    databaseLookupService.removeSubscribedUser(guildID, user);
                    text = "Success! Username has been unsubscribed to on the server";
                }
            }
        }

        return new Response(text);
    }
}
