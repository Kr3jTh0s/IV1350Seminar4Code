package src.main.java.processSale.view;

import java.math.BigDecimal;

import src.main.java.processSale.controller.Controller;

/**
 * The View class represents the user interface layer of the application.
 * It interacts with the Controller to perform operations and display
 * information to the user. This class is responsible for initiating
 * actions and passing user inputs to the Controller.
 */
public class View {
    private final Controller controller; // The controller instance used by this view
    private final InputHandler inputHandler; // A helper class for testing user interactions

    /**
     * Creates a new instance of the View class and sets up the connection
     * with the Controller. The Controller is provided as a parameter and
     * is used to delegate operations initiated by the View.
     *
     * @param contr The controller instance to be used by this view.
     */
    public View(Controller controller) {
        this.controller = controller;
        controller.setView(this);

        // Initialize the test view
        inputHandler = new InputHandler(controller);
    }

    /**
     * Starts the user interaction by delegating to the TestView class.
     */
    public void awaitInputs() {
        inputHandler.awaitStartInputs();
    }

    public void displayTotalPrice(BigDecimal totalPrice) {
        System.out.printf("Sale ended. Total price: %.2f SEK%n", totalPrice);
    }

    public void displayAddedItem(String addedItem) {
        System.out.println(addedItem);
    }
}