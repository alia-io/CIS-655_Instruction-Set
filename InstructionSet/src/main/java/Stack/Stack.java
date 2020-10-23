package Stack;

public class Stack {

    StackNode head;
    int size;

    public Stack() {
        this.head = null;
        this.size = 0;
    }

    public void push(String value) {
        StackNode newNode = new StackNode(value, this.head);
        this.head = newNode;
        this.size = this.size + 1;
    }

    public String pop() {
        String value;
        if (this.head == null) { return null; }
        value = this.head.getValue();
        this.head = head.getNext();
        this.size = this.size - 1;
        return value;
    }

    public int getSize() {
        return this.size;
    }

}
