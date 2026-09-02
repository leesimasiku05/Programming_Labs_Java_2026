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

        System.out.println("========================================");
        System.out.println("           WELCOME TO SECUREBANK");
        System.out.println("========================================");

        while (true) {

            System.out.println();
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("----------------------------------------");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    createAccount(scanner);
                    break;

                case "2":
                    login(scanner);
                    break;

                case "3":
                    System.out.println();
                    System.out.println("Thank you for using SecureBank.");
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println();
                    System.out.println("Invalid choice.");
                    System.out.println("Please select an option from 1 to 3.");
            }
        }
    }

    // ==============================
    // CREATE ACCOUNT
    // ==============================

    private static void createAccount(Scanner scanner) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("           CREATE ACCOUNT");
        System.out.println("========================================");

        System.out.print("Enter your full name: ");
        String accountHolderName = scanner.nextLine();

        System.out.print("Create a PIN: ");
        String pin = scanner.nextLine();

        System.out.print("Confirm your PIN: ");
        String confirmPin = scanner.nextLine();

        if (!pin.equals(confirmPin)) {

            System.out.println();
            System.out.println("PINs do not match.");
            System.out.println("Account creation cancelled.");
            return;
        }

        try (
                Socket socket = new Socket(SERVER_ADDRESS, PORT);

                BufferedReader input = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()
                        )
                );

                PrintWriter output = new PrintWriter(
                        socket.getOutputStream(),
                        true
                )
        ) {

            // Tell server we want to create an account
            output.println("CREATE_ACCOUNT");

            output.println(accountHolderName);
            output.println(pin);

            System.out.println();

            String response;

            while ((response = input.readLine()) != null) {
                System.out.println(response);
            }

        } catch (Exception e) {

            System.out.println();
            System.out.println("Could not connect to bank server.");
            System.out.println("Make sure the server is running.");
        }
    }

    // ==============================
    // LOGIN
    // ==============================

    private static void login(Scanner scanner) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("                LOGIN");
        System.out.println("========================================");

        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        try (
                Socket socket = new Socket(SERVER_ADDRESS, PORT);

                BufferedReader input = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()
                        )
                );

                PrintWriter output = new PrintWriter(
                        socket.getOutputStream(),
                        true
                )
        ) {

            output.println("LOGIN");
            output.println(accountNumber);
            output.println(pin);

            String loginResult = input.readLine();

            if ("LOGIN_SUCCESS".equals(loginResult)) {

                String accountHolderName = input.readLine();

                System.out.println();
                System.out.println("Login successful!");
                System.out.println("Welcome, " + accountHolderName + ".");

                showBankingMenu(scanner, accountNumber);

            } else {

                System.out.println();
                System.out.println("LOGIN FAILED");
                System.out.println("Invalid account number or PIN.");
            }

        } catch (Exception e) {

            System.out.println();
            System.out.println("Could not connect to bank server.");
            System.out.println("Make sure the server is running.");
        }
    }

    // ==============================
    // BANKING MENU
    // ==============================

    private static void showBankingMenu(
            Scanner scanner,
            String accountNumber) {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("             SECUREBANK ATM");
            System.out.println("========================================");

            System.out.println("Account Number: " + accountNumber);
            System.out.println();
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Logout");
            System.out.println("----------------------------------------");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    checkBalance(accountNumber);
                    break;

                case "2":
                    depositMoney(scanner, accountNumber);
                    break;

                case "3":
                    withdrawMoney(scanner, accountNumber);
                    break;

                case "4":
                    System.out.println();
                    System.out.println("You have been logged out.");
                    return;

                default:
                    System.out.println();
                    System.out.println("Invalid choice.");
                    System.out.println("Please select an option from 1 to 4.");
            }
        }
    }

    // ==============================
    // CHECK BALANCE
    // ==============================

    private static void checkBalance(String accountNumber) {

        try (
                Socket socket = new Socket(SERVER_ADDRESS, PORT);

                BufferedReader input = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()
                        )
                );

                PrintWriter output = new PrintWriter(
                        socket.getOutputStream(),
                        true
                )
        ) {

            output.println("CHECK_BALANCE");
            output.println(accountNumber);

            String balance = input.readLine();

            System.out.println();
            System.out.println("========================================");
            System.out.println("             ACCOUNT BALANCE");
            System.out.println("========================================");
            System.out.println("Account Number: " + accountNumber);
            System.out.printf("Available Balance: ZMW %.2f%n",
                    Double.parseDouble(balance));

        } catch (Exception e) {

            System.out.println();
            System.out.println("Unable to retrieve account balance.");
        }
    }

    // ==============================
    // DEPOSIT
    // ==============================

    private static void depositMoney(
            Scanner scanner,
            String accountNumber) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             DEPOSIT MONEY");
        System.out.println("========================================");

        System.out.print("Enter amount to deposit: ZMW ");

        try {

            double amount = Double.parseDouble(
                    scanner.nextLine()
            );

            if (amount <= 0) {
                System.out.println();
                System.out.println(
                        "Amount must be greater than zero."
                );
                return;
            }

            try (
                    Socket socket =
                            new Socket(SERVER_ADDRESS, PORT);

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

                output.println("DEPOSIT");
                output.println(accountNumber);
                output.println(amount);

                String result = input.readLine();
                String newBalance = input.readLine();

                if ("SUCCESS".equals(result)) {

                    System.out.println();
                    System.out.println("Deposit successful!");
                    System.out.printf(
                            "Amount deposited: ZMW %.2f%n",
                            amount
                    );
                    System.out.printf(
                            "New balance: ZMW %.2f%n",
                            Double.parseDouble(newBalance)
                    );

                } else {

                    System.out.println();
                    System.out.println(
                            "Unable to complete deposit."
                    );
                }
            }

        } catch (NumberFormatException e) {

            System.out.println();
            System.out.println("Invalid amount.");
            System.out.println(
                    "Please enter a valid number."
            );

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Unable to connect to bank server."
            );
        }
    }

    // ==============================
    // WITHDRAW
    // ==============================

    private static void withdrawMoney(
            Scanner scanner,
            String accountNumber) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("            WITHDRAW MONEY");
        System.out.println("========================================");

        System.out.print("Enter amount to withdraw: ZMW ");

        try {

            double amount = Double.parseDouble(
                    scanner.nextLine()
            );

            if (amount <= 0) {

                System.out.println();
                System.out.println(
                        "Amount must be greater than zero."
                );
                return;
            }

            try (
                    Socket socket =
                            new Socket(SERVER_ADDRESS, PORT);

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

                output.println("WITHDRAW");
                output.println(accountNumber);
                output.println(amount);

                String result = input.readLine();
                String message = input.readLine();
                String newBalance = input.readLine();

                if ("SUCCESS".equals(result)) {

                    System.out.println();
                    System.out.println("Withdrawal successful!");
                    System.out.printf(
                            "Amount withdrawn: ZMW %.2f%n",
                            amount
                    );
                    System.out.printf(
                            "Remaining balance: ZMW %.2f%n",
                            Double.parseDouble(newBalance)
                    );

                } else {

                    System.out.println();
                    System.out.println(message);
                }
            }

        } catch (NumberFormatException e) {

            System.out.println();
            System.out.println("Invalid amount.");
            System.out.println(
                    "Please enter a valid number."
            );

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Unable to connect to bank server."
            );
        }
    }
}