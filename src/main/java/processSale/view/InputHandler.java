package src.main.java.processSale.view;

import java.math.BigDecimal;
import java.util.Scanner;

import src.main.java.processSale.controller.Controller;
import src.main.java.processSale.model.ItemNotFoundException;

/**
 * Handles user input for the console-based interface.
 * Provides a simple way to interact with the system for testing.
 */
class InputHandler {
    private final Controller controller;
    private final Outputs out = new Outputs();

    // Enum for main menu options
    private enum StartInputs {
        AUTO, START, EXIT
    }

    // Enum for register menu options
    private enum RegisterInputs {
        END, EXIT
    }

    /**
     * Helper class for printing prompts and messages.
     */
    private static class Outputs {
        void printAwaitStart() {
            System.out.println("""
                    Enter:
                    AUTO - to automate real quick
                    START - to start new sale
                    EXIT - to exit program
                    """);
        }

        void printAwaitRegister() {
            System.out.println("""
                    Enter:
                    [item identifier] - to register item
                    END - to end sale
                    EXIT - to exit program
                    """);
        }

        void printAwaitPayment() {
            System.out.println("Enter: \n[payment] - to process payment\n");
        }

        void printInvalidInput() {
            System.out.println("Invalid input.");
        }
    }

    /**
     * Creates a new InputHandler.
     * 
     * @param controller The controller instance.
     */
    public InputHandler(Controller controller) {
        this.controller = controller;
    }

    /**
     * Main loop for handling user input and navigating between stages.
     */
    public void awaitInputs() {
        try (Scanner scanner = new Scanner(System.in)) {
            int stage = 1;
            while (true) {
                switch (stage) {
                    case 1 -> stage = handleStartMenu(scanner);
                    case 2 -> stage = handleRegisterMenu(scanner);
                    case 3 -> stage = handlePaymentMenu(scanner);
                    case 4 -> stage = autoRegister();
                    case 5 -> System.exit(0);
                }
            }
        }
    }

    /**
     * Handles the start menu input.
     */
    private int handleStartMenu(Scanner scanner) {
        out.printAwaitStart();
        String input = scanner.nextLine().trim();
        if (isValidInput(input, StartInputs.values())) {
            return switch (StartInputs.valueOf(input.toUpperCase())) {
                case AUTO -> 4;
                case START -> {
                    controller.startSale();
                    yield 2;
                }
                case EXIT -> 5;
            };
        }
        out.printInvalidInput();
        return 1;
    }

    /**
     * Handles the register menu input.
     */
    private int handleRegisterMenu(Scanner scanner) {
        out.printAwaitRegister();
        String input = scanner.nextLine().trim();
        if (isValidInput(input, RegisterInputs.values())) {
            return switch (RegisterInputs.valueOf(input.toUpperCase())) {
                case END -> {
                    controller.endSale("null");
                    yield 3;
                }
                case EXIT -> 5;
            };
        }
        try {
            controller.registerItem(input);
        } catch (ItemNotFoundException e) {
            controller.logItemNotFound(e);
        }
        return 2;
    }

    /**
     * Handles the payment input.
     */
    private int handlePaymentMenu(Scanner scanner) {
        out.printAwaitPayment();
        String input = scanner.nextLine().trim();
        if (isValidBigDecimal(input)) {
            controller.processSale(new BigDecimal(input));
            return 1;
        }
        out.printInvalidInput();
        return 3;
    }

    /**
     * Automates a sale for quick testing.
     */
    private int autoRegister() {
        try {
            controller.startSale();
            controller.registerItem("1");
            controller.registerItem("1");
            controller.registerItem("5");
            controller.registerItem("8");
        } catch (ItemNotFoundException e) {
            controller.logItemNotFound(e);
        }
        try {
            controller.registerItem("error");
            controller.endSale("null");
            controller.processSale(BigDecimal.valueOf(300));
            controller.processSale(BigDecimal.valueOf(400));
            controller.startSale();
            controller.registerItem("2");
            controller.endSale("null");
            controller.processSale(BigDecimal.valueOf(20));
        } catch (ItemNotFoundException e) {
            controller.logItemNotFound(e);
        }
        return 1;
    }

    /**
     * Checks if the input matches any value in the given enum.
     */
    private boolean isValidInput(String input, Enum<?>[] validInputs) {
        for (Enum<?> valid : validInputs) {
            if (input.equalsIgnoreCase(valid.name())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the input can be parsed as a BigDecimal.
     */
    private boolean isValidBigDecimal(String input) {
        try {
            new BigDecimal(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}