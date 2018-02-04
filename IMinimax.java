/**
 * IMinimax.java
 * Author: Yu-Lin Yang
 * Created for 8th Light Coding Challenge
 * 
 * Description: Foundation of the required methods in order
 *  for a game state to be compatible for Minimax algorithm
 *  with alpha-beta pruning.
 */
public interface IMinimax {
    public boolean isTerminal();
    public Object[] getSuccessors(char symbol);
    public int getScore();
    public int run_with_pruning(Object state, char symbol);
    public int max_value_with_pruning(Object state, int alpha, int beta);
    public int min_value_with_pruning(Object state, int alpha, int beta);
}