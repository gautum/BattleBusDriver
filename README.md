Click [here](https://discordapp.com/oauth2/authorize?client_id=418856305592107009&scope=bot&permissions=68608) to add to server.

# Bot commands:



|             Command            |                 Description                |
|:------------------------------:|:------------------------------------------:|
|            !bus help           |                    Help                    |
|    !bus stats *epicUsername*   |       Get stats for any epic username      |
|           !bus stats           |     Get stats for your linked username     |
|    !bus link *epicUsername*    |           Link your epic username          |
|           !bus unlink          |          Unlink your epic username         |
| !bus leaders *gameMode* *stat* | Display the leaderboard for any given stat |

#### Available game modes:
solo, duo, squad, total

#### Available stats:
score, wins, winRate, kills, kd, killsPerMinute, killsPerMatch,
matchesPlayed, minutesPlayed, top10, top25, top5, top12, top3, top6

### Server owner commands:

|             Command             |                       Description                       |
|:-------------------------------:|:-------------------------------------------------------:|
|     !bus add *epicUsername*     |           Add any epic username to leaderboard          |
|    !bus remove *epicUsername*   |         Remove an epic username from leaderboard        |
|  !bus subscribe *epicUsername*  |     Receive win notifications from an epic username     |
| !bus unsubscribe *epicUsername* | Stop receiving win notifications from a subscribed user |
|          !bus channel           |            Set channel to the current channel           |

# Features:

#### Leaderboard:

The leaderboard is a local server-based leaderboard that has all the usernames that have been linked and all the usernames that have been added by the server owner. The leaderboard can be filtered for different stats including wins, kill/death ratio, win percentage, matches played, and much more.

#### Linked usernames:

Usernames that are linked using the "link" command will be able to use the "stats" command without passing in a username evertime. They will also be automatically added to the server's leaderboard and it will display the discord account name next to the linked epic username. Linked usernames will also automatically trigger win notifications.

#### Unlinked usernames:

Usernames added by the server owner will only be added to the server's leaderboard. These usernames will be displayed on the leaderboard without a linked discord name next to it. These usernames will not trigger win notifications.

#### Subscribed usernames:
Usernames that are subscribed to by the server owner will trigger win notifications but they will not be placed on the leaderboard. It is possible to subscribe to a username and add the same username to the leaderboard by using both the add and subscribe commands.

#### Win notifications:

The win notifications are sent by the bot after a linked username wins a game. These notifications can be sent to a specific channel specified by the server owner using the "channel" command. A sample notification would look something like this "Congrats to Player for getting another solo win". Note: Win notifications will only appear for usernames that have been linked.
