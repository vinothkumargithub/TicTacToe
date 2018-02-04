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
import java.util.concurrent.ThreadLocalRandom;

/**
 * Tic-Tac-Toe: Two-player console version.
 */
public class TicTacToe {
  public TicTacToe() {

  }
  // The game board and the game status
  public static char[] placeholderBoard = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
  public static int currentState;  // the current state of the game
  public static String currentPlayer; // the current player
  /**
   * config definition
   * Index 0: 1 = PvP; 2 = PvC; 3 = CvC
   * Index 1: 1 = Go first; 2 = Go second
   * Index 2: 1 = Play as X; 2 = Play as O
   */
  private static int[] config = new int[3];
  private static TTTBoard board;

  public static Scanner input = new Scanner(System.in); // the input Scanner
  private static boolean p1turn; // true if player 1's turn
  
  /** The entry main method (the program starts here) */
  public static void main(String[] args) {
    // Initialize the game-board and current status
    //  COMMENTED OUT FOR DEBUGGING PURPOSES2
    initGame();
    //config[0] = 2; config[1] = 2; config[2] = 1;
    board = new TTTBoard();
    startGame();

    System.out.print("Game over\n");
  }
  /** Initializes the game */
  private static void initGame() {

    boolean validInput = false;
    do {
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

    // currentState = 0; // "playing" or ready to play
    // currentPlayer = "X";  // cross plays first
  }

  private static void startGame() {
    switch(config[0]) {
      case 1: startPvP(); break;
      case 2: startPvC(); break;
      case 3: startCvC(); break;
      default: throw new RuntimeException("Game Selection Failed");
    }
  }
  private static void startPvP() {
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
  private static void startPvC() {
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
  private static void startCvC() {
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

  public static void getHumanSpot(char symbol) {
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
  private static void getCPUSpot(char symbol) {
    board.run_with_pruning(board, symbol);
    if(TTTBoard.nextState != null) {
      TTTBoard temp = TTTBoard.nextState;
      temp.prevBoard = board.currBoard;
      board = temp;
      printBoard();
    }
  }

  private static void getCPURandomSpot(char symbol) {
    Random rn = new Random();
    int spot = rn.nextInt(9);
    board.move(spot, symbol);
    printBoard();
  }

  public static void printBoard() {
    String output = "";
    output += "*---------------Tic-Tac-Toe---------------*\n";
    output += "Enter [1-9] for the corresponding position:\n";
    output += TTTBoard.printBoard(placeholderBoard);
    output += "Press Q at any time to quit game.\n";
    output += "\nPrevious board:\n" + board.printPrevBoard();
    output += "\nCurrent board:\n"  + board.printCurrBoard();
    output += "*---------------Tic-Tac-Toe---------------*\n";
    System.out.println(output);
  }

  /** Return true if the game was just won */
  public static boolean gameIsOver() {
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
  public static boolean tie() {
    for(int i = 0; i < board.currBoard.length; ++i)
      if(board.currBoard[i] == (char) (i+'0') ) return false;
    return true;
  }

}
