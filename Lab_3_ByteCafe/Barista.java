public class Barista implements Runnable {

    private final OrderQueue orderQueue;

    public Barista(OrderQueue orderQueue) {
        this.orderQueue = orderQueue;
    }

    @Override
    public void run() {

        while (true) {

            String order = orderQueue.takeOrder();

            if (order != null) {

                System.out.println(
                        Thread.currentThread().getName()
                        + " is brewing: " + order
                );

                try {
                    Thread.sleep(1500);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                System.out.println(
                        Thread.currentThread().getName()
                        + " finished brewing: " + order
                );
            }
        }
    }
}
