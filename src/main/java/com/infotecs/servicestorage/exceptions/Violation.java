package com.infotecs.servicestorage.exceptions;

public class Violation {
    private final String type;
    private final String fieldName;
    private final String message;

    public Violation(String type, String fieldName, String message) {
        this.type = type;
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }
}
