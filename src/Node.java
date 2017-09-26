import java.util.List;

public class Node<T extends Comparable<T>> {

    private String attributeName;
    private List<Pair<T, Node<T>>> list;
    private T value;

    public Node(String attributeName, List<Pair<T, Node<T>>> list, T value) {
        this.attributeName = attributeName;
        this.list = list;
        this.value = value;
    }

    public Node() {
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<Pair<T, Node<T>>> getList() {
        return list;
    }

    public void setList(List<Pair<T, Node<T>>> list) {
        this.list = list;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    private boolean isLeaf() {
        return value != null && list == null && attributeName == null;
    }

    @Override
    public String toString() {
        if (isLeaf()) {
            return "Leaf \"" + value + "\"";
        } else {
            return "Node " + attributeName + "\n " + list;
        }

    }
}