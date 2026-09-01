public class Barista implements Runnable {

  // Stores the shared order queue
  private final OrderQueue orderQueue;

  // Creates a barista with an order queue
  public Barista(OrderQueue orderQueue) {
    this.orderQueue = orderQueue;
  }

  @Override
  public void run() {

    // Keep processing orders
    while (true) {

        String customerOrder = orderQueue.takeOrder();

        // Process the order if one is available
        if (customerOrder != null) {

            brewOrder(customerOrder);
        }
    }
  }

  // Brews and completes an order
  private void brewOrder(String customerOrder) {

    System.out.println(
            Thread.currentThread().getName()
            + " is brewing: " + customerOrder
    );

    try {
        // Simulate brewing time
        Thread.sleep(1500);

    } catch (InterruptedException e) {

        // Stop the thread safely
        Thread.currentThread().interrupt();
        return;
    }

    System.out.println(
            Thread.currentThread().getName()
            + " finished brewing: " + customerOrder
    );
  }
}
