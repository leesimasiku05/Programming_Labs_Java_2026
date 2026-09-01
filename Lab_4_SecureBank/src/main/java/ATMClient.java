import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ATMClient {

  // Server connection details
  private static final String SERVER_ADDRESS = "localhost";
  private static final int PORT = 5000;

  public static void main(String[] args) {

    // Create scanner for user input
    Scanner scanner = new Scanner(System.in);

    // Display ATM heading
    System.out.println("================================");
    System.out.println("          SECUREBANK ATM");
    System.out.println("================================");

    // Get account details from the user
    System.out.print("Enter account number: ");
    String accountNumber = scanner.nextLine();

    System.out.print("Enter PIN: ");
    String pin = scanner.nextLine();

    try (
            // Connect to the bank server
            Socket socket =
                    new Socket(
                            SERVER_ADDRESS,
                            PORT
                    );

            // Receive data from the server
            BufferedReader input =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()
                            )
                    );

            // Send data to the server
            PrintWriter output =
                    new PrintWriter(
                            socket.getOutputStream(),
                            true
                    )
    ) {

        // Send account number to the server
        output.println(accountNumber);

        // Send PIN to the server
        output.println(pin);

        // Receive the bank response
        String line;

        System.out.println(
                "\n----- BANK RESPONSE -----"
        );

        // Display the server response
        while ((line = input.readLine()) != null) {
            System.out.println(line);
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

    // Close the scanner
    scanner.close();
  }
}
