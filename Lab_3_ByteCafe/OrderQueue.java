import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {

  // Stores customer orders
  private final Queue<String> orders = new LinkedList<>();

  // Maximum number of orders allowed
  private static final int MAX_CAPACITY = 5;

  // Adds an order to the queue
  public synchronized void placeOrder(String order) {

    try {
        // Wait while the queue is full
        while (orders.size() >= MAX_CAPACITY) {
            wait();
        }

        // Add the new order
        orders.add(order);

        System.out.println(
                Thread.currentThread().getName()
                + " placed order: " + order
                + " | Orders in queue: " + orders.size()
        );

        // Notify waiting threads
        notifyAll();

    } catch (InterruptedException e) {
        // Restore the interrupted status
        Thread.currentThread().interrupt();
    }
  }

  // Removes and returns the next order
  public synchronized String takeOrder() {

    try {
        // Wait while there are no orders
        while (orders.isEmpty()) {
            wait();
        }

        // Remove the next order
        String customerOrder = orders.remove();

        System.out.println(
                Thread.currentThread().getName()
                + " took order: " + customerOrder
                + " | Orders in queue: " + orders.size()
        );

        // Notify waiting threads
        notifyAll();

        return customerOrder;

    } catch (InterruptedException e) {
        // Restore the interrupted status
        Thread.currentThread().interrupt();
        return null;
    }
  }
}
