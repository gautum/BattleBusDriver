package com.nwjefferies.battleBusDriver.eventListeners.messageUtils;

public class Response {
    private EmbedField[] fields;
    private String text;
    private String title;
    private boolean isEmbed;

    public Response(EmbedField[] fields) {
        this.fields = fields;
        this.isEmbed = true;
    }

    public Response(String text) {
        this.text = text;
        this.isEmbed = false;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() { return title; }
    public String getText() { return text; }
    public EmbedField[] getFields() { return fields; }
    public boolean isEmbed() { return isEmbed; }

}
