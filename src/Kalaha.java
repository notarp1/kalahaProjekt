import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kalaha {

    static int kuglerGlobal;
    static int boardGlobal[];
    static boolean currentPlayer, winnerGlobal;
    //https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-4-alpha-beta-pruning/
    //Copied from geekforgeeks 09/03/2021
    static int minimax(int depth, int nodeIndex, Boolean maximizingPlayer, int values[], int alpha, int beta)
    {
        // Terminating condition. i.e
        // leaf node is reached
        if (depth == 3)
            return values[nodeIndex];

        maximizingPlayer = currentPlayer;

        if (maximizingPlayer)
        {
            int best = getKugleSum(boardGlobal, currentPlayer);

            // Recur for left and
            // right children
            for (int i = 0; i < 6; i++)
            {
                int val = minimax(depth + 1, nodeIndex * 2 + i,
                        false, values, alpha, beta);
                best = Math.max(best, val);
                alpha = Math.max(alpha, best);

                // Alpha Beta Pruning
                if (beta <= alpha)
                    break;
            }
            return best;
        }
        else
        {
            int best = getKugleSum(boardGlobal, currentPlayer);

            // Recur for left and
            // right children
            for (int i = 0; i < 2; i++)
            {

                int val = minimax(depth + 1, nodeIndex * 2 + i,
                        true, values, alpha, beta);
                best = Math.min(best, val);
                beta = Math.min(beta, best);

                // Alpha Beta Pruning
                if (beta <= alpha)
                    break;
            }
            return best;
        }



    }
    private static int getKugleSum(int[] board, boolean currentPlayer){
       int sum;

       if(currentPlayer){

           sum = 0;
           for(int i = 1; i<6; i++){

               sum += board[i];
           }
       } else {

           sum = 0;
           for(int i = 7; i<13; i++){

               sum += board[i];
           }
       }
        return sum;
    }


    public static void main(String[] args) {


        System.out.println("Indtast kugler");
        Scanner sc= new Scanner(System.in);    //System.in is a standard input stream
        int kugler = sc.nextInt();
        kuglerGlobal = kugler;
        boolean player1 = true;
        boolean winner = false;
        winnerGlobal = winner;


        int board[] = {0, kugler, kugler ,kugler ,kugler ,kugler, kugler, 0, kugler, kugler, kugler, kugler, kugler, kugler};
        boardGlobal = board;
        printBoard(board);

        while(!winner){
            currentPlayer = player1;

            if(player1){
                System.out.println("Spiller 1 vælg nummer");
            } else  System.out.println("Spiller 2 vælg nummer");

            int input = sc.nextInt();



            int valg = board[input];
            board[input] = 0;


            for(int i = 1; i< valg+1; i++){
                int val2 = input + i;


                if(val2 > 13){

                    val2 = val2 - 14;
                }
                board[val2] ++;



                if(i == valg){

                    if(!isWinner(kugler, winner, board)){
                        if(val2 == 0 || val2 == 7){
                            System.out.println("Extra tur");
                        } else player1 = !player1;
                    } else winner = true;

                }
            }
            printBoard(board);
        }
    }

    private static int[][] evalMove(int[] boardGlobal){
        int[] moves = {1, 2, 3, 4, 5, 6};

        List<Integer> path = new ArrayList<>();

        for(int j = 1; j<6; j++) {
            int valg = j;
            path.add(j);
            int input = boardGlobal[valg];

            for (int i = 1; i < valg + 1; i++) {
                int val2 = input + i;


                if (val2 > 13) {

                    val2 = val2 - 14;
                }
                boardGlobal[val2]++;


                if (i == valg) {

                    if (!isWinner(kuglerGlobal, winnerGlobal, boardGlobal)) {
                        if (val2 == 0 || val2 == 7) {
                            System.out.println("Extra tur");
                        } else {
                            currentPlayer = !currentPlayer;
                            return [boardGlobal[7]][]
                        }
                    } else winnerGlobal = true;

                }
            }
        }

    }
    private static boolean isWinner(int kugler, boolean winner, int[] board) {
        System.out.println(board[0] + ": Board 0");
        System.out.println(board[7] + ": Board 7");
        System.out.println(kugler + ": Kugler");
        if(board[0] + board[7] == kugler * 12){
            winner = true;
            if(board[0] < board[7]){
                System.out.println("Tillykke spiller 1!!");
            } else  System.out.println("Tillykke spiller 2!!");
        }
        return winner;
    }

    private static void printBoard(int[] board) {
        System.out.println("---------------------------------------");
        System.out.println("      13  12  11  10  9   8");
        System.out.println("    | " + board[13] + " | " + board[12] + " | " + board[11] + " | " + board[10] + " | " + board[9] + " | " + board[8] + " |");
        System.out.println("  |" + board[0] + "|                        |" + board[7] + "|  ");
        System.out.println("    | " + board[1] + " | " + board[2] + " | " + board[3] + " | " + board[4] + " | " + board[5] + " | " + board[6] + " |");
        System.out.println("      1   2   3   4   5   6");
        System.out.println("---------------------------------------");
    }

}

