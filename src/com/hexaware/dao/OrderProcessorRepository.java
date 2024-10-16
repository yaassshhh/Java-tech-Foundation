package com.hexaware.dao;

import com.hexaware.entity.Cart;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Order;
import com.hexaware.entity.Product;
import com.hexaware.entity.OrderItem;

import java.util.List;
import java.util.Map;

public interface OrderProcessorRepository {
    boolean createProduct(Product product);
    boolean createCustomer(Customer customer);
    boolean deleteProduct(int productId);
    boolean deleteCustomer(int customerId);
    boolean addToCart(Customer customer, Product product, int quantity);
    boolean removeFromCart(int customerId, int productId);
    List<Product> getAllFromCart(Customer customer);
    boolean placeOrder(Customer customer, List<Map<Product, Integer>> productsWithQuantity, String shippingAddress);
    List<Map<Product, Integer>> getOrdersByCustomer(int customerId);
}
