import java.sql.*;

public class InventoryManager {

    private Connection conn;

    InventoryManager(Connection conn){
        this.conn = conn;
    }

    public void addProduct(int id,String name,int quantity, double price){
        String query = "INSERT INTO Products(ProductID,Name,Quantity,Price) VALUES(?,?,?,?)";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setInt(3,quantity);
            preparedStatement.setDouble(4,price);

            preparedStatement.executeUpdate();

            System.out.println("Item added Succesfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewProducts() {
        String query = "SELECT * FROM Products";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // 1. Getting MetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();


            System.out.println("\n------------------------------------------------------------");
            for (int i = 1; i <= columnCount; i++) {
                // %-15s matlab 15 spaces ki jagah left-align ke saath
                System.out.printf("%-15s", rsmd.getColumnName(i).toUpperCase());
            }
            System.out.println("\n------------------------------------------------------------");


            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {

                    System.out.printf("%-15s", rs.getObject(i));
                }
                System.out.println();
            }
            System.out.println("------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateProductInfo(int productID,int price ){
        String query = "UPDATE Products SET Price = ? WHERE ProductID = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1,price);
            preparedStatement.setInt(2,productID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteProduct(int ProductID){
        String query = "DELETE FROM Products WHERE ProductID = ? ";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1,ProductID);
            preparedStatement.executeUpdate();
            System.out.println("Item Deleted Successfully");
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void lowStockAlert(){
        String query = "Select Name from Products where Quantity <= 5 ";
        try( PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet =   preparedStatement.executeQuery()) {


            System.out.print("Items having count <=5 : ");
          while(resultSet.next()){
              System.out.print(resultSet.getString("Name")+" , ");
          }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchProduct(String name){
        String query = "SELECT * FROM Products WHERE Name LIKE ?";
        String cleanString = "%" + name.trim() + "%";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, cleanString);
            ResultSet resultSet = preparedStatement.executeQuery();

           // check to see if there is atleast 1 row
            if (resultSet.next()) {
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();


                System.out.println("\n---------------- Search Results ----------------------------");
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-15s", rsmd.getColumnName(i).toUpperCase());
                }
                System.out.println("\n------------------------------------------------------------");


                do {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.printf("%-15s", resultSet.getObject(i));
                    }
                    System.out.println();
                } while (resultSet.next());

                System.out.println("------------------------------------------------------------");

            } else {
                System.out.println( name + "Not found");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void totalStockPrice(){
        String query = "SELECT Price,Quantity FROM Products";

        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            double sum = 0;
            if(resultSet!=null){
                while(resultSet.next()){
                    sum += resultSet.getDouble("price")*resultSet.getInt("quantity");
                }
            }
            System.out.println("Total Stock Price: "+ sum);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
