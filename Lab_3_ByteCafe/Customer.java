import java.util.Random;

public class Customer implements Runnable {

  // Stores the shared order queue
  private final OrderQueue orderQueue;

  // Generates random values
  private final Random random = new Random();

  // Available drinks on the menu
  private final String[] menu = {
        "Espresso",
        "Latte",
        "Cappuccino",
        "Americano",
        "Mocha"
  };

  // Creates a customer with an order queue
  public Customer(OrderQueue orderQueue) {
    this.orderQueue = orderQueue;
  }

  @Override
  public void run() {

    // Place 5 orders
    for (int orderNumber = 1; orderNumber <= 5; orderNumber++) {

        placeRandomOrder();
        waitBeforeNextOrder();
    }

    // Show when the customer is finished
    System.out.println(
            Thread.currentThread().getName()
            + " finished placing orders."
    );
  }

  // Creates and places a random order
  private void placeRandomOrder() {

    String customerOrder =
            menu[random.nextInt(menu.length)];

    orderQueue.placeOrder(customerOrder);
  }

  // Waits before placing the next order
  private void waitBeforeNextOrder() {

    try {
        // Random delay between 500ms and 1000ms
        int delay = 500 + random.nextInt(501);
        Thread.sleep(delay);

    } catch (InterruptedException e) {

        // Stop the thread safely
        Thread.currentThread().interrupt();
    }
  }
}
