import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {

  private static final int PORT = 5000;

  public static void main(String[] args) {

    // Set up database
    Database.setupDatabase();

    System.out.println("================================");
    System.out.println("       SECUREBANK SERVER");
    System.out.println("================================");

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {

        System.out.println("Server started on port " + PORT);
        System.out.println("Waiting for ATM clients...");

        while (true) {

            Socket clientSocket = serverSocket.accept();

            System.out.println(
                    "Client connected: "
                    + clientSocket.getInetAddress()
            );

            // Handle each client in its own thread
            Thread clientThread = new Thread(
                    new ClientHandler(clientSocket)
            );

            clientThread.start();
        }

    } catch (Exception e) {
        System.out.println("Server error: " + e.getMessage());
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
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );

            PrintWriter output = new PrintWriter(
                    clientSocket.getOutputStream(),
                    true
            )
    ) {

        // Receive account number
        String accountNumber = input.readLine();

        // Receive PIN
        String pin = input.readLine();

        System.out.println(
                "Login attempt for account: " + accountNumber
        );

        // Check database
        Double balance =
                Database.getBalance(accountNumber, pin);

        if (balance != null) {

            output.println(
                    "LOGIN SUCCESSFUL\n"
                    + "Account: " + accountNumber
                    + "\nBalance: ZMW "
                    + String.format("%.2f", balance)
            );

        } else {

            output.println(
                    "LOGIN FAILED\n"
                    + "Invalid account number or PIN."
            );
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
}
