import java.time.Duration;
        import java.time.Instant;
        import java.util.List;
        import java.util.concurrent.*;

public class DocumentWordCountWithCompletionService {

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

        // ExecutorCompletionService
        Instant executorCompletionServiceStart = Instant.now();

        int executorCompletionServiceCount = 0;

        ExecutorService threadPool = Executors.newFixedThreadPool(documents.size());

        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(threadPool);

        for(String document: documents){

            Callable<Integer> result = new Callable<>(){
                @Override
                public Integer call() throws InterruptedException {
                    return cloudComputerWordCounterMock(document);
                }
            };

            executorCompletionService.submit(result);
        }

        int awaitingResultCount = documents.size();

        while (awaitingResultCount != 0){
            Future<Integer> future = executorCompletionService.poll();

            if(future != null){
                executorCompletionServiceCount += future.get();
                awaitingResultCount--;
            }
        }

        threadPool.shutdown();

        Instant executorCompletionServiceEnd = Instant.now();

        System.out.println("Duration of ExecutorCompletionService  implementation: " + Duration.between(executorCompletionServiceStart, executorCompletionServiceEnd));
        System.out.println("Result: " + executorCompletionServiceCount);

        return blockingCount;
    }

    static int cloudComputerWordCounterMock(String document) throws InterruptedException {
        int processingTime = (int) (Math.random() * 10);
        TimeUnit.SECONDS.sleep(processingTime);
        return document.split(" ").length;
    }

}
