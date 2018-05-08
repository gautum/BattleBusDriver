package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import com.nwjefferies.epicTools.EpicUser;
import sx.blah.discord.api.IDiscordClient;

import java.io.IOException;
import java.util.Calendar;

public class AddHandler extends CommandHandler{

    public AddHandler(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        super(client, databaseLookupService);
    }
    @Override
    public Response processCommand(Command command) {

        if(!command.isValidCommand()) {
            return null;
        }
        String text = "";
        System.out.println("[" + Calendar.getInstance().getTime() + "][AddHandler] Processing add command");
        long guildID = command.getGuildId();

        if(command.getNumArguments() < 3) {
            text = "Unable to add username: Too few arguments passed";
        }
        else if(command.isServerOwner()) {
            String targetUsername = command.getArgumentsAfterCommandWithSpaces();
            EpicUser user = new EpicUser(targetUsername);
            if(!user.isValidUser()) {
                text = "Unable to add username: Could not find Epic username";
            }
            else {

                databaseLookupService.addUser(user);

                if(databaseLookupService.containsLinkedUser(guildID, user)) {
                    text = "Unable to add username: This username is already linked in the server";
                }
                else {
                    if(databaseLookupService.addUnlinkedUser(guildID, user)) {
                        text = "Success! Username has been added to the server";
                    }
                    else {
                        text = "Unable to add username: This username has already been added to the server";
                    }

                }
            }
        }
        else {
            text = "Unable to add username: You must be the server owner to use this command";
        }

        return new Response(text);
    }
}
