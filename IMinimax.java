public interface IMinimax {
    public boolean isTerminal();
    public Object[] getSuccessors(char symbol);
    public int getScore();
    public int run_with_pruning(Object state, char symbol);
    public int max_value_with_pruning(Object state, int alpha, int beta);
    public int min_value_with_pruning(Object state, int alpha, int beta);
}