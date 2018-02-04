/**
 * IBoard.java
 * Author: Yu-Lin Yang
 * Created for 8th Light Coding Challenge
 * 
 * Description: Interface for various types of game boards
 *  to be created. Designed for game boards that are playable
 *  in the command line.
 */
public interface IBoard {
    public void move(int index, char symbol);
    public String printCurrBoard();
    public String printPrevBoard(); 
}