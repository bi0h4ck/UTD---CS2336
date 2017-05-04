package LinkedList;

/**
 * Created by diempham on 5/4/17.
 */
public class Node {
    String string;
    Node next;

    public Node(String string){
        this.string = string;
        next = null;
    }
    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }



}
