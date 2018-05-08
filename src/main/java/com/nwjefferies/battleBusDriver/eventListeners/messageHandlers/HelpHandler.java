package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.EmbedField;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import sx.blah.discord.api.IDiscordClient;

import java.util.ArrayList;
import java.util.Calendar;

public class HelpHandler extends CommandHandler {
    private static final String KEYWORD = "!bus";
    public HelpHandler(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        super(client, databaseLookupService);
    }
    @Override
    public Response processCommand(Command command) {
        System.out.println("[" + Calendar.getInstance().getTime() + "][HelpHandler] Processing help command");

        ArrayList<EmbedField> fields = new ArrayList<EmbedField>();

        EmbedField link = new EmbedField("Link your epic account:", false);
        link.appendFieldValue("```" + KEYWORD + " link <epic username>```");

        EmbedField unlink = new EmbedField("Unlink your epic account:", false);
        unlink.appendFieldValue("```" + KEYWORD + " unlink```");

        EmbedField yourStats = new EmbedField("Get stats for your linked username:", false);
        yourStats.appendFieldValue("```" + KEYWORD + " stats```");

        EmbedField anyStats = new EmbedField("Get stats for any epic username:", false);
        anyStats.appendFieldValue("```" + KEYWORD + " stats <epic username>```");

        EmbedField leaderboard = new EmbedField("Display the leaderboard:", false);
        leaderboard.appendFieldValue("Shows leaderboard for all linked epic accounts and added usernames.");
        leaderboard.appendFieldValue("");
        leaderboard.appendFieldValue("**Available Stats:** score, wins, win%, kills, kd, kills/min, kills/match");
        leaderboard.appendFieldValue("matchesPlayed, minutesPlayed, top10, top25, top5, top12, top3, top6");
        leaderboard.appendFieldValue("```" + KEYWORD + " leaders <solo/duo/squad/total> <stat>```");

        EmbedField add = new EmbedField("Add any epic username to leaderboard:", false);
        add.appendFieldValue("Must be server owner.");
        add.appendFieldValue("```" + KEYWORD + " add <epic username>```");

        EmbedField remove = new EmbedField("Remove an epic username from leaderboard:", false);
        remove.appendFieldValue("Must be server owner.");
        remove.appendFieldValue("```" + KEYWORD + " remove <epic username>```");

        EmbedField channelCommand = new EmbedField("Set channel to the current channel:", false);
        channelCommand.appendFieldValue("Must be server owner.");
        channelCommand.appendFieldValue("```" + KEYWORD + " channel```");

        fields.add(link);
        fields.add(unlink);
        fields.add(yourStats);
        fields.add(anyStats);
        fields.add(leaderboard);
        fields.add(add);
        fields.add(remove);
        fields.add(channelCommand);

        EmbedField[] fieldArray = new EmbedField[fields.size()];
        Response response = new Response(fields.toArray(fieldArray));
        response.setTitle("Help Menu");

        return response;
    }
}
