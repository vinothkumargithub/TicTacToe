/**
 * TTTBoard.java
 * Author: Yu-Lin Yang
 * Created for 8th Light Coding Challenge
 * 
 * Description: This serves as the tictactoe board
 *  as well as a board that functions as a state for the
 *  minimax algorithm
 */
import java.util.*;
public class TTTBoard implements IBoard, IMinimax {
    public TTTBoard() {
        currBoard = new char[9];
        prevBoard = new char[9];
        for(int i = 0; i < 9 ; ++i) {
            currBoard[i] = (char) (i+'0');
            prevBoard[i] = (char) (i+'0');
        }
    }
    // For minimax score construction
    public TTTBoard(char[] arr) {
        this.currBoard = Arrays.copyOf(arr, arr.length);
    }
    public char[] currBoard;
    public char[] prevBoard;

    public void move(int index, char symbol) {
        System.arraycopy( currBoard, 0, prevBoard, 0, currBoard.length );
        currBoard[index] = symbol;
    }
    public String printPrevBoard() {
        return printThisBoard(prevBoard);
    }
    public String printCurrBoard() {
        return printThisBoard(currBoard);
    }
    public String printThisBoard(char[] board) {
        char[] tempBoard = new char[9];
        System.arraycopy( board, 0, tempBoard, 0, tempBoard.length );
        for(int i  = 0; i < tempBoard.length; ++i)
            if(Character.isDigit(tempBoard[i]) ) tempBoard[i] = '_';
        return printBoard(tempBoard);
    }
    public static String printBoard(char[] board) {
        String separator = "\n===+===+===\n";
        String output = "";
        output += " " + board[0] + " | " + board[1] + " | " + board[2];
        output += separator;
        output += " " + board[3] + " | " + board[4] + " | " + board[5];
        output += separator;
        output += " " + board[6] + " | " + board[7] + " | " + board[8];
        output += "\n";
        return output;
    }
    
    // Minimax Required Methods
    // Determines if the board is a terminal node or not and return boolean
    // terminal = no moves for either players
    public boolean isTerminal() {
        TTTBoard[] successors = getSuccessors('X');
        TTTBoard[] npsucc = getSuccessors('O');
        if(successors.length == 0  && npsucc.length == 0)
            return true;
        return false;
    }

    // Gets all possible moves that can be made by this symbol
    public TTTBoard[] getSuccessors(char symbol) {
        ArrayList<char[]> stateList = new ArrayList<>();
        for(int i = 0; i < currBoard.length; ++i) {
            if(currBoard[i] == (char) (i+'0')) {
                char[] dest = new char[currBoard.length];
                System.arraycopy(currBoard, 0, dest, 0, dest.length);
                dest[i] = symbol;
                stateList.add(dest);
            }
        }
        TTTBoard[] successors = new TTTBoard[stateList.size()];
        for(int i = 0; i < stateList.size(); ++i) {
            successors[i] = new TTTBoard(stateList.get(i));
        }
        return successors;
    }
    
    // Get an objective score if victory is achieved (positive for X, negative for O)
    public int getScore() {
        // Note: this method assumes non used squares have discrete symbols
        // Check diagonals for victory (either X or O)
        if (this.currBoard[0]==this.currBoard[4] && this.currBoard[4]==this.currBoard[8])
            return this.currBoard[0] == 'X' ? 100 : -100;
     
        if (this.currBoard[2]==this.currBoard[4] && this.currBoard[4]==this.currBoard[6])
            return this.currBoard[2] == 'X' ? 100 : -100;

        // Checking all rows for victory (either X or O)
        for (int row = 0; row < 3; ++row) {
            if (this.currBoard[row]== this.currBoard[row+3] &&
            this.currBoard[row+3]==this.currBoard[row+6])
                return this.currBoard[row] == 'X' ? 100 : -100;
        }
     
        // Checking all rows for victory (either X or O)
        for (int col = 0; col < 9; col+=3)
        {
            if (this.currBoard[col]== this.currBoard[col+1] &&
            this.currBoard[col+1]==this.currBoard[col+2])
                return this.currBoard[col] == 'X' ? 100 : -100;
        }

        // Else if none of them have won then return 0
        return 0;
    }


    /**
     *  Minimax algorithm with alpha beta pruning.
     *  Given a state and turn (defined by symbol), minimax will
     *  output the most optimal move for that player and store that
     *  move in "nextState"
     */
    public static int explored; // # of iterations traversed (depth)
    public static TTTBoard nextState; // Optimal next move.

    // Input: Current state of the board, whose turn it is by symbol
    // Output: Optimal score (irrelevant in this case)
    public int run_with_pruning(Object curr_state, char symbol) {
        // TO DO: run the alpha-beta pruning algorithm and return the game theoretic value
        // X is max; O is min.
        explored = 0;
        if(symbol == 'X') {
            return max_value_with_pruning(curr_state,Integer.MIN_VALUE,Integer.MAX_VALUE);
        }
        else {
            return min_value_with_pruning(curr_state,Integer.MIN_VALUE,Integer.MAX_VALUE);
        }
    }
    // Max-Value of the alpha-beta pruning algorithm
    public int max_value_with_pruning(Object state, int alpha, int beta) {
        if(state == null || !(state instanceof TTTBoard)) throw new ClassCastException();
        TTTBoard curr_state = (TTTBoard) state;
        boolean firstIteration = (explored == 0); 
        explored++;
        int score = curr_state.getScore();
        if (score == 100 || score == -100) return score ;
        if( curr_state.isTerminal() ) return 0;
        
        TTTBoard[] successors = curr_state.getSuccessors('X');
        for(TTTBoard s : successors){
            int min = min_value_with_pruning(s, alpha, beta);
            if(min > alpha) {
                alpha = min;
                if(firstIteration)
                    nextState = s;
            }
            if(alpha >= beta) return beta; 
            alpha = Math.max(alpha, min);
        }
        return alpha;
    }
    
    public int min_value_with_pruning(Object state, int alpha, int beta) {
        // TO DO: implement Max-Value of the alpha-beta pruning algorithm
        if(state == null || !(state instanceof TTTBoard)) throw new ClassCastException();
        TTTBoard curr_state = (TTTBoard) state;
        boolean firstIteration = (explored == 0); 
        explored++;
        int score = curr_state.getScore();
        if (score == 100 || score == -100) return score ;
        if( curr_state.isTerminal() ) return 0;
        
        TTTBoard[] successors = curr_state.getSuccessors('O');
        for(TTTBoard s : successors){
            int max = max_value_with_pruning(s, alpha, beta);
            if(max < beta){
                beta = max;
                if(firstIteration)
                    nextState = s;
            }
            if(alpha >= beta) return alpha; 
            beta = Math.min(beta, max);
        }
        return beta;
    }
        
}