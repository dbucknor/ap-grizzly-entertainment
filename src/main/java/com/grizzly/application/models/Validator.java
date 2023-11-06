package com.grizzly.application.models;

import java.util.List;

public class Validator {
    private String errorMessage;
    private Object value;

    public Validator(Object value) {
        this.value = value;
        System.out.println(value);
    }

    public boolean validate(List<Constraint> constraints) {
        boolean isValid = true;

        for (Constraint c : constraints
        ) {
            c.setValue1(value);
            isValid = c.validate();

            if (!isValid) {
                errorMessage = c.getErrorMessage();
                break;
            }
        }

        return isValid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
