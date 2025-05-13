package src.main.java.processSale.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class TotalRevenueFileOutput implements RevenueObserver {
    private PrintWriter logStream;

    public TotalRevenueFileOutput() {
        try {
            logStream = new PrintWriter(new FileWriter("out\\TotalRevenueFileOutput.txt", true), true);
        } catch (IOException e) {
            System.out.println("PRINT ERROR!");
            e.printStackTrace();
        }
    }

    @Override
    public void logSumOfPayments(BigDecimal totalPrice){
        logStream.printf("New payment recorded. Current cash in register: %.2f SEK%n", totalPrice);
        logStream.close();
    }
}