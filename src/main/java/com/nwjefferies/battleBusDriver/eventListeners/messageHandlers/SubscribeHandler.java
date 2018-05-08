package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import com.nwjefferies.epicTools.EpicUser;
import sx.blah.discord.api.IDiscordClient;

import java.util.Calendar;

public class SubscribeHandler extends CommandHandler{

    public SubscribeHandler(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        super(client, databaseLookupService);
    }
    @Override
    public Response processCommand(Command command) {
        if(!command.isValidCommand()) {
            return null;
        }
        String text = "";
        System.out.println("[" + Calendar.getInstance().getTime() + "][SubscribeHandler] Processing subscribe command");
        long guildID = command.getGuildId();

        if(command.getNumArguments() < 3) {
            text = "Unable to subscribe to username: Too few arguments passed";
        }
        else if(command.isServerOwner()) {
            String targetUsername = command.getArgumentsAfterCommandWithSpaces();
            EpicUser user = new EpicUser(targetUsername);
            if(!user.isValidUser()) {
                text = "Unable to subscribe to username: Could not find Epic username";
            }
            else {
                if(databaseLookupService.containsLinkedUser(guildID, user)) {
                    text = "Unable to subscribe to username: This username has already been linked in the server";
                }
                else {
                    databaseLookupService.addUser(user);
                    if(databaseLookupService.addSubscribedUser(guildID, user)) {
                        text = "Success! Username has been subscribed to on the server";
                    }
                    else {
                        text = "Unable to subscribe to username: This username has already been subscribed to";
                    }

                }
            }
        }
        else {
            text = "Unable to subscribe to username: You must be the server owner to use this command";
        }

        return new Response(text);
    }
}
