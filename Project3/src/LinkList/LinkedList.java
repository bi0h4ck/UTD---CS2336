package LinkList;

import java.util.NoSuchElementException;

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

    public int size(){
        int size = 0;
        for(DoubleLinkNode node = head; node.next != null; node = node.next){
            size++;
        }
        return size;
    }

    public void addNode(DoubleLinkNode node){
        if(head == null) {
            head = node;
        }
        else{
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
   }

   public void addLater(DoubleLinkNode node, DoubleLinkNode laterNode){
        if(node.next != null){
            laterNode.next = node.next;
            laterNode.prev = node;
            node.next = laterNode;

        } else {
            addNode(laterNode);
        }
   }


   public void insertNode(DoubleLinkNode node) {
       if (head == null)
           head = node;
       else if (head.next == null) {
           if ((node.row == head.row && node.seat > head.seat) || (node.row > head.row))
               addLater(head, node);
           else {
               head.prev = node.next;
               node.prev = null;
               head.next = null;
               node = head;
           }
       } else {
           DoubleLinkNode cur = head;
           while (node.row != cur.row) {
               cur = cur.getNext();
           }
           DoubleLinkNode next = cur.next;
           if (node.seat < cur.seat) {
               if (cur.prev == null) {
                   cur.prev = node.next;
                   node.prev = null;
               } else {
                   cur.getPrev().next = node.prev;
                   cur.prev = node.next;
               }
           } else {
               //tested
               while (next.row == cur.row && next.seat < node.seat) {
                   cur = cur.next;
                   next = cur.next;
               }
               addLater(cur, node);
           }

       }
   }

   public void removeNode(DoubleLinkNode node){
       if(head == null){
           throw new NoSuchElementException();
       }
       //if there is one node, head = tail
       if (head == tail && node.row == head.row && node.seat == head.seat){
           this.head = this.tail = null;
           return;
       }
       //if the node = head, remove the head
       if (node.row == head.row && node.seat == head.seat && head.next != null){
            this.head = this.head.getNext();
            this.head.setPrev(null);
            return;
       }
       //if the node = tail, remove the tail
       if(node.row == tail.row && node.seat == tail.seat){
            this.tail = this.tail.getPrev();
            this.tail.setNext(null);
            return;
       }
       for(DoubleLinkNode cur = head; cur != null; cur = cur.getNext()){
           if(cur.row == node.row && cur.seat == node.seat){
               DoubleLinkNode prev = cur.getPrev();
               DoubleLinkNode next = cur.getNext();
               prev.setNext(next);
               next.setPrev(prev);
               return;
           }
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

























