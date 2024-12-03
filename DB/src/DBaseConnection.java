import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

public class DBaseConnection {

  private Connection conn = null;
  private Statement stmt = null;
  private ResultSet rset = null;

  String schema = "client";
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
        rset = stmt.executeQuery("SELECT * FROM users;");
        printResultSet(rset);

        System.out.println("Inserted Contents");
        String username = "\'johndoe\'";
        String password = "\'password123\'";
        String hashedPassword = "'" + BCrypt.hashpw(password, BCrypt.gensalt());
        
        String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setString(1, username);
        pstmt.setString(2, hashedPassword);
        pstmt.executeUpdate();

        rset = stmt.executeQuery("SELECT * FROM users;");
        printResultSet(rset);

        rset = stmt.executeQuery("SELECT * FROM users WHERE username=" + username + ";");
        rset.next();

        rset = stmt.executeQuery("SELECT * FROM users WHERE username=" + username + ";");
        printResultSet(rset);

        rset = stmt.executeQuery("SELECT * FROM users WHERE username=" + username + ";");
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

