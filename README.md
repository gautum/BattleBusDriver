Click <a href="https://discordapp.com/oauth2/authorize?client_id=418856305592107009&scope=bot&permissions=68608">here</a> to add to server.

<h1>Bot commands:</h1>
<table style="width: 100%">
  <colgroup>
       <col span="1" style="width: 50%;">
       <col span="1" style="width: 50%;">
  </colgroup>
  <tr>
    <td>Help</td>
    <td> !bus help</td>
  </tr>
  <tr>
    <td>Lookup stats for any user by epic username</td>
    <td>!bus stats &lt;username&gt;</td>
  </tr>
  <tr>
    <td>Lookup stats for your linked epic username</td>
    <td>!bus stats</td>
  </tr>
  <tr>
    <td>Link an epic username to your discord account</td>
    <td>!bus link &lt;username&gt;</td>
  </tr>
  <tr>
    <td>Unlink an epic username from your account</td>
    <td>!bus unlink</td>
  </tr>
  <tr>
    <td>Display leaderboard</td>
    <td>!bus leaders &lt;solo/duo/squad&gt; &lt;stat&gt;</td>
  </tr>
</table>
  
<h4>Server owner commands:</h4>
<table>
  <colgroup>
       <col span="1" style="width: 50%;">
       <col span="1" style="width: 50%;">
  </colgroup>
  <tr>
    <td>Set the channel where to send win notifications</td>
    <td>!bus channel</td>
  </tr>
  <tr>
    <td>Add an epic username to the leaderboard</td>
    <td>!bus add &lt;username&gt;</td>
  </tr>
  <tr>
    <td>Remove an epic username from the leaderboard</td>
    <td>!bus remove</td>
  </tr>
</table>


<h1>Features:</h1>
<h4>Leaderboard:</h4>
<p>The leaderboard is a local server-based leaderboard that has all the usernames that have been linked and all the usernames that have been added by the server owner. The leaderboard can be filtered for different stats including wins, kill/death ratio, win percentage, matches played, and much more.</p>
<h4>Linked usernames:</h4>
<p>Usernames that are linked using the "link" command will be able to use the "stats" command without passing in a username evertime. They will also be automatically added to the server's leaderboard and it will display the discord account name next to the linked epic username.</p>
<h4>Unlinked usernames:</h4>
<p>Usernames added by the server owner will only be added to the server's leaderboard. These usernames will be displayed on the leaderboard without a linked discord name next to it. These usernames will not trigger win notifications</p>
<h4>Win notifications:</h4>
<p>The win notifications are sent by the bot after a linked username wins a game. These notifications can be sent to a specific channel specified by the server owner using the "channel" command. A sample notification would look something like this "Congrats to Player for getting another solo win". Note: Win notifications will only appear for usernames that have been linked.</p>
