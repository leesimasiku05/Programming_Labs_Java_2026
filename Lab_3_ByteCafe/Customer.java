import java.util.Random;

public class Customer implements Runnable {

    private final OrderQueue orderQueue;
    private final Random random = new Random();

    private final String[] menu = {
            "Espresso",
            "Latte",
            "Cappuccino",
            "Americano",
            "Mocha"
    };

    public Customer(OrderQueue orderQueue) {
        this.orderQueue = orderQueue;
    }

    @Override
    public void run() {

        for (int i = 1; i <= 5; i++) {

            String order = menu[random.nextInt(menu.length)];

            orderQueue.placeOrder(order);

            try {
                // Random delay between 500ms and 1000ms
                int delay = 500 + random.nextInt(501);
                Thread.sleep(delay);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println(
                Thread.currentThread().getName()
                + " finished placing orders."
        );
    }
}
