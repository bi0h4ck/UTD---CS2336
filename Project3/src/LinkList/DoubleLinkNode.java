package LinkList;

/**
 * Created by diempham on 3/14/17.
 */
public class DoubleLinkNode extends BaseNode {
    public DoubleLinkNode next;
    public DoubleLinkNode prev;

//    public DoubleLinkNode(int row, int seat){
//        super(row, seat);
//    }

    public DoubleLinkNode(int row, int seat){
        super(row, seat);
        this.prev = null;
        this.next = null;
    }

    public DoubleLinkNode getNext() {
        return next;
    }

    public void setNext(DoubleLinkNode next) {
        this.next = next;
    }

    public DoubleLinkNode getPrev() {
        return prev;
    }

    public void setPrev(DoubleLinkNode prev) {
        this.prev = prev;
    }


}
