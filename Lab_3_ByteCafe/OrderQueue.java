import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {

    private final Queue<String> orders = new LinkedList<>();
    private final int MAX_CAPACITY = 5;

    // Producer method
    public synchronized void placeOrder(String order) {

        try {
            // Wait if the queue is full
            while (orders.size() == MAX_CAPACITY) {
                wait();
            }

            orders.add(order);

            System.out.println(
                    Thread.currentThread().getName()
                    + " placed order: " + order
                    + " | Orders in queue: " + orders.size()
            );

            // Notify waiting threads
            notifyAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Consumer method
    public synchronized String takeOrder() {

        try {
            // Wait if the queue is empty
            while (orders.isEmpty()) {
                wait();
            }

            String order = orders.remove();

            System.out.println(
                    Thread.currentThread().getName()
                    + " took order: " + order
                    + " | Orders in queue: " + orders.size()
            );

            // Notify waiting threads
            notifyAll();

            return order;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}