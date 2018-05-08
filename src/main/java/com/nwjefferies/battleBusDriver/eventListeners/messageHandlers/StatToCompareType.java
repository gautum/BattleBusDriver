package com.nwjefferies.battleBusDriver.eventListeners.messageHandlers;

public enum StatToCompareType {
    SOLO_TOP10, SOLO_TOP25, DUO_TOP5, DUO_TOP12, SQUAD_TOP3, SQUAD_TOP6,
    SOLO_KILLS, DUO_KILLS, SQUAD_KILLS, TOTAL_KILLS,
    SOLO_MINUTESPLAYED, DUO_MINUTESPLAYED, SQUAD_MINUTESPLAYED, TOTAL_MINUTESPLAYED,
    SOLO_WINS, DUO_WINS, SQUAD_WINS, TOTAL_WINS,
    SOLO_MATCHESPLAYED, DUO_MATCHESPLAYED, SQUAD_MATCHESPLAYED, TOTAL_MATCHESPLAYED,
    SOLO_SCORE, DUO_SCORE, SQUAD_SCORE,
    SOLO_KD, DUO_KD, SQUAD_KD, TOTAL_KD,
    SOLO_WINRATE, DUO_WINRATE, SQUAD_WINRATE, TOTAL_WINRATE,
    SOLO_KILLSPERMINUTE, DUO_KILLSPERMINUTE, SQUAD_KILLSPERMINUTE, TOTAL_KILLSPERMINUTE,
    SOLO_KILLSPERMATCH, DUO_KILLSPERMATCH, SQUAD_KILLSPERMATCH, TOTAL_KILLSPERMATCH;

    public static StatToCompareType getStatType(String gameMode, String statType) throws IllegalArgumentException{
        String strStatTypeToCompare = gameMode.toUpperCase() + "_" + statType.toUpperCase();
        return valueOf(strStatTypeToCompare);
    }

    public static String getBetterLookingString(StatToCompareType statType) {
        switch(statType) {
            case SOLO_TOP10:			return "Top 10";
            case SOLO_TOP25:			return "Top 25";
            case DUO_TOP5:				return "Top 5";
            case DUO_TOP12:				return "Top 12";
            case SQUAD_TOP3:			return "Top 3";
            case SQUAD_TOP6:			return "Top 6";
            case SOLO_KILLS:			return "Solo Kills";
            case DUO_KILLS:				return "Duo Kills";
            case SQUAD_KILLS:			return "Squad Kills";
            case TOTAL_KILLS:			return "Total Kills";
            case SOLO_MINUTESPLAYED:	return "Solo Minutes Played";
            case DUO_MINUTESPLAYED:		return "Duo Minutes Played";
            case SQUAD_MINUTESPLAYED:	return "Squad Minutes Played";
            case TOTAL_MINUTESPLAYED:	return "Total Minutes Played";
            case SOLO_WINS:				return "Solo Wins";
            case DUO_WINS:				return "Duo Wins";
            case SQUAD_WINS:			return "Squad Wins";
            case TOTAL_WINS:			return "Total Wins";
            case SOLO_MATCHESPLAYED:	return "Solo Matches Played";
            case DUO_MATCHESPLAYED:		return "Duo Matches Played";
            case SQUAD_MATCHESPLAYED:	return "Squad Matches Played";
            case TOTAL_MATCHESPLAYED:	return "Total Matches Played";
            case SOLO_SCORE:			return "Solo Score";
            case DUO_SCORE:				return "Duo Score";
            case SQUAD_SCORE:			return "Squad Score";
            case SOLO_KD:				return "Solo K/D";
            case DUO_KD:				return "Duo K/D";
            case SQUAD_KD:				return "Squad K/D";
            case TOTAL_KD:				return "Total K/D";
            case SOLO_WINRATE:			return "Solo Win%";
            case DUO_WINRATE:			return "Duo Win%";
            case SQUAD_WINRATE:			return "Squad Win%";
            case TOTAL_WINRATE:			return "Total Win%";
            case SOLO_KILLSPERMINUTE:	return "Solo Kills/Minute";
            case DUO_KILLSPERMINUTE:	return "Duo Kills/Minute";
            case SQUAD_KILLSPERMINUTE:	return "Squad Kills/Minute";
            case TOTAL_KILLSPERMINUTE:	return "Total Kills/Minute";
            case SOLO_KILLSPERMATCH:	return "Solo Kills/Match";
            case DUO_KILLSPERMATCH:		return "Duo Kills/Match";
            case SQUAD_KILLSPERMATCH:	return "Squad Kills/Match";
            case TOTAL_KILLSPERMATCH:	return "Total Kills/Match";
            default:					return "";
        }
    }
}
