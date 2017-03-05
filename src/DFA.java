import java.util.ArrayList;
import java.util.List;

/**
 * Created by raluca on 05.03.2017.
 */
public class DFA {

    List<String> states;
    String initialState;
    List<Transitions> transitions;
    List<String> finalStates;

    public DFA()

    {
        this.states = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.initialState = "";
    }
    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public List<Transitions> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transitions> transitions) {
        this.transitions = transitions;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(List<String> finalStates) {
        this.finalStates = finalStates;
    }

    @Override
    public String toString() {
        return "DFA[" +
                "states={" + states + "} " +
                ", initialState='" + initialState + '\'' +
                ", transitions= {" + transitions + "} " +
                ", finalStates= { " + finalStates + " }" +
                ']';
    }
}
