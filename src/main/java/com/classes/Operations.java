package com.classes;

import com.utils.Utils;

public class Operations {

    private String operationID;
    private String typeOfOperation;
    private String date;
    private String time;
    private String description;
    private String personUsername;
    private double operationalMoney;
    private double previousBudget;
    private final Utils utils = new Utils();

    Operations() {}

    public Operations(String operationID, String typeOfOperation, String date, String time, String description, String personUsername, double operationalMoney, double previousBudget) {
        this.operationID = operationID;
        this.typeOfOperation = typeOfOperation;
        this.date = date;
        this.time = time;
        this.description = description;
        this.personUsername = personUsername;
        this.operationalMoney = operationalMoney;
        this.previousBudget = previousBudget;
    }

    Operations(String typeOfOperation, String description, String personUsername, double operationalMoney, double previousBudget) {
        this.operationID = this.utils.generateRandomId();
        this.typeOfOperation = typeOfOperation;
        this.date = this.utils.getCurrentDate();
        this.time = this.utils.getCurrentTime();
        this.description = description;
        this.personUsername = personUsername;
        this.operationalMoney = operationalMoney;
        this.previousBudget = previousBudget;
    }

    public String getOperationID() {
        return operationID;
    }

    public String getTypeOfOperation() {
        return typeOfOperation;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getPersonUsername() {
        return personUsername;
    }

    public double getOperationalMoney() {
        return operationalMoney;
    }

    public double getPreviousBudget() {
        return previousBudget;
    }
}
