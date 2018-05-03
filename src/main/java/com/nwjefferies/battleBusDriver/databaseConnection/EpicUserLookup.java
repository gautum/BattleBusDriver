package com.nwjefferies.battleBusDriver.databaseConnection;

public class EpicUserLookup {
    private String display_name;

    private int solo_score;
    private int duo_score;
    private int squad_score;
    private int solo_matches_played;
    private int duo_matches_played;
    private int squad_matches_played;
    private int solo_wins;
    private int duo_wins;
    private int squad_wins;
    private int solo_minutes_played;
    private int duo_minutes_played;
    private int squad_minutes_played;
    private int solo_kills;
    private int duo_kills;
    private int squad_kills;
    private int squad_top3;
    private int squad_top6;
    private int duo_top12;
    private int duo_top5;
    private int solo_top25;
    private int solo_top10;
    private float solo_kill_death_ratio;
    private float duo_kill_death_ratio;
    private float squad_kill_death_ratio;
    private float solo_win_ratio;
    private float duo_win_ratio;
    private float squad_win_ratio;
    private float solo_kills_per_minute;
    private float duo_kills_per_minute;
    private float squad_kills_per_minute;
    private float solo_kills_per_match;
    private float duo_kills_per_match;
    private float squad_kills_per_match;
    private int total_matches_played;
    private int total_minutes_played;
    private int total_wins;
    private int total_kills;
    private float total_kill_death_ratio;
    private float total_win_ratio;
    private float total_kills_per_minute;
    private float total_kills_per_match;


    EpicUserLookup(String display_name, int solo_score, int duo_score, int squad_score, int solo_matches_played,
                              int duo_matches_played, int squad_matches_played, int solo_wins, int duo_wins,
                              int squad_wins, int solo_minutes_played, int duo_minutes_played, int squad_minutes_played,
                              int solo_kills, int duo_kills, int squad_kills, int squad_top3, int squad_top6,
                              int duo_top12, int duo_top5, int solo_top25, int solo_top10, float solo_kill_death_ratio,
                              float duo_kill_death_ratio, float squad_kill_death_ratio, float solo_win_ratio,
                              float duo_win_ratio, float squad_win_ratio, float solo_kills_per_minute,
                              float duo_kills_per_minute, float squad_kills_per_minute, float solo_kills_per_match,
                              float duo_kills_per_match, float squad_kills_per_match, int total_matches_played,
                              int total_minutes_played, int total_wins, int total_kills, float total_kill_death_ratio,
                              float total_win_ratio, float total_kills_per_minute, float total_kills_per_match) {

        this.display_name = display_name;
        this.solo_score = solo_score;
        this.duo_score = duo_score;
        this.squad_score = squad_score;
        this.solo_matches_played = solo_matches_played;
        this.duo_matches_played = duo_matches_played;
        this.squad_matches_played = squad_matches_played;
        this.solo_wins = solo_wins;
        this.duo_wins = duo_wins;
        this.solo_minutes_played = solo_minutes_played;
        this.squad_wins = squad_wins;
        this.duo_minutes_played = duo_minutes_played;
        this.squad_minutes_played = squad_minutes_played;
        this.solo_kills = solo_kills;
        this.duo_kills = duo_kills;
        this.squad_kills = squad_kills;
        this.squad_top3 = squad_top3;
        this.squad_top6 = squad_top6;
        this.duo_top12 = duo_top12;
        this.duo_top5 = duo_top5;
        this.solo_top25 = solo_top25;
        this.solo_top10 = solo_top10;
        this.solo_kill_death_ratio = solo_kill_death_ratio;
        this.duo_kill_death_ratio = duo_kill_death_ratio;
        this.squad_kill_death_ratio = squad_kill_death_ratio;
        this.solo_win_ratio = solo_win_ratio;
        this.duo_win_ratio = duo_win_ratio;
        this.squad_win_ratio = squad_win_ratio;
        this.solo_kills_per_minute = solo_kills_per_minute;
        this.duo_kills_per_minute = duo_kills_per_minute;
        this.squad_kills_per_minute = squad_kills_per_minute;
        this.solo_kills_per_match = solo_kills_per_match;
        this.duo_kills_per_match = duo_kills_per_match;
        this.squad_kills_per_match = squad_kills_per_match;
        this.total_matches_played = total_matches_played;
        this.total_minutes_played = total_minutes_played;
        this.total_wins = total_wins;
        this.total_kills = total_kills;
        this.total_kill_death_ratio = total_kill_death_ratio;
        this.total_win_ratio = total_win_ratio;
        this.total_kills_per_minute = total_kills_per_minute;
        this.total_kills_per_match = total_kills_per_match;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public int getSolo_score() {
        return solo_score;
    }

    public int getDuo_score() {
        return duo_score;
    }

    public int getSquad_score() {
        return squad_score;
    }

    public int getSolo_matches_played() {
        return solo_matches_played;
    }

    public int getDuo_matches_played() {
        return duo_matches_played;
    }

    public int getSquad_matches_played() {
        return squad_matches_played;
    }

    public int getSolo_wins() {
        return solo_wins;
    }

    public int getDuo_wins() {
        return duo_wins;
    }

    public int getSolo_minutes_played() {
        return solo_minutes_played;
    }

    public int getSquad_wins() {
        return squad_wins;
    }

    public int getDuo_minutes_played() {
        return duo_minutes_played;
    }

    public int getSquad_minutes_played() {
        return squad_minutes_played;
    }

    public int getSolo_kills() {
        return solo_kills;
    }

    public int getDuo_kills() {
        return duo_kills;
    }

    public int getSquad_kills() {
        return squad_kills;
    }

    public int getSquad_top3() {
        return squad_top3;
    }

    public int getSquad_top6() {
        return squad_top6;
    }

    public int getDuo_top12() {
        return duo_top12;
    }

    public int getDuo_top5() {
        return duo_top5;
    }

    public int getSolo_top25() {
        return solo_top25;
    }

    public int getSolo_top10() {
        return solo_top10;
    }

    public float getSolo_kill_death_ratio() {
        return solo_kill_death_ratio;
    }

    public float getDuo_kill_death_ratio() {
        return duo_kill_death_ratio;
    }

    public float getSquad_kill_death_ratio() {
        return squad_kill_death_ratio;
    }

    public float getSolo_win_ratio() {
        return solo_win_ratio;
    }

    public float getDuo_win_ratio() {
        return duo_win_ratio;
    }

    public float getSquad_win_ratio() {
        return squad_win_ratio;
    }

    public float getSolo_kills_per_minute() {
        return solo_kills_per_minute;
    }

    public float getDuo_kills_per_minute() {
        return duo_kills_per_minute;
    }

    public float getSquad_kills_per_minute() {
        return squad_kills_per_minute;
    }

    public float getSolo_kills_per_match() {
        return solo_kills_per_match;
    }

    public float getDuo_kills_per_match() {
        return duo_kills_per_match;
    }

    public float getSquad_kills_per_match() {
        return squad_kills_per_match;
    }

    public int getTotal_matches_played() {
        return total_matches_played;
    }

    public int getTotal_minutes_played() {
        return total_minutes_played;
    }

    public int getTotal_wins() {
        return total_wins;
    }

    public int getTotal_kills() {
        return total_kills;
    }

    public float getTotal_kill_death_ratio() {
        return total_kill_death_ratio;
    }

    public float getTotal_win_ratio() {
        return total_win_ratio;
    }

    public float getTotal_kills_per_minute() {
        return total_kills_per_minute;
    }

    public float getTotal_kills_per_match() {
        return total_kills_per_match;
    }

}
