package StackAndQueue;

/**
 * Created by diempham on 3/19/17.
 */
public class Queue {
    public Node head;
    public Node tail;


    int size;

    public Queue(){
        head = null;
        tail = null;
        size = 0;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void enqueue(Node n){
        if(head == null){
            head = n;
            tail = head;
            size = 1;
        } else {
            tail.next = n;
            tail = n;
            size++;
        }
    }
    public Node dequeue(Node n){
        if(head == null){
            return null;
        } else {
            Node tmp = new Node(head.c);
            head = head.next;
            size--;
            return tmp;
        }
    }
}
