import java.util.*;

public class Task3_Fixed {

    static String encrypted = "c_tsreiuaaddcvenay__ktsst_knoerw";
    static String target    = "advanced_network_security_tasks_";


    static int    LENGTH    = encrypted.length();
    static long   count     = 0;

    public static void main(String[] args) {
        System.out.println("Searching for correct rows, columns, and key...");
        System.out.println("Encrypted length: " + LENGTH);

        for (int rows = 2; rows <= LENGTH / 2; rows++) {
            if (LENGTH % rows != 0) continue;
            int cols = LENGTH / rows;
            if (cols > 10) {
                System.out.println("Skipping rows=" + rows + ", cols=" + cols + " (too large)");
                continue;
            }
            System.out.println("Trying rows=" + rows + ", cols=" + cols + "...");
            int[] rowArr = buildArray(rows);
            int[] colArr = buildArray(cols);
            if (searchPerms(rowArr, colArr, rows, cols)) return;
        }
        System.out.println("No solution found.");
        System.out.println("Total keys tested: " + count);
    }

    static boolean searchPerms(int[] rowArr, int[] colArr, int rows, int cols) {
        return permuteRows(rowArr, 0, colArr, rows, cols);
    }

    static boolean permuteRows(int[] rowArr, int start, int[] colArr, int rows, int cols) {
        if (start == rowArr.length) return permuteCols(rowArr, colArr, 0, rows, cols);
        for (int i = start; i < rowArr.length; i++) {
            swap(rowArr, start, i);
            if (permuteRows(rowArr, start + 1, colArr, rows, cols)) return true;
            swap(rowArr, start, i);
        }
        return false;
    }

    static boolean permuteCols(int[] rowArr, int[] colArr, int start, int rows, int cols) {
        if (start == colArr.length) {
            count++;
            String result = decrypt(encrypted, rows, cols, rowArr, colArr);
            if (result.equals(target)) {
                System.out.println("\n[SUCCESS! Message Hacked]");
                System.out.println("Rows: " + rows + ", Cols: " + cols);
                System.out.println("Row Order: " + Arrays.toString(rowArr));
                System.out.println("Col Order: " + Arrays.toString(colArr));
                System.out.println("Decrypted: " + result);
                System.out.println("Total keys tested: " + count);
                return true;
            }
            return false;
        }
        for (int i = start; i < colArr.length; i++) {
            swap(colArr, start, i);
            if (permuteCols(rowArr, colArr, start + 1, rows, cols)) return true;
            swap(colArr, start, i);
        }
        return false;
    }

    static void swap(int[] arr, int a, int b) {
        int tmp = arr[a]; arr[a] = arr[b]; arr[b] = tmp;
    }

    public static int[] buildArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = i;
        return arr;
    }

    public static String decrypt(String text, int rows, int cols,
                                  int[] rowOrder, int[] colOrder) {
        char[][] matrix = new char[rows][cols];
        int index = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                matrix[i][j] = text.charAt(index++);

        char[][] rowStep = new char[rows][cols];
        for (int i = 0; i < rows; i++)
            rowStep[rowOrder[i]] = matrix[i].clone();

        char[][] finalMatrix = new char[rows][cols];
        for (int j = 0; j < cols; j++)
            for (int i = 0; i < rows; i++)
                finalMatrix[i][colOrder[j]] = rowStep[i][j];

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                sb.append(finalMatrix[i][j]);
        return sb.toString();
    }
}
