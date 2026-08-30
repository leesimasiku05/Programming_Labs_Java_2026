public class ByteCafe {

    public static void main(String[] args) {
        OrderQueue orderQueue = new OrderQueue();

        Thread customer1 = new Thread(
                new Customer(orderQueue),
                "Customer-1"
        );

        Thread customer2 = new Thread(
                new Customer(orderQueue),
                "Customer-2"
        );

        Thread customer3 = new Thread(
                new Customer(orderQueue),
                "Customer-3"
        );

        Thread barista1 = new Thread(
                new Barista(orderQueue),
                "Barista-1"
        );

        Thread barista2 = new Thread(
                new Barista(orderQueue),
                "Barista-2"
        );

        System.out.println("================================");
        System.out.println("       BYTECAFE ORDER SYSTEM");
        System.out.println("================================");

        customer1.start();
        customer2.start();
        customer3.start();

        barista1.start();
        barista2.start();
    }
}
