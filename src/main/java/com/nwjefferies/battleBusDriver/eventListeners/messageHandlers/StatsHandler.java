package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.databaseConnection.EpicUserLookup;
import com.nwjefferies.battleBusDriver.databaseConnection.LinkedUserLookup;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.EmbedField;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import com.nwjefferies.epicTools.EpicUser;
import com.xilixir.fortniteapi.v2.Stats;
import sx.blah.discord.api.IDiscordClient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class StatsHandler extends CommandHandler{

    public StatsHandler(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        super(client, databaseLookupService);
    }
    @Override
    public Response processCommand(Command command) {
        if(!command.isValidCommand()) {
            return null;
        }
        String text = "";
        System.out.println("[" + Calendar.getInstance().getTime() + "][StatsHandler] Processing stats command");
        long guildID = command.getGuildId();

        // look up stats for a linked user
        if(command.getNumArguments() == 2) {
            // check if author has a linked user
            if(!databaseLookupService.containsLinkedUser(guildID, command.getAuthorId())) {
                text = "Unable to retrieve stats: You do not have a linked Epic username. Link one first or pass in a username with the \"stats\"  command";
            }
            else {
                LinkedUserLookup linkedUserLookup = databaseLookupService.getLinkedUser(guildID, command.getAuthorId());
                EpicUserLookup epicUserLookup = databaseLookupService.getEpicUser(linkedUserLookup.getDisplay_name());
                return new Response(processStatsHelper(epicUserLookup));
            }
        }
        else { // look up stats for the username provided
            String targetUsername = command.getArgumentsAfterCommandWithSpaces();
            EpicUser user = new EpicUser(targetUsername);
            if(!user.isValidUser()) {
                text = "Unable to retrieve stats: Could not find Epic username";
            }
            else {
                return new Response(processStatsHelper(user));
            }
        }

        return new Response(text);
    }

    private EmbedField[] processStatsHelper(EpicUserLookup epicUserLookup) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        EmbedField solo;
        EmbedField duo;
        EmbedField squad;

        solo = new EmbedField("Solo:", true);
        duo = new EmbedField("Duo:", true);
        squad = new EmbedField("Squad:", true);

        solo.appendFieldValue("**Score:** " + epicUserLookup.getSolo_score());
        solo.appendFieldValue("**Wins:** " + epicUserLookup.getSolo_wins());
        solo.appendFieldValue("**Top 10:** " + epicUserLookup.getSolo_top10());
        solo.appendFieldValue("**Top 25:** " + epicUserLookup.getSolo_top25());
        solo.appendFieldValue("**K/d:** " + decimalFormat.format(epicUserLookup.getSolo_kill_death_ratio()));
        solo.appendFieldValue("**Win%:** " + decimalFormat.format(epicUserLookup.getSolo_win_ratio()));
        solo.appendFieldValue("**Kills/Minute:** " + decimalFormat.format(epicUserLookup.getSolo_kills_per_minute()));
        solo.appendFieldValue("**Kills/Match:** " + decimalFormat.format(epicUserLookup.getSolo_kills_per_match()));
        solo.appendFieldValue("**Minutes played:** " + epicUserLookup.getSolo_minutes_played());
        solo.appendFieldValue("**Matches played:** " + epicUserLookup.getSolo_matches_played());

        duo.appendFieldValue("**Score:** " + epicUserLookup.getDuo_score());
        duo.appendFieldValue("**Wins:** " + epicUserLookup.getDuo_wins());
        duo.appendFieldValue("**Top 5:** " + epicUserLookup.getDuo_top5());
        duo.appendFieldValue("**Top 12:** " + epicUserLookup.getDuo_top12());
        duo.appendFieldValue("**K/d:** " + decimalFormat.format(epicUserLookup.getDuo_kill_death_ratio()));
        duo.appendFieldValue("**Win%:** " + decimalFormat.format(epicUserLookup.getDuo_win_ratio()));
        duo.appendFieldValue("**Kills/Minute:** " + decimalFormat.format(epicUserLookup.getDuo_kills_per_minute()));
        duo.appendFieldValue("**Kills/Match:** " + decimalFormat.format(epicUserLookup.getDuo_kills_per_match()));
        duo.appendFieldValue("**Minutes played:** " + epicUserLookup.getDuo_minutes_played());
        duo.appendFieldValue("**Matches played:** " + epicUserLookup.getDuo_matches_played());

        squad.appendFieldValue("**Score:** " + epicUserLookup.getSquad_score());
        squad.appendFieldValue("**Wins:** " + epicUserLookup.getSquad_wins());
        squad.appendFieldValue("**Top 3:** " + epicUserLookup.getSquad_top3());
        squad.appendFieldValue("**Top 6:** " + epicUserLookup.getSquad_top6());
        squad.appendFieldValue("**K/d:** " + decimalFormat.format(epicUserLookup.getSquad_kill_death_ratio()));
        squad.appendFieldValue("**Win%:** " + decimalFormat.format(epicUserLookup.getSquad_win_ratio()));
        squad.appendFieldValue("**Kills/Minute:** " + decimalFormat.format(epicUserLookup.getSquad_kills_per_minute()));
        squad.appendFieldValue("**Kills/Match:** " + decimalFormat.format(epicUserLookup.getSquad_kills_per_match()));
        squad.appendFieldValue("**Minutes played:** " + epicUserLookup.getSquad_minutes_played());
        squad.appendFieldValue("**Matches played:** " + epicUserLookup.getSquad_matches_played());

        ArrayList<EmbedField> fields = new ArrayList<EmbedField>();
        fields.add(solo);
        fields.add(duo);
        fields.add(squad);
        EmbedField [] fieldArray = new EmbedField[fields.size()];
        fields.toArray(fieldArray);
        return fieldArray;
    }


    private EmbedField[] processStatsHelper(EpicUser user) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        Stats stats = user.getStats();

        EmbedField solo;
        EmbedField duo;
        EmbedField squad;

        solo = new EmbedField("Solo:", true);
        duo = new EmbedField("Duo:", true);
        squad = new EmbedField("Squad:", true);

        solo.appendFieldValue("**Score:** " + (int)stats.getSoloScore());
        solo.appendFieldValue("**Wins:** " + (int)stats.getSoloWins());
        solo.appendFieldValue("**Top 10:** " + (int)stats.getSoloTop10());
        solo.appendFieldValue("**Top 25:** " + (int)stats.getSoloTop25());
        solo.appendFieldValue("**K/d:** " + decimalFormat.format(stats.getSoloKillDeathRatio()));
        solo.appendFieldValue("**Win%:** " + decimalFormat.format(stats.getSoloWinRatio()));
        solo.appendFieldValue("**Kills/Minute:** " + decimalFormat.format(stats.getSoloKillsPerMinute()));
        solo.appendFieldValue("**Kills/Match:** " + decimalFormat.format(stats.getSoloKillsPerMatch()));
        solo.appendFieldValue("**Minutes played:** " + (int)stats.getSoloMinutesPlayed());
        solo.appendFieldValue("**Matches played:** " + (int)stats.getSoloMatchesPlayed());

        duo.appendFieldValue("**Score:** " + (int)stats.getDuoScore());
        duo.appendFieldValue("**Wins:** " + (int)stats.getDuoWins());
        duo.appendFieldValue("**Top 5:** " + (int)stats.getDuoTop5());
        duo.appendFieldValue("**Top 12:** " + (int)stats.getDuoTop12());
        duo.appendFieldValue("**K/d:** " + decimalFormat.format(stats.getDuoKillDeathRatio()));
        duo.appendFieldValue("**Win%:** " + decimalFormat.format(stats.getDuoWinRatio()));
        duo.appendFieldValue("**Kills/Minute:** " + decimalFormat.format(stats.getDuoKillsPerMinute()));
        duo.appendFieldValue("**Kills/Match:** " + decimalFormat.format(stats.getDuoKillsPerMatch()));
        duo.appendFieldValue("**Minutes played:** " + (int)stats.getDuoMinutesPlayed());
        duo.appendFieldValue("**Matches played:** " + (int)stats.getDuoMatchesPlayed());

        squad.appendFieldValue("**Score:** " + (int)stats.getSquadScore());
        squad.appendFieldValue("**Wins:** " + (int)stats.getSquadWins());
        squad.appendFieldValue("**Top 3:** " + (int)stats.getSquadTop3());
        squad.appendFieldValue("**Top 6:** " + (int)stats.getSquadTop6());
        squad.appendFieldValue("**K/d:** " + decimalFormat.format(stats.getSquadKillDeathRatio()));
        squad.appendFieldValue("**Win%:** " + decimalFormat.format(stats.getSquadWinRatio()));
        squad.appendFieldValue("**Kills/Minute:** " + decimalFormat.format(stats.getSquadKillsPerMinute()));
        squad.appendFieldValue("**Kills/Match:** " + decimalFormat.format(stats.getSquadKillsPerMatch()));
        squad.appendFieldValue("**Minutes played:** " + (int)stats.getSquadMinutesPlayed());
        squad.appendFieldValue("**Matches played:** " + (int)stats.getSquadMatchesPlayed());

        ArrayList<EmbedField> fields = new ArrayList<EmbedField>();
        fields.add(solo);
        fields.add(duo);
        fields.add(squad);
        EmbedField [] fieldArray = new EmbedField[fields.size()];
        fields.toArray(fieldArray);
        return fieldArray;
    }
}
