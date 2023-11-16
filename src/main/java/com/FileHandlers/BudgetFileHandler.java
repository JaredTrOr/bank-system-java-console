package com.FileHandlers;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BudgetFileHandler {

    private double budget; //Global budget variable
    private final String BUDGET_FILE_PATH = "src/main/java/com/csvfiles/Budget.csv";

    public BudgetFileHandler() {
        setBudgetFromFile();
    }

    public void setBudgetFromFile() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.BUDGET_FILE_PATH));
            try {

                this.budget = Double.parseDouble(reader.readLine());

            } catch (IOException e) {
                System.out.println("Error when reading the budget file: "+e);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error when opening the budget file: "+e);
        }
    }

    public Map<String, Object> deposit(double amount) {

        Map<String, Object> depositFeedback = new HashMap<>();
        depositFeedback.put("success", false);

        if (amount <= 0) {
            depositFeedback.put("message", "Please, write a proper amount of money");
        }

        else {
            try {
                File budgetFile = new File(this.BUDGET_FILE_PATH);
                this.budget += amount;
                PrintWriter writer = new PrintWriter(budgetFile);
                writer.print(this.budget);
                writer.close();

                //Reset the budget global variable
                setBudgetFromFile();

                depositFeedback.put("success", true);
                depositFeedback.put("message", "Deposit made succesfully");
            } catch(FileNotFoundException e) {
                depositFeedback.put("message", "Error when opening the budget file: "+e);
            }
        }

        return depositFeedback;
    }

    public Map<String, Object> withdraw(double amount) {

        Map<String, Object> withdrawFeedback = new HashMap<>();
        withdrawFeedback.put("success", false);

        if (amount > this.budget) {
            withdrawFeedback.put("message", "Please, write a proper amount of money");
        }

        else {
            try {
                File budgetFile = new File(this.BUDGET_FILE_PATH);
                this.budget -= amount;
                PrintWriter writer = new PrintWriter(budgetFile);
                writer.print(this.budget);
                writer.close();

                //Reset the budget global variable
                setBudgetFromFile();

                withdrawFeedback.put("success", true);
                withdrawFeedback.put("message", "Withdrawal made succesfully");
            } catch(FileNotFoundException e) {
                withdrawFeedback.put("message", "Error when opening the budget file: "+e);
            }
        }

        return withdrawFeedback;

    }

    public double getBudget() {
        return budget;
    }
}
