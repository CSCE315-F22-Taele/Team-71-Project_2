import java.sql.*;
import java.util.Scanner;
import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

/*
CSCE 331
9-28-2022 Lab
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

  /*
   *
   * Connects to PostgreSQL database and populates the database with data from the
   * CSV files
   * 
   * @author: Group 71
   * 
   * @param: String []
   * 
   * @return: Function returns nothing. It is void.
   * 
   * @throws: Function throws nothing.
   */
  static serverGUI sg;
  public static void main(String args[]) {
    sg = new serverGUI();

   
  }// end main

  public void addOrder(){
     // Building the connection with your credentials
     Connection conn = null;
     String teamNumber = "71"; // Your team number
     String sectionNumber = "906"; // Your section number
     String dbName = "csce331_" + sectionNumber + "_" + teamNumber;
     String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
     dbSetup2 myCredentials = new dbSetup2();
 
     // Connecting to the database
     try {
       conn = DriverManager.getConnection(dbConnectionString, dbSetup2.user, dbSetup2.pswd);
     } catch (Exception e) {
       e.printStackTrace();
       System.err.println(e.getClass().getName() + ": " + e.getMessage());
       System.exit(0);
     }
 
     System.out.println("Opened database successfully");
 
     try {
       // create a statement object
       Statement stmt = conn.createStatement();
       String sqlStatement = "";
       
       for(int i = 0; i < sg.cartNames.size(); i++){
        System.out.println(sg.cartNames.get(i));
        sqlStatement = "INSERT INTO saleshistory3 (" + "item," + "cost," + "date" + ")" + " VALUES(" + "'"+sg.cartNames.get(i)+ "'" + "," + sg.cartPrices.get(i) + "," + "'" + sg.date + "'"+ ")";
        stmt.addBatch(sqlStatement);


      }
      for(int i = 0; i < sg.ingredientList.size(); i++){
        sqlStatement = "UPDATE inventory SET inventory_count = inventory_count - 1 WHERE inventory_name = " + "'" + sg.ingredientList.get(i) + "'";
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

  public String viewInventory(){
    String holder = "";
    // Building the connection with your credentials
    Connection conn = null;
    String teamNumber = "71"; // Your team number
    String sectionNumber = "906"; // Your section number
    String dbName = "csce331_" + sectionNumber + "_" + teamNumber;
    String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
    dbSetup2 myCredentials = new dbSetup2();

    // Connecting to the database
    try {
      conn = DriverManager.getConnection(dbConnectionString, dbSetup2.user, dbSetup2.pswd);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    System.out.println("Opened database successfully");

    try {
      // create a statement object
      Statement stmt = conn.createStatement();
      String sqlStatement = "";
      
      sqlStatement = "SELECT * from inventory";
      ResultSet rs =stmt.executeQuery(sqlStatement);
      ResultSetMetaData rm = rs.getMetaData();
      int size = rm.getColumnCount();

      for(int i=1; i <=size; i++){
        holder += rm.getColumnName(i) + " ";
        //System.out.println(rm.getColumnName(i));
      }
      holder += "\n";
      System.out.println();
      while(rs.next()){
        for(int i = 1;i<=size; i++){
          //System.out.println(rs.getString(i));
          holder += rs.getString(i) + "\n";
        }
      
        //System.out.println();
      }
      
    //sqlStatement = "UPDATE inventory SET inventory_count = WHERE inventory_name = " + "'" + sg.ingredientList.get(i) + "'";
     // stmt.addBatch(sqlStatement);
     //stmt.executeBatch();
     
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


  public String changeInventory(){
    String holder = "";
    // Building the connection with your credentials
    Connection conn = null;
    String teamNumber = "71"; // Your team number
    String sectionNumber = "906"; // Your section number
    String dbName = "csce331_" + sectionNumber + "_" + teamNumber;
    String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
    dbSetup2 myCredentials = new dbSetup2();

    // Connecting to the database
    try {
      conn = DriverManager.getConnection(dbConnectionString, dbSetup2.user, dbSetup2.pswd);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    System.out.println("Opened database successfully");

    try {
      // create a statement object
      Statement stmt = conn.createStatement();
      String sqlStatement = "";
      

      sqlStatement = "UPDATE inventory SET inventory_count =" + sg.mg.iNumber + "WHERE inventory_name = " + "'" + sg.mg.iName + "'";
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

  public String viewSales(String date){
    String holder = "";
    // Building the connection with your credentials
    Connection conn = null;
    String teamNumber = "71"; // Your team number
    String sectionNumber = "906"; // Your section number
    String dbName = "csce331_" + sectionNumber + "_" + teamNumber;
    String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
    dbSetup2 myCredentials = new dbSetup2();

    // Connecting to the database
    try {
      conn = DriverManager.getConnection(dbConnectionString, dbSetup2.user, dbSetup2.pswd);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    System.out.println("Opened database successfully");

    try {
      // create a statement object
      Statement stmt = conn.createStatement();
      String sqlStatement = "";
      
      sqlStatement = "SELECT * from saleshistory3 where TO_DATE(DATE, 'MM/DD/YYYY HH:MI') = '" + date + "'" ;

      ResultSet rs =stmt.executeQuery(sqlStatement);
      ResultSetMetaData rm = rs.getMetaData();
      int size = rm.getColumnCount();

      for(int i=1; i <=size; i++){
        //holder += rm.getColumnName(i) + " ";
        //System.out.println(rm.getColumnName(i));
      }
      //holder += "\n";
      System.out.println();
      while(rs.next()){
        for(int i = 1;i<=size; i++){
          //System.out.println(rs.getString(i));
          holder += rs.getString(i) + "\n";
        }
      
        //System.out.println();
      }
      
    //sqlStatement = "UPDATE inventory SET inventory_count = WHERE inventory_name = " + "'" + sg.ingredientList.get(i) + "'";
     // stmt.addBatch(sqlStatement);
     //stmt.executeBatch();
     
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