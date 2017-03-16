package LinkList;

/**
 * Created by diempham on 3/14/17.
 */
public class LinkedList {
    public DoubleLinkNode head;
    public DoubleLinkNode tail;


    public LinkedList(){

    }

    public LinkedList(DoubleLinkNode node){
        this.head = node;
        DoubleLinkNode next = node.getNext();
        while(next != null) {
          next = next.getNext();
        }
        this.tail = next;
    }

    public DoubleLinkNode getHead() {
        return head;
    }

    public void setHead(DoubleLinkNode head) {
        this.head = head;
    }

    public DoubleLinkNode getTail() {
        return tail;
    }

    public void setTail(DoubleLinkNode tail) {
        this.tail = tail;
    }

    public void addNode(DoubleLinkNode node){
        if(head == null) {
            head = node;
            tail = node;
        }
        else{
            tail.next = node;
            tail = tail.next;
        }
   }

   public void printList(){
        DoubleLinkNode cur = head;
        while(cur != null){
            System.out.printf("%d-%d ", cur.row, cur.seat);
            cur = cur.next;
        }
   }
}

























