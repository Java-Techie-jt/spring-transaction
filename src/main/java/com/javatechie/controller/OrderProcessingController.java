package com.javatechie.controller;

import com.javatechie.entity.Order;
import com.javatechie.service.OrderProcessingService;
import com.javatechie.service.isolation.ReadCommittedDemo;
import com.javatechie.service.isolation.ReadUncommittedDemo;
import com.javatechie.service.isolation.RepeatableReadDemo;
import com.javatechie.service.isolation.SerializableIsolationDemo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderProcessingController {

    private final OrderProcessingService orderProcessingService;

    private final ReadUncommittedDemo readUncommittedDemo;


    private final ReadCommittedDemo readCommittedDemo;


    private final RepeatableReadDemo repeatableReadDemo;

    private final SerializableIsolationDemo serializableIsolationDemo;


    public OrderProcessingController(OrderProcessingService orderProcessingService,
                                     ReadUncommittedDemo readUncommittedDemo,
                                     ReadCommittedDemo readCommittedDemo,
                                     RepeatableReadDemo repeatableReadDemo,
                                     SerializableIsolationDemo serializableIsolationDemo) {
        this.orderProcessingService = orderProcessingService;
        this.readUncommittedDemo=readUncommittedDemo;
        this.readCommittedDemo=readCommittedDemo;
        this.repeatableReadDemo=repeatableReadDemo;
        this.serializableIsolationDemo=serializableIsolationDemo;
    }

    /**
     * API to place an order
     *
     * @param order the order details
     * @return the processed order with updated total price
     */
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderProcessingService.placeAnOrder(order));
    }


    @GetMapping("/isolation")
    public String testIsolation() throws InterruptedException {
        //readUncommittedDemo.testReadUncommitted(1);
        //readCommittedDemo.testReadCommitted(1);
        //repeatableReadDemo.demonstrateRepeatableRead(1);
        serializableIsolationDemo.testSerializableIsolation(1);
        return "success";
    }


}
