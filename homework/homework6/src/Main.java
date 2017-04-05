//Diem Pham
//dtp160130

import BinaryTree.*;

public class Main {
    public static void main(String[] args) {
        BinaryTree tree =  new BinaryTree();
        tree.insert(new Node(45));
        tree.insert(new Node(58));
        tree.insert(new Node(90));
        tree.insert(new Node(20));
        tree.insert(new Node(15));
        tree.insert(new Node(37));
        tree.insert(new Node(1));
        tree.insert(new Node(55));
        tree.insert(new Node(16));

        System.out.println("Original tree: ");
        tree.inOrderTraversal();

        BinaryTree twin = tree.clone();
        System.out.println("Twin tree: ");
        twin.inOrderTraversal();

        System.out.println("Original Tree equals twin: " + tree.equals(twin));

        BinaryTree emptyTree = new BinaryTree();
        System.out.println("Original Tree equals empty tree: " + tree.equals(emptyTree));

        BinaryTree littleTree = new BinaryTree();
        littleTree.insert(new Node(45));
        littleTree.insert(new Node(58));
        littleTree.insert(new Node(90));
        littleTree.insert(new Node(20));
        littleTree.insert(new Node(17));
        System.out.println("Little tree: ");
        littleTree.inOrderTraversal();
        System.out.println("Little tree equals twin: " + littleTree.equals(twin));
    }
}
