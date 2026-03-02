import java.sql.Connection;

import java.util.Scanner;

public class Main{
    public static void main(String [] args){

        Connection conn = DatabaseConnection.getConnection();
        InventoryManager manager = new InventoryManager(conn);

        Scanner sc = new Scanner(System.in);


        while (true) {
            System.out.println("\n=== SMART INVENTORY SYSTEM (SQL DATABASE) ===");
            System.out.println("1. Add Product" );
            System.out.println("2. Search Product");
            System.out.println("3. Check Low Stock");
            System.out.println("4. Delete Product");
            System.out.println("5. View Total Asset Value");
            System.out.println("6. Update Stock");
            System.out.println("7. Show All Stock");
            System.out.println("8. Exit");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Id: "); int id = sc.nextInt();
                    sc.nextLine(); // buffer clear
                    System.out.print("Enter Product Name: "); String name = sc.nextLine();
                    System.out.print("Enter Quantity: "); int qty = sc.nextInt();
                    System.out.print("Enter Price: "); double price = sc.nextDouble();
                    manager.addProduct(id,name,qty,price);
                    break;
                case 2:
                    System.out.println("Enter Product name: ");
                    sc.nextLine();
                    String ProductName = sc.nextLine();
                    manager.searchProduct(ProductName);
                    break;
                case 3:
                    manager.lowStockAlert();
                    break;
                case 4:
                    System.out.println("Enter Product Id you want to delete");
                    int ProductId = sc.nextInt();
                    manager.deleteProduct(ProductId);
                    break;
                case 5:
                    manager.totalStockPrice();
                    break;
                case 6:
                    System.out.println("Enter Product ID: ");
                    int pID = sc.nextInt();
                    System.out.println("Enter new Price: ");
                    int newPrice = sc.nextInt();
                    manager.updateProductInfo(pID,newPrice);
                    break;

                case 7:
                    manager.viewProducts();
                    break;
                case 8:
                    System.out.println("System Closing...");
                    System.exit(0);
                default:
                    System.out.println("Invalid Option!");
            }
        }
    }

 }

