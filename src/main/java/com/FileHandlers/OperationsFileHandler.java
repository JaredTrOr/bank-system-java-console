package com.FileHandlers;

import com.classes.Operations;

import java.io.*;
import java.util.ArrayList;

public class OperationsFileHandler {
    private final ArrayList<Operations> operationList = new ArrayList<>(); //Global list variable
    private final String BUDGET_FILE_PATH = "src/main/java/com/csvfiles/Operations.csv";

    public OperationsFileHandler() {
        setOperationListFromFile();
    }

    public void setOperationListFromFile() {

        this.operationList.clear();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.BUDGET_FILE_PATH));

            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");

                    Operations operation = new Operations(
                            values[0], values[1], values[2], values[3], values[4], values[5], Double.parseDouble(values[6]),
                            Double.parseDouble(values[7])
                    );

                    this.operationList.add(operation);
                }

            } catch(IOException e) {
                System.out.println("[Error]: There was an error getting the member list");
            }
        } catch (FileNotFoundException e) {
            System.out.println("[Error]: when opening the member file: "+e);
        }
    }

    public String saveOperation(Operations operation) {

        String response = "Operation registered successfully";

        String line = operation.getOperationID()+","+operation.getTypeOfOperation()+","+operation.getDate()+","+operation.getTime()+","+operation.getDescription()+","+operation.getPersonUsername()+','+operation.getOperationalMoney()+','+operation.getPreviousBudget();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.BUDGET_FILE_PATH, true));
            writer.write(line);
            writer.newLine();
            writer.flush();

            setOperationListFromFile();
            return response;

        } catch (FileNotFoundException e) {
            response = "Error when opening the operation file";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    public ArrayList<Operations> getOperationList() {
        return operationList;
    }
}
