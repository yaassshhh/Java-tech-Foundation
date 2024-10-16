package com.hexaware.testing;

import com.hexaware.dao.OrderProcessorRepository;
import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.Product;
import org.junit.Before;
import org.junit.Test;
import com.hexaware.util.DBConnection;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ProductDAOTest {

    private OrderProcessorRepository orderProcessor;
    private Connection connection;

    @Before
    public void setUp() {
        connection = DBConnection.getConnection();
        orderProcessor = new OrderProcessorRepositoryImpl(connection);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(1000.00);
        product.setDescription("A high-end laptop");
        product.setStock_quantity(10);

        boolean result = orderProcessor.createProduct(product);

        assertTrue("Product should be created successfully", result);
    }
}

