package LinkedList;

/**
 * Created by diempham on 5/4/17.
 */
public class Stack {
    Node top;

    public Stack(){

    }

    public Node getTop() {
        return top;
    }

    public void setTop(Node top) {
        this.top = top;
    }

    public void push(Node n){
        if(n != null){
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
        if(top == null)
            return null;
        else {
            Node temp = new Node(top.string);
            top = top.next;
            return temp;
        }
    }

    public boolean isEmpty(){
        return top.string.isEmpty();
    }

    //print a top
    public void print(){
        if(!isEmpty())
            System.out.print(this.peek().string + " ");
    }


}
