//Diem Pham
//dtp160130

package BinaryTree;

public class BinaryTree
{
    Node root;

    public BinaryTree(){
        root = null;
    }

    public Node getRoot(){
        return root;
    }

    public void setRoot(Node n) {
        root = n;
    }

    public boolean search(Node n) {
        Node cur = root;
        while (cur != null)
        {
            if(n.compareTo(cur) == 0)
                return true;
            else if(n.compareTo(cur) < 0)
                cur = cur.left;
            else
                cur = cur.right;
        }
        return false;
    }

    public void insert(Node n)
    {
        if (root == null)
        {
            root = n;
            return;
        }
        else
        {
            Node cur = root;

            while (true)
            {
                if(n.compareTo(cur) < 0)
                {
                    if (cur.left == null)
                    {
                        cur.left = n;
                        return;
                    }
                    cur = cur.left;
                }
                else
                {
                    if (cur.right == null)
                    {
                        cur.right = n;
                        return;
                    }
                    cur = cur.right;
                }
            }
        }

    }

    public Node delete(Node n)
    {
        Node cur = root, parent = root;

        while (cur != null)
        {
            //check if node is to the left
            if (n.compareTo(cur) < 0)
            {
                parent = cur;
                cur = cur.left;
            }
            //check if node is to the right
            else if (n.compareTo(cur) > 0)
            {
                parent = cur;
                cur = cur.right;
            }
            //break loop when node found
            else
                break;
        }

        //check if node found - loop could end when cur == null
        if (cur != null)
        {
            //check if cur has 0 children
            if (cur.left == null && cur.right == null)
                //make link from parent = null
                if (parent.left == cur)
                    parent.left = null;
                else
                    parent.right = null;
                //check if cur has 1 child on right
            else if(cur.left == null)
            {
                //link proper parent link to child of cur
                if (parent.left == cur)
                    parent.left = cur.right;
                else
                    parent.right = cur.right;
                //disconnect cur from tree
                cur.right = null;
            }
            //check if cur has 1 child on left
            else if (cur.right == null)
            {
                //link proper parent link to child of cur
                if (parent.right == cur)
                    parent.right = cur.left;
                else
                    parent.left = cur.left;
                //disconnect cur from tree
                cur.left = null;
            }
            //cur has 2 children
            else
            {
                //parent will hold position where node will be copied
                parent = cur;
                //move cur to left child
                cur = cur.left;

                //move cur down right branch of left subtree
                while (cur.right != null)
                    cur = cur.right;

                //copy content from node to node
                parent.num = cur.num;
                //call delete again to delete the node that was copied
                delete (cur);
            }


        }
        return cur;
    }

    public BinaryTree clone(){
        BinaryTree newTree = new BinaryTree();
        if (this.getRoot() != null){
            newTree.setRoot(new Node(this.getRoot().getNum()));
            root.copy(newTree.getRoot());
        }
        return newTree;
    }

    //Print out inorder
    public void inOrderTraversal(){
        inOrderPrinter(root);
    }

    public void inOrderPrinter(Node root){
        if(root != null){
            inOrderPrinter(root.left);
            System.out.println(root.num + " ");
            inOrderPrinter(root.right);
        }
    }

    //Check this tree equals to the other tree
    public boolean equals(BinaryTree comparedTree){
        if(root == null && comparedTree.root == null)
            return true;
        if(root == null || comparedTree.root == null){
            return root == comparedTree.root;
        }
        return ((root.getNum() == comparedTree.getRoot().getNum()) &&
        equals(root.left, comparedTree.root.left) &&
        equals(root.right, comparedTree.root.right));
    }

    //Check this node equals to the other node
    public boolean equals(Node node, Node comparedNode){
        if(node == null && comparedNode == null)
            return true;
        if(node == null || comparedNode == null){
            return node == comparedNode;
        }
        return (equals(node.left, comparedNode.left) &&
                equals(node.right, comparedNode.right));
    }

}