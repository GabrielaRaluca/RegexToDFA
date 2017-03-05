/**
 * Created by raluca on 05.03.2017.
 */
public class Transitions {
    String sourceState;
    char symbol;
    String destinationState;

    public Transitions(String sourceState, char symbol, String destinationState) {
        this.sourceState = sourceState;
        this.symbol = symbol;
        this.destinationState = destinationState;
    }

    @Override
    public String toString() {
        return "Transitions{" +
                "sourceState='" + sourceState + '\'' +
                ", symbol=" + symbol +
                ", destinationState='" + destinationState + '\'' +
                '}';
    }
}
