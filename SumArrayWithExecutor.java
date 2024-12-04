import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SumArrayWithExecutor {
    private static int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> future1 = executor.submit(() -> {
            int sum = 0;
            for (int i = 0; i < array.length / 2; i++) {
                sum += array[i];
            }
            return sum;
        });

        Future<Integer> future2 = executor.submit(() -> {
            int sum = 0;
            for (int i = array.length / 2; i < array.length; i++) {
                sum += array[i];
            }
            return sum;
        });

        int totalSum = future1.get() + future2.get();
        executor.shutdown();
        System.out.println("Total Sum: " + totalSum);
    }
}
