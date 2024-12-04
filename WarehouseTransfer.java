import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

class Product {
    private final String name;
    private final int weight;

    public Product(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return name + " (" + weight + " kg)";
    }
}

class WarehouseTask extends RecursiveAction {
    private static final int MAX_WEIGHT = 150;
    private final List<Product> products;
    private final int start;
    private final int end;

    public WarehouseTask(List<Product> products, int start, int end) {
        this.products = products;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        int totalWeight = 0;
        List<Product> selectedProducts = new ArrayList<>();

        for (int i = start; i < end; i++) {
            Product product = products.get(i);
            if (totalWeight + product.getWeight() <= MAX_WEIGHT) {
                totalWeight += product.getWeight();
                selectedProducts.add(product);
            }
            if (totalWeight == MAX_WEIGHT) {
                break;
            }
        }

        if (!selectedProducts.isEmpty()) {
            System.out.println("Грузчики переносят: " + selectedProducts);
            // Здесь можно добавить логику для "переноса" товаров
            // Например, имитация времени на перенос
            try {
                Thread.sleep(1000); // Имитация времени на перенос
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Если остались товары, создаем подзадачи
        if (end - start > 1) {
            int mid = (start + end) / 2;
            invokeAll(new WarehouseTask(products, start, mid), new WarehouseTask(products, mid, end));
        }
    }
}

public class WarehouseTransfer {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Товар 1", 50));
        products.add(new Product("Товар 2", 30));
        products.add(new Product("Товар 3", 70));
        products.add(new Product("Товар 4", 20));
        products.add(new Product("Товар 5", 60));
        products.add(new Product("Товар 6", 40));
        products.add(new Product("Товар 7", 10));

        ForkJoinPool pool = new ForkJoinPool(3); // 3 грузчика
        WarehouseTask task = new WarehouseTask(products, 0, products.size());
        pool.invoke(task);
    }
}
