# Unbeatable Tic Tac Toe
1-2 player(s) command line based Tic-Tac-Toe game with an unbeatable AI implemented using minimax with alpha-beta pruning.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

- Clone the repository and compile all the java files. For example:
```
javac TicTacToe.java
```
- To begin game:
```
java TicTacToe
```

## Code Structure
The code was designed specifically to fulfill the requirements for this task while providing the flexibility to change and scale if needed to. I made two overarching interfaces designed to be flexible for any sort of games that involve a board and/or an AI in any two-player zero-sum deterministic game of perfect information:
```
IBoard.java and IMinimax.java
```
are the blueprint for any game to be created that wishes to use another variation of board and that also wishes to apply the Minimax algorithm as an AI player.

### TicTacToe.java:
This is the driver class as well as the user facing class. This class instantiates all the required elements. This class contains all functionalities that relate to the command line (i/o).
### TTTBoard.java:
This is the implementation of board specifically for tic-tac-toe. In here, I implement the required methods as laid forth by the interface. In the second half of the file, I define for Minimax what it means to win the game (how scoring works) and how pieces move (getSuccessors).


## Minimax with Alpha Beta Pruning
For the computer player(s) in PvC or CvC, I implemented a variation of the minimax algorithm with alpha beta pruning. Minimax can be applied to any two-player zero-sum finite deterministic games of perfect information.

The motivation for applying alpha-beta pruning is to disregard states (iterations) that is already known to the algorithm to not be chosen. This can signifigantly reduce the number of iterations in the minimax algorithm.

Note: another viable solution for large games (not so much tic-tac-toe but for larger games such as chess), I could've applied an heuristic evaluation function. This allows me to set a depth cutoff point in order to get an "estimate" of the best move. The motivation for this is to reduce iterations. The depth can then increase with iterative deepening.


## Running the tests

I provided a suite ofnon-exchaustive tests of the individual methods of the program.

To run:
```
java GameTester
```
will test all non-user input and non-algorithmic related functions. This tests whether the linkage of obtaining the computer's move works as intended, as well as various construction and printing of this specific board is consistent every time. This also manually tests whether gameOver/getScore functions are calculated correctly.

## Acknowledgments

- Built for 8th Light Coding Challenge in February 2018.
- Made by Yu-Lin Yang
