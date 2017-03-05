import java.util.*;

/**
 * Created by raluca on 04.03.2017.
 */

public class Main {
    public static final char LAMBDA = '!';

    public static void buildTree(List<Tree> stack, String alphabet, List<NodeInfo> info)
    {
        Tree tree;
        Tree left;
        Tree right;

        for (int i = info.size() - 1; i >= 0; i--)
        {
            tree = new Tree();
            if(alphabet.indexOf(info.get(i).getValue()) >= 0 || info.get(i).getValue() == LAMBDA)
            {

                tree.insert(info.get(i));

                //add all operands to the stack(just the symbols)
                stack.add(tree);
            }
            else
            {
                if(info.get(i).getValue() != '*') // get last 2 operands, build a tree with them and
                    //push that tree instead into the stack
                {
                    left = stack.get(stack.size() - 1);
                    right = stack.get(stack.size() - 2);

                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);

                    tree = tree.insert(info.get(i), left, right);
                    stack.add(tree);
                }
                else // else, if * we only need one operand
                {
                    left = stack.get(stack.size() - 1);
                    stack.remove(stack.size() - 1);

                    tree = tree.insert(info.get(i), left, null);
                    stack.add(tree);
                }
            }
        }
    }

    public static void calculateFunctions(Node syntaxTreeRoot, String alphabet, NodeInfo[] symbolsInfo)
    {
        if(syntaxTreeRoot.left != null)
        {
            calculateFunctions(syntaxTreeRoot.left, alphabet, symbolsInfo);
        }
        if(syntaxTreeRoot.right != null)
        {
            calculateFunctions(syntaxTreeRoot.right, alphabet, symbolsInfo);
        }
        //if lambda or * set nullable true
        if(syntaxTreeRoot.getNodeInfo().getValue() == LAMBDA || syntaxTreeRoot.getNodeInfo().getValue() == '*')
        {
            syntaxTreeRoot.getNodeInfo().setNullable(true);
            if(syntaxTreeRoot.getNodeInfo().getPosition() > 0)
                symbolsInfo[syntaxTreeRoot.getNodeInfo().getPosition() - 1] = syntaxTreeRoot.getNodeInfo();
        }

        //if leaf with alphabet symbol set firstpos and lastpos
        if(alphabet.indexOf(syntaxTreeRoot.getNodeInfo().getValue()) >= 0)
        {
            syntaxTreeRoot.getNodeInfo().getFirstPos().add(syntaxTreeRoot.getNodeInfo().getPosition());
            syntaxTreeRoot.getNodeInfo().getLastPos().add(syntaxTreeRoot.getNodeInfo().getPosition());

            if(syntaxTreeRoot.getNodeInfo().getPosition() > 0)
                symbolsInfo[syntaxTreeRoot.getNodeInfo().getPosition() - 1] = syntaxTreeRoot.getNodeInfo();
        }

        //if operator or set firstpos and last pos
        if(syntaxTreeRoot.getNodeInfo().getValue() == '|')
        {
            Set<Integer> set = new HashSet<>();
            set.addAll(syntaxTreeRoot.left.getNodeInfo().getFirstPos());
            set.addAll(syntaxTreeRoot.right.getNodeInfo().getFirstPos());
            syntaxTreeRoot.getNodeInfo().getFirstPos().addAll(set);

            set.clear();
            set.addAll(syntaxTreeRoot.left.getNodeInfo().getLastPos());
            set.addAll(syntaxTreeRoot.right.getNodeInfo().getLastPos());
            syntaxTreeRoot.getNodeInfo().getLastPos().addAll(set);

            syntaxTreeRoot.getNodeInfo().setNullable(syntaxTreeRoot.left.getNodeInfo().isNullable() || syntaxTreeRoot.right.getNodeInfo().isNullable());

            if(syntaxTreeRoot.getNodeInfo().getPosition() > 0)
                symbolsInfo[syntaxTreeRoot.getNodeInfo().getPosition() - 1] = syntaxTreeRoot.getNodeInfo();

        }

        //if cat operator set firstpos, lastpos and followpos
        if(syntaxTreeRoot.getNodeInfo().getValue() == '.')
        {
            syntaxTreeRoot.getNodeInfo().setNullable(syntaxTreeRoot.left.getNodeInfo().isNullable() && syntaxTreeRoot.right.getNodeInfo().isNullable());

            Set<Integer> set = new HashSet<>();

            if(syntaxTreeRoot.left.getNodeInfo().isNullable())
            {
                set.addAll(syntaxTreeRoot.left.getNodeInfo().getFirstPos());
                set.addAll(syntaxTreeRoot.right.getNodeInfo().getFirstPos());
                syntaxTreeRoot.getNodeInfo().getFirstPos().addAll(set);
            }
            else
            {
                syntaxTreeRoot.getNodeInfo().getFirstPos().addAll(syntaxTreeRoot.left.getNodeInfo().getFirstPos());
            }

            if(syntaxTreeRoot.right.getNodeInfo().isNullable())
            {
                set.clear();
                set.addAll(syntaxTreeRoot.left.getNodeInfo().getLastPos());
                set.addAll(syntaxTreeRoot.right.getNodeInfo().getLastPos());
                syntaxTreeRoot.getNodeInfo().getLastPos().addAll(set);
            }
            else
            {
                syntaxTreeRoot.getNodeInfo().getLastPos().addAll(syntaxTreeRoot.right.getNodeInfo().getLastPos());
            }

            for(int i = 0; i < syntaxTreeRoot.left.getNodeInfo().getLastPos().size(); i++)
            {
                for(int j = 0; j < syntaxTreeRoot.right.getNodeInfo().getFirstPos().size(); j++)
                {
                    syntaxTreeRoot.followPos(syntaxTreeRoot.left.getNodeInfo().getLastPos().get(i), syntaxTreeRoot.right.getNodeInfo().getFirstPos().get(j));
                }
            }

            if(syntaxTreeRoot.getNodeInfo().getPosition() > 0)
                 symbolsInfo[syntaxTreeRoot.getNodeInfo().getPosition() - 1] = syntaxTreeRoot.getNodeInfo();

        }

        //if * operator set firstpos, last pos and followpos
        if(syntaxTreeRoot.getNodeInfo().getValue() == '*')
        {
            syntaxTreeRoot.getNodeInfo().getFirstPos().addAll(syntaxTreeRoot.left.getNodeInfo().getFirstPos());
            syntaxTreeRoot.getNodeInfo().getLastPos().addAll(syntaxTreeRoot.left.getNodeInfo().getLastPos());

            for(int i = 0; i < syntaxTreeRoot.left.getNodeInfo().getLastPos().size(); i++)
            {
                for(int j = 0; j < syntaxTreeRoot.left.getNodeInfo().getFirstPos().size(); j++)
                {
                    syntaxTreeRoot.followPos(syntaxTreeRoot.left.getNodeInfo().getLastPos().get(i), syntaxTreeRoot.left.getNodeInfo().getFirstPos().get(j));
                }
            }

            if(syntaxTreeRoot.getNodeInfo().getPosition() > 0)
                symbolsInfo[syntaxTreeRoot.getNodeInfo().getPosition() - 1] = syntaxTreeRoot.getNodeInfo();

        }

    }

    public static void buildDFA(DFA dfa, Node root, String alphabet, NodeInfo[] symbolsInfo)
    {
        Queue<String> queue = new PriorityQueue<>();
        String initialState = "";
        ArrayList<Integer> firstPos = (ArrayList<Integer>) root.getNodeInfo().getFirstPos();
        for(int i = 0; i < firstPos.size(); i++)
        {
            initialState  += firstPos.get(i).toString();
        }
        dfa.setInitialState(initialState);
        queue.add(initialState);

        String currentState = "";
        String state = "";
        while(!queue.isEmpty())
        {
            currentState = queue.poll();
            
            if(!dfa.getStates().contains(currentState))
                dfa.getStates().add(currentState);

            if(currentState.indexOf((char)(symbolsInfo.length + '0')) >= 0)
            {
                if(!dfa.getFinalStates().contains(currentState))
                    dfa.getFinalStates().add(currentState);
            }
            //mark currentState
            for(int i = 0; i < alphabet.length() - 1; i++)
            {
                state = "";
                Set<Integer> set = new HashSet<>();
                //construct followPos
                for(int j = 0; j < currentState.length(); j++)
                {
                    //get position corresponding to each letter from the currentState
                    int position = Character.getNumericValue(currentState.charAt(j));

                    if(symbolsInfo[position - 1].getValue() == alphabet.charAt(i))
                        //get the followPos of that position
                         set.addAll(symbolsInfo[position - 1].getFollowPos());
                }
                if(!set.isEmpty())
                {
                    for (int j = 0; j < set.toArray().length; j++) {
                        state += set.toArray()[j].toString();
                    }

                    dfa.getTransitions().add(new Transitions(currentState, alphabet.charAt(i), state));

                    if (!dfa.getStates().contains(state))
                        queue.add(state);
                }
            }
        }
    }

    public static void main(String args[])
    {
        String regularExpression;
        String alphabet;

        Scanner input = new Scanner(System.in);
        //regularExpression = "....*|ababb#";

       // System.out.println("Introduceti expresia regulata in forma prefixata: ");
       // regularExpression = input.nextLine();
       // regularExpression += "#";
        regularExpression = ".|.a.b*c.*jk#";

        //System.out.println("Introduceti alfabetul: ");
        //alphabet = input.nextLine();
        //alphabet += "#";
        alphabet = "abcjk#";

        Tree syntaxTree;

        List<Tree> stack = new LinkedList<>();

        char[] regExChars = regularExpression.toCharArray();
        ArrayList<NodeInfo> info = new ArrayList<>();
        int count = 0;
        NodeInfo nodeInfo;

        for(int i = 0; i < regExChars.length; i++)
        {
            if(alphabet.indexOf(regExChars[i]) >= 0 || regExChars[i] == LAMBDA)
            {
                count++;
                nodeInfo = new NodeInfo(regExChars[i], count, false, new ArrayList<Integer>(), new ArrayList<Integer>(),new ArrayList<Integer>());
            }
            else
            {
                nodeInfo = new NodeInfo(regExChars[i],0, false, new ArrayList<Integer>(), new ArrayList<Integer>(),new ArrayList<Integer>());
            }
            info.add(nodeInfo);
        }

        buildTree(stack, alphabet, info);
        syntaxTree = stack.get(0);
        NodeInfo[] symbolsInfo = new NodeInfo[count];
        calculateFunctions(syntaxTree.root, alphabet, symbolsInfo);

        syntaxTree.print();

        System.out.println(
                "aaaaaaaaaaaa"
        );
        for(int i = 0 ; i < symbolsInfo.length; i++)
        {
            System.out.println(symbolsInfo[i]);
        }

        DFA dfa = new DFA();
        buildDFA(dfa, syntaxTree.root, alphabet, symbolsInfo);

        System.out.println();
        System.out.println(dfa);
    }
}
