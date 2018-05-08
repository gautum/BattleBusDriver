package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.databaseConnection.EpicUserLookup;
import com.nwjefferies.battleBusDriver.databaseConnection.LinkedUserLookup;
import com.nwjefferies.battleBusDriver.databaseConnection.UnlinkedOrSubscribedUserLookup;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.EmbedField;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import com.nwjefferies.epicTools.EpicUser;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import static com.nwjefferies.battleBusDriver.eventListeners.messageHandlers.StatToCompareType.*;

public class LeadersHandler extends CommandHandler{

    public LeadersHandler(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        super(client, databaseLookupService);
    }
    @Override
    public Response processCommand(Command command) {
        if(!command.isValidCommand()) {
            return null;
        }
        String text = "";
        System.out.println("[" + Calendar.getInstance().getTime() + "][LeadersHandler] Processing leaders command");
        long guildID = command.getGuildId();

        ArrayList<LinkedUserLookup> linkedUsers = databaseLookupService.getLinkedUsersForGuildID(guildID);
        ArrayList<UnlinkedOrSubscribedUserLookup> unlinkedUsers = databaseLookupService.getUnlinkedUsersByGuildID(guildID);
        ArrayList<EpicUserLookupArrayObject> usersForLeaderboard = getAllUsersFromLinkedAndUnlinked(linkedUsers, unlinkedUsers);

        if(usersForLeaderboard.size() == 0) {
            return new Response("Unable to process leaderboard: No players added to the leaderboard yet");
        }

        StatToCompareType statToCompareType = null;
        try {
            if (command.getNumArguments() == 2) {
                statToCompareType = TOTAL_WINS;
            } else if (command.getNumArguments() == 3) {
                statToCompareType = getStatType("total", command.getSplit()[2]);
            } else if (command.getNumArguments() == 4) {
                statToCompareType = getStatType(command.getSplit()[2], command.getSplit()[3]);
            } else {
                return new Response("Unable to process leaderboard: Too many arguments passed");
            }
        }
        catch(IllegalArgumentException e) {
            return new Response("Unable to process leaderboard: Not a valid stat");
        }

        Collections.sort(usersForLeaderboard, new EpicUserLookupArrayObjectComparator(statToCompareType));

        return new Response(getLeaderboardFields(usersForLeaderboard, statToCompareType));
    }

    private ArrayList<EpicUserLookupArrayObject> getAllUsersFromLinkedAndUnlinked(ArrayList<LinkedUserLookup> linkedUsers,
                                                                       ArrayList<UnlinkedOrSubscribedUserLookup> unlinkedUsers) {
        ArrayList<EpicUserLookupArrayObject> users = new ArrayList<EpicUserLookupArrayObject>();

        IGuild guild = null;

        for(LinkedUserLookup linkedUserLookup : linkedUsers) {
            if(guild == null) {
                guild = client.getGuildByID(linkedUserLookup.getGuild_id());
            }
            IUser iUser = client.getUserByID(linkedUserLookup.getDiscord_id());
            String name = iUser.getNicknameForGuild(guild);
            if(name == null) {
                name = iUser.getName();
            }

            EpicUserLookup epicUserLookup = databaseLookupService.getEpicUser(linkedUserLookup.getDisplay_name());
            users.add(new EpicUserLookupArrayObject(epicUserLookup, name));
        }
        for(UnlinkedOrSubscribedUserLookup unlinkedOrSubscribedUserLookup : unlinkedUsers) {
            EpicUserLookup epicUserLookup = databaseLookupService.getEpicUser(unlinkedOrSubscribedUserLookup.getDisplay_name());
            users.add(new EpicUserLookupArrayObject(epicUserLookup));
        }

        return users;
    }

    private EmbedField[] getLeaderboardFields(ArrayList<EpicUserLookupArrayObject> users, StatToCompareType statToCompareType) {
        EmbedField[] leaderboardFields = new EmbedField[3];

        EmbedField names = new EmbedField("Name", true);
        EmbedField epicUsernames = new EmbedField("Epic Username", true);
        EmbedField stats = new EmbedField(StatToCompareType.getBetterLookingString(statToCompareType), true);

        for(EpicUserLookupArrayObject epicUserLookupArrayObject : users) {
            EpicUserLookup epicUserLookup = epicUserLookupArrayObject.getEpicUserLookup();
            String name = epicUserLookupArrayObject.getLinkedName();
            if(name == null) {
                name = "(Unlinked)";
            }
            names.appendFieldValue(name);
            epicUsernames.appendFieldValue(epicUserLookup.getDisplay_name());
            stats.appendFieldValue(epicUserLookupArrayObject.getStat(statToCompareType));
        }
        leaderboardFields[0] = names;
        leaderboardFields[1] = epicUsernames;
        leaderboardFields[2] = stats;
        return leaderboardFields;
    }


    private class EpicUserLookupArrayObject {
        private EpicUserLookup epicUserLookup;
        private String linkedName;
        public EpicUserLookupArrayObject(EpicUserLookup epicUserLookup, String linkedName) {
            this.epicUserLookup = epicUserLookup;
            this.linkedName = linkedName;
        }
        public EpicUserLookupArrayObject(EpicUserLookup epicUserLookup) {
            this.epicUserLookup = epicUserLookup;
            this.linkedName = null;
        }

        public EpicUserLookup getEpicUserLookup() {
            return epicUserLookup;
        }

        public String getStat(StatToCompareType statType) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            switch(statType) {
                case SOLO_TOP10: return String.valueOf(epicUserLookup.getSolo_top10());
                case SOLO_TOP25: return String.valueOf(epicUserLookup.getSolo_top25());
                case DUO_TOP5: return String.valueOf(epicUserLookup.getDuo_top5());
                case DUO_TOP12: return String.valueOf(epicUserLookup.getDuo_top12());
                case SQUAD_TOP3: return String.valueOf(epicUserLookup.getSquad_top3());
                case SQUAD_TOP6: return String.valueOf(epicUserLookup.getSquad_top6());
                case SOLO_KILLS: return String.valueOf(epicUserLookup.getSolo_kills());
                case DUO_KILLS: return String.valueOf(epicUserLookup.getDuo_kills());
                case SQUAD_KILLS: return String.valueOf(epicUserLookup.getSquad_kills());
                case TOTAL_KILLS: return String.valueOf(epicUserLookup.getTotal_kills());
                case SOLO_MINUTESPLAYED: return String.valueOf(epicUserLookup.getSolo_minutes_played());
                case DUO_MINUTESPLAYED: return String.valueOf(epicUserLookup.getDuo_minutes_played());
                case SQUAD_MINUTESPLAYED: return String.valueOf(epicUserLookup.getSquad_minutes_played());
                case TOTAL_MINUTESPLAYED: return String.valueOf(epicUserLookup.getTotal_minutes_played());
                case SOLO_WINS: return String.valueOf(epicUserLookup.getSolo_wins());
                case DUO_WINS: return String.valueOf(epicUserLookup.getDuo_wins());
                case SQUAD_WINS: return String.valueOf(epicUserLookup.getSquad_wins());
                case TOTAL_WINS: return String.valueOf(epicUserLookup.getTotal_wins());
                case SOLO_MATCHESPLAYED: return String.valueOf(epicUserLookup.getSolo_matches_played());
                case DUO_MATCHESPLAYED: return String.valueOf(epicUserLookup.getDuo_matches_played());
                case SQUAD_MATCHESPLAYED: return String.valueOf(epicUserLookup.getSquad_matches_played());
                case TOTAL_MATCHESPLAYED: return String.valueOf(epicUserLookup.getTotal_matches_played());
                case SOLO_SCORE: return String.valueOf(epicUserLookup.getSolo_score());
                case DUO_SCORE: return String.valueOf(epicUserLookup.getDuo_score());
                case SQUAD_SCORE: return String.valueOf(epicUserLookup.getSquad_score());
                case SOLO_KD: return decimalFormat.format(epicUserLookup.getSolo_kill_death_ratio());
                case DUO_KD: return decimalFormat.format(epicUserLookup.getDuo_kill_death_ratio());
                case SQUAD_KD: return decimalFormat.format(epicUserLookup.getSquad_kill_death_ratio());
                case TOTAL_KD: return decimalFormat.format(epicUserLookup.getTotal_kill_death_ratio());
                case SOLO_WINRATE: return decimalFormat.format(epicUserLookup.getSolo_win_ratio());
                case DUO_WINRATE: return decimalFormat.format(epicUserLookup.getDuo_win_ratio());
                case SQUAD_WINRATE: return decimalFormat.format(epicUserLookup.getSquad_win_ratio());
                case TOTAL_WINRATE: return decimalFormat.format(epicUserLookup.getTotal_win_ratio());
                case SOLO_KILLSPERMINUTE: return decimalFormat.format(epicUserLookup.getSolo_kills_per_minute());
                case DUO_KILLSPERMINUTE: return decimalFormat.format(epicUserLookup.getDuo_kills_per_minute());
                case SQUAD_KILLSPERMINUTE: return decimalFormat.format(epicUserLookup.getSquad_kills_per_minute());
                case TOTAL_KILLSPERMINUTE: return decimalFormat.format(epicUserLookup.getTotal_kills_per_minute());
                case SOLO_KILLSPERMATCH: return decimalFormat.format(epicUserLookup.getSolo_kills_per_match());
                case DUO_KILLSPERMATCH: return decimalFormat.format(epicUserLookup.getDuo_kills_per_match());
                case SQUAD_KILLSPERMATCH: return decimalFormat.format(epicUserLookup.getSquad_kills_per_match());
                case TOTAL_KILLSPERMATCH: return decimalFormat.format(epicUserLookup.getTotal_kills_per_match());

                default:
                    return "0";
            }
        }

        public String getLinkedName() {
            return linkedName;
        }
    }

    private class EpicUserLookupArrayObjectComparator implements Comparator<EpicUserLookupArrayObject> {
        private StatToCompareType toCompare;
        public EpicUserLookupArrayObjectComparator(StatToCompareType toCompare) {
            this.toCompare = toCompare;
        }

        @Override
        public int compare(EpicUserLookupArrayObject o1, EpicUserLookupArrayObject o2) {
            EpicUserLookup left = o1.getEpicUserLookup();
            EpicUserLookup right = o2.getEpicUserLookup();

            switch(toCompare) {
                case SOLO_TOP10: return Integer.compare(right.getSolo_top10(), left.getSolo_top10());
                case SOLO_TOP25: return Integer.compare(right.getSolo_top25(), left.getSolo_top25());
                case DUO_TOP5: return Integer.compare(right.getDuo_top5(), left.getDuo_top5());
                case DUO_TOP12: return Integer.compare(right.getDuo_top12(), left.getDuo_top12());
                case SQUAD_TOP3: return Integer.compare(right.getSquad_top3(), left.getSquad_top3());
                case SQUAD_TOP6: return Integer.compare(right.getSquad_top6(), left.getSquad_top6());
                case SOLO_KILLS: return Integer.compare(right.getSolo_kills(), left.getSolo_kills());
                case DUO_KILLS: return Integer.compare(right.getDuo_kills(), left.getDuo_kills());
                case SQUAD_KILLS: return Integer.compare(right.getSquad_kills(), left.getSquad_kills());
                case TOTAL_KILLS: return Integer.compare(right.getTotal_kills(), left.getTotal_kills());
                case SOLO_MINUTESPLAYED: return Integer.compare(right.getSolo_minutes_played(), left.getSolo_minutes_played());
                case DUO_MINUTESPLAYED: return Integer.compare(right.getDuo_minutes_played(), left.getDuo_minutes_played());
                case SQUAD_MINUTESPLAYED: return Integer.compare(right.getSquad_minutes_played(), left.getSquad_minutes_played());
                case TOTAL_MINUTESPLAYED: return Integer.compare(right.getTotal_minutes_played(), left.getTotal_minutes_played());
                case SOLO_WINS: return Integer.compare(right.getSolo_wins(), left.getSolo_wins());
                case DUO_WINS: return Integer.compare(right.getDuo_wins(), left.getDuo_wins());
                case SQUAD_WINS: return Integer.compare(right.getSquad_wins(), left.getSquad_wins());
                case TOTAL_WINS: return Integer.compare(right.getTotal_wins(), left.getTotal_wins());
                case SOLO_MATCHESPLAYED: return Integer.compare(right.getSolo_matches_played(), left.getSolo_matches_played());
                case DUO_MATCHESPLAYED: return Integer.compare(right.getDuo_matches_played(), left.getDuo_matches_played());
                case SQUAD_MATCHESPLAYED: return Integer.compare(right.getSquad_matches_played(), left.getSquad_matches_played());
                case TOTAL_MATCHESPLAYED: return Integer.compare(right.getTotal_matches_played(), left.getTotal_matches_played());
                case SOLO_SCORE: return Integer.compare(right.getSolo_score(), left.getSolo_score());
                case DUO_SCORE: return Integer.compare(right.getDuo_score(), left.getDuo_score());
                case SQUAD_SCORE: return Integer.compare(right.getSquad_score(), left.getSquad_score());
                case SOLO_KD: return Float.compare(right.getSolo_kill_death_ratio(), left.getSolo_kill_death_ratio());
                case DUO_KD: return Float.compare(right.getDuo_kill_death_ratio(), left.getDuo_kill_death_ratio());
                case SQUAD_KD: return Float.compare(right.getSquad_kill_death_ratio(), left.getSquad_kill_death_ratio());
                case TOTAL_KD: return Float.compare(right.getTotal_kill_death_ratio(), left.getTotal_kill_death_ratio());
                case SOLO_WINRATE: return Float.compare(right.getSolo_win_ratio(), left.getSolo_win_ratio());
                case DUO_WINRATE: return Float.compare(right.getDuo_win_ratio(), left.getDuo_win_ratio());
                case SQUAD_WINRATE: return Float.compare(right.getSquad_win_ratio(), left.getSquad_win_ratio());
                case TOTAL_WINRATE: return Float.compare(right.getTotal_win_ratio(), left.getTotal_win_ratio());
                case SOLO_KILLSPERMINUTE: return Float.compare(right.getSolo_kills_per_minute(), left.getSolo_kills_per_minute());
                case DUO_KILLSPERMINUTE: return Float.compare(right.getDuo_kills_per_minute(), left.getDuo_kills_per_minute());
                case SQUAD_KILLSPERMINUTE: return Float.compare(right.getSquad_kills_per_minute(), left.getSquad_kills_per_minute());
                case TOTAL_KILLSPERMINUTE: return Float.compare(right.getTotal_kills_per_minute(), left.getTotal_kills_per_minute());
                case SOLO_KILLSPERMATCH: return Float.compare(right.getSolo_kills_per_match(), left.getSolo_kills_per_match());
                case DUO_KILLSPERMATCH: return Float.compare(right.getDuo_kills_per_match(), left.getDuo_kills_per_match());
                case SQUAD_KILLSPERMATCH: return Float.compare(right.getSquad_kills_per_match(), left.getSquad_kills_per_match());
                case TOTAL_KILLSPERMATCH: return Float.compare(right.getTotal_kills_per_match(), left.getTotal_kills_per_match());

                default:
                    return 0;
            }
        }
    }
}
