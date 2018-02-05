public class GameTester {
    public static void main(String[] args) {

            TicTacToe ttt = new TicTacToe();
            ttt.config[0] = 2; // Pvc
            ttt.config[1] = 1; // Go first
            ttt.config[2] = 1; // Play as X's

            // Test Start game
            //startFail = false;
            //ttt.startPvP();
            //startFail = !ttt.p1turn; // Should be true
            
            // Check getCPUSpot
            boolean cpuPass = true;
            char[] ph = {'_','X','_','_','X','_','_','X','_'};
            ttt.board = new TTTBoard(ph);
            ttt.board.currBoard = new char[] {'X','O','X','O','X','O','X','O','X'};
            char[] temp = new char[] {'X','O','X','O','X','O','X','O','X'};
            char[] ph2 = new char[] {'O','_','_','_','O','_','_','_','O'};
            ttt.board.nextState = new TTTBoard(ph2);
            ttt.board.nextState.prevBoard = ph2;
            ttt.getCPUSpot('X');
            cpuPass = ttt.board.prevBoard != temp;
            if (!cpuPass) System.out.println("Board update failed");

            // Check TTTBoard
            TTTBoard b = new TTTBoard();
            boolean cstrPass = true;
            for(int i = 0 ; i < 9 ; ++i)  {
                if(b.currBoard[i] != (char)(i+'0') || b.prevBoard[i] != (char)(i+'0'))
                    cstrPass = false;
            }
            if(!cstrPass) System.out.println("Board Construction failed");

            // Check printing
            boolean printPass = true;
            printPass  =  b.printThisBoard(b.prevBoard).equals(b.printPrevBoard());
            printPass  =  b.printThisBoard(b.currBoard).equals(b.printCurrBoard());
            if(!printPass) System.out.println("Board Print failed");

            // Check Terminal
            boolean termPass = true;
            char[] arr = {'X','O','X','O','X','O','X','O','X'};
            b = new TTTBoard(arr);
            if(!b.isTerminal()) termPass = false;
            for(int i = 0; i <arr.length ;  ++i) {
                arr[i] = (char) (i+'0');
                b = new TTTBoard(arr);
                if(b.isTerminal())
                    termPass = false;
                arr[i] = 'X';
            }
            if(!termPass) System.out.println("Board Terminality failed");

            // Check score-check
            boolean scorePass = true;
            arr = new char[]{'X','O','X','O','X','O','X','O','X'};
            b = new TTTBoard(arr);
            if(b.getScore() != 100) scorePass = false;
            
            arr = new char[]{'X','X','X','1','2','3','4','5','6'}; // row1
            b = new TTTBoard(arr);
            if(b.getScore() != 100) scorePass = false;
            
            arr = new char[]{'1','2','3','X','X','X','4','5','6'}; // row2
            b = new TTTBoard(arr);
            if(b.getScore() != 100) scorePass = false;
            
            arr = new char[]{'1','2','3','4','5','6','X','X','X'}; // row3
            b = new TTTBoard(arr);
            if(b.getScore() != 100) scorePass = false;
            
            arr = new char[]{'O','1','2','3','O','4','5','6','O'}; // Diag 1
            b = new TTTBoard(arr);
            if(b.getScore() != -100) scorePass = false;
            
            arr = new char[]{'1','2','O','5','O','6','O','3','4'}; // Diag 2
            b = new TTTBoard(arr);
            if(b.getScore() != -100) scorePass = false;
            
            arr = new char[]{'X','1','2','X','3','4','X','5','6'}; // col1
            b = new TTTBoard(arr);
            if(b.getScore() != 100) scorePass = false;
            
            arr = new char[]{'5','X','1','2','X','3','4','X','6'}; // col2 
            b = new TTTBoard(arr);
            if(b.getScore() != 100) scorePass = false;
            
            arr = new char[]{'1','2','X','3','4','X','5','6','X'}; // col3
            b = new TTTBoard(arr);
            if(b.getScore() != 100) scorePass = false;
            
            if(!scorePass) System.out.println("Score Check failed");



            if(termPass && scorePass && cstrPass && printPass && cpuPass)
                System.out.println("All synthethic tests passed!");
    }
}