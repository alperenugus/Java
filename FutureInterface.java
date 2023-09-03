import java.util.concurrent.*;

public class FutureInterface {

    public static void main(String[] args) throws Exception {
        System.out.println( "sum :" + sum(10));
        threadPool.shutdown();
    }

    static int THREAD_COUNT = 100;
    static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

    static int sum(int n) throws ExecutionException, InterruptedException {

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call(){
                int sum = 0;
                for(int i = 0; i < n; i++){
                    sum += i;
                }
                return sum;
            }
        };

        Future<Integer> future = threadPool.submit(callable);
        return future.get();
    }

}
