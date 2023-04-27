package tictactoe;

import java.util.*;

public class Main {

    private static final String[][] grid = {{" ", " ", " "},{" ", " ", " "},{" ", " ", " "}};
    private static List<Integer> coordinates = new ArrayList<>();

    public static void printLine() {
        System.out.println("---------");
    }

    public static void buildGrid(String[] input) {

        /*
            Defining the pointer variable outside the loop means that changes inside the loop
            won't reset, and the ptr will continue to increment.

            Outer Inner Pointer

                0   0   0
                0   1   1
                0   2   2
                1   0   3
                1   1   4
                etc...
         */
        int ptr = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (ptr < input.length) {
                    grid[i][j] = input[ptr];
                }
                ptr++;
            }
        }
//        System.out.println(Arrays.deepToString(grid));

    }



    public static void printGrid() {
        printLine();

        for (String[] strings : grid) {
            System.out.print("| ");
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println("|");
        }
        printLine();
    }

    public static boolean checkWinner(int moves) {

        String row1 = String.join(grid[0][0], grid[0][1], grid[0][2]);
        String row2 = String.join(grid[1][0], grid[1][1], grid[1][2]);
        String row3 = String.join(grid[2][0], grid[2][1], grid[2][2]);

        String col1 = String.join(grid[0][0], grid[1][0], grid[2][0]);
        String col2 = String.join(grid[0][1], grid[1][1], grid[2][1]);
        String col3 = String.join(grid[0][2], grid[1][2], grid[2][2]);

        String diag1 = String.join(grid[0][0], grid[1][1], grid[2][2]);
        String diag2 = String.join(grid[0][2], grid[1][1], grid[2][0]);

        List<String> strings = List.of(row1, row2, row3, col1, col2, col3, diag1, diag2);

        // Check for X or O win.
        for (String s : strings) {
            if (s.contains("XXX")) {
                System.out.println("X wins");
                return true;
            }

            if (s.contains("OOO")) {
                System.out.println("O wins");
                return true;
            }
//
//            System.out.println(moves);
            if (moves >= 9) {
                System.out.println("Draw");
                return true;
            }
        }
        return false;

    }

    public static String getPlayer(int moves) {
        return (moves % 2 == 0) ? "O" : "X";
    }

    public static void playerMove(String player) {
        System.out.println("Enter coordinates: ");
        String[] input =  new Scanner(System.in).nextLine().split(" ");

        // Sanitize input (numeric value between 1 and 3)
        for (String s : input) {
            try {
                int num = Integer.parseInt(s);

                if (num < 1 || num > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    coordinates = new ArrayList<>();
                    break;
                }
                coordinates.add(num);
            } catch (NumberFormatException ex) {
                System.out.println("You should enter numbers!");
                coordinates = new ArrayList<>();
                break;
            }
        }

        // if we have coordinates, check if the spot available?
        if (coordinates.size() == 2) {
            int x = coordinates.get(0) - 1;
            int y = coordinates.get(1) - 1;
            String cell = grid[x][y];

            if (cell.equals("X") || cell.equals("O")) {
                System.out.println("This cell is occupied! Choose another one!");
                coordinates = new ArrayList<>();
            } else {
                grid[x][y] = player;
            }
        }
    }



    public static void playerTurn(int moves) {

         while (coordinates.size() < 2) {
             playerMove(getPlayer(moves));
         }
         coordinates = new ArrayList<>();
    }

    public static void main(String[] args) {

        printGrid();

        boolean gameOver = false;
        int moves = 1;
        while(!gameOver) {
            playerTurn(moves);
            printGrid();
            if (moves > 5) {
                gameOver = checkWinner(moves);
            }
            moves++;
        }
    }


}
