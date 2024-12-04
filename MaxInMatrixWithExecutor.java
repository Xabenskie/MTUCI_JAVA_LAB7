import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MaxInMatrixWithExecutor {
    private static int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(matrix.length);
        Future<Integer>[] futures = new Future[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            final int row = i;
            futures[i] = executor.submit(() -> {
                int rowMax = Integer.MIN_VALUE;
                for (int value : matrix[row]) {
                    if (value > rowMax) {
                        rowMax = value;
                    }
                }
                return rowMax;
            });
        }

        int max = Integer.MIN_VALUE;
        for (Future<Integer> future : futures) {
            max = Math.max(max, future.get());
        }

        executor.shutdown();
        System.out.println("Max Element: " + max);
    }
}
