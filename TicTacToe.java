// import java.util.Scanner;
import java.util.*;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] board = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        while (true) {
            playerMove(board, scanner);
            if (isGameFinished(board)) {
                break;
            }
            printBoard(board);

            aiMove(board);
            if (isGameFinished(board)) {
                break;
            }
            printBoard(board);
        }
        scanner.close();
    }

    private static boolean isGameFinished(char[][] board) {
        if (hasContestantWon(board, 'X')) {
            printBoard(board);
            System.out.println("Player wins!");
            return true;
        }
        if (hasContestantWon(board, 'O')) {
            printBoard(board);
            System.out.println("AI wins!");
            return true;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        printBoard(board);
        System.out.println("The game ended in a tie!");
        return true;
    }

    private static boolean hasContestantWon(char[][] board, char symbol) {
        if ((board[0][0] == symbol && board[0][1] == symbol && board[0][2] == symbol) ||
                (board[1][0] == symbol && board[1][1] == symbol && board[1][2] == symbol) ||
                (board[2][0] == symbol && board[2][1] == symbol && board[2][2] == symbol) ||
                (board[0][0] == symbol && board[1][0] == symbol && board[2][0] == symbol) ||
                (board[0][1] == symbol && board[1][1] == symbol && board[2][1] == symbol) ||
                (board[0][2] == symbol && board[1][2] == symbol && board[2][2] == symbol) ||
                (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
            return true;
        }
        return false;
    }

    private static void printBoard(char[][] board) {
        System.out.print((char)27 + "[H" + (char)27 + "[2J");
        System.out.print("\n  0   1   2\n");
        for (int i = 0; i < board.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < board[i].length - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < board.length - 1) {
                System.out.println(" ------------");
            }
        }
    }

    private static void playerMove(char[][] board, Scanner scanner) {
        String userInput;
        while (true) {
            System.out.println("Enter your move (row and column): ");
            userInput = scanner.nextLine();
            if (isValidMove(board, userInput)) {
                break;
            } else {
                System.out.println(userInput + " is not a valid move.");
            }
        }
        int row = Character.getNumericValue(userInput.charAt(0));
        int col = Character.getNumericValue(userInput.charAt(1));
        board[row][col] = 'X';
    }

    private static boolean isValidMove(char[][] board, String position) {
        if (position.length() != 2) {
            return false;
        }
        int row = Character.getNumericValue(position.charAt(0));
        int col = Character.getNumericValue(position.charAt(1));
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            return false;
        }
        if (board[row][col] != ' ') {
            return false;
        }
        return true;
    }

    private static void aiMove(char[][] board) {
        int[] bestMove = minimax(board, 'O');
        board[bestMove[1]][bestMove[2]] = 'O';
    }

    private static int[] minimax(char[][] board, char player) {
        int[] bestMove = new int[3];
        bestMove[0] = (player == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        if (hasContestantWon(board, 'X')) {
            bestMove[0] = -1;
            return bestMove;
        } else if (hasContestantWon(board, 'O')) {
            bestMove[0] = 1;
            return bestMove;
        } else if (isBoardFull(board)) {
            bestMove[0] = 0;
            return bestMove;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimax(board, (player == 'O') ? 'X' : 'O')[0];
                    board[i][j] = ' ';
                    if (player == 'O') {
                        if (score > bestMove[0]) {
                            bestMove[0] = score;
                            bestMove[1] = i;
                            bestMove[2] = j;
                        }
                    } else {
                        if (score < bestMove[0]) {
                            bestMove[0] = score;
                            bestMove[1] = i;
                            bestMove[2] = j;
                        }
                    }
                }
            }
        }
        return bestMove;
    }
    private static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
