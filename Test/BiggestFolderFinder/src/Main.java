import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        String folderPath = "D:/разбор";
        File file = new File(folderPath);

        long start1 = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);
        System.out.println(size);

        long duration1 = System.currentTimeMillis() - start1;



        long sum = 0;

        long start2 = System.currentTimeMillis();
        System.out.println(getFolderSize(file));
        long duration2 = System.currentTimeMillis() - start2;

        System.out.println("ForkJoinPool: " + duration1 +"\n"
                + "getFolderSize: " + duration2);
    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sum = 0;

        File[] files = folder.listFiles();
        assert files != null;
        for (File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }
}