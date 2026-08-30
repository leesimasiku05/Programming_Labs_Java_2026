import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:sqlite:bank.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void setupDatabase() {

        String createTable = """
                CREATE TABLE IF NOT EXISTS accounts (
                    account_number TEXT PRIMARY KEY,
                    pin TEXT NOT NULL,
                    balance REAL NOT NULL
                )
                """;

        String insertAccount = """
                INSERT OR IGNORE INTO accounts
                (account_number, pin, balance)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = connect()) {

            // Create the table first
            try (PreparedStatement statement =
                         connection.prepareStatement(createTable)) {

                statement.executeUpdate();
            }

            // Insert the test accounts
            try (PreparedStatement statement =
                         connection.prepareStatement(insertAccount)) {

                // Account 1
                statement.setString(1, "10001");
                statement.setString(2, "1234");
                statement.setDouble(3, 5000.00);
                statement.executeUpdate();

                // Account 2
                statement.setString(1, "10002");
                statement.setString(2, "5678");
                statement.setDouble(3, 8500.00);
                statement.executeUpdate();

                // Account 3
                statement.setString(1, "10003");
                statement.setString(2, "9999");
                statement.setDouble(3, 12000.00);
                statement.executeUpdate();
            }

            System.out.println("Database ready.");

        } catch (SQLException e) {

            System.out.println("Database setup failed!");
            e.printStackTrace();
        }
    }

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