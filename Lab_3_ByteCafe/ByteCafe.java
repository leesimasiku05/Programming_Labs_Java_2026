public class ByteCafe {

  public static void main(String[] args) {

    // Create the shared order queue
    OrderQueue orderQueue = new OrderQueue();

    // Create customer and barista threads
    Thread[] customerThreads = createCustomerThreads(orderQueue);
    Thread[] baristaThreads = createBaristaThreads(orderQueue);

    // Display the system heading
    displayHeading();

    // Start all threads
    startThreads(customerThreads);
    startThreads(baristaThreads);
  }

  // Creates the customer threads
  private static Thread[] createCustomerThreads(
        OrderQueue orderQueue) {

    return new Thread[] {
            new Thread(
                    new Customer(orderQueue),
                    "Customer-1"
            ),
            new Thread(
                    new Customer(orderQueue),
                    "Customer-2"
            ),
            new Thread(
                    new Customer(orderQueue),
                    "Customer-3"
            )
    };
  }

  // Creates the barista threads
  private static Thread[] createBaristaThreads(
        OrderQueue orderQueue) {

    return new Thread[] {
            new Thread(
                    new Barista(orderQueue),
                    "Barista-1"
            ),
            new Thread(
                    new Barista(orderQueue),
                    "Barista-2"
            )
    };
  }

  // Displays the program heading
  private static void displayHeading() {

    System.out.println("================================");
    System.out.println("       BYTECAFE ORDER SYSTEM");
    System.out.println("================================");
  }

  // Starts all threads in the given array
  private static void startThreads(Thread[] threads) {

    for (Thread thread : threads) {
        thread.start();
    }
  }
}
