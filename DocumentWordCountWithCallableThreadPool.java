import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DocumentWordCountWithCallableThreadPool {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        List<String> documents = List.of(
                "This is a document",
                "Here is a second document",
                "This one is the third one",
                "There can be hundreds of documents"
        );

        int totalWordCount = countWordsOfDocuments(documents);

        System.out.println(totalWordCount);

    }

    static int countWordsOfDocuments(List<String> documents) throws InterruptedException, ExecutionException {
        // Blocking
        Instant blockingStart = Instant.now();

        int blockingCount = 0;

        for(String document : documents){
            blockingCount += cloudComputerWordCounterMock(document);
        }

        Instant blockingEnd = Instant.now();
        System.out.println("Duration of blocking implementation: " + Duration.between(blockingStart, blockingEnd));
        System.out.println("Result: " + blockingCount);

        // Using Callables

        Instant callableStart = Instant.now();

        int callablesWithFutureCount = 0;

        ArrayList<Future<Integer>> futures = new ArrayList<>();

        ExecutorService threadPool = Executors.newFixedThreadPool(documents.size());

        for(String document : documents){

            Callable<Integer> result = new Callable<>(){
                @Override
                public Integer call() throws InterruptedException {
                    return cloudComputerWordCounterMock(document);
                }
            };

            Future<Integer> future = threadPool.submit(result);
            futures.add(future);
        }

        for(Future<Integer> future : futures){
            callablesWithFutureCount += future.get();
        }

        threadPool.shutdown();

        Instant callableEnd = Instant.now();

        System.out.println("Duration of callables with futures implementation: " + Duration.between(callableStart, callableEnd));
        System.out.println("Result: " + callablesWithFutureCount);

        return blockingCount;
    }

    static int cloudComputerWordCounterMock(String document) throws InterruptedException {
        int processingTime = (int) (Math.random() * 10);
        TimeUnit.SECONDS.sleep(processingTime);
        return document.split(" ").length;
    }

}
