package com.hexaware.main;

import com.hexaware.dao.OrderProcessorRepository;
import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.Cart;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Product;
import com.hexaware.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Main {
    private static OrderProcessorRepository orderProcessor;

    public static void main(String[] args) throws SQLException {
        // Establish a database connection -- "db.properties"
        Connection connection = DBConnection.getConnection();
        orderProcessor = new OrderProcessorRepositoryImpl(connection);
        
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nE-Commerce Application Menu:");
            System.out.println("1. Register Customer");
            System.out.println("2. Create Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Add to Cart");
            System.out.println("5. Remove From Cart");
            System.out.println("6. View Cart");
            System.out.println("7. Place Order");
            System.out.println("8. View Customer Orders");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerCustomer(scanner);
                    break;
                case 2:
                    createProduct(scanner);
                    break;
                case 3:
                    deleteProduct(scanner);
                    break;
                case 4:
                    addToCart(scanner);
                    break;
                case 5:
                	removeFromCart(scanner);
                    break;
                case 6:
                    viewCart(scanner);
                    break;
                case 7:
                    placeOrder(scanner);
                    break;
                case 8:
                    viewCustomerOrders(scanner);
                    break;
                case 9:
                    System.out.println("Bye-Bye");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 8);

        scanner.close();
    }

    private static void registerCustomer(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);

        if (orderProcessor.createCustomer(customer)) {
            System.out.println("Customer registered successfully!");
        } else {
            System.out.println("Failed to register customer.");
        }
    }

    private static void createProduct(Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter stock quantity: ");
        int stockQuantity = scanner.nextInt();

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setStock_quantity(stockQuantity);

        if (orderProcessor.createProduct(product)) {
            System.out.println("Product created successfully!");
        } else {
            System.out.println("Failed to create product.");
        }
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.print("Enter product ID to delete: ");
        int productId = scanner.nextInt();
        if (orderProcessor.deleteProduct(productId)) {
            System.out.println("Product deleted successfully!");
        } else {
            System.out.println("Failed to delete product.");
        }
    }

    private static void addToCart(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        Customer customer = new Customer();
        customer.setCustomer_id(customerId);
        
        Product product = new Product();
        product.setProduct_id(productId);
        
        if (orderProcessor.addToCart(customer, product, quantity)) {
            System.out.println("Product added to cart successfully!");
        } else {
            System.out.println("Failed to add product to cart.");
        }
    }
    private static void removeFromCart(Scanner scanner) {
    	System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        
        System.out.print("Enter product ID to remove: ");
        int productId = scanner.nextInt();
        
        if (orderProcessor.removeFromCart(customerId, productId)) {
            System.out.println("Product removed successfully!");
        } else {
            System.out.println("Failed to removed product.");
        }
    }

    private static void viewCart(Scanner scanner) {
        System.out.print("Enter customer ID to view cart: ");
        int customerId = scanner.nextInt();
        
        Customer customer = new Customer();
        customer.setCustomer_id(customerId);
        
        List<Product> cartItems = orderProcessor.getAllFromCart(customer);
        
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            System.out.println("Products in cart:");
            for (Product product : cartItems) {
                System.out.println("ID: " + product.getProduct_id() + ", Name: " + product.getName() + ", Price: " + product.getPrice());
            }
        }
    }

    private static void placeOrder(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter shipping address: ");
        String shippingAddress = scanner.nextLine();
        
        List<Map<Product, Integer>> productsWithQuantity = new ArrayList<>();
        Map<Product, Integer> orderItem = new HashMap<>();
        
        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        
        Product product = new Product();
        product.setProduct_id(productId);
        
        orderItem.put(product, quantity);
        productsWithQuantity.add(orderItem);

        Customer customer = new Customer(customerId); 
        if (orderProcessor.placeOrder(customer, productsWithQuantity, shippingAddress)) {
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Failed to place order.");
        }
    }

    private static void viewCustomerOrders(Scanner scanner) {
        System.out.print("Enter customer ID to view orders: ");
        int customerId = scanner.nextInt();
        
        List<Map<Product, Integer>> orders = orderProcessor.getOrdersByCustomer(customerId);
        
        if (orders.isEmpty()) {
            System.out.println("No orders found for this customer.");
        } else {
            System.out.println("Orders for customer ID " + customerId + ":");
            for (Map<Product, Integer> order : orders) {
                for (Map.Entry<Product, Integer> entry : order.entrySet()) {
                    System.out.println("Product ID: " + entry.getKey().getProduct_id() + ", Quantity: " + entry.getValue());
                }
            }
        }
    }
}

