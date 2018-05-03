package com.nwjefferies.battleBusDriver;


import com.nwjefferies.battleBusDriver.databaseConnection.*;
import com.nwjefferies.epicTools.EpicUser;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.ArrayList;

public class FortniteAPIPoller {

    private IDiscordClient client;
    private DatabaseLookupService databaseLookupService;

    public FortniteAPIPoller(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        this.client = client;
        this.databaseLookupService = databaseLookupService;
    }

    public void run() {
        while(true) {
            ArrayList<EpicUserLookup> epicUsers = databaseLookupService.getEpicUsers();
            for(EpicUserLookup userLookup : epicUsers) {
                int lastSolo = userLookup.getSolo_wins();
                int lastDuo = userLookup.getDuo_wins();
                int lastSquad = userLookup.getSquad_wins();

                String display_name = userLookup.getDisplay_name();
                EpicUser userWithCurrentStats = new EpicUser(display_name);

                int diffSolo = (int) userWithCurrentStats.getStats().getSoloWins() - lastSolo;
                int diffDuo = (int) userWithCurrentStats.getStats().getDuoWins() - lastDuo;
                int diffSquad = (int) userWithCurrentStats.getStats().getSquadWins() - lastSquad;

                databaseLookupService.updateUserWithCurrentStats(userWithCurrentStats);

                if((diffSolo | diffDuo | diffSquad) > 0) {
                    sendMessageToSubscribedGuilds(display_name, diffSolo, diffDuo, diffSquad);
                    sendMessageToLinkedGuilds(display_name, diffSolo, diffDuo, diffSquad);
                    System.out.println("Sending message!");
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private void sendMessageToSubscribedGuilds(String display_name, int diffSolo, int diffDuo, int diffSquad) {
        ArrayList<UnlinkedOrSubscribedUserLookup> subscribedUsers =
                databaseLookupService.getSubscribedUsersByDisplayName(display_name);

        for(UnlinkedOrSubscribedUserLookup lookup : subscribedUsers) {
            GuildLookup guildLookup = databaseLookupService.getGuild(lookup.getGuild_id());

            if(diffSolo > 0) {
                if(diffSolo == 1) {
                    sendMessage(display_name + " got a solo win.", guildLookup.getChannelID());
                }
                else {
                    sendMessage(display_name + " got " + diffSolo + " solo wins.", guildLookup.getChannelID());
                }
            }
            if(diffDuo > 0) {
                if(diffDuo == 1) {
                    sendMessage(display_name + " got a duo win.", guildLookup.getChannelID());
                }
                else {
                    sendMessage(display_name + " got " + diffDuo + " duo wins.", guildLookup.getChannelID());
                }
            }
            if(diffSquad > 0) {
                if(diffSquad == 1) {
                    sendMessage(display_name + " got a squad win.", guildLookup.getChannelID());
                }
                else {
                    sendMessage(display_name + " got " + diffSquad + " squad wins.", guildLookup.getChannelID());
                }
            }

        }
    }

    private void sendMessageToLinkedGuilds(String display_name, int diffSolo, int diffDuo, int diffSquad) {
        ArrayList<LinkedUserLookup> subscribedUsers =
                databaseLookupService.getLinkedUsersForDisplayName(display_name);

        for(LinkedUserLookup lookup : subscribedUsers) {
            GuildLookup guildLookup = databaseLookupService.getGuild(lookup.getGuild_id());

            IUser user = client.getUserByID(lookup.getDiscord_id());

            if(diffSolo > 0) {
                if(diffSolo == 1) {
                    sendMessage("Congrats to " + user + " for getting a solo win.", guildLookup.getChannelID());
                }
                else {
                    sendMessage("Congrats to " + user + " for getting " + diffSolo + " solo wins.", guildLookup.getChannelID());
                }
            }
            if(diffDuo > 0) {
                if(diffDuo == 1) {
                    sendMessage("Congrats to " + user + " for getting a duo win.", guildLookup.getChannelID());
                }
                else {
                    sendMessage("Congrats to " + user + " for getting " + diffDuo + " duo wins.", guildLookup.getChannelID());
                }
            }
            if(diffSquad > 0) {
                if(diffSquad == 1) {
                    sendMessage("Congrats to " + user + " for getting a squad win.", guildLookup.getChannelID());
                }
                else {
                    sendMessage("Congrats to " + user + " for getting " + diffSquad + " squad wins.", guildLookup.getChannelID());
                }
            }

        }
    }


    private void sendMessage(String text, long channelID) {
        try {
            // Builds (sends) and new message in the channel that the original message was sent with the content of the original message.
            new MessageBuilder(client).withChannel(client.getChannelByID(channelID)).withContent(text).build();
        } catch (RateLimitException e) { // RateLimitException thrown. The bot is sending messages too quickly!
            System.err.print("Sending messages too quickly!");
            e.printStackTrace();
        } catch (DiscordException e) { // DiscordException thrown. Many possibilities. Use getErrorMessage() to see what went wrong.
            System.err.print(e.getErrorMessage()); // Print the error message sent by Discord
            e.printStackTrace();
        } catch (MissingPermissionsException e) { // MissingPermissionsException thrown. The bot doesn't have permission to send the message!
            System.err.print("Missing permissions for channel!");
            e.printStackTrace();
        }
    }
}
