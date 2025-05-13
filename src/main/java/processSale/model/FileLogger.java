package src.main.java.processSale.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class FileLogger implements Logger {
    private PrintWriter logStream;

    public FileLogger() {
        try {
            logStream = new PrintWriter(new FileWriter("out\\ErrorLog.txt", true), true);
        } catch (IOException e) {
            System.out.println("PRINT ERROR!");
            e.printStackTrace();
        }
    }

    @Override
    public void logItemNotFound(String itemID) {
        logStream.println("Item not found in inventory: " + itemID);
    }
    
    public void logInsufficientPayment(BigDecimal insufficientPayment) {
        logStream.println("Insufficient payemnt. The payed amount is " + insufficientPayment + " below total price.");
    }

    public void logConnectionError(String source) {
        logStream.println("Connection could not be established with " + source + ". Please try again later.\n");
    }
}
