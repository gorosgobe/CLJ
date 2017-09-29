package decisiontrees;

import java.util.List;

public class DecisionNode<T extends Comparable<T>> {

    private String attributeName;
    private List<Pair<T, DecisionNode<T>>> list;
    private T value;

    public DecisionNode(String attributeName, List<Pair<T, DecisionNode<T>>> list, T value) {
        this.attributeName = attributeName;
        this.list = list;
        this.value = value;
    }

    public DecisionNode() {
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<Pair<T, DecisionNode<T>>> getList() {
        return list;
    }

    public void setList(List<Pair<T, DecisionNode<T>>> list) {
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
            return "decisiontrees.DecisionNode " + attributeName + "\n " + list;
        }

    }
}