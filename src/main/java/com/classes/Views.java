package com.classes;

import com.filehandlers.BudgetFileHandler;
import com.filehandlers.MemberFileHandler;
import com.filehandlers.OperationsFileHandler;
import com.utils.Utils;
import com.validations.InputValidations;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Views {
    private final Scanner input;
    private final MemberFileHandler memberFileHandler;
    private final BudgetFileHandler budgetFileHandler;
    private final OperationsFileHandler operationsFileHandler;
    private final InputValidations inputValidations;
    private final Utils utils;


    public Views() {
        this.input = new Scanner(System.in);
        this.memberFileHandler = new MemberFileHandler();
        this.budgetFileHandler = new BudgetFileHandler();
        this.operationsFileHandler = new OperationsFileHandler();
        this.inputValidations = new InputValidations();
        this.utils = new Utils();
    }

    public void displayLandingMenu() {
        boolean exitDisplayLandingMenu = true;

        while (exitDisplayLandingMenu) {
            System.out.println("-----------------------------------------");
            System.out.println("WELCOME TO BANK ACCOUNT MANAGEMENT SYSTEM");
            System.out.println("-----------------------------------------");

            System.out.println("Please choose an option: ");
            System.out.println("1).-Login");
            System.out.println("2).-Exit");
            System.out.println();

            String userInput = this.input.nextLine();

            switch(userInput) {
                case "1": displayLoginMenu(); break;
                case "2":
                    System.out.println("Bye bye...");
                    exitDisplayLandingMenu = false;
                    break;
                default:
                    System.out.println("[Error]: Incorrect option, you are writing something wrong, try again");
                    break;
            }

        }

    }

    private void displayLoginMenu() {
        System.out.println("---------------------------------------");
        System.out.println("LOGIN TO BANK ACCOUNT MANAGEMENT SYSTEM");
        System.out.println("---------------------------------------");

        System.out.print("Username: ");
        String username = this.input.nextLine();

        System.out.print("PIN: ");
        String pin = this.input.nextLine();

        Map<String, Object> response = this.memberFileHandler.login(username, pin);

        if ((boolean)(response.get("success"))) {

            System.out.println("Login successfully!!");

            //It has to be checked if the user logging in is an admin or a user
            //and then display its corresponding view

            Member member = (Member) response.get("member");

            switch (member.getRol()) {
                case 0: displayMemberMenu(member); break;
                case 1: displayAdministratorMenu(member); break;
            }
        }
        else {
            System.out.println("User doesn't exist");
        }
    }

    private void displayAdministratorMenu(Member admin) {

        boolean exitAdministratorMenu = true;

        while (exitAdministratorMenu) {
            System.out.println("---------------------------------------");
            System.out.println("           ADMINISTRATOR MENU          ");
            System.out.println("---------------------------------------");

            System.out.println("ID: "+admin.getMemberID());
            System.out.println("Username: "+admin.getUsername());
            System.out.println("Email: "+admin.getEmail());
            System.out.println("Phone number: "+admin.getPhoneNumber());
            System.out.println();

            System.out.println("Money available: $"+this.budgetFileHandler.getBudget());

            System.out.println();
            System.out.println("Choose the option you want to execute:");
            System.out.println("1).-Withdraw money");
            System.out.println("2).-Deposit money");
            System.out.println("3).-Add a new member");
            System.out.println("4).-List members");
            System.out.println("5).-List operations");
            //System.out.println("6).-Modify account");
            System.out.println("6).-Logout");

            String adminInput = this.input.nextLine();

            switch(adminInput) {
                case "1": displayWithdrawMenu(admin); break;
                case "2": displayDepositMoneyMenu(admin); break;
                case "3": displayAddNewMember(); break;
                case "4": displayMemberList(); break;
                case "5": displayTransactionList(); break;
                //case "6": displayModifyAccountMenu(); break;
                case "6":
                    System.out.println("Bye bye admin...");
                    exitAdministratorMenu = false;
                    break;
                default:
                    System.out.println("[Error]: Incorrect option, you are writing something wrong, try again");
                    break;
            }
        }

    }

    private void displayWithdrawMenu(Member member) {
        System.out.println("WITHDRAW MONEY");
        System.out.println();

        double previousBudget = this.budgetFileHandler.getBudget();
        System.out.println("Money available: $"+previousBudget);
        System.out.println("Write the amount of money you want to withdraw: ");

        Map<String, Object> moneyInput = this.inputValidations.checkDoubleInput(this.input.nextLine());
        boolean isNumber = (boolean)(moneyInput.get("isNumber"));

        if (!isNumber) {
            System.out.println("Please write a number, try again");
        }
        else {

            System.out.println("Description: ");
            String description = this.input.nextLine();

            double amountToWithdraw = (double)(moneyInput.get("number"));

            Map<String, Object> depositFeedback = this.budgetFileHandler.withdraw(amountToWithdraw);

            if ( (boolean) (depositFeedback.get("success")) ) {
                displayTicketTransaction("Withdraw", description, amountToWithdraw, previousBudget);

                Operations operation = new Operations("withdraw", description, member.getUsername(), amountToWithdraw, previousBudget);
                String saveOperationResponse = this.operationsFileHandler.saveOperation(operation);
                System.out.println(saveOperationResponse);

            } else {
                System.out.println(depositFeedback.get("message"));
                System.out.println();
            }

        }

    }

    private void displayDepositMoneyMenu(Member admin) {
        System.out.println("DEPOSIT MONEY");
        System.out.println();

        double previousBudget = this.budgetFileHandler.getBudget();
        System.out.println("Money available: $"+previousBudget);
        System.out.println("Write the amount of money you want to deposit: ");

        Map<String, Object> moneyInput = this.inputValidations.checkDoubleInput(this.input.nextLine());
        boolean isNumber = (boolean)(moneyInput.get("isNumber"));

        if (!isNumber) {
            System.out.println("Please write a number, try again");
        }
        else {

            System.out.println("Description: ");
            String description = this.input.nextLine();

            double amountToDeposit = (double)(moneyInput.get("number"));

            Map<String, Object> depositFeedback = this.budgetFileHandler.deposit(amountToDeposit);

            if ( (boolean) (depositFeedback.get("success")) ) {
                displayTicketTransaction("Deposit", description, amountToDeposit, previousBudget);

                Operations operation = new Operations("deposit", description, admin.getUsername(), amountToDeposit, previousBudget);
                String saveOperationResponse = this.operationsFileHandler.saveOperation(operation);
                System.out.println(saveOperationResponse);
            }

            System.out.println(depositFeedback.get("message"));
            System.out.println();
        }
    }

    private void displayAddNewMember() {
        System.out.println("---------------------------------------");
        System.out.println("           ADD A NEW MEMBER            ");
        System.out.println("---------------------------------------");
        System.out.print("Complete name: ");
        String name = this.input.nextLine();

        System.out.print("Username: ");
        String username = this.input.nextLine();

        System.out.print("Email: ");
        String email = this.input.nextLine();

        System.out.print("Phone number: ");
        String phoneNumber = this.input.nextLine();

        System.out.println("Birthdate \"mm/dd/yyyy\"");
        System.out.print("Day: ");
        String day = this.input.nextLine();

        System.out.print("Month: ");
        String month = this.input.nextLine();

        System.out.print("Year: ");
        String year = this.input.nextLine();

        String birthdate = month + "/" + day + "/" + year;

        boolean exitGenderMenu =  true;
        String gender = "";
        while (exitGenderMenu) {
            System.out.println("Gender");
            System.out.println("1).-Male");
            System.out.println("2).-Female");
            System.out.println("3).-Other");
            gender = this.input.nextLine();

            if (gender.equals("1") || gender.equals("2")) {
                if (gender.equals("1")) gender = "male";
                else gender = "female";
                exitGenderMenu = false;
            }
            else if (gender.equals("3")) {
                gender = "other";
                exitGenderMenu = false;
            }
            else {
                System.out.println("Please select a correct option, try again");
            }
        }


        System.out.println("Occupation: ");
        String occupation = this.input.nextLine();

        boolean exitRolMenu = true;
        String rol = "";
        while(exitRolMenu) {
            System.out.println("Choose a role: ");
            System.out.println("1).-Administrator");
            System.out.println("2).-User");
            rol = this.input.nextLine();

            switch (rol) {
                case "1":
                    exitRolMenu = false;
                    break;
                case "2":
                    rol = "0";
                    exitRolMenu = false;
                    break;
                default:
                    System.out.println("Please select a correct option, try again");
            }
        }

        int rolInt = Integer.parseInt(rol);

        occupation = occupation.toLowerCase();

        //Create the insertion
        Member member = new Member(name, username, email, phoneNumber, birthdate, gender, occupation, rolInt);

        String message = this.memberFileHandler.createMember(member);

        System.out.println(message);
        System.out.println("THE PIN FOR THE MEMBER IS: "+member.getPIN());
        this.input.nextLine();
        System.out.println("ENTER TO CONTINUE");
    }

    private void displayMemberList() {
        System.out.println("---------------------------------------");
        System.out.println("              MEMBER LIST              ");
        System.out.println("---------------------------------------");

        ArrayList<Member> memberList = this.memberFileHandler.getMemberList();

        for (Member member : memberList) {
            System.out.println("ID: "+member.getMemberID());
            System.out.println("USERNAME: "+member.getUsername());
            System.out.println("NAME: "+member.getName());
            System.out.println("EMAIL: "+member.getEmail());
            System.out.println("PIN: "+member.getPIN());
            System.out.println("---------------------------------------");
        }

        System.out.println("ENTER TO CONTINUE");
        this.input.nextLine();

    }

    private void displayTransactionList() {
        System.out.println("---------------------------------------");
        System.out.println("        DISPLAY TRANSACTION LIST       ");
        System.out.println("---------------------------------------");

        ArrayList<Operations> operationList = this.operationsFileHandler.getOperationList();

        for (Operations operation : operationList) {
            System.out.println("Type of operation: "+operation.getTypeOfOperation().toUpperCase());
            System.out.println("Person: "+operation.getPersonUsername());
            System.out.println("Date: "+operation.getDate());
            System.out.println("Time: "+operation.getTime());
            System.out.println("Description: "+operation.getDescription());
            System.out.println();
            System.out.println("Operational money: "+operation.getOperationalMoney());
            System.out.println("Previous budget: "+operation.getPreviousBudget());
            System.out.println("---------------------------------------");
        }
        System.out.println("ENTER TO CONTINUE");
        this.input.nextLine();
    }

    //Demo version still in process...
    private void displayModifyAccountMenu() {
        System.out.println("MODIFY ACCOUNT MENU");
    }

    private void displayTicketTransaction(String typeOfTransaction, String description, double amount, double previousBudget
    ) {

        typeOfTransaction = typeOfTransaction.toUpperCase();

        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("Type of operation: "+typeOfTransaction);
        System.out.println("Date: "+this.utils.getCurrentDate());
        System.out.println("Time: "+this.utils.getCurrentTime());
        System.out.println("Description of the operation: "+description);
        System.out.println();
        String word = typeOfTransaction.equals("DEPOSIT") ? "deposited" : "withdrawn";
        System.out.println("Money "+word+": "+amount);
        System.out.println("Previous budget: "+previousBudget);
        System.out.println();
        System.out.println("Current budget: "+this.budgetFileHandler.getBudget());
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.println("ENTER TO CONTINUE");
        this.input.nextLine();
    }

    private void displayMemberMenu(Member user) {
        boolean exitUserMenu = true;

        while (exitUserMenu) {
            System.out.println("---------------------------------------");
            System.out.println("              MEMBER MENU              ");
            System.out.println("---------------------------------------");
            System.out.println("ID: " + user.getMemberID());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Phone number: " + user.getPhoneNumber());
            System.out.println();

            System.out.println("Money available: $" + this.budgetFileHandler.getBudget());

            System.out.println();
            System.out.println("Choose the option you want to execute:");
            System.out.println("1).-Withdraw money");
            System.out.println("2).-Logout");

            String userInput = this.input.nextLine();

            switch(userInput) {
                case "1": displayWithdrawMenu(user); break;
                case "2":
                    System.out.println("Bye bye user");
                    exitUserMenu = false;
                    break;
            }
        }

    }
}
