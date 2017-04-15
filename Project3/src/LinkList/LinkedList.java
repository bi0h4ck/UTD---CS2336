package LinkList;

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

    public boolean isEmpty(){
        return head == null;
    }

    //add a node at the end
    public void addNode(DoubleLinkNode node){
        if(head == null){
            head = tail = node;
        } else {
            tail.next = node;
            tail.next.prev = tail;
            tail = node;
        }
        size++;
   }

   //add a node before head
   public void push(DoubleLinkNode node){
        node.setPrev(null);
        node.setNext(head);
        if(head != null){
            head.setPrev(node);
        }
        setHead(node);
        size++;
   }

    public LinkedList insertLinkList(LinkedList listOfSeat){
        return insertLinkList(listOfSeat, null);
    }

    public LinkedList insertLinkList(LinkedList listOfSeat, DoubleLinkNode focus){
        if(focus == null)
            focus = head;
        if(head == null) {
            listOfSeat.setHead(head);
            listOfSeat.setTail(tail);

            listOfSeat.size += this.size;
            return listOfSeat;
            //tail of the list < head, insert the list at the beginning
        }
        else if (listOfSeat.tail.getRow() < head.getRow()) {
            listOfSeat.tail.next = head;
            focus.prev = listOfSeat.tail;
            listOfSeat.tail = tail;

            listOfSeat.size += this.size;
            return listOfSeat;
        } else if (listOfSeat.tail.getRow() == focus.getRow() && listOfSeat.tail.getSeat() < focus.getSeat()) {
            listOfSeat.head.prev = focus.prev;
            listOfSeat.tail.next = focus;
            listOfSeat.head.prev.next = listOfSeat.head;
            listOfSeat.tail.next.prev = listOfSeat.tail;
            listOfSeat.size += this.size;
            return listOfSeat;
            //head of the list > tail, insert the list in the end
        } else if (listOfSeat.head.getRow() > tail.getRow()) {
            listOfSeat.head.prev = tail;
            tail.next = listOfSeat.head;
            listOfSeat.head = head;

            listOfSeat.size += this.size;
            return listOfSeat;
            // head of the list on the same row with the tail, and with bigger seat, insert the list in the end
        } else if (listOfSeat.head.getRow() == tail.getRow() && listOfSeat.head.getSeat() > tail.getSeat()) {
            listOfSeat.head.prev = tail;
            tail.next = listOfSeat.head;
            listOfSeat.head = head;

            listOfSeat.size += this.size;
            return listOfSeat;
        } // the tail of the list in the end of the row
         else if(listOfSeat.tail.getRow() == focus.getRow() && listOfSeat.tail.getSeat() == 20) {
            focus = focus.getFirstNodeAtRow(listOfSeat.tail.getRow());
            listOfSeat.head.prev = focus.prev;
            listOfSeat.tail.next = focus;
            listOfSeat.head.prev.next = listOfSeat.head;
            listOfSeat.tail.next.prev = listOfSeat.tail;
            listOfSeat.size += this.size;
            return listOfSeat;
        }
        //insert the list somewhere between head and tail after cur
        else {
            LinkedList updated = insertLinkList(listOfSeat, focus.getNext());
            listOfSeat.head = listOfSeat.head.getFirst();
            return updated;
        }
    }

    public LinkedList removeNodes(LinkedList subList){
        if(subList.getSize() == this.getSize()){
            return new LinkedList();
//            //if the listOfSeat locates in the beginning
        } else if (this.getSize() - subList.getSize() >= 1 && head == subList.head) {
//            //listOfSeat.tail.next is the head
            head = head.getNext(subList.getSize());
            head.setPrev(null);
//            // if the listOfSeat locates in the end
        } else if (this.getSize() - subList.getSize() >= 1 && this.tail == subList.tail){
//            //listOfSeat.tail is the tail
            tail = tail.getPrev(subList.getSize());
            tail.setNext(null);
//            //if the listOfSeat in the middle of openList
        } else {
//            //listOfSeat.head.prev points to the node after nodeOfSeat.tail
            DoubleLinkNode spliceStart = getHead().getNodeAt(subList.getHead().getRow(), subList.getHead().getSeat());
            DoubleLinkNode spliceEnd = spliceStart.getNodeAt(subList.getTail().getRow(), subList.getTail().getSeat());

            if(spliceEnd.getNext() != null)
                spliceEnd.getNext().setPrev(spliceStart.getPrev());

            if(spliceStart.getPrev() != null)
                spliceStart.getPrev().setNext(spliceEnd.getNext());
        }
        this.size = this.getSize() - subList.getSize();
        return this;
    }


   public void removeNode(DoubleLinkNode node){
       if (node.prev == null) {
           head = node.next;
       } else {
           node.prev.next = node.next;
       }

       if (node.next == null) {
           tail = node.prev;
       } else {
           node.next.prev = node.prev;
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

























