import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static final String DATABASE_URL =
            "jdbc:sqlite:bank.db";

    // ==================================
    // DATABASE CONNECTION
    // ==================================

    public static Connection connect()
            throws SQLException {

        return DriverManager.getConnection(
                DATABASE_URL
        );
    }

    // ==================================
    // DATABASE SETUP
    // ==================================

    public static void setupDatabase() {

        String createTable = """
                CREATE TABLE IF NOT EXISTS accounts (
                    account_number TEXT PRIMARY KEY,
                    account_holder_name TEXT NOT NULL,
                    pin TEXT NOT NULL,
                    balance REAL NOT NULL
                )
                """;

        try (
                Connection connection = connect();

                PreparedStatement statement =
                        connection.prepareStatement(
                                createTable
                        )
        ) {

            statement.executeUpdate();

            System.out.println(
                    "Database ready."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Database setup failed!"
            );

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    // ==================================
    // CREATE ACCOUNT
    // ==================================

    public static String createAccount(
            String accountHolderName,
            String pin) {

        String accountNumber =
                generateAccountNumber();

        String sql = """
                INSERT INTO accounts
                (account_number, account_holder_name, pin, balance)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection connection = connect();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, accountNumber);
            statement.setString(2, accountHolderName);
            statement.setString(3, pin);
            statement.setDouble(4, 0.00);

            statement.executeUpdate();

            return accountNumber;

        } catch (SQLException e) {

            System.out.println(
                    "Account creation failed: "
                            + e.getMessage()
            );

            return null;
        }
    }

    // ==================================
    // GENERATE ACCOUNT NUMBER
    // ==================================

    private static String generateAccountNumber() {

        String sql =
                "SELECT MAX(CAST(account_number AS INTEGER)) "
                        + "FROM accounts";

        try (
                Connection connection = connect();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            if (result.next()) {

                int highestAccountNumber =
                        result.getInt(1);

                if (result.wasNull()) {
                    return "10001";
                }

                return String.valueOf(
                        highestAccountNumber + 1
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Could not generate account number."
            );
        }

        return "10001";
    }

    // ==================================
    // AUTHENTICATE USER
    // ==================================

    public static String authenticateUser(
            String accountNumber,
            String pin) {

        String sql = """
                SELECT account_holder_name
                FROM accounts
                WHERE account_number = ?
                AND pin = ?
                """;

        try (
                Connection connection = connect();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, accountNumber);
            statement.setString(2, pin);

            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {

                if (result.next()) {

                    return result.getString(
                            "account_holder_name"
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Authentication error: "
                            + e.getMessage()
            );
        }

        return null;
    }

    // ==================================
    // GET BALANCE
    // ==================================

    public static Double getBalance(
            String accountNumber) {

        String sql = """
                SELECT balance
                FROM accounts
                WHERE account_number = ?
                """;

        try (
                Connection connection = connect();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, accountNumber);

            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {

                if (result.next()) {

                    return result.getDouble(
                            "balance"
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database error: "
                            + e.getMessage()
            );
        }

        return null;
    }

    // ==================================
    // DEPOSIT MONEY
    // ==================================

    public static boolean depositMoney(
            String accountNumber,
            double amount) {

        String sql = """
                UPDATE accounts
                SET balance = balance + ?
                WHERE account_number = ?
                """;

        try (
                Connection connection = connect();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setDouble(1, amount);
            statement.setString(2, accountNumber);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Deposit error: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // ==================================
    // WITHDRAW MONEY
    // ==================================

    public static boolean withdrawMoney(
            String accountNumber,
            double amount) {

        String sql = """
                UPDATE accounts
                SET balance = balance - ?
                WHERE account_number = ?
                """;

        try (
                Connection connection = connect();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setDouble(1, amount);
            statement.setString(2, accountNumber);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Withdrawal error: "
                            + e.getMessage()
            );

            return false;
        }
    }
}