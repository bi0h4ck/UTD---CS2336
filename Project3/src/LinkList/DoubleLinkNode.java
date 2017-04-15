package LinkList;

import java.util.NoSuchElementException;

/**
 * Created by diempham on 3/14/17.
 */
public class DoubleLinkNode extends BaseNode {
    protected DoubleLinkNode next;
    protected DoubleLinkNode prev;


    public DoubleLinkNode(){
    }

    public DoubleLinkNode(int row, int seat){
        super(row, seat);
        this.prev = null;
        this.next = null;
    }

    public DoubleLinkNode getFirst() {
        if(prev == null) {
            return this;
        } else {
            return prev.getFirst();
        }
    }

    public DoubleLinkNode getLast() {
        if(next == null) {
            return this;
        } else {
            return next.getLast();
        }
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

    public DoubleLinkNode getNext(int n) throws NoSuchElementException {
        if (n > 0) {
            if (this.getNext() == null) {
                return null;
            }
            return getNext().getNext(n - 1);
        } else if (n < 0) {
            throw new NoSuchElementException("Invalid value supplied. (" + n + ")");
        }

        return this;
    }

    public DoubleLinkNode getPrev(int n){
        if (n > 0){
            if(this.getPrev() == null){
                return null;
            }
            return getPrev().getPrev(n - 1);
        } else if (n < 0 ){
            throw new NoSuchElementException("Invalid value supplied. (" + n + ")");
        }
        return this;
    }

    public DoubleLinkNode getNodeAt(int row, int seat){
        if (this.getRow() == row && this.getSeat() == seat){
            return this;
        } else {
            return getNext().getNodeAt(row, seat);
        }
    }

    public DoubleLinkNode getFirstNodeAtRow(int row){
        if (this.getRow() - row == 1) {
            return this;
        } else {
            return getNext().getFirstNodeAtRow(row);
        }
    }

    public void insertNode(DoubleLinkNode node) {
        if(node.getRow() < this.getRow() && getPrev() == null){
            setPrev(node);
        } else if(node.getRow() > this.getRow() && getNext() == null) {
            setNext(node);
        } else if (node.getRow() == this.getRow() && node.getSeat() < this.getSeat()){
            setPrev(node);
        } else if(node.getRow() == this.getRow() && getNext().getSeat() > node.getSeat()){
            next.setPrev(node);
            node.setPrev(this);
            node.setNext(next);
            this.setNext(node);
        } else {
          next.insertNode(node);
        }
    }


}
