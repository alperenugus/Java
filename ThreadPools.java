import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadPools {

    public static void main(String[] args) {
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        Order order3 = new Order(3);
        Order order4 = new Order(4);
        Order order5 = new Order(5);
        receiveAndExecuteClientOrders(order1);
        receiveAndExecuteClientOrders(order2);
        receiveAndExecuteClientOrders(order3);
        receiveAndExecuteClientOrders(order4);
        receiveAndExecuteClientOrders(order5);
    }

    static class Order{
        Order(Integer name){
            this.name = name.toString();
        }
        String name;
        void process(){
            System.out.println("Order " + name + " Processed.");
        }
    }

    public static void receiveAndExecuteClientOrders(Order order){
        int expectedConcurrentOrders = 100;

        Executor executor = Executors.newFixedThreadPool(expectedConcurrentOrders);

        executor.execute(() -> {
            order.process();
        });
    }

}
