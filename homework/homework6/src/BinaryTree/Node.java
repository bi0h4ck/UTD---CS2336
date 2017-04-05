//Diem Pham
//dtp160130
package BinaryTree;

public class Node implements Comparable<Node>
{
    public int num;
    public Node left = null, right = null;

    public Node(){

    }
    public Node(int n) {
        num = n;
    }

    public Node(char c) {
        num = c;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int n) {
        num = n;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node n) {
        left = n;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node n) {
        right = n;
    }

    @Override
    public int compareTo(Node n) {
        return this.num - n.num;
    }

    //copy this node to a new node
    public void copy(Node newNode){
        if(this.getLeft() != null){
            newNode.setLeft(new Node(this.getLeft().getNum()));
            getLeft().copy(newNode.getLeft());
        }
        if(this.getRight() != null){
            newNode.setRight(new Node(this.getRight().getNum()));
            getRight().copy(newNode.getRight());
        }
    }
}