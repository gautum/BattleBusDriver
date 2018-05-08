package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import com.nwjefferies.epicTools.EpicUser;
import sx.blah.discord.api.IDiscordClient;

import java.util.Calendar;

public class RemoveHandler extends CommandHandler{

    public RemoveHandler(IDiscordClient client, DatabaseLookupService databaseLookupService) {
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

        if(command.getNumArguments() < 3) {
            text = "Unable to remove username: Too few arguments passed";
        }
        else {
            // check if server owner
            if(!command.isServerOwner()) {
                text = "Unable to remove username: You must be the server owner to use this command";
            }
            else {
                String targetUsername = command.getArgumentsAfterCommandWithSpaces();
                EpicUser user = new EpicUser(targetUsername);
                // not checking to see if it's a valid user or not because this handler doesn't need to know that

                // check if user is in the unlinked users
                if(!databaseLookupService.containsUnlinkedUser(guildID, user)) {
                    text = "Unable to remove username: This unlinked username was not found in the server";
                    if(databaseLookupService.containsLinkedUser(guildID, user)) {
                        text += "\nNote: Linked usernames cannot be removed, " +
                                "they may only be unlinked by the users that linked them";
                    }
                }
                else {
                    databaseLookupService.removeUnlinkedUser(guildID, user);
                    text = "Success! Username has been removed from the server";
                }
            }
        }

        return new Response(text);
    }
}
