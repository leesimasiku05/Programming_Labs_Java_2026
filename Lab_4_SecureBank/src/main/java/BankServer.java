import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {

    private static final int PORT = 5000;

    public static void main(String[] args) {

        Database.setupDatabase();

        System.out.println("========================================");
        System.out.println("           SECUREBANK SERVER");
        System.out.println("========================================");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println(
                    "Server started on port " + PORT
            );

            System.out.println(
                    "Waiting for ATM clients..."
            );

            while (true) {

                Socket clientSocket = serverSocket.accept();

                System.out.println(
                        "Client connected: "
                                + clientSocket.getInetAddress()
                );

                Thread clientThread =
                        new Thread(
                                new ClientHandler(clientSocket)
                        );

                clientThread.start();
            }

        } catch (Exception e) {

            System.out.println(
                    "Server error: " + e.getMessage()
            );
        }
    }
}


// Handles communication with one ATM client
class ClientHandler implements Runnable {

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try (
                BufferedReader input =
                        new BufferedReader(
                                new InputStreamReader(
                                        clientSocket.getInputStream()
                                )
                        );

                PrintWriter output =
                        new PrintWriter(
                                clientSocket.getOutputStream(),
                                true
                        )
        ) {

            String requestType = input.readLine();

            switch (requestType) {

                case "CREATE_ACCOUNT":
                    handleCreateAccount(input, output);
                    break;

                case "LOGIN":
                    handleLogin(input, output);
                    break;

                case "CHECK_BALANCE":
                    handleCheckBalance(input, output);
                    break;

                case "DEPOSIT":
                    handleDeposit(input, output);
                    break;

                case "WITHDRAW":
                    handleWithdraw(input, output);
                    break;

                default:
                    output.println("ERROR");
                    break;
            }

        } catch (Exception e) {

            System.out.println(
                    "Client connection error: "
                            + e.getMessage()
            );

        } finally {

            try {
                clientSocket.close();
            } catch (Exception e) {
                // Ignore closing error
            }
        }
    }

    // ==================================
    // CREATE ACCOUNT
    // ==================================

    private void handleCreateAccount(
            BufferedReader input,
            PrintWriter output) throws Exception {

        String accountHolderName = input.readLine();
        String pin = input.readLine();

        String accountNumber =
                Database.createAccount(
                        accountHolderName,
                        pin
                );

        if (accountNumber != null) {

            output.println(
                    "========================================"
            );

            output.println(
                    "       ACCOUNT CREATED SUCCESSFULLY"
            );

            output.println(
                    "========================================"
            );

            output.println(
                    "Account Holder: " + accountHolderName
            );

            output.println(
                    "Account Number: " + accountNumber
            );

            output.println(
                    "Starting Balance: ZMW 0.00"
            );

            output.println(
                    "Please remember your account number."
            );

        } else {

            output.println(
                    "Unable to create account."
            );
        }
    }

    // ==================================
    // LOGIN
    // ==================================

    private void handleLogin(
            BufferedReader input,
            PrintWriter output) throws Exception {

        String accountNumber = input.readLine();
        String pin = input.readLine();

        String accountHolderName =
                Database.authenticateUser(
                        accountNumber,
                        pin
                );

        System.out.println(
                "Login attempt for account: "
                        + accountNumber
        );

        if (accountHolderName != null) {

            output.println("LOGIN_SUCCESS");
            output.println(accountHolderName);

        } else {

            output.println("LOGIN_FAILED");
        }
    }

    // ==================================
    // CHECK BALANCE
    // ==================================

    private void handleCheckBalance(
            BufferedReader input,
            PrintWriter output) throws Exception {

        String accountNumber = input.readLine();

        Double balance =
                Database.getBalance(accountNumber);

        if (balance != null) {
            output.println(balance);
        } else {
            output.println("0");
        }
    }

    // ==================================
    // DEPOSIT
    // ==================================

    private void handleDeposit(
            BufferedReader input,
            PrintWriter output) throws Exception {

        String accountNumber = input.readLine();

        double amount =
                Double.parseDouble(input.readLine());

        boolean successful =
                Database.depositMoney(
                        accountNumber,
                        amount
                );

        if (successful) {

            Double newBalance =
                    Database.getBalance(accountNumber);

            output.println("SUCCESS");
            output.println(newBalance);

        } else {

            output.println("FAILED");
            output.println("0");
        }
    }

    // ==================================
    // WITHDRAW
    // ==================================

    private void handleWithdraw(
            BufferedReader input,
            PrintWriter output) throws Exception {

        String accountNumber = input.readLine();

        double amount =
                Double.parseDouble(input.readLine());

        Double currentBalance =
                Database.getBalance(accountNumber);

        if (currentBalance == null) {

            output.println("FAILED");
            output.println("Account not found.");
            output.println("0");
            return;
        }

        if (amount > currentBalance) {

            output.println("FAILED");
            output.println(
                    "Insufficient funds."
            );
            output.println(currentBalance);
            return;
        }

        boolean successful =
                Database.withdrawMoney(
                        accountNumber,
                        amount
                );

        if (successful) {

            Double newBalance =
                    Database.getBalance(accountNumber);

            output.println("SUCCESS");
            output.println("Withdrawal completed.");
            output.println(newBalance);

        } else {

            output.println("FAILED");
            output.println(
                    "Unable to complete withdrawal."
            );
            output.println(currentBalance);
        }
    }
}