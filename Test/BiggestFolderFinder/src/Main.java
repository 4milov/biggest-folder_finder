import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {

        String folderPath = "D:/разбор";
        long sizeLimit = 50 * 1024 * 1024 ;
        File file = new File(folderPath);
//        Node root = new Node(file);
        Node root = new Node(file, sizeLimit);

        //длительность выполнения метода ForkJoinPool
        long start1 = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(root);
        ForkJoinPool pool = new ForkJoinPool(); //используется для распределения нагрузки между потоками
        pool.invoke(calculator); // invoke возвращает результат (в нашем случае размер)
        System.out.println(root);

        long duration1 = System.currentTimeMillis() - start1;

        System.out.println("ForkJoinPool: " + duration1 +"\n");
    }
}