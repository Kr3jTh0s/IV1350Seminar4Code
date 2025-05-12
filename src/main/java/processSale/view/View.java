package src.main.java.processSale.view;

import java.math.BigDecimal;
import java.util.Scanner;

import src.main.java.processSale.controller.*;

/**
 * The View class represents the user interface layer of the application.
 * It interacts with the Controller to perform operations and display
 * information to the user. This class is responsible for initiating
 * actions and passing user inputs to the Controller.
 */
public class View {
    private final Controller contr; // The controller instance used by this view
    private final TestView testing; // A helper class for testing user interactions

    /**
     * Creates a new instance of the View class and sets up the connection
     * with the Controller. The Controller is provided as a parameter and
     * is used to delegate operations initiated by the View.
     *
     * @param contr The controller instance to be used by this view.
     */
    public View(Controller contr) {
        this.contr = contr;
        contr.setView(this);
        testing = new TestView(contr, this); // Initialize the test view
    }

    /**
     * Starts the user interaction by delegating to the TestView class.
     */
    public void awaitInputs() {
        testing.awaitStartInputs();
    }

    public void itemNotFound(String itemID) {
        System.out.println("Item not found in inventory: " + itemID);
    }

    public void displayTotalPrice(BigDecimal totalPrice){
        System.out.printf("Sale ended. Total price: %.2f SEK%n", totalPrice);
    }

    public void displayAddedItem(String addedItem) {
        System.out.println(addedItem);
    }
}

/**
 * The TestView class handles user input for testing purposes.
 * It provides a simple console-based interface for interacting with the system.
 */
class TestView {
    private final Controller contr; // The controller instance
    private final View view;        // The main view instance

    private enum StartInputs {
        AUTO, START, EXIT
    }

    private enum RegisterInputs {
        END, EXIT
    }

    /**
     * Creates a new instance of TestView.
     *
     * @param contr The controller instance.
     * @param view  The main view instance.
     */
    public TestView(Controller contr, View view) {
        this.contr = contr;
        this.view = view;
    }

    /**
     * Waits for user input to start the application.
     */
    public void awaitStartInputs() {
        try (Scanner inputScanner = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                System.out.println("Enter: \n" +
                                   "AUTO - to automate real quick\n" +
                                   "START - to start new sale\n" +
                                   "EXIT - to exit program\n");

                String userInput = inputScanner.nextLine();
                if (checkStartInputs(userInput)) {
                    switch (getStartInput(userInput)) {
                        case AUTO -> {
                            autoRegister();
                            exit = true;
                        }
                        case START -> startSale(inputScanner);
                        case EXIT -> exit = true;
                    }
                } else {
                    System.out.println("Invalid input.");
                }
            }
        }
        System.exit(0);
    }

    private boolean checkStartInputs(String input) {
        return isValidInput(input, StartInputs.values());
    }

    private StartInputs getStartInput(String input) {
        return StartInputs.valueOf(input.toUpperCase());
    }

    private void startSale(Scanner inputScanner) {
        contr.startSale();
        awaitRegisterInputs(inputScanner);
    }

    private void awaitRegisterInputs(Scanner inputScanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Enter: \n" +
                               "[item identifier] - to register item\n" +
                               "END - to end sale\n" +
                               "EXIT - to exit program\n");

            String userInput = inputScanner.nextLine();
            if (checkRegisterInputs(userInput)) {
                switch (getRegisterInput(userInput)) {
                    case END -> {
                        contr.endSale("null");
                        awaitPaymentInput(inputScanner);
                        exit = true;
                    }
                    case EXIT -> System.exit(0);
                }
            } else {
                contr.registerItem(userInput);
            }
        }
    }

    private boolean checkRegisterInputs(String input) {
        return isValidInput(input, RegisterInputs.values());
    }

    private RegisterInputs getRegisterInput(String input) {
        return RegisterInputs.valueOf(input.toUpperCase());
    }

    private void awaitPaymentInput(Scanner inputScanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Enter: \n" +
                               "[payment] - to process payment\n");

            String userInput = inputScanner.nextLine();
            if (checkPaymentInputs(userInput)) {
                contr.processSale(new BigDecimal(userInput));
                exit = true;
            } else {
                System.out.println("Invalid payment input.");
            }
        }
    }

    private boolean checkPaymentInputs(String input) {
        try {
            new BigDecimal(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidInput(String input, Enum<?>[] validInputs) {
        for (Enum<?> validInput : validInputs) {
            if (input.equalsIgnoreCase(validInput.name())) {
                return true;
            }
        }
        return false;
    }

    private void autoRegister() {
        contr.startSale();
        contr.registerItem("1");
        contr.registerItem("5");
        contr.registerItem("4");
        contr.registerItem("1");
        contr.registerItem("5");
        contr.registerItem("3");
        contr.endSale("null");
        contr.processSale(BigDecimal.valueOf(700));
    }
}