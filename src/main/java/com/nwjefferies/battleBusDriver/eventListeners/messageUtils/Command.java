package com.nwjefferies.battleBusDriver.eventListeners.messageUtils;

import com.nwjefferies.battleBusDriver.eventListeners.TypeOfCommand;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class Command {

    private String [] split;
    private IMessage message;
    private IChannel channel;

    public Command(IMessage message, IChannel channel) {
        split = message.getContent().split("\\s+");
        this.message = message;
        this.channel = channel;
    }

    /*public IMessage getMessage() {
        return message;
    }*/
    public long getAuthorId() {
        return message.getAuthor().getLongID();
    }

    /*public IChannel getChannel() {
        return channel;
    }*/
    public long getGuildId() {
        return channel.getGuild().getLongID();
    }
    public long getChannelId() {
        return channel.getLongID();
    }
    public String getChannelName() {
        return channel.getName();
    }

    public int getNumArguments() {
        return split.length;
    }

    public String[] getSplit() {
        return split;
    }

    public TypeOfCommand getCommandType() {
        try {
            if(split.length == 1) {
                return TypeOfCommand.HELP;
            }

            String firstWord = split[1].toUpperCase();
            return TypeOfCommand.valueOf(firstWord);
        }
        catch(IllegalArgumentException e) {
            return null;
        }
        catch(NullPointerException e) {
            return null;
        }
    }

    public String getArgumentsAfterCommandWithSpaces() {
        String username = "";
        if(split.length > 3) {
            for(int i = 2; i < split.length; i++) {
                username += split[i];
                if(i != split.length-1) {
                    username += " ";
                }
            }
        }
        else {
            username = split[2];
        }
        return username;
    }

    public boolean isServerOwner() {
        return channel.getGuild().getOwner().getLongID() == message.getAuthor().getLongID();
    }

    public boolean isValidCommand() {
        return (getCommandType() != null);
    }
}
