package LinkList;

import java.util.NoSuchElementException;

/**
 * Created by diempham on 3/14/17.
 */
public class LinkedList {
    public DoubleLinkNode head;
    public DoubleLinkNode tail;
    public int size;

    public LinkedList(){
        size = 0;
    }

//    public LinkedList(DoubleLinkNode node){
//        this.head = node;
//        DoubleLinkNode next = node.getNext();
//        while(next != null) {
//          next = next.getNext();
//        }
//        this.tail = next;
//    }

    public LinkedList(DoubleLinkNode node){
        this.head = this.tail = node;
        size = 1;
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
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int size(){
        int size = 0;
        for(DoubleLinkNode node = head; node.next != null; node = node.next){
            size++;
        }
        return size;
    }

    public boolean isEmpty(){
        boolean isEmpty = false;
        try{
            isEmpty = head == null;
        } catch (NoSuchElementException e){

        }
        return isEmpty;
    }

    //add a node at the end
    public void addNode(DoubleLinkNode node){
        if(head == null) {
            head = node;
        }
        else{
            tail.next = node;
            //node.prev = tail;
        }
        tail = node;
        size++;
   }

   //add a node before head
   public void push(DoubleLinkNode node){
        node.prev = null;
        node.next = head;
        if(head != null){
            head.prev = node;
        }
        head = node;
        size++;
   }

   public void addLater(DoubleLinkNode node, DoubleLinkNode laterNode){
        if(node.next != null){
            laterNode.next = node.next;
            laterNode.prev = node;
            node.next = laterNode;

        } else {
            addNode(laterNode);
        }
        size++;
   }


   public void insertNode(DoubleLinkNode node) {
       if (head == null){
           head = node;
           size++;
       }
       //if the node is smaller than head
       else if ((node.row < head.row) || (node.row == head.row && node.seat < head.seat))
           //Insert the node before head
           push(node);
       else {
           DoubleLinkNode cur = head;
           //move cur until its row = node.row
           while(cur.next != null && node.row <= cur.row && cur.next.seat < node.seat){
               cur = cur.next;
           }
           node.next = cur.next;
           cur.next = node;
           size++;
       }

   }
   public void inserLinkList(LinkedList list){
       if(head == null){
           
       }
   }

   public void removeNode(DoubleLinkNode node){
       if(this.isEmpty()){
           System.out.println("The list is empty");
       }
       //remove the head when there is one node
       else if (head == tail && node.row == head.row && node.seat == head.seat){
           this.head = this.tail = null;
       }
       //remove the head when there are more than one node
       else if (head.next != null && node.row == head.row && node.seat == head.seat){
            head = head.next;
            head.prev = null;
       }
       //if the node = tail, remove the tail
       else if (node.row == tail.row && node.seat == tail.seat){
            tail = tail.prev;
            tail.next = null;
       // if the deleted node is not either head or tail
       } else {
           DoubleLinkNode cur = head;
           //move cur until cur.next = node
           while(cur.next != null && node.row != cur.row && cur.seat != node.seat){
               cur = cur.next;
           }
           //remove cur
           if(cur.next != null)
                cur.next.prev = cur.prev;
           if(cur.prev != null)
                cur.prev.next = cur.next;
           }
       size--;
   }

   public void printList(){
        DoubleLinkNode cur = head;
        while(cur != null){
            System.out.printf("%d-%d ", cur.row, cur.seat);
            cur = cur.next;
        }
   }


}

























