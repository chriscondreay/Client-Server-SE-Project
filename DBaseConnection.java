package ClientServer;

import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class DBaseConnection {

  private Connection conn = null;
  private Statement stmt = null;
  private ResultSet rset = null;

  String schema = "world";
  private String url = "jdbc:mysql://127.0.0.1:3306/" + schema;

  public DBaseConnection(String username, String password) {
    try {
      conn = DriverManager.getConnection(url, username, password);

      stmt = conn.createStatement();
      rset = stmt.executeQuery("SELECT VERSION()");

      if (rset.next()) {
        System.out.println("MySQL Version: " + rset.getString(1) + "\n=====================\n");
      }
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
    }
  }

  public void accessDatabase() {
    try {
      System.out.println("Original Contents");
      rset = stmt.executeQuery("SELECT * FROM city;");
      printResultSet(rset);

      System.out.println("Inserted Contents");
      int ID = 5000;
      String name = "\'Thousand Oaks\'";
      String cc = "\'USA\'";
      String state = "\'California\'";
      int population = 123453;
      stmt.executeUpdate("INSERT INTO city VALUE(" + ID + ", " + cc + ", " + state + ", " + population + ");");

      rset = stmt.executeQuery("SELECT * FROM city;");
      printResultSet(rset);

      rset = stmt.executeQuery("SELECT * FROM city WHERE Name=" + name + ";");
      rset.next();

      population = Integer.parseInt(rset.getString(i));
      System.out.println("Updated Contents");
      stmt.executeUpdate("UPDATE city SET population=" + (population + 1) + "WHERE Name=" + name + ";");

      rset = stmt.executeQuery("SELECT * FROM city WHERE Name=" + name + ";");
      printResultSet(rset);

      stmt.executeUpdate("SELECT * FROM city WHERE Name=" + name + ";");

      rset = stmt.executeQuery("SELECT * FROM city WHERE Name=" + name + ";");
      System.out.println("Should be blank");
      printResultSet(rset);

    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
    }
  }

  public void printResultSet(ResultSet rset) {
    try {
      ResultSetMetaData rsmd = rset.getMetaData();
      int numberOfColumns = rsmd.getColumnCount();
      System.out.println("Columns: " + numberOfColumns);

      while (rset.next()) {
        for (int i = 1; i < numberOfColumns; i++) {
          System.out.println(rset.getString(i) + "\t");
        }
        System.out.println(rset.getString(numberOfColumns));
      }
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
    }
  }

  public static void main(String[] args) {
    Scanner kb = new Scanner(System.in);
    System.out.println("MySQL username: ");
    String username = kb.next();
    System.out.println("MySQL password: ");
    String password = kb.next();

    DBaseConnection dbc = new DBaseConnection(username, password);
    dbc.accessDatabase();
  }

}
