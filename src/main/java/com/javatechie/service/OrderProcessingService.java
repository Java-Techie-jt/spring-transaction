package com.javatechie.service;

import com.javatechie.entity.Order;
import com.javatechie.entity.Product;
import com.javatechie.handler.InventoryHandler;
import com.javatechie.handler.OrderHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderProcessingService {

    private final OrderHandler orderHandler;

    private final InventoryHandler inventoryHandler;

    public OrderProcessingService(OrderHandler orderHandler,
                                  InventoryHandler inventoryHandler) {
        this.orderHandler = orderHandler;
        this.inventoryHandler = inventoryHandler;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Order placeAnOrder(Order order){

        // get product inventory
        Product product = inventoryHandler.getProduct(order.getProductId());

        // validate stock availability <(5)
        validateStockAvailability(order, product);

        // update total price in order entity
        order.setTotalPrice(order.getQuantity() * product.getPrice());

        //save order
        Order saveOrder = orderHandler.saveOrder(order);

        //update stock in inventory
        updateInventoryStock(order, product);

        return saveOrder;
    }

    private static void validateStockAvailability(Order order, Product product) {
        if(order.getQuantity()> product.getStockQuantity()){
            throw new RuntimeException("Insufficient stock !");
        }
    }

    private void updateInventoryStock(Order order, Product product) {
        int availableStock= product.getStockQuantity() - order.getQuantity();
        product.setStockQuantity(availableStock);
        inventoryHandler.updateProductDetails(product);
    }


}
