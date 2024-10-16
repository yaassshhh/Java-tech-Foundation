package com.hexaware.dao;


import com.hexaware.entity.Cart;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Order;
import com.hexaware.entity.Product;
import com.hexaware.entity.OrderItem;
import com.hexaware.exception.CustomerNotFoundException;
import com.hexaware.exception.ProductNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProcessorRepositoryImpl implements OrderProcessorRepository {
    private Connection connection;

    public OrderProcessorRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    // SQL Queries
    private static final String INSERT_PRODUCT_QUERY = "INSERT INTO products (name, price, description, stock_quantity) VALUES (?, ?, ?, ?)";
    private static final String INSERT_CUSTOMER_QUERY = "INSERT INTO customers (name, email, password) VALUES (?, ?, ?)";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE product_id = ?";
    private static final String DELETE_CUSTOMER_QUERY = "DELETE FROM customers WHERE customer_id = ?";
    private static final String INSERT_TO_CART_QUERY = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
    private static final String DELETE_FROM_CART_QUERY = "DELETE FROM cart WHERE customer_id = ? AND product_id = ?";
    private static final String SELECT_ALL_FROM_CART_QUERY = "SELECT p.product_id, p.name, p.price, p.description, p.stock_quantity, c.quantity " +
            "FROM cart c JOIN products p ON c.product_id = p.product_id WHERE c.customer_id = ?";
    private static final String INSERT_ORDER_QUERY = "INSERT INTO orders (customer_id, total_price, shipping_address) VALUES (?, ?, ?)";
    private static final String INSERT_ORDER_ITEM_QUERY = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
    private static final String SELECT_ORDERS_BY_CUSTOMER_QUERY = "SELECT o.order_id, oi.product_id, oi.quantity " +
            "FROM orders o JOIN order_items oi ON o.order_id = oi.order_id WHERE o.customer_id = ?";

    @Override
    public boolean createProduct(Product product) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_QUERY)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getStock_quantity());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createCustomer(Customer customer) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CUSTOMER_QUERY)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPassword());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int productId) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {
            statement.setInt(1, productId);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_QUERY)) {
            statement.setInt(1, customerId);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addToCart(Customer customer, Product product, int quantity) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_TO_CART_QUERY)) {
            statement.setInt(1, customer.getCustomer_id());
            statement.setInt(2, product.getProduct_id());
            statement.setInt(3, quantity);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeFromCart(int customerId, int productId) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_FROM_CART_QUERY)) {
            statement.setInt(1, customerId);
            statement.setInt(2, productId);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Product> getAllFromCart(Customer customer) {
        List<Product> productsInCart = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_FROM_CART_QUERY)) {
            statement.setInt(1, customer.getCustomer_id());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProduct_id(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setDescription(resultSet.getString("description"));
                product.setStock_quantity(resultSet.getInt("stock_quantity"));
                productsInCart.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productsInCart;
    }

    @Override
    public boolean placeOrder(Customer customer, List<Map<Product, Integer>> productsWithQuantity, String shippingAddress) {
        try {
            connection.setAutoCommit(false);
            double totalPrice = 10.0;

            // Create order
            int orderId;
            try (PreparedStatement orderStatement = connection.prepareStatement(INSERT_ORDER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
                orderStatement.setInt(1, customer.getCustomer_id());
                orderStatement.setDouble(2, totalPrice);
                orderStatement.setString(3, shippingAddress);
                orderStatement.executeUpdate();

                ResultSet generatedKeys = orderStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                } else {
                    throw new Exception("Failed to create order, no ID obtained.");
                }
            }

            // Insert order items
            for (Map<Product, Integer> entry : productsWithQuantity) {
                for (Map.Entry<Product, Integer> productEntry : entry.entrySet()) {
                    Product product = productEntry.getKey();
                    int quantity = productEntry.getValue();
                    try (PreparedStatement orderItemStatement = connection.prepareStatement(INSERT_ORDER_ITEM_QUERY)) {
                        orderItemStatement.setInt(1, orderId);
                        orderItemStatement.setInt(2, product.getProduct_id());
                        orderItemStatement.setInt(3, quantity);
                        orderItemStatement.executeUpdate();
                    }
                }
            }

            // Commit transaction
            connection.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<Map<Product, Integer>> getOrdersByCustomer(int customerId) {
        List<Map<Product, Integer>> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_BY_CUSTOMER_QUERY)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProduct_id(resultSet.getInt("product_id"));

                Map<Product, Integer> orderDetails = new HashMap<>();
                orderDetails.put(product, resultSet.getInt("quantity"));
                orders.add(orderDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
}
