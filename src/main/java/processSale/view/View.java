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
    private final Controller controller;     // The controller instance used by this view
    private final InputHandler inputHandler; // A helper class for testing user interactions

    /**
     * Creates a new instance of the View class and sets up the connection
     * with the Controller. The Controller is provided as a parameter and
     * is used to delegate operations initiated by the View.
     *
     * @param controller The controller instance to be used by this view.
     */
    public View(Controller controller) {
        this.controller = controller;
        controller.setView(this);
        controller.setObservers(new TotalRevenueView(), new TotalRevenueFileOutput());
        inputHandler = new InputHandler(controller);
    }

    /**
     * Starts the user interaction by delegating to the InputHandler class.
     * This method initiates the main input loop for the user interface.
     */
    public void awaitInputs() {
        inputHandler.awaitInputs();
    }

    /**
     * Displays the total price to the user after a sale has ended.
     *
     * @param totalPrice The total price of the sale to display.
     */
    public void displayTotalPrice(BigDecimal totalPrice) {
        System.out.printf("Sale ended. Total price: %.2f SEK%n", totalPrice);
    }

    /**
     * Displays information about an added item to the user.
     *
     * @param addedItem A string describing the item that was added.
     */
    public void displayAddedItem(String addedItem) {
        System.out.println(addedItem);
    }
}