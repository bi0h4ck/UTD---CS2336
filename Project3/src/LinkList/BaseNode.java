package LinkList;

/**
 * Created by diempham on 3/14/17.
 */
public abstract class BaseNode {
    int row;
    int seat;

    protected BaseNode(){

    }
    protected BaseNode(int row, int seat){
        this.row = row;
        this.seat = seat;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }





}
