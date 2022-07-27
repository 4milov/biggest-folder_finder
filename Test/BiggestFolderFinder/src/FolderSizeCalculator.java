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
        if (folder.isFile()){
            return folder.length();
        }
        long sum = 0;
        List<FolderSizeCalculator> subTask = new LinkedList<>(); //подзадачи в список
        File[] files = folder.listFiles();
        assert files != null;
        for(File file : files){
            FolderSizeCalculator task = new FolderSizeCalculator(file);
            task.fork(); // ассинхронный запуск задачи
            subTask.add(task); // добавляем в список подзадач
        }

        for(FolderSizeCalculator task : subTask){
            sum+= task.join(); //дожидается выполнения задачи(в потоке) и возвращает результат ее выполнения
        }
        return sum;
    }
}
