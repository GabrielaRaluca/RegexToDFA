import java.util.List;

/**
 * Created by raluca on 05.03.2017.
 */
public class NodeInfo {

    char value;
    int position;
    boolean nullable;
    List<Integer> firstPos;
    List<Integer> lastPos;
    List<Integer> followPos;



    public NodeInfo(char value, int position, boolean nullable, List<Integer> firstPos, List<Integer> lastPos, List<Integer> followPos) {
        this.value = value;
        this.position = position;
        this.nullable = nullable;
        this.firstPos = firstPos;
        this.lastPos = lastPos;
        this.followPos = followPos;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public List<Integer> getFirstPos() {
        return firstPos;
    }

    public void setFirstPos(List<Integer> firstPos) {
        this.firstPos = firstPos;
    }

    public List<Integer> getLastPos() {
        return lastPos;
    }

    public void setLastPos(List<Integer> lastPos) {
        this.lastPos = lastPos;
    }

    public List<Integer> getFollowPos() {
        return followPos;
    }

    public void setFollowPos(List<Integer> followPos) {
        this.followPos = followPos;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "value=" + value +
                ", position=" + position +
                ", nullable=" + nullable +
                ", firstPos=" + firstPos +
                ", lastPos=" + lastPos +
                ", followPos=" + followPos +
                '}';
    }
}
