import java.util.ArrayList;
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
        ArrayList<ArrayList<ArrayList<Integer>>> path;


        int board[] = {0, kugler, kugler ,kugler ,kugler ,kugler, kugler, 0, kugler, kugler, kugler, kugler, kugler, kugler};

        printBoard(board);

        while(!winner) {
            currentPlayer = player1;

            if (player1) {

                path = evalMove(board, kugler);
                player1 = false;

            } else {
                System.out.println("Spiller 2 vælg nummer");

                int input = sc.nextInt();


                int valg = board[input];
                board[input] = 0;


                for (int i = 1; i < valg + 1; i++) {
                    int val2 = input + i;


                    if (val2 > 13) {

                        val2 = val2 - 14;
                    }
                    board[val2]++;


                    if (i == valg) {

                        if (!isWinner(kugler, winner, board)) {
                            if (val2 == 0 || val2 == 7) {
                                System.out.println("Extra tur");
                            } else player1 = !player1;
                        } else winner = true;

                    }
                }
                printBoard(board);
            }
        }
    }

    private static ArrayList<ArrayList<ArrayList<Integer>>> evalMove(int[] board, int kugler){

        ArrayList<ArrayList<ArrayList<Integer>>> pathComplete = new ArrayList<>();

        ArrayList<ArrayList<Integer>> path = new ArrayList<>();
        ArrayList<Integer> cost = new ArrayList<>();




        for (int j = 1; j < 7; j++) {
            printBoard(board);
            int[] boardApprox = board;
            pathComplete.add(recursive(kugler, j, path, cost, boardApprox, false, 0));

        }
        return pathComplete;




    }

    private static ArrayList<ArrayList<Integer>> recursive(int kugler, int j, ArrayList<ArrayList<Integer>> cost, ArrayList<Integer> path, int[] board, boolean winner, int limit) {

        if (limit >= 3) {
            return cost;
        }



                int input = j;
                int valg = board[input];
                board[input] = 0;
                path.add(j);


                for (int i = 1; i < valg + 1; i++) {
                    int val2 = input + i;


                    if (val2 > 13) {

                        val2 = val2 - 14;
                    }
                    board[val2]++;


                    if (i == valg) {

                        cost.add(board[7], path);

                        if (!isWinner(kugler, winner, board)) {

                            if (val2 == 0 || val2 == 7) {
                                limit = limit + 1;


                                return recursive(kugler, j, cost, path, board, winner, limit);
                            } else {
                                if (input == 6) return cost;

                            }
                        } else {
                            winnerGlobal = true;

                            return cost;
                        }

                    }
                }



        return  cost;
    }
    private static boolean isWinner(int kugler, boolean winner, int[] board) {

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

