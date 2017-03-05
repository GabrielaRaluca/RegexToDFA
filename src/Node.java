/**
 * Created by raluca on 04.03.2017.
 */
public class Node {
    NodeInfo value;
    Node left;
    Node right;

    public Node(NodeInfo value)
    {
        this.value = value;
        left = null;
        right = null;
    }

    public NodeInfo getNodeInfo()
    {
        return this.value;
    }

    public void followPos(int position, int value)
    {
        if(this.getNodeInfo().getPosition() == position)
        {
            this.getNodeInfo().getFollowPos().add(value);

        }
        else
        {
            if(this.left != null)
                this.left.followPos(position, value);

            if(this.right != null)
                this.right.followPos(position, value);
        }
    }
}
