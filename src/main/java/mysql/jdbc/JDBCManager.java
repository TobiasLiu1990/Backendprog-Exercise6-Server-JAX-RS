package mysql.jdbc;

import exercise6.server.Item;
import java.sql.*;

public class JDBCManager {

    private final LoginDB user = new LoginDB();
    private final String DB_URL = "jdbc:mysql://localhost:3306/onlinestore";
    private final String USER = user.getUser();
    private final String PW = user.getPw();
    private Connection conn = null;

    public JDBCManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PW);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Method to save an item to database
    public void addItemToDatabase(Item item) {
        String query = "INSERT INTO items (item_name, price, category_id) VALUES (?, ?, ?)";

        try (PreparedStatement pStmt = conn.prepareStatement(query)) {
            pStmt.setString(1, item.getItemName());
            pStmt.setInt(2, item.getPrice());
            pStmt.setString(3, item.getCategory());
            pStmt.executeUpdate();

            System.out.println(item.getItemName() + " added to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to retrieve an item from database by id. (currently only used to print all out in a for-loop in ListItemServlet).
    public Item getItemFromDatabase(int id) {
        String query = "SELECT * FROM items WHERE item_id = ?";

        try (PreparedStatement pStmt = conn.prepareStatement(query)) {
            pStmt.setInt(1, id);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                String itemName = rs.getString(2);
                int itemPrice = rs.getInt(3);
                String itemCategory = findItemsCategoryName(rs.getInt(4));      //Need to find the actual name of the id for category
                return new Item(itemName, itemPrice, itemCategory);
            } else {
                System.out.println("NO ITEM!");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //getItemFromDatabase() does not find the name of the category (only number connected).
    //Need to find the actual name of the category an item is in.
    public String findItemsCategoryName(int categoryId) {
        String query = "SELECT Category.category_name FROM Category " +
                "JOIN Items ON Category.category_id = Items.item_id where Category.category_id = ?";

        try (PreparedStatement pStmt = conn.prepareStatement(query)) {
            pStmt.setInt(1, categoryId);
            ResultSet rs = pStmt.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countAllEntriesInDatabase() {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(item_id) FROM items");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Method to search for item.


    //Method to show a category

}










