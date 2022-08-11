import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Node {

    private File folder;
    private ArrayList<Node> children;
    private long size;

    public Node(File folder){
        this.folder = folder;
        children = new ArrayList<>();
    }

    public File getFolder(){
        return folder;
    }

    public void addChild(Node node){
        children.add(node);
    }

    public ArrayList<Node> getChildren(){
        return children;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String toString(){

        StringBuilder builder = new StringBuilder();
        String size = SizeCalculator.getHumanReadableSize(getSize());
        builder.append(folder.getName()).append(" - ").append(size).append("\n");
        for (Node child : children) {
            builder.append("  ").append(child.toString());
        }
        return builder.toString();
    }


}
