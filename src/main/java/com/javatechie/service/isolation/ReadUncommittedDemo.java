package com.javatechie.service.isolation;

import com.javatechie.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadUncommittedDemo {

    @Autowired
    private ProductService productService;

    public void testReadUncommitted(int id) throws InterruptedException {

        // Start Transaction A (Thread 1) to update the stock but not commit, then roll back
        Thread threadA = new Thread(() -> {
            try {
                productService.updateStock(id, 5);  // Change stock to 5
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start Transaction B (Thread 2) to read the stock
        Thread threadB = new Thread(() -> {
            try {
                Thread.sleep(2000);  // Wait a moment to ensure Thread A starts and holds the transaction
                int stock = productService.checkStock(id);  // Read stock during Transaction A
                System.out.println("Stock read by Transaction B: " + stock);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Start the threads
        threadA.start();
        threadB.start();

        // Wait for threads to complete
        threadA.join();
        threadB.join();
    }
}
