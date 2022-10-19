import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Surfaces a server and manager GUI and allows for modification of the database
 * through the GUI
 * 
 * @author Team_71
 */
public class jdbcpostgreSQL2 {

  // Commands to run this script
  // This will compile all java files in this directory
  // javac *.java
  // This command tells the file where to find the postgres jar which it needs to
  // execute postgres commands, then executes the code

  /* DON"T COPY PASTE WRITE THE COMMANDS IN YOUR TERMINAL MANUALLY */

  // Windows: java -cp ".;postgresql-42.2.8.jar" jdbcpostgreSQL
  // Mac/Linux: java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL

  // MAKE SURE YOU ARE ON VPN or TAMU WIFI TO ACCESS DATABASE

  /**
   *
   * Connects to PostgreSQL database and populates the database with data from the
   * CSV files
   * 
   * 
   * @param String []
   * 
   * @return Function returns nothing. It is void.
   * 
   * @throws
   */
  static serverGUI sg;

  Connection conn = null;
  String teamNumber = "71"; // Your team number
  String sectionNumber = "906"; // Your section number
  String dbName = "csce331_" + sectionNumber + "_" + teamNumber;
  String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;

  public static void main(String args[]) {
    sg = new serverGUI();

  }// end main
    /**
   *
   * Connects to database using credentials at the top of the file.
   * 
   * 
   * @param
   * 
   * @return Function returns nothing. It is void.
   * 
   * @throws
   */

  public void connectToDatabase(){
    try {
      conn = DriverManager.getConnection(dbConnectionString, dbSetup2.user, dbSetup2.pswd);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    System.out.println("Opened database successfully");
  }

  /**
   *
   * Adds an order tp the sales history and updates the inventory count of the
   * associated ingredient item.
   * 
   * 
   * @param
   * 
   * @return Function returns nothing. It is void.
   * 
   * @throws
   */
  public void addOrder() {    
    // Connecting to the database
    connectToDatabase();

    try {
      // create a statement object
      Statement stmt = conn.createStatement();
      String sqlStatement = "";

      for (int i = 0; i < sg.cartNames.size(); i++) {
        System.out.println(sg.cartNames.get(i));
        sqlStatement = "INSERT INTO saleshistory3 (" + "item," + "cost," + "date" + ")" + " VALUES(" + "'"
            + sg.cartNames.get(i) + "'" + "," + sg.cartPrices.get(i) + "," + "'" + sg.date + "'" + ")";
        stmt.addBatch(sqlStatement);

      }
      for (int i = 0; i < sg.ingredientList.size(); i++) {
        sqlStatement = "UPDATE inventory SET inventory_count = inventory_count - 1 WHERE inventory_name = " + "'"
            + sg.ingredientList.get(i) + "'";
        stmt.addBatch(sqlStatement);
      }
      stmt.executeBatch();

      System.out.println("--------------------Query Results--------------------");
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch (Exception e) {
      System.out.println("Connection NOT Closed.");
    }
  }

  /**
   *
   * Returns a string containing all the items in the inventory, and their
   * assocaited count
   * 
   * 
   * @param
   * 
   * @return String.
   * 
   * @throws
   */
  public String viewInventory() {
    connectToDatabase();
    System.out.println("Opened database successfully");

    try {
      // create a statement object
      Statement stmt = conn.createStatement();
      String sqlStatement = "";

      sqlStatement = "SELECT * from inventory";
      ResultSet rs = stmt.executeQuery(sqlStatement);
      ResultSetMetaData rm = rs.getMetaData();
      int size = rm.getColumnCount();

      for (int i = 1; i <= size; i++) {
        holder += rm.getColumnName(i) + " ";
        // System.out.println(rm.getColumnName(i));
      }
      holder += "\n";
      System.out.println();
      while (rs.next()) {
        for (int i = 1; i <= size; i++) {
          // System.out.println(rs.getString(i));
          holder += rs.getString(i) + "\n";
        }

        // System.out.println();
      }

      // sqlStatement = "UPDATE inventory SET inventory_count = WHERE inventory_name =
      // " + "'" + sg.ingredientList.get(i) + "'";
      // stmt.addBatch(sqlStatement);
      // stmt.executeBatch();

      System.out.println("--------------------Query Results--------------------");
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch (Exception e) {
      System.out.println("Connection NOT Closed.");
    }
    return holder;
  }

  /**
   *
   * Updates the inventory count of an item, with user input collected from server
   * GUI interactions.
   * 
   * 
   * @param
   * 
   * @return String.
   * 
   * @throws
   */
  public String changeInventory() {
    String holder = "";
    // Building the connection with your credentials
    connectToDatabase();

    try {
      // create a statement object
      Statement stmt = conn.createStatement();
      String sqlStatement = "";

      sqlStatement = "UPDATE inventory SET inventory_count =" + sg.mg.iNumber + "WHERE inventory_name = " + "'"
          + sg.mg.iName + "'";
      stmt.addBatch(sqlStatement);
      stmt.executeBatch();

      System.out.println("--------------------Query Results--------------------");
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch (Exception e) {
      System.out.println("Connection NOT Closed.");
    }
    return holder;
  }

  /**
   *
   * Accepts a String, date, and returns all sales data from the corresponding
   * inputed date.
   * 
   * 
   * @param String
   * 
   * @return String
   * 
   * @throws
   */
  public String viewSales(String date) {
    String holder = "";
    connectToDatabase();
    try {
      // create a statement object
      Statement stmt = conn.createStatement();
      String sqlStatement = "";

      sqlStatement = "SELECT * from saleshistory3 where TO_DATE(DATE, 'MM/DD/YYYY HH24:MI') = '" + date + "'";

      ResultSet rs = stmt.executeQuery(sqlStatement);
      ResultSetMetaData rm = rs.getMetaData();
      int size = rm.getColumnCount();

      for (int i = 1; i <= size; i++) {
        // holder += rm.getColumnName(i) + " ";
        // System.out.println(rm.getColumnName(i));
      }
      // holder += "\n";
      System.out.println();
      while (rs.next()) {
        for (int i = 1; i <= size; i++) {
          // System.out.println(rs.getString(i));
          holder += rs.getString(i) + "\n";
        }

        // System.out.println();
      }

      // sqlStatement = "UPDATE inventory SET inventory_count = WHERE inventory_name =
      // " + "'" + sg.ingredientList.get(i) + "'";
      // stmt.addBatch(sqlStatement);
      // stmt.executeBatch();

      System.out.println("--------------------Query Results--------------------");
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch (Exception e) {
      System.out.println("Connection NOT Closed.");
    }
    return holder;
  }

  /**
   *
   * Returns a string containing a list of items whose current inventory is less
   * than the item's minimum amount to have around before needing to restock.
   * 
   * 
   * @param
   * 
   * @return String
   * 
   * @throws
   */
  public String viewRestock() {
    String holder = "";
    connectToDatabase();
    try {
      // create a statement object
      Statement stmt = conn.createStatement();
      String sqlStatement = "";

      sqlStatement = "SELECT * from inventory where inventory_count < 10;";
      ResultSet rs = stmt.executeQuery(sqlStatement);
      ResultSetMetaData rm = rs.getMetaData();
      int size = rm.getColumnCount();

      for (int i = 1; i <= size; i++) {
        holder += rm.getColumnName(i) + " ";
        // System.out.println(rm.getColumnName(i));
      }
      holder += "\n";
      System.out.println();
      while (rs.next()) {
        for (int i = 1; i <= size; i++) {
          // System.out.println(rs.getString(i));
          holder += rs.getString(i) + "\n";
        }

        // System.out.println();
      }

      // sqlStatement = "UPDATE inventory SET inventory_count = WHERE inventory_name =
      // " + "'" + sg.ingredientList.get(i) + "'";
      // stmt.addBatch(sqlStatement);
      // stmt.executeBatch();

      System.out.println("--------------------Query Results--------------------");
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch (Exception e) {
      System.out.println("Connection NOT Closed.");
    }
    return holder;
  }

  /**
   *
   * Given a timestamp,returns a tring containing a list of items that only sold
   * less than 10% of
   * their inventory between the timestamp and the current time, assuming no
   * restocks have happened during the window.
   * 
   * 
   * @param
   * 
   * @return String
   * 
   * @throws
   */
  public String viewTenPercentIngredients() {
    String holder = "";
    // Building the connection with your credentials
    connectToDatabase();

    try {
      // create a statement object
      Statement stmt = conn.createStatement();
      String sqlStatement = "";

      sqlStatement = "SELECT inventory_name from inventory where inventory_count > inventory_original * 0.9;";
      stmt.addBatch(sqlStatement);
      ResultSet rs = stmt.executeQuery(sqlStatement);
      ResultSetMetaData rm = rs.getMetaData();
      int size = rm.getColumnCount();

      for (int i = 1; i <= size; i++) {
        // holder += rm.getColumnName(i) + " ";
        // System.out.println(rm.getColumnName(i));
      }
      holder += "\n";
      System.out.println();
      while (rs.next()) {
        for (int i = 1; i <= size; i++) {
          // System.out.println(rs.getString(i));
          holder += rs.getString(i) + "," + "\n";
        }
      }

      System.out.println("--------------------Query Results--------------------");
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch (Exception e) {
      System.out.println("Connection NOT Closed.");
    }
    return holder;
  }

}