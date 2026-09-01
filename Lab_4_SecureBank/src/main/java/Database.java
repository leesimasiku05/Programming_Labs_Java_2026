import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

  // Database connection details
  private static final String URL = "jdbc:sqlite:bank.db";

  // Connects to the database
  public static Connection connect() throws SQLException {
    return DriverManager.getConnection(URL);
  }

  // Sets up the database
  public static void setupDatabase() {

    createAccountsTable();
    insertTestAccounts();

    System.out.println("Database ready.");
  }

  // Creates the accounts table
  private static void createAccountsTable() {

    String createTable = """
            CREATE TABLE IF NOT EXISTS accounts (
                account_number TEXT PRIMARY KEY,
                pin TEXT NOT NULL,
                balance REAL NOT NULL
            )
            """;

    try (
            Connection connection = connect();
            PreparedStatement statement =
                    connection.prepareStatement(createTable)
    ) {

        statement.executeUpdate();

    } catch (SQLException e) {

        System.out.println("Database setup failed!");
        e.printStackTrace();
    }
  }

  // Adds test accounts to the database
  private static void insertTestAccounts() {

    String insertAccount = """
            INSERT OR IGNORE INTO accounts
            (account_number, pin, balance)
            VALUES (?, ?, ?)
            """;

    try (
            Connection connection = connect();
            PreparedStatement statement =
                    connection.prepareStatement(insertAccount)
    ) {

        // Add test accounts
        addAccount(statement, "10001", "1234", 5000.00);
        addAccount(statement, "10002", "5678", 8500.00);
        addAccount(statement, "10003", "9999", 12000.00);

    } catch (SQLException e) {

        System.out.println("Could not add test accounts.");
        e.printStackTrace();
    }
  }

  // Adds one account to the database
  private static void addAccount(
        PreparedStatement statement,
        String accountNumber,
        String pin,
        double balance) throws SQLException {

    statement.setString(1, accountNumber);
    statement.setString(2, pin);
    statement.setDouble(3, balance);
    statement.executeUpdate();
  }

  // Gets the balance for a valid account
  public static Double getBalance(
        String accountNumber,
        String pin) {

    String sql = """
            SELECT balance
            FROM accounts
            WHERE account_number = ? AND pin = ?
            """;

    try (
            Connection connection = connect();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setString(1, accountNumber);
        statement.setString(2, pin);

        try (ResultSet result = statement.executeQuery()) {

            if (result.next()) {
                return result.getDouble("balance");
            }
        }

    } catch (SQLException e) {

        System.out.println(
                "Database error: " + e.getMessage()
        );
    }

    return null;
  }
}
