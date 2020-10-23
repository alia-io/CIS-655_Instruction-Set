package Stack;

public class StackNode {

    private String value;
    private StackNode next;

    public StackNode(String value, StackNode next) {
        this.value = value;
        this.next = next;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public StackNode getNext() {
        return this.next;
    }

}
