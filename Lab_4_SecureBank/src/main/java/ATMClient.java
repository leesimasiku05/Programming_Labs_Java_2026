import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ATMClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("          SECUREBANK ATM");
        System.out.println("================================");

        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

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

            // Send account number
            output.println(accountNumber);

            // Send PIN
            output.println(pin);

            // Receive response
            String line;

            System.out.println(
                    "\n----- BANK RESPONSE -----"
            );

            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {

            System.out.println(
                    "Could not connect to bank server."
            );

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }

        scanner.close();
    }
}