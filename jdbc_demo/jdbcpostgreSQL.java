import java.sql.*;
//import javax.swing.JOptionPane;

/*
CSCE 315
9-25-2019 Original
2/7/2020 Update for AWS
 */
public class jdbcpostgreSQL {
  public static void main(String args[]) {
    dbSetupExample my = new dbSetupExample();
    String teamNumber = "71"; // Your team number
    String sectionNumber = "906"; // Your section number
    String dbName = "csce331_" + sectionNumber + "_" + teamNumber;
    // Building the connection
    Connection conn = null;
    try {
      Class.forName("org.postgresql.Driver");
      conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName,
          my.user, my.pswd);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    } // end try catch
    String item_name = "";
    try {
      // create a statement object
      System.out.println("Entered Try");
      Statement stmt = conn.createStatement();
      System.out.println("Created Connection");
      // create an SQL statement
      String sqlStatement = "SELECT inventory_name FROM inventory;";
      System.out.println("Made sql statement");
      // send statement to DBMS
      ResultSet result = stmt.executeQuery(sqlStatement);
      System.out.println("Executed query");

      // OUTPUT
      System.out.println("Biscuit count from Database.");
      System.out.println("______________________________________");
      while (result.next()) {
        System.out.println(result.getString("inventory_name"));
      }
    } catch (Exception e) {
      System.out.println("Error accessing Database.");
    }
    // closing the connection
    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch (Exception e) {
      System.out.println("Connection NOT Closed.");
    } // end try catch
  }// end main
}// end Class
