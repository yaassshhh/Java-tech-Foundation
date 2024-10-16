-- Task 1  Database Design: ----------------------------------------------------------------------------------------

-- 1
CREATE DATABASE TechShop;

USE TechShop;

-- 2,4
CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Phone VARCHAR(20),
    Address VARCHAR(255)
);

CREATE TABLE Products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(100) NOT NULL,
    Description TEXT,
    Price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Orders (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT,
    OrderDate DATETIME NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE OrderDetails (
    OrderDetailID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT,
    ProductID INT,
    Quantity INT NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

CREATE TABLE Inventory (
    InventoryID INT AUTO_INCREMENT PRIMARY KEY,
    ProductID INT,
    QuantityInStock INT NOT NULL,
    LastStockUpdate DATETIME NOT NULL,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

show tables;

-- 5

INSERT INTO Customers (FirstName, LastName, Email, Phone, Address)
VALUES
('Ram','Lal', 'ram@example.com', '1234567890', 'Delhi'),
('Laxman', 'Lal', 'laxman@example.com', '1234566850', 'Bhopal'),
('Bharat', 'Lal', 'bharat@example.com', '1894567890', 'Indore'),
('Hanuman', 'Lal', 'Hanuman@example.com', '1898567890', 'Ayodhya'),
('Krishna', 'Lal', 'Krishna@example.com', '1894578990', 'Dwarika'),
('Bheem', 'Lal', 'bheem@example.com', '1894967890', 'Indore'),
('Arjun', 'Lal', 'arjun@example.com', '1244567890', 'Pune'),
('Sugreev', 'Lal', 'sugreev@example.com', '1894567890', 'Bali'),
('Ganesh', 'Lal', 'ganesh@example.com', '1894563330', 'Kashi'),
('Kartikeya', 'Lal', 'kartikeya@example.com', '8894567890', 'Mumbai');


INSERT INTO Products (ProductName, Description, Price)
VALUES
('Laptop', 'High-performance laptop', 1200.00),
('Smartphone', 'Latest model smartphone', 800.00),
('Headphones', 'Noise-cancelling headphones', 150.00),
('Smartwatch', 'Advanced smartwatch', 250.00),
('Tablet', '10-inch display tablet', 500.00),
('Camera', 'Digital camera with zoom lens', 1000.00),
('Gaming Console', 'Next-gen gaming console', 600.00),
('Bluetooth Speaker', 'Portable Bluetooth speaker', 100.00),
('Keyboard', 'Mechanical keyboard', 80.00),
('Monitor', '24-inch HD monitor', 200.00);

INSERT INTO Orders (CustomerID, OrderDate, TotalAmount)
VALUES
(1, '2024-09-01 10:00:00', 1350.00),
(2, '2024-09-02 11:30:00', 1600.00),
(3, '2024-09-03 12:15:00', 900.00),
(4, '2024-09-04 14:00:00', 150.00),
(5, '2024-09-05 15:30:00', 1800.00),
(6, '2024-09-06 16:45:00', 250.00),
(7, '2024-09-07 17:20:00', 1200.00),
(8, '2024-09-08 18:10:00', 800.00),
(9, '2024-09-09 19:00:00', 300.00),
(10, '2024-09-10 20:05:00', 200.00);

INSERT INTO OrderDetails (OrderID, ProductID, Quantity)
VALUES
(1, 1, 1), 
(1, 3, 2), 
(2, 6, 1), 
(2, 5, 2), 
(3, 7, 1), 
(4, 3, 1), 
(5, 2, 1), 
(5, 4, 1), 
(6, 8, 2), 
(7, 9, 3); 

INSERT INTO Inventory (ProductID, QuantityInStock, LastStockUpdate)
VALUES
(1, 50, '2024-09-01 09:00:00'), 
(2, 100, '2024-09-01 09:30:00'), 
(3, 200, '2024-09-01 10:00:00'), 
(4, 150, '2024-09-01 10:30:00'), 
(5, 80, '2024-09-01 11:00:00'), 
(6, 30, '2024-09-01 11:30:00'), 
(7, 40, '2024-09-01 12:00:00'), 
(8, 300, '2024-09-01 12:30:00'), 
(9, 150, '2024-09-01 13:00:00'), 
(10, 70, '2024-09-01 13:30:00'); 

show tables;
select * from customers, orders, inventory, orderdetails ,products;

-- SELECT TOP 5 * FROM Customers;


-- Task 2  Select, Where, Between, AND, LIKE: -------------------------------------------------------------------------------------------


-- 1
select firstName, email from customers;

-- 2
SELECT 
    o.OrderID,
    o.OrderDate,
    CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName
FROM 
    Orders o
JOIN 
    Customers c ON o.CustomerID = c.CustomerID;

-- 3
INSERT INTO Customers (FirstName, LastName, Email, Phone, Address)
VALUES ('Balram', 'Dau', 'balram@example.com', '1234542890', 'Dwarika');

SET SQL_SAFE_UPDATES = 0;
-- 4
UPDATE Products
SET Price = Price * 1.10;
-- 5
DELETE FROM OrderDetails
WHERE OrderID = 123;

DELETE FROM Orders
WHERE OrderID = 123;

-- 6
INSERT INTO Orders (CustomerID, OrderDate, TotalAmount)
VALUES (1, '2024-09-19 14:30:00', 500.00);

-- 7
UPDATE Customers
SET Email = 'newemail@example.com',
    Address = 'newAddress'
WHERE CustomerID = @customerID;

-- 8

-- 9
DELETE FROM OrderDetails
WHERE OrderID IN (SELECT OrderID FROM Orders WHERE CustomerID = @CustomerID);

DELETE FROM Orders
WHERE CustomerID = @CustomerID;

-- 10
INSERT INTO Products (ProductName, Description, Price)
VALUES ('Wireless Earbuds', 'Bluetooth wireless earbuds with noise cancellation', 99.99);

-- 11
ALTER TABLE Orders ADD Status VARCHAR(50);

UPDATE Orders
SET Status = 'Shipped' /*-- OR 'Pending'*/
WHERE OrderID = @OrderID;

-- 12


-- Task 3 Aggregate functions, Having, Order By, GroupBy and Joins:  ------------------------------------------------------------------------------------------

-- 1
SELECT 
	o.customerID,
    o.OrderID,
    o.OrderDate,
    o.TotalAmount,
    c.FirstName,
    c.LastName,
    c.Email,
    c.Phone,
    c.Address
FROM 
    Orders o
JOIN 
    Customers c ON o.CustomerID = c.CustomerID;
    
-- 2
SELECT 
    p.ProductName,
    SUM(od.Quantity * p.Price) AS TotalRevenue
FROM 
    OrderDetails od
JOIN 
    Products p ON od.ProductID = p.ProductID
GROUP BY 
    p.ProductName;

-- 3
SELECT 
	c.customerID,
    c.FirstName,
    c.LastName,
    c.Email,
    c.Phone,
    c.Address
FROM 
    Customers c
JOIN 
    Orders o ON c.CustomerID = o.CustomerID
GROUP BY 
    c.CustomerID;

-- 4
SELECT 
    p.ProductName,
    SUM(od.Quantity) AS TotalQuantityOrdered
FROM 
    OrderDetails od
JOIN 
    Products p ON od.ProductID = p.ProductID
GROUP BY 
    p.ProductName
ORDER BY 
    TotalQuantityOrdered DESC
LIMIT 1;


-- 5
alter table products
add category Varchar(20);


update  Products
set  Category = 'Electronic Gadgets';

select  ProductName, Category
from Products
where  Category = 'Electronic Gadgets';

-- 6
SELECT 
    c.FirstName,
    c.LastName,
    AVG(o.TotalAmount) AS AverageOrderValue
FROM 
    Customers c
JOIN 
    Orders o ON c.CustomerID = o.CustomerID
GROUP BY 
    c.CustomerID;

-- 7
SELECT 
    o.OrderID,
    c.FirstName,
    c.LastName,
    c.Email,
    c.Phone,
    o.TotalAmount AS TotalRevenue
FROM 
    Orders o
JOIN 
    Customers c ON o.CustomerID = c.CustomerID
ORDER BY 
    o.TotalAmount DESC
LIMIT 1;

-- 8
SELECT 
    p.ProductName,
    COUNT(od.OrderDetailID) AS TimesOrdered
FROM 
    OrderDetails od
JOIN 
    Products p ON od.ProductID = p.ProductID
GROUP BY 
    p.ProductName
ORDER BY 
    TimesOrdered DESC;

-- 9
SELECT 
    c.FirstName,
    c.LastName,
    c.Email,
    c.Phone,
    p.ProductName
FROM 
    Customers c
JOIN 
    Orders o ON c.CustomerID = o.CustomerID
JOIN 
    OrderDetails od ON o.OrderID = od.OrderID
JOIN 
    Products p ON od.ProductID = p.ProductID
WHERE 
    p.ProductName = 'Laptop';

-- 10
SELECT 
    SUM(o.TotalAmount) AS TotalRevenue
FROM 
    Orders o
WHERE 
	o.OrderDate BETWEEN '2024-01-01' AND '2024-12-31';

-- Task 4 -----------------------------------------------------------------------------------------------------------------------------

-- 1
SELECT customer_id, customer_name
FROM customers
WHERE customer_id NOT IN (
    SELECT customer_id
    FROM orders
);

-- 2
SELECT (SELECT COUNT(*) FROM products) AS total_products;

-- 3
SELECT SUM(od.quantity * p.price) AS total_revenue
FROM order_details od
JOIN products p ON od.product_id = p.product_id;

-- 4
SELECT AVG(od.quantity) AS average_quantity
FROM order_details od
JOIN products p ON od.product_id = p.product_id
WHERE p.category = 'Electronic Gadgets';

-- 5
SELECT SUM(od.quantity * p.price) AS total_revenue
FROM order_details od
JOIN orders o ON od.order_id = o.order_id
JOIN products p ON od.product_id = p.product_id
WHERE o.customer_id = '1';


-- 6
SELECT c.customer_id, c.customer_name, COUNT(o.order_id) AS order_count
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name
ORDER BY order_count DESC;

-- 7
SELECT c.customer_id, c.customer_name, COUNT(o.order_id) AS order_count
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name
ORDER BY order_count DESC
LIMIT 10;  

-- 8
SELECT customer_id, customer_name, total_spending
FROM (
    SELECT c.customer_id, c.customer_name, SUM(od.quantity * p.price) AS total_spending
    FROM customers c
    JOIN orders o ON c.customer_id = o.customer_id
    JOIN order_details od ON o.order_id = od.order_id
    JOIN products p ON od.product_id = p.product_id
    WHERE p.category = 'Electronics'  -- Replace with the appropriate category name
    GROUP BY c.customer_id, c.customer_name
) AS customer_spending
ORDER BY total_spending DESC
LIMIT 1;


-- 9
SELECT AVG(total_revenue) AS average_order_value
FROM (
    SELECT o.customer_id, SUM(od.quantity * p.price) AS total_revenue
    FROM orders o
    JOIN order_details od ON o.order_id = od.order_id
    JOIN products p ON od.product_id = p.product_id
    GROUP BY o.customer_id
) AS customer_revenue;

-- 10
SELECT c.customer_id, c.customer_name, COUNT(o.order_id) AS order_count
FROM customers c
LEFT JOIN orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name
ORDER BY order_count DESC;

