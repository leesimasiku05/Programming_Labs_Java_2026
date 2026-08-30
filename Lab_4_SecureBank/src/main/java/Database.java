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
                    account_number VARCHAR(50) PRIMARY KEY,
                    pin VARCHAR(20) NOT NULL,
                    balance DOUBLE NOT NULL
                )
                """;

        String insertAccount = """
                INSERT OR IGNORE INTO accounts
                (account_number, pin, balance)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = connect();
             PreparedStatement createStatement =
                     connection.prepareStatement(createTable);
             PreparedStatement insertStatement =
                     connection.prepareStatement(insertAccount)) {

            // Create the accounts table
            createStatement.executeUpdate();

            // Account 1
            insertStatement.setString(1, "10001");
            insertStatement.setString(2, "1234");
            insertStatement.setDouble(3, 5000.00);
            insertStatement.executeUpdate();

            // Account 2
            insertStatement.setString(1, "10002");
            insertStatement.setString(2, "5678");
            insertStatement.setDouble(3, 8500.00);
            insertStatement.executeUpdate();

            // Account 3
            insertStatement.setString(1, "10003");
            insertStatement.setString(2, "9999");
            insertStatement.setDouble(3, 12000.00);
            insertStatement.executeUpdate();

            System.out.println("Database ready.");

        } catch (SQLException e) {

            System.out.println("Database setup failed!");
            e.printStackTrace();
        }
    }

    public static Double getBalance(String accountNumber, String pin) {

        String sql = """
                SELECT balance
                FROM accounts
                WHERE account_number = ? AND pin = ?
                """;

        try (Connection connection = connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);
            statement.setString(2, pin);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getDouble("balance");
            }

        } catch (SQLException e) {

            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}


