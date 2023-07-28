// Program to implement a Java method which reads a text file and counts how often words occur using multi-threading.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;

public class FileReaderWordCountWithMultithreadingImpl{
    public static final int THREAD_COUNT=3;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        Map<String, Long> counter = new ConcurrentHashMap<>();
        BlockingQueue<String> dataQueue = new LinkedBlockingQueue<>();

        Scanner readFile = new Scanner(new File("/Users/Naincy/Documents/gpl-3.0.txt"));
        while (readFile.hasNext()) {
            String word = readFile.next().toLowerCase()
                    .replaceAll("[^\\s\\-a-zA-Z0-9]", "");
            //System.out.println("Adding word: " + word);
            dataQueue.add(word);
        }
        readFile.close();

        CountDownLatch latch = new CountDownLatch(dataQueue.size());
        ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            es.execute(new Runnable() {
                           @Override
                           public void run() {
                               while (!dataQueue.isEmpty()) {
                                   String word = dataQueue.poll();
                                   //System.out.println("Consuming word: " + word);
                                   if(word!=null)
                                   {
                                       counter.computeIfAbsent(word, k -> 0L);
                                       counter.computeIfPresent(word, (k, v) -> v + 1);
                                   }
                                   latch.countDown();
                               }
                           }
                       }
            );
        }
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MILLISECONDS);
        latch.await();

        mapDisplay(counter);

        //counter.forEach((K, V) -> System.out.print("'" + K + "'" + ":" + V + ","));
    }

    public static void mapDisplay(Map<String, Long> counter)
    {
        System.out.print("{");
        for (Map.Entry<String,Long> entry : counter.entrySet())
            System.out.print("'" + entry.getKey() + "'" +
                    ":" + entry.getValue()+",");
        System.out.print("\b}");
    }
}
