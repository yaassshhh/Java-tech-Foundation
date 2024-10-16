package com.hexaware.testing;

import com.hexaware.dao.OrderProcessorRepository;
import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Product;
import org.junit.Before;
import org.junit.Test;
import com.hexaware.util.DBConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class OrderDAOTest {

    private OrderProcessorRepository orderProcessor;
    private Connection connection;

    @Before
    public void setUp() {
        connection = DBConnection.getConnection();
        orderProcessor = new OrderProcessorRepositoryImpl(connection);
    }

    @Test
    public void testPlaceOrder() {
        Customer customer = new Customer(1);  
        List<Map<Product, Integer>> productsWithQuantity = new ArrayList<>();
        Map<Product, Integer> orderItem = new HashMap<>();

        Product product = new Product();
        product.setProduct_id(1);  
        orderItem.put(product, 2); 
        productsWithQuantity.add(orderItem);

        String shippingAddress = "123 Main Street";

        boolean result = orderProcessor.placeOrder(customer, productsWithQuantity, shippingAddress);

        assertTrue("Order should be placed successfully", result);
    }
}

