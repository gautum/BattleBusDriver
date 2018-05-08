package com.nwjefferies.battleBusDriver.eventListeners;


import com.nwjefferies.battleBusDriver.databaseConnection.DatabaseLookupService;
import com.nwjefferies.battleBusDriver.eventListeners.messageHandlers.CommandHandlerContainer;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Command;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.EmbedField;
import com.nwjefferies.battleBusDriver.eventListeners.messageUtils.Response;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.*;

public class MessageListener implements IListener<MessageReceivedEvent> {
    private IDiscordClient client;
    private static final String KEYWORD = "!bus";

    private static CommandHandlerContainer commandHandlerContainer;
    private DatabaseLookupService databaseLookupService;

    public MessageListener(IDiscordClient client, DatabaseLookupService databaseLookupService) {
        this.client = client;
        commandHandlerContainer = new CommandHandlerContainer(client, databaseLookupService);
        this.databaseLookupService = databaseLookupService;
    }

    @Override
    public void handle(MessageReceivedEvent event) {

        if(!event.getMessage().getContent().startsWith(KEYWORD)) {
            return;
        }

        Command command = new Command(event.getMessage(), event.getChannel());
        Response response = commandHandlerContainer.processCommand(command);

        if(response != null) {
            sendResponse(response, event);
        }
    }

    private void sendResponse(Response response, MessageReceivedEvent event) {
        EmbedField [] fields = response.getFields();
        String title = response.getTitle();
        String text = response.getText();

        if(response.isEmbed()) {
            if(fields != null && fields.length > 0) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.withTitle(title);
                for(int i = 0; i < fields.length; i++) {
                    builder.appendField(fields[i].getFieldName(), fields[i].getFieldValue(), fields[i].getInline());
                }
                RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
            }
        }
        else {
            if(text.length() > 0) {
                try {
                    // Builds (sends) and new message in the channel that the original message was sent with the content of the original message.
                    new MessageBuilder(client).withChannel(event.getChannel()).withContent(text).build();
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
    }
}
