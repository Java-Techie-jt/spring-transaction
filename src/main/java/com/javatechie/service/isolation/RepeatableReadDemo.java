package com.javatechie.service.isolation;

import com.javatechie.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepeatableReadDemo {

    @Autowired
    private ProductService productService;

    public void demonstrateRepeatableRead(int productId) throws InterruptedException {

        // Transaction A: Update stock
        Thread transactionA = new Thread(() -> {
            try {
                productService.updateStock(productId, 5); // Update stock to 5
            } catch (Exception e) {
                System.out.println("Transaction A: Exception occurred: " + e.getMessage());
            }
        });

        // Transaction B: Read stock multiple times
        Thread transactionB = new Thread(() -> {
            try {
                productService.fetchStock(productId); // Read stock before and after Transaction A's update
            } catch (Exception e) {
                System.out.println("Transaction B: Exception occurred: " + e.getMessage());
            }
        });

        // Start both transactions
        transactionA.start();
        transactionB.start();

        // Wait for both threads to complete
        transactionA.join();
        transactionB.join();
    }
}
