import java.util.*;

public class Task2 {
    
    static String encrypted = "e_uscrlcoa_ksseytmr_tciiyiit_s";
    static String target = "system_security_is_critical_ok";
    static int ROWS = 5;
    static int COLS = 6;

    public static void main(String[] args) {
        List<int[]> rowPerms = new ArrayList<>();
        List<int[]> colPerms = new ArrayList<>();
        
      
        generatePerms(new int[]{0, 1, 2, 3, 4}, 0, rowPerms);
        generatePerms(new int[]{0, 1, 2, 3, 4, 5}, 0, colPerms);

        System.out.println("Searching for the correct key...\n");
        
        System.out.printf("%-15s | %-20s | %-32s | %s\n", "Row Order", "Col Order", "Decrypt Result", "Status");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (int[] rows : rowPerms) {
            for (int[] cols : colPerms) {
                String result = decrypt(encrypted, rows, cols);
                
                String rowStr = Arrays.toString(rows).replaceAll("[\\[\\],]", "");
                String colStr = Arrays.toString(cols).replaceAll("[\\[\\],]", "");

                if (result.equals(target)) {

                    System.out.printf("%-15s | %-20s | %-32s | %s\n", rowStr, colStr, result, "CORRECT");
                    System.out.println("-----------------------------------------------------------------------------------------");
                    System.out.println("\n[SUCCESS! Message Hacked]");
                    System.out.println("Correct Row Order: " + Arrays.toString(rows));
                    System.out.println("Correct Col Order: " + Arrays.toString(cols));
                    System.out.println("Final Text: " + result);
                    return; 
                } else {

                    System.out.printf("%-15s | %-20s | %-32s | %s\n", rowStr, colStr, result, "INCORRECT");
                }
            }
        }
    }

    public static String decrypt(String text, int[] rowOrder, int[] colOrder) {
        char[][] matrix = new char[ROWS][COLS];
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                matrix[i][j] = text.charAt(index++);
            }
        }

        char[][] rowStep = new char[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            rowStep[rowOrder[i]] = matrix[i];
        }

        char[][] finalMatrix = new char[ROWS][COLS];
        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i < ROWS; i++) {
                finalMatrix[i][colOrder[j]] = rowStep[i][j];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                sb.append(finalMatrix[i][j]);
            }
        }
        return sb.toString();
    }

    public static void generatePerms(int[] arr, int start, List<int[]> res) {
        if (start == arr.length) {
            res.add(arr.clone());
            return;
        }
        for (int i = start; i < arr.length; i++) {
            int temp = arr[start]; arr[start] = arr[i]; arr[i] = temp;
            generatePerms(arr, start + 1, res);
            temp = arr[start]; arr[start] = arr[i]; arr[i] = temp;
        }
    }
}
