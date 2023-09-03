import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class Order{
    static int expectedConcurrentOrders = 100;
    static Executor threadPool = Executors.newFixedThreadPool(expectedConcurrentOrders);
    Order(Integer name){
        this.name = name.toString();
    }
    String name;
    void process(){
        System.out.println("Order " + name + " Processed.");
    }

    public static void receiveAndExecuteClientOrders(Order order){
        Order.threadPool.execute(() -> {
            order.process();
        });
    }
}

public class ThreadPools {

    public static void main(String[] args) {
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        Order order3 = new Order(3);
        Order order4 = new Order(4);
        Order order5 = new Order(5);
        Order.receiveAndExecuteClientOrders(order1);
        Order.receiveAndExecuteClientOrders(order2);
        Order.receiveAndExecuteClientOrders(order3);
        Order.receiveAndExecuteClientOrders(order4);
        Order.receiveAndExecuteClientOrders(order5);
    }
}
