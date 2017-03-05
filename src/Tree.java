/**
 * Created by raluca on 04.03.2017.
 */
public class Tree {
    public Node root;

    public Tree()
    {
        root = null;
    }

    public void insert(NodeInfo value)
    {
        root = new Node(value);
        root.left = null;
        root.right = null;
    }

    public Tree insert(NodeInfo c, Tree left, Tree right)
    {
        Tree tree = new Tree();
        tree.insert(c);

        tree.root.left = left.root;
        if(right != null)
            tree.root.right = right.root;

        return tree;
    }

    public void print()
    {
        print(root);

    }

    public void print(Node n)
    {
        if(n != null)
        {
            System.out.println(n.value + " ");
            print(n.left);
            print(n.right);
        }
    }
}
