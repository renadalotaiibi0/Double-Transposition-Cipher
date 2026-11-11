import java.util.Scanner;

public class DoubleTransposition {

    public static char[][] fillGrid(String text, int rows, int cols) {
        char[][] grid = new char[rows][cols];
        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (index < text.length()) {
                    grid[r][c] = text.charAt(index++);
                } else {
                    grid[r][c] = '_';
                }
            }
        }
        return grid;
    }

    public static char[][] transposeColumns(char[][] grid, int[] colOrder, int rows, int cols) {
        char[][] newGrid = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                newGrid[r][c] = grid[r][colOrder[c]];
            }
        }
        return newGrid;
    }

    public static char[][] transposeRows(char[][] grid, int[] rowOrder, int rows, int cols) {
        char[][] newGrid = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            newGrid[r] = grid[rowOrder[r]].clone();
        }
        return newGrid;
    }

    public static String readGrid(char[][] grid, int rows, int cols) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                sb.append(grid[r][c]);
            }
        }
        return sb.toString();
    }

    public static void printGrid(char[][] grid, int rows, int cols) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(grid[r][c] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine().replace(' ', '_');

        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();

        System.out.print("Enter number of columns: ");
        int cols = sc.nextInt();

        int[] rowOrder = new int[rows];
        System.out.print("Enter row order: ");
        for (int i = 0; i < rows; i++) rowOrder[i] = sc.nextInt();

        int[] colOrder = new int[cols];
        System.out.print("Enter column order: ");
        for (int i = 0; i < cols; i++) colOrder[i] = sc.nextInt();

        System.out.println("\n========== ENCRYPTION STEPS ==========");

        char[][] grid = fillGrid(plaintext, rows, cols);
        System.out.println("\nStep 1 - Original Grid:");
        printGrid(grid, rows, cols);

        char[][] afterColTranspose = transposeColumns(grid, colOrder, rows, cols);
        System.out.println("\nStep 2 - After Column Transposition:");
        printGrid(afterColTranspose, rows, cols);

        char[][] afterRowTranspose = transposeRows(afterColTranspose, rowOrder, rows, cols);
        System.out.println("\nStep 3 - After Row Transposition:");
        printGrid(afterRowTranspose, rows, cols);

        String ciphertext = readGrid(afterRowTranspose, rows, cols);
        System.out.println("\n======================================");
        System.out.println("Original Text  : " + plaintext);
        System.out.println("Encrypted Text : " + ciphertext);
        System.out.println("======================================");

        sc.close();
    }
}
