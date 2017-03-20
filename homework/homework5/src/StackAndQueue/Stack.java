package StackAndQueue;

/**
 * Created by diempham on 3/19/17.
 */
public class Stack {
    public Node top;

    public Node getTop() {
        return top;
    }

    public void setTop(Node top) {
        this.top = top;
    }

    public void push(Node n){
        if (n != null){
            n.next = top;
            top = n;
        }
    }
    public Node peek(){
        if(top != null){
            return top;
        } else
            return null;
    }
    public Node pop(){
        if(top == null){
            return null;
        } else {
            Node temp = new Node(top.c);
            top = top.next;
            return temp;
        }
    }
}
