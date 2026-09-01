import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {

  private static final int PORT = 5000;

  public static void main(String[] args) {

    // Set up the database
    Database.setupDatabase();

    // Display server heading
    System.out.println("================================");
    System.out.println("       SECUREBANK SERVER");
    System.out.println("================================");

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {

        // Start the bank server
        System.out.println("Server started on port " + PORT);
        System.out.println("Waiting for ATM clients...");

        while (true) {

            // Wait for an ATM client
            Socket clientSocket = serverSocket.accept();

            System.out.println(
                    "Client connected: "
                    + clientSocket.getInetAddress()
            );

            // Handle the client in a separate thread
            Thread clientThread = new Thread(
                    new ClientHandler(clientSocket)
            );

            clientThread.start();
        }

    } catch (Exception e) {

        // Handle server errors
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
            // Receive data from the ATM
            BufferedReader input =
                    new BufferedReader(
                            new InputStreamReader(
                                    clientSocket.getInputStream()
                            )
                    );

            // Send data to the ATM
            PrintWriter output =
                    new PrintWriter(
                            clientSocket.getOutputStream(),
                            true
                    )
    ) {

        // Get account details
        String accountNumber = input.readLine();
        String pin = input.readLine();

        System.out.println(
                "Login attempt for account: " + accountNumber
        );

        // Check the account details
        Double balance =
                Database.getBalance(accountNumber, pin);

        if (balance != null) {

            // Send successful login response
            output.println(
                    "LOGIN SUCCESSFUL\n"
                    + "Account: " + accountNumber
                    + "\nBalance: ZMW "
                    + String.format("%.2f", balance)
            );

        } else {

            // Send failed login response
            output.println(
                    "LOGIN FAILED\n"
                    + "Invalid account number or PIN."
            );
        }

    } catch (Exception e) {

        // Handle client errors
        System.out.println(
                "Client connection error: "
                + e.getMessage()
        );

    } finally {

        try {
            // Close the client connection
            clientSocket.close();
        } catch (Exception e) {
            // Ignore closing errors
        }
    }
  }

  }

  // ATM client communicates with the bank server
  class ATMClient {

  private static final String SERVER_ADDRESS = "localhost";
  private static final int PORT = 5000;

  public static void main(String[] args) {

    BufferedReader console =
            new BufferedReader(
                    new InputStreamReader(System.in)
            );

    System.out.println("================================");
    System.out.println("          SECUREBANK ATM");
    System.out.println("================================");

    try {

        // Get account details
        System.out.print("Enter account number: ");
        String accountNumber = console.readLine();

        System.out.print("Enter PIN: ");
        String pin = console.readLine();

        // Connect to the bank server
        try (
                Socket socket =
                        new Socket(
                                SERVER_ADDRESS,
                                PORT
                        );

                BufferedReader input =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()
                                )
                        );

                PrintWriter output =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true
                        )
        ) {

            // Send account details
            output.println(accountNumber);
            output.println(pin);

            System.out.println(
                    "\n----- BANK RESPONSE -----"
            );

            // Display the server response
            String line;

            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
        }

    } catch (Exception e) {

        // Handle connection errors
        System.out.println(
                "Could not connect to bank server."
        );

        System.out.println(
                "Error: " + e.getMessage()
        );
    }
  }
}
