package com.hexaware.testing;

import com.hexaware.dao.OrderProcessorRepository;
import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import com.hexaware.util.DBConnection;

import java.sql.Connection;

import static org.junit.Assert.*;

public class CustomerDAOTest {

    private OrderProcessorRepository orderProcessor;
    private Connection connection;

    @Before
    public void setUp() {
        connection = DBConnection.getConnection();
        orderProcessor = new OrderProcessorRepositoryImpl(connection);
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setEmail("alice@example.com");
        customer.setPassword("password123");

        boolean result = orderProcessor.createCustomer(customer);

        assertTrue("Customer should be created successfully", result);
    }
}

}
