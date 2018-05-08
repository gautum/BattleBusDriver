package com.nwjefferies.battleBusDriver.eventListeners.messageUtils;

public class EmbedField {
    private String fieldName;
    private String fieldValue;
    private boolean inline;
    public EmbedField(String fieldName, boolean inline) {
        this.fieldName = fieldName;
        this.inline = inline;
        this.fieldValue = "";
    }
    public void appendFieldValue(String strToAppend) {
        fieldValue = fieldValue + strToAppend + "\n";
    }
    public String getFieldName() {
        return fieldName;
    }
    public String getFieldValue() {
        return fieldValue;
    }
    public boolean getInline() {
        return inline;
    }
}