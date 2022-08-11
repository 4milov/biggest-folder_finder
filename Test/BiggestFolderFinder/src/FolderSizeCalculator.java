import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator extends RecursiveTask<Long> {
    private final Node node;
    public FolderSizeCalculator(Node node) {
        this.node = node;
    }

    @Override
    protected Long compute(){
        File folder = node.getFolder();
        if (folder.isFile()){  //если файл, то возвращаем размер файла
            long length = folder.length();
            node.setSize(length);
            return length;
        }
        long sum = 0;
        List<FolderSizeCalculator> subTask = new LinkedList<>(); //запоминаем задачи в связанный список
        File[] files = folder.listFiles(); // добавляем в массив files список файлов
        assert files != null;
        for(File file : files){
            Node child = new Node(file, node.getLimit());
            FolderSizeCalculator task = new FolderSizeCalculator(child); //проходим по каждому файлу из списка и вызываем для него FolderSizeCalculator
            task.fork(); // асинхронный запуск задачи - в разные потоки
            subTask.add(task); // добавляем в список подзадач, который был создан выше
            node.addChild(child);
        }

        for(FolderSizeCalculator task : subTask){
            sum+= task.join(); //дожидается выполнения задачи(в потоке) и возвращает результат ее выполнения (сумма)
        }
        node.setSize(sum);
        return sum;
    }
}
