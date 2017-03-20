package StackAndQueue;

/**
 * Created by diempham on 3/19/17.
 */
public class Node {
    char c;
    Node next;

    public Node(char c){
        this.c = c;
        next = null;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }


}
