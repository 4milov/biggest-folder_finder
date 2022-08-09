import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator extends RecursiveTask<Long> {
    private final File folder;
    public FolderSizeCalculator(File folder) {
        this.folder = folder;
    }

    @Override
    protected Long compute(){
        if (folder.isFile()){  //если файл, то возвращаем размер файла
            return folder.length();
        }
        long sum = 0;
        List<FolderSizeCalculator> subTask = new LinkedList<>(); //запоминаем задачи в связанный список
        File[] files = folder.listFiles(); // добавляем в массив files список файлов
        assert files != null;
        for(File file : files){
            FolderSizeCalculator task = new FolderSizeCalculator(file); //проходим по каждому файлу из списка и вызываем для него FolderSizeCalculator
            task.fork(); // асинхронный запуск задачи - в разные потоки
            subTask.add(task); // добавляем в список подзадач, который был создан выше
        }

        for(FolderSizeCalculator task : subTask){
            sum+= task.join(); //дожидается выполнения задачи(в потоке) и возвращает результат ее выполнения (сумма)
        }
        return sum;
    }
}
