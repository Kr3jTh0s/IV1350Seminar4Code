package src.main.java.processSale.view;

import java.math.BigDecimal;
import java.util.Scanner;

import src.main.java.processSale.controller.Controller;

/**
 * The TestView class handles user input for testing purposes.
 * It provides a simple console-based interface for interacting with the system.
 */
class InputHandler {
    private final Controller controller; // The controller instance

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
    public InputHandler(Controller controller) {
        this.controller = controller;
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
        controller.startSale();
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
                        controller.endSale("null");
                        awaitPaymentInput(inputScanner);
                        exit = true;
                    }
                    case EXIT -> System.exit(0);
                }
            } else {
                controller.registerItem(userInput);
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
                controller.processSale(new BigDecimal(userInput));
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
        controller.startSale();
        controller.registerItem("1");
        controller.registerItem("5");
        controller.registerItem("4");
        controller.registerItem("1");
        controller.registerItem("5");
        controller.registerItem("3");
        controller.registerItem("error");
        controller.endSale("null");
        controller.processSale(BigDecimal.valueOf(700));
    }
}