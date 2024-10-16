package com.yash.excpHandling;

import java.util.InputMismatchException;
import java.util.Scanner;

// -------------Exception for Invalid Amount
class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

//  ------------Exception for Insufficient Funds
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// ------------ Exception for Invalid Menu Choice
class InvalidChoiceException extends Exception {
    public InvalidChoiceException(String message) {
        super(message);
    }
}

// ------------ Exception for Account Not Created
class AccountNotCreatedException extends Exception {
    public AccountNotCreatedException(String message) {
        super(message);
    }
}

// this is the Bank Account Class 
class BankAccount {
    private double balance;

    public BankAccount() {
        this.balance = 0.0;
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        balance += amount;
        System.out.println("Successfully deposited: Rs." + amount);
    }

    public void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdraw amount must be positive.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds. Your current balance is: Rs." + balance);
        }
        balance -= amount;
        System.out.println("Successfully withdrew: Rs." + amount);
    }

    public double getBalance() {
        return balance;
    }
}

//This code consists of main menu in this i have given 5 choices 
//1 . Create a new account 
//2. Deposit the money 
//3. withdraw the money 
//4. Check the account balance  
//5. Exit the choice 

public class Excp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankAccount account = null; // No account created initially
        boolean exit = false;

        while (!exit) {
            try {
                System.out.println("\nBank Account Management System");
                System.out.println("1. Create New Account");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Check Balance");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                
                if (!sc.hasNextInt()) {
                    throw new InputMismatchException("Invalid input. Please enter a number.");
                }

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        if (account == null) {
                            account = new BankAccount();
                            System.out.println("New bank account created.");
                        } else {
                            System.out.println("Account already exists.");
                        }
                        // this will print the message that account has been created ( if not exists) otherwise it will print u have an account.
                        break;
                    case 2:
                        if (account == null) throw new AccountNotCreatedException("No account exists. Please create an account first.");
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = sc.nextDouble();
                        account.deposit(depositAmount);
                        // so this is for depositing the amount and also it will check ( exception) where the person have an account or not if not it will give the message please create an account
                        break;
                    case 3:
                        if (account == null) throw new AccountNotCreatedException("No account exists. Please create an account first.");
                        System.out.print("Enter withdraw amount: ");
                        double withdrawAmount = sc.nextDouble();
                        account.withdraw(withdrawAmount);
                        break;
                        // amount withdrawn from the users.
                    case 4:
                        if (account == null) throw new AccountNotCreatedException("No account exists. Please create an account first.");
                        System.out.println("Current balance: Rs." + account.getBalance());
                        break;
                        // this is for checking the balance.
                        
                    case 5:
                        exit = true;
                        System.out.println("Thank you for using the Bank Account Management System.");
                        break;
                        // in this if person select 5 then it will exits the choices,
                        // if the person select any other number it will throw a message ( enter valid choice)
                    default:
                        throw new InvalidChoiceException("Invalid choice. Please choose a valid option.");
                }
            } catch (AccountNotCreatedException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InvalidAmountException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InsufficientFundsException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InvalidChoiceException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        sc.close();
    }
}
