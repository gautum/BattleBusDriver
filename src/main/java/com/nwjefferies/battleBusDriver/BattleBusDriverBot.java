package com.nwjefferies.battleBusDriver;

import java.sql.SQLException;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.discordTools.DiscordCredentials;
import com.nwjefferies.battleBusDriver.eventListeners.GuildCreateListener;
import com.nwjefferies.battleBusDriver.eventListeners.MessageListener;
import com.xilixir.fortniteapi.v2.Configuration;


import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

public class BattleBusDriverBot {

    public static BattleBusDriverBot BOT_INSTANCE; // Singleton instance of the bot.
    public static IDiscordClient client; // The instance of the discord client.
    public static DatabaseLookupService databaseLookupService;

    public static void main(String [] args) {


        Configuration discordLogin = new Configuration("discord_login", DiscordCredentials.class);
        if(!tryLogin(discordLogin.read())) {
            return;
        }

        FortniteAPIPoller poller = new FortniteAPIPoller(client, databaseLookupService);
        poller.run();
    }

    public BattleBusDriverBot(IDiscordClient client) throws SQLException{
        this.client = client; // Sets the client instance to the one provided
        databaseLookupService = new DatabaseLookupService();
        databaseLookupService.establishConnection();
        client.getDispatcher().registerListener(new GuildCreateListener(client, databaseLookupService));
        //client.getDispatcher().registerListener(new BattleBusDriverGuildLeaveListener(client, guilds));
        //client.getDispatcher().registerListener(new BattleBusDriverReadyListener(client));
        client.getDispatcher().registerListener(new MessageListener(client, databaseLookupService));
    }

    private static boolean tryLogin(DiscordCredentials discordCredentials) {
        try {
            BOT_INSTANCE = login(discordCredentials.getToken());
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static BattleBusDriverBot login(String token) throws Exception{
        BattleBusDriverBot bot; // Initializing the bot variable

        ClientBuilder builder = new ClientBuilder(); // Creates a new client builder instance
        builder.withToken(token); // Sets the bot token for the client
        IDiscordClient client = builder.login(); // Builds the IDiscordClient instance and logs it in
        bot = new BattleBusDriverBot(client); // Creating the bot instance
        return bot;
    }

}


