package com.nwjefferies.battleBusDriver.databaseConnection;


import com.nwjefferies.epicTools.EpicUser;
import com.xilixir.fortniteapi.v2.Configuration;
import com.xilixir.fortniteapi.v2.Stats;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseLookupService {

    private static Connection connection;

    public void establishConnection() throws SQLException{

        Configuration databaseLogin = new Configuration("database_login", DatabaseCredentials.class);
        DatabaseCredentials databaseCredentials = databaseLogin.read();
        connection = DriverManager.getConnection(databaseCredentials.getDbUrl(), databaseCredentials.getUsername(), databaseCredentials.getPassword());
    }

    public boolean addGuild(long guild_id, long channel_id) {
        try {
            Statement stmt = connection.createStatement();
            String query = "INSERT INTO guild (guild_id, channel_id)\n" +
                    "VALUES (" + guild_id + ", " + channel_id +");";
            stmt.execute(query);
            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public boolean removeGuild(long guild_id) {
        try {
            Statement stmt = connection.createStatement();
            String query = "DELETE FROM guild\n" +
                    "WHERE guild_id = \"" + guild_id + "\";";
            stmt.execute(query);
            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public boolean setChannel(long guild_id, long channel_id) {
        try {
            Statement stmt = connection.createStatement();
            String query = "UPDATE guild\n" +
                    "SET channel_id = " + channel_id + "\n" +
                    "WHERE guild_id = " + guild_id + ";";
            stmt.execute(query);
            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public ArrayList<GuildLookup> getGuilds() {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM guild;";
            ResultSet rs = stmt.executeQuery(query);

            ArrayList<GuildLookup> lookups = new ArrayList<GuildLookup>();

            while (rs.next() != false) {
                lookups.add(new GuildLookup(rs.getLong("guild_id"), rs.getLong("channel_id")));
            }
            stmt.close();
            return lookups;
        }
        catch(SQLException e) {
            return null;
        }
    }

    public GuildLookup getGuild(long guildID) {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM guild\n" +
                    "WHERE guild_id = " + guildID +
                    ";";
            ResultSet rs = stmt.executeQuery(query);

            rs.last();
            GuildLookup out = new GuildLookup(rs.getLong("guild_id"), rs.getLong("channel_id"));
            stmt.close();
            return out;
        }
        catch(SQLException e) {
            return null;
        }
//        catch(NullPointerException e) {
//            return null;
//        }
    }

    public ArrayList<EpicUserLookup> getEpicUsers() {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM epic_users;";
            ResultSet rs = stmt.executeQuery(query);

            ArrayList<EpicUserLookup> lookups = new ArrayList<EpicUserLookup>();

            while (rs.next() != false) {
                lookups.add(new EpicUserLookup(rs.getString("display_name"),
                        rs.getInt("solo_score"), rs.getInt("duo_score"),
                        rs.getInt("squad_score"), rs.getInt("solo_matches_played"),
                        rs.getInt("duo_matches_played"), rs.getInt("squad_matches_played"),
                        rs.getInt("solo_wins"), rs.getInt("duo_wins"),
                        rs.getInt("squad_wins"), rs.getInt("solo_minutes_played"),
                        rs.getInt("duo_minutes_played"), rs.getInt("squad_minutes_played"),
                        rs.getInt("solo_kills"), rs.getInt("duo_kills"),
                        rs.getInt("squad_kills"), rs.getInt("squad_top3"),
                        rs.getInt("squad_top6"), rs.getInt("duo_top12"),
                        rs.getInt("duo_top5"), rs.getInt("solo_top25"),
                        rs.getInt("solo_top10"), rs.getFloat("solo_kill_death_ratio"),
                        rs.getFloat("duo_kill_death_ratio"),
                        rs.getFloat("squad_kill_death_ratio"), rs.getFloat("solo_win_ratio"),
                        rs.getFloat("duo_win_ratio"), rs.getFloat("squad_win_ratio"),
                        rs.getFloat("solo_kills_per_minute"),
                        rs.getFloat("duo_kills_per_minute"),
                        rs.getFloat("squad_kills_per_minute"),
                        rs.getFloat("solo_kills_per_match"), rs.getFloat("duo_kills_per_match"),
                        rs.getFloat("squad_kills_per_match"), rs.getInt("total_matches_played"),
                        rs.getInt("total_minutes_played"), rs.getInt("total_wins"),
                        rs.getInt("total_kills"), rs.getFloat("total_kill_death_ratio"),
                        rs.getFloat("total_win_ratio"), rs.getFloat("total_kills_per_minute"),
                        rs.getFloat("total_kills_per_match")));
            }
            stmt.close();
            return lookups;
        }
        catch(SQLException e) {
            return null;
        }
    }

    public EpicUserLookup getEpicUser(String display_name) {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM epic_users\n" +
                    "WHERE display_name = \"" + display_name + "\";";
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            EpicUserLookup out = new EpicUserLookup(rs.getString("display_name"),
                    rs.getInt("solo_score"), rs.getInt("duo_score"),
                    rs.getInt("squad_score"), rs.getInt("solo_matches_played"),
                    rs.getInt("duo_matches_played"), rs.getInt("squad_matches_played"),
                    rs.getInt("solo_wins"), rs.getInt("duo_wins"),
                    rs.getInt("squad_wins"), rs.getInt("solo_minutes_played"),
                    rs.getInt("duo_minutes_played"), rs.getInt("squad_minutes_played"),
                    rs.getInt("solo_kills"), rs.getInt("duo_kills"),
                    rs.getInt("squad_kills"), rs.getInt("squad_top3"),
                    rs.getInt("squad_top6"), rs.getInt("duo_top12"),
                    rs.getInt("duo_top5"), rs.getInt("solo_top25"),
                    rs.getInt("solo_top10"), rs.getFloat("solo_kill_death_ratio"),
                    rs.getFloat("duo_kill_death_ratio"),
                    rs.getFloat("squad_kill_death_ratio"), rs.getFloat("solo_win_ratio"),
                    rs.getFloat("duo_win_ratio"), rs.getFloat("squad_win_ratio"),
                    rs.getFloat("solo_kills_per_minute"),
                    rs.getFloat("duo_kills_per_minute"),
                    rs.getFloat("squad_kills_per_minute"),
                    rs.getFloat("solo_kills_per_match"), rs.getFloat("duo_kills_per_match"),
                    rs.getFloat("squad_kills_per_match"), rs.getInt("total_matches_played"),
                    rs.getInt("total_minutes_played"), rs.getInt("total_wins"),
                    rs.getInt("total_kills"), rs.getFloat("total_kill_death_ratio"),
                    rs.getFloat("total_win_ratio"), rs.getFloat("total_kills_per_minute"),
                    rs.getFloat("total_kills_per_match"));
            stmt.close();
            return out;
        }
        catch(SQLException e) {
            return null;
        }
    }

    public boolean addUser(EpicUser user) {
        if(user == null || !user.isValidUser()) {
            return false;
        }
        try {
            Stats stats = user.getStats();
            Statement stmt = connection.createStatement();
            String query = "INSERT INTO epic_users (display_name, solo_score, duo_score, squad_score, " +
                    "solo_matches_played, duo_matches_played, squad_matches_played, solo_wins, duo_wins, " +
                    "squad_wins, solo_minutes_played, duo_minutes_played, squad_minutes_played, solo_kills, duo_kills, " +
                    "squad_kills, squad_top3, squad_top6, duo_top12, duo_top5, solo_top25, solo_top10, " +
                    "solo_kill_death_ratio, duo_kill_death_ratio, squad_kill_death_ratio, solo_win_ratio, " +
                    "duo_win_ratio, squad_win_ratio, solo_kills_per_minute, duo_kills_per_minute, " +
                    "squad_kills_per_minute, solo_kills_per_match, duo_kills_per_match, squad_kills_per_match, " +
                    "total_matches_played, total_minutes_played, total_wins, total_kills, total_kill_death_ratio," +
                    "total_win_ratio, total_kills_per_minute, total_kills_per_match)\n" +
                    "VALUES(" + "\"" + user.getDisplayName() + "\"" + ", " + stats.getSoloScore() + ", " + stats.getDuoScore() + "," +
                    stats.getSquadScore() + ","  + stats.getSoloMatchesPlayed() + ", " + stats.getDuoMatchesPlayed() + ", " + stats.getSquadMatchesPlayed() + ", " + stats.getSoloWins() + ", " + stats.getDuoWins() + ", " +
                    stats.getSquadWins() + ", " + stats.getSoloMinutesPlayed() + ", " + stats.getDuoMinutesPlayed() + ", " + stats.getSquadMinutesPlayed() + ", " + stats.getSoloKills() + ", " + stats.getDuoKills() + "," +
                    stats.getSquadKills() + ", " + stats.getSquadTop3() + ", " + stats.getSquadTop6() + ", " + stats.getDuoTop12() + ", " + stats.getDuoTop5() + ", " + stats.getSoloTop25() + ", " + stats.getSoloTop10() + ", " +
                    stats.getSoloKillDeathRatio() + ", " + stats.getDuoKillDeathRatio() + ", " + stats.getSquadKillDeathRatio() + ", " + stats.getSoloWinRatio() + "," +
                    stats.getDuoWinRatio() + ", " + stats.getSquadWinRatio() + ", " + stats.getSoloKillsPerMinute() + ", " + stats.getDuoKillsPerMinute() + ", " +
                    stats.getSquadKillsPerMinute() + ", " + stats.getSoloKillsPerMatch() + ", " + stats.getDuoKillsPerMatch() + ", " + stats.getSquadKillsPerMatch() + ", " +
                    stats.getTotalMatchesPlayed() + "," + stats.getTotalMinutesPlayed() + ", " + stats.getTotalWins() + ", " + stats.getTotalKills() + ", " + stats.getTotalKillDeathRatio() + ", " +
                    stats.getTotalWinRatio() + ", " + stats.getTotalKillsPerMinute() + ", " + stats.getTotalKillsPerMatch() + ")" +
                    ";";

            stmt.execute(query);

            stmt.close();
            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public boolean removeUser(EpicUser user) {
        try {
            Statement stmt = connection.createStatement();
            String query = "DELETE FROM epic_users\n" +
                    "WHERE display_name = \"" + user.getDisplayName() + "\";";

            stmt.execute(query);

            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public boolean updateUserWithCurrentStats(EpicUser user) {
        if(user == null || !user.isValidUser()) {
            return false;
        }
        try {
            Stats stats = user.getStats();
            Statement stmt = connection.createStatement();
            String query = "UPDATE epic_users " +
                    "SET solo_score = " + stats.getSoloScore() + ", duo_score = " + stats.getDuoScore() +
                    ", squad_score = " + stats.getSquadScore() + ", solo_matches_played = " + stats.getSoloMatchesPlayed() +
                    ", duo_matches_played = " + stats.getDuoMatchesPlayed() + ", squad_matches_played = " + stats.getSquadMatchesPlayed() +
                    ", solo_wins = " + stats.getSoloWins() + ", duo_wins = " + stats.getDuoWins() + ", squad_wins = " +
                    stats.getSquadWins() + ", solo_minutes_played = " + stats.getSoloMinutesPlayed() + ", duo_minutes_played = " +
                    stats.getDuoMinutesPlayed() + ", squad_minutes_played = " + stats.getSquadMinutesPlayed() + ", solo_kills = " +
                    stats.getSoloKills() + ", duo_kills = " + stats.getDuoKills() + ", squad_kills = " + stats.getSquadKills() +
                    ", squad_top3 = " + stats.getSquadTop3() + ", squad_top6 = " + stats.getSquadTop6() + ", duo_top12 = " +
                    stats.getDuoTop12() + ", duo_top5 = " + stats.getDuoTop5() + ", solo_top25 = " + stats.getSoloTop25() +
                    ", solo_top10 = " + stats.getSoloTop10() + ", solo_kill_death_ratio = " + stats.getSoloKillDeathRatio() +
                    ", duo_kill_death_ratio = " + stats.getDuoKillDeathRatio() + ", squad_kill_death_ratio = " + stats.getSquadKillDeathRatio() +
                    ", solo_win_ratio = " + stats.getSoloWinRatio() + ", duo_win_ratio = " + stats.getDuoWinRatio() +
                    ", squad_win_ratio = " + stats.getSquadWinRatio() + ", solo_kills_per_minute = " + stats.getSoloKillsPerMinute() +
                    ", duo_kills_per_minute = " + stats.getDuoKillsPerMinute() + ", squad_kills_per_minute = " + stats.getSquadKillsPerMinute() +
                    ", solo_kills_per_match = " + stats.getSoloKillsPerMatch() + ", duo_kills_per_match = " + stats.getDuoKillsPerMatch() +
                    ", squad_kills_per_match = " + stats.getSquadKillsPerMatch() + ", total_matches_played = " + stats.getTotalMatchesPlayed() +
                    ", total_minutes_played = " + stats.getTotalMinutesPlayed() + ", total_wins = " + stats.getTotalWins() +
                    ", total_kills = " + stats.getTotalKills() + ", total_kill_death_ratio = " + stats.getTotalKillDeathRatio() +
                    ", total_win_ratio = " + stats.getTotalWinRatio() + ", total_kills_per_minute = " + stats.getTotalKillsPerMinute() +
                    ", total_kills_per_match = " + stats.getTotalKillsPerMatch() + "\n" +
                    "WHERE display_name = " + "\"" + user.getDisplayName() + "\"" +
                    ";";

            stmt.execute(query);

            stmt.close();
            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public boolean addLinkedUser(EpicUser user, long guildID, long discordID) {
        if(user == null || !user.isValidUser()) {
            return false;
        }
        try {
            Statement stmt = connection.createStatement();
            String query = "INSERT INTO guild_linked_users (guild_id, discord_id, display_name)\n" +
                    "VALUES(" + guildID + ", " + discordID + ", \"" + user.getDisplayName() + "\")" +
                    ";";

            stmt.execute(query);

            stmt.close();
            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public boolean removeLinkedUser(long guildID, long discordID) {
        try {
            Statement stmt = connection.createStatement();
            String query = "DELETE FROM guild_linked_users\n" +
                    "WHERE (guild_id, discord_id) = (" + guildID + ", " + discordID + ")" +
                    ";";

            stmt.execute(query);

            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public LinkedUserLookup getLinkedUser(long guildID, long discordID) {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM guild_linked_users\n" +
                    "WHERE (guild_id, discord_id) = (" + guildID + ", " + discordID + ")" +
                    ";";
            ResultSet rs = stmt.executeQuery(query);

            rs.last();
            LinkedUserLookup out = new LinkedUserLookup(guildID, discordID, rs.getString("display_name"));
            stmt.close();
            return out;
        }
        catch(SQLException e) {
            return null;
        }
    }

    public ArrayList<LinkedUserLookup> getLinkedUsersForDisplayName(String displayName) {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM guild_linked_users\n" +
                    "WHERE display_name = \"" + displayName + "\"" +
                    ";";
            ResultSet rs = stmt.executeQuery(query);

            ArrayList<LinkedUserLookup> lookups = new ArrayList<LinkedUserLookup>();

            while (rs.next() != false) {
                lookups.add(new LinkedUserLookup(rs.getLong("guild_id"),
                        rs.getLong("discord_id"), displayName));
            }
            stmt.close();
            return lookups;
        }
        catch(SQLException e) {
            return null;
        }
    }

    public ArrayList<LinkedUserLookup> getLinkedUsersForGuildID(long guildID) {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM guild_linked_users\n" +
                    "WHERE guild_id = " + guildID +
                    ";";
            ResultSet rs = stmt.executeQuery(query);

            ArrayList<LinkedUserLookup> lookups = new ArrayList<LinkedUserLookup>();

            while (rs.next() != false) {
                lookups.add(new LinkedUserLookup(guildID, rs.getLong("discord_id"),
                        rs.getString("display_name")));
            }
            stmt.close();
            return lookups;
        }
        catch(SQLException e) {
            return null;
        }
    }

    public boolean containsLinkedUser(long guildID, long discordID) {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM guild_linked_users\n" +
                    "WHERE guild_id = " + guildID + " AND " + " discord_id = " + discordID +
                    ";";
            ResultSet rs = stmt.executeQuery(query);
            int count = 0;
            while(rs.next()) {
                count++;
            }
            stmt.close();
            if(count > 0) {
                return true;
            }
            return false;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public boolean containsLinkedUser(long guildID, EpicUser user)
    {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM guild_linked_users\n" +
                    "WHERE guild_id = " + guildID + " AND " + " display_name = \"" + user.getDisplayName() + "\"" +
                    ";";
            ResultSet rs = stmt.executeQuery(query);
            int count = 0;
            while(rs.next()) {
                count++;
            }
            stmt.close();
            if(count > 0) {
                return true;
            }
            return false;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public boolean addSubscribedUser(long guildID, EpicUser user) {
        if(user == null || !user.isValidUser()) {
            return false;
        }
        try {
            Statement stmt = connection.createStatement();
            String query = "INSERT INTO guild_subscribed_users (guild_id, display_name)\n" +
                    "VALUES(" + guildID + ", \"" + user.getDisplayName() + "\")" +
                    ";";

            stmt.execute(query);

            stmt.close();
            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public boolean removeSubscribedUser(long guildID, EpicUser user) {
        try {
            Statement stmt = connection.createStatement();
            String query = "DELETE FROM guild_subscribed_users\n" +
                    "WHERE (guild_id, display_name) = (" + guildID + ", \"" + user.getDisplayName() + "\")" +
                    ";";

            stmt.execute(query);

            stmt.close();
            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public ArrayList<UnlinkedOrSubscribedUserLookup> getSubscribedUsersByDisplayName(String displayName) {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM guild_subscribed_users\n" +
                    "WHERE display_name = \"" + displayName + "\"" +
                    ";";
            ResultSet rs = stmt.executeQuery(query);

            ArrayList<UnlinkedOrSubscribedUserLookup> lookups = new ArrayList<UnlinkedOrSubscribedUserLookup>();

            while (rs.next() != false) {
                lookups.add(new UnlinkedOrSubscribedUserLookup(rs.getLong("guild_id"), displayName));
            }
            stmt.close();
            return lookups;
        }
        catch(SQLException e) {
            return null;
        }
    }

    public ArrayList<UnlinkedOrSubscribedUserLookup> getSubscribedUsersByGuildID(long guildID) {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM guild_subscribed_users\n" +
                    "WHERE guild_id = " + guildID +
                    ";";
            ResultSet rs = stmt.executeQuery(query);

            ArrayList<UnlinkedOrSubscribedUserLookup> lookups = new ArrayList<UnlinkedOrSubscribedUserLookup>();

            while (rs.next() != false) {
                lookups.add(new UnlinkedOrSubscribedUserLookup(guildID, rs.getString("display_name")));
            }
            stmt.close();
            return lookups;
        }
        catch(SQLException e) {
            return null;
        }
    }

    public boolean addUnlinkedUser(long guildID, EpicUser user) {
        if(user == null || !user.isValidUser()) {
            return false;
        }
        try {
            Statement stmt = connection.createStatement();
            String query = "INSERT INTO guild_unlinked_users (guild_id, display_name)\n" +
                    "VALUES(" + guildID + ", \"" + user.getDisplayName() + "\")" +
                    ";";

            stmt.execute(query);

            stmt.close();
            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public boolean removeUnlinkedUser(long guildID, EpicUser user) {
        try {
            Statement stmt = connection.createStatement();
            String query = "DELETE FROM guild_unlinked_users\n" +
                    "WHERE (guild_id, display_name) = (" + guildID + ", \"" + user.getDisplayName() + "\")" +
                    ";";

            stmt.execute(query);

            stmt.close();
            return true;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public ArrayList<UnlinkedOrSubscribedUserLookup> getUnlinkedUsersByGuildID(long guildID) {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM guild_unlinked_users\n" +
                    "WHERE guild_id = " + guildID +
                    ";";
            ResultSet rs = stmt.executeQuery(query);

            ArrayList<UnlinkedOrSubscribedUserLookup> lookups = new ArrayList<UnlinkedOrSubscribedUserLookup>();

            while (rs.next() != false) {
                lookups.add(new UnlinkedOrSubscribedUserLookup(guildID, rs.getString("display_name")));
            }
            stmt.close();
            return lookups;
        }
        catch(SQLException e) {
            return null;
        }
    }

    public boolean containsUnlinkedUser(long guildID, EpicUser user)
    {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM guild_unlinked_users\n" +
                    "WHERE guild_id = " + guildID + " AND " + " display_name = \"" + user.getDisplayName() + "\"" +
                    ";";
            ResultSet rs = stmt.executeQuery(query);
            int count = 0;
            while(rs.next()) {
                count++;
            }
            stmt.close();
            if(count > 0) {
                return true;
            }
            return false;
        }
        catch(SQLException e) {
            return false;
        }
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

}
