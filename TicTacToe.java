/**
 * TicTacToe.java
 * Author: Yu-Lin Yang
 * Created for 8th Light Coding Challenge
 * 
 * Description: This is the driver class for the TicTacToe game.
 * Requires: TTTBoard.java as that file serves
 *  as the board to be played on as well as
 *  the implementation of the AI.
 * 
 * To run: "javac TicTacToe.java TTTBoard.java" followed by
 *         "java TicTacToe"
 */
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

/**
 * Tic-Tac-Toe: Two-player console version.
 */
public class TicTacToe {
  public TicTacToe() {}
  // The game board and the game status
  public final char[] placeholderBoard = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
  
  /**
   * config definition
   * Index 0: 1 = PvP; 2 = PvC; 3 = CvC
   * Index 1: 1 = Go first; 2 = Go second
   * Index 2: 1 = Play as X; 2 = Play as O
   */
  public int[] config = new int[3];
  public TTTBoard board;

  public Scanner input = new Scanner(System.in); // the input Scanner
  public boolean p1turn; // true if player 1's turn
  
  /** The entry main method (the program starts here) */
  public static void main(String[] args) {
    // Initialize the game-board and current status
    TicTacToe ttt = new TicTacToe();
    if(ttt.initGame())
      ttt.startGame();
  }
  /** Initializes the game */
  public boolean initGame() {
    System.out.println("~Welcome to Tic-Tac-Toe~");
    boolean validInput = false;
    do {
      System.out.println("Please select game option:");
      System.out.println("[1] for Player vs Player\n[2] for Player vs Computer\n[3] for Computer vs Computer");
      System.out.print("User Input: ");
      if(input.hasNextInt()) {
        int gameType = input.nextInt();
        if( gameType > 0 && gameType < 4) {
          config[0] = gameType;
          validInput = true;
        } else
          System.out.println("Bad input; Please try again.");
      } else {
        input.next();
        System.out.println("Bad input; Please try again.");
        continue;
      }

    } while (!validInput);  // repeat until input is valid

    
    if(config[0] != 3){
      // Go first or second?
      validInput = false;
      do {
        System.out.println("Would you like to go first or second?");
        System.out.println("[1] to go first\n[2] to go second");
        System.out.print("User Input: ");
        if(input.hasNextInt()) {
          int turn = input.nextInt();
          if( turn == 1 || turn == 2) {
            config[1] = turn;
            validInput = true;
          } else
            System.out.println("Bad input; Please try again.");
        } else {
          input.next();
          System.out.println("Bad input; Please try again.");
          continue;
        }
      } while (!validInput);

      // X or O?
      validInput = false;
      do {
        System.out.println("Would you like to play as X's or O's?");
        System.out.println("[1] to play as X's\n[2] to play as O's");
        System.out.print("User Input: ");
        if(input.hasNextInt()) {
          int symbol = input.nextInt();
          if( symbol == 1 || symbol == 2) {
            config[2] = symbol;
            validInput = true;
          } else
            System.out.println("Bad input; Please try again.");
        } else {
          input.next();
          System.out.println("Bad input; Please try again.");
          continue;
        }
      } while (!validInput);
    }

    //Instantiate board
    board = new TTTBoard();
    return true;
  }

  public void startGame() {
    switch(config[0]) {
      case 1: startPvP(); break;
      case 2: startPvC(); break;
      case 3: startCvC(); break;
      default: throw new RuntimeException("Game Selection Failed");
    }
    System.out.print("Game over. Thanks for playing. \n");
  }
  public void startPvP() {
    char p1 = config[2] == 1 ? 'X' : 'O';
    char p2 = config[2] == 1 ? 'O' : 'X';
    p1turn = config[1] == 1;
    printBoard();
    do {
      getHumanSpot(p1turn ? p1 : p2);
      p1turn = !p1turn;
    } while (!gameIsOver() && !tie()); // repeat if not game-over

    if(gameIsOver())
      System.out.printf("Player %d won!\n",p1turn ? 2 : 1);
    else
      System.out.println("Game ended in a draw.");
  }
  private void startPvC() {
    char p1 = config[2] == 1 ? 'X' : 'O';
    char cpu = config[2] == 1 ? 'O' : 'X';
    p1turn = config[1] == 1;
    if(!p1turn) { // If CPU first, make random move for variety.
      getCPURandomSpot(cpu);
      p1turn = !p1turn;
    }
    else
      printBoard();
    do {
      if(p1turn)
        getHumanSpot(p1);
      else 
        getCPUSpot(cpu);
      p1turn = !p1turn;
    } while (!gameIsOver() && !tie());
    if(gameIsOver())
      System.out.printf("%s\n",p1turn ? "The computer won." : "You win!");
    else
      System.out.println("Game ended in a draw.");

  }
  private void startCvC() {
    char cpu1 = 'X';
    char cpu2 = 'O';
    boolean cpu1turn = false;
    getCPURandomSpot(cpu1); // Allow random starts.
    do {
      if(cpu1turn)
        getCPUSpot(cpu1);
      else 
        getCPUSpot(cpu2);
      cpu1turn = !cpu1turn;
    } while (!gameIsOver() && !tie());
    if(gameIsOver())
      System.out.printf("Computer %d won!\n",p1turn ? 2 : 1);
    else
      System.out.println("Game ended in a draw.");
  }

  public void getHumanSpot(char symbol) {
    boolean validInput = false;
    do {
      System.out.printf("Player %d's turn now.\n", p1turn ? 1 : 2);
      System.out.print("User Input: ");
      if(input.hasNextInt()) {
        int spot = input.nextInt();
        if (spot >= 1 && spot <= 9 && spot-1 == Character.getNumericValue(board.currBoard[spot-1])) {
          board.move(spot-1, symbol);
          printBoard();
          validInput = true;
        } else
          System.out.println("Bad input; Please try again.");
      } else {
        input.next();
        System.out.println("Bad input; Please try again.");
        continue;
      }
    } while (!validInput);
  }
  public void getCPUSpot(char symbol) {
    cpuDelay();
    board.run_with_pruning(board, symbol);
    if(TTTBoard.nextState != null) {
      TTTBoard temp = TTTBoard.nextState;
      temp.prevBoard = board.currBoard;
      board = temp;
      printBoard();
    }
    else
      throw new RuntimeException("CPU can't find next position.");
  }

  public void getCPURandomSpot(char symbol) {
    cpuDelay();
    Random rn = new Random();
    int spot = rn.nextInt(9);
    board.move(spot, symbol);
    printBoard();
  }
  public void cpuDelay() {
    try {
      System.out.println("Computer thinking...");
      Random rn = new Random();
      Thread.sleep(rn.nextInt(1000)+500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void printBoard() {
    String output = "";
    output += "*---------------Tic-Tac-Toe---------------*\n";
    output += "Enter [1-9] for the corresponding position:\n";
    output += TTTBoard.printBoard(placeholderBoard);
    output += "\nPrevious board:\n" + board.printPrevBoard();
    output += "\nCurrent board:\n"  + board.printCurrBoard();
    output += "*---------------Tic-Tac-Toe---------------*\n";
    System.out.println(output);
  }

  /** Return true if the game was just won */
  public boolean gameIsOver() {
    return board.currBoard[0] == board.currBoard[1] && board.currBoard[1] == board.currBoard[2] ||
      board.currBoard[3] == board.currBoard[4] && board.currBoard[4] == board.currBoard[5] ||
      board.currBoard[6] == board.currBoard[7] && board.currBoard[7] == board.currBoard[8] ||
      board.currBoard[0] == board.currBoard[3] && board.currBoard[3] == board.currBoard[6] ||
      board.currBoard[1] == board.currBoard[4] && board.currBoard[4] == board.currBoard[7] ||
      board.currBoard[2] == board.currBoard[5] && board.currBoard[5] == board.currBoard[8] ||
      board.currBoard[0] == board.currBoard[4] && board.currBoard[4] == board.currBoard[8] ||
      board.currBoard[2] == board.currBoard[4] && board.currBoard[4] == board.currBoard[6];
  }

  /** Return true if it is a draw (no more empty cell) */
  public boolean tie() {
    for(int i = 0; i < board.currBoard.length; ++i)
      if(board.currBoard[i] == (char) (i+'0') ) return false;
    return true;
  }

}
