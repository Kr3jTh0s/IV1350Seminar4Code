package src.main.java.processSale.startup;

import src.main.java.processSale.controller.*;
import src.main.java.processSale.integration.*;
import src.main.java.processSale.view.*;

/**
 * The Main class is the entry point of the application. It initializes the
 * necessary components such as the printer, inventory system, discount system,
 * and accounting system. These components are passed to the Controller, which
 * manages the application's operations. The View is also initialized to handle
 * user interactions and start the process of awaiting inputs.
 */
public class Main {
    /**
     * The main method initializes the application and starts the user interaction.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("---------- Application Startup ----------");

        // Initialize external systems
        Printer printer = new Printer();
        Inventory inventory = new Inventory();
        Discount discount = new Discount();
        Account account = new Account();

        // Initialize the controller and view
        Controller controller = new Controller(printer, inventory, discount, account);
        View view = new View(controller);

        System.out.println("---------- Startup Complete ----------\n");

        // Start the user interaction
        view.awaitInputs();
    }
}