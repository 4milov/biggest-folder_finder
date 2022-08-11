import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private final static char[] sizeMultipiers = {'B', 'K','M', 'G', 'T'};

    public static void main(String[] args) {

        String folderPath = "D:/разбор";
        File file = new File(folderPath);
        Node root = new Node(file);

        //Сравнение длительности выполнения методов ForkJoinPool и getFolderSize
        long start1 = System.currentTimeMillis();

//        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
//        ForkJoinPool pool = new ForkJoinPool(); //используется для распределения нагрузки между потоками
//        long size = pool.invoke(calculator); // invoke возвращает результат (в нашем случае размер)
//        System.out.println(size);


        FolderSizeCalculator calculator = new FolderSizeCalculator(root);
        ForkJoinPool pool = new ForkJoinPool(); //используется для распределения нагрузки между потоками
        pool.invoke(calculator); // invoke возвращает результат (в нашем случае размер)
        System.out.println(root);

        long duration1 = System.currentTimeMillis() - start1;



        long sum = 0;

        long start2 = System.currentTimeMillis();
        System.out.println(getFolderSize(file));
        long duration2 = System.currentTimeMillis() - start2;

        System.out.println("ForkJoinPool: " + duration1 +"\n"
                + "getFolderSize: " + duration2);

        System.out.println(getSizeFromHumanReadable("235K")); // Из Кб переводит в байты
        System.out.println(getHumanReadableSize(240640)); // Из байтов переводит в килобайты
        System.exit(0);


    }

    //Получение размера папки
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

    //Байты в Кб, Мб, Гб
    public static String getHumanReadableSize(long size){
        for(int i = 0; i < sizeMultipiers.length; i++){
            double value = size / Math.pow(1024, i);
            if(value < 1024) {
                return Math.round(value) + "" + sizeMultipiers[i] + (i>0 ? "b" : "");
            }
        }
        return "Very big!";
    }

    //B, Kb, Mb, Gb,Tb в байты
    public static long getSizeFromHumanReadable(String size){
        HashMap<Character, Integer> char2multiplier = getMultipliers();
        char sizeFactor = size.replaceAll("[0-9\\s+]+","").charAt(0);
        int multiplier = char2multiplier.get(sizeFactor);
        return multiplier * Long.parseLong(size.replaceAll("[^0-9]",""));
    }

    //Словарь для getSizeFromHumanReadable
    private static HashMap<Character, Integer> getMultipliers(){
        HashMap<Character, Integer> char2multiplier = new HashMap<>();
        for(int i = 0; i < sizeMultipiers.length; i++){
            char2multiplier.put(sizeMultipiers[i], (int) Math.pow(1024,i));
        } return char2multiplier;
    }
}