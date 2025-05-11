import java.util.*;

public class RadixSortNumbers {
    public static void main(String[] args) {
         // Creating Scanner class object
        Scanner sc = new Scanner(System.in);

        // User input on how many elements (numbers) user want to sort
        System.out.print("Enter numbers of elements that you would like to sort: ");
        int totalWords = sc.nextInt();

        // Integer array declaration for accepting list of input from user
        int[] inputNumbers = new int[totalWords];
        int range = 10; // digits 0–9

        // User input
        // Accept each word from user
        for (int i = 0; i < totalWords; i++) {
            System.out.print("Enter element " + (i + 1) + ": ");
            inputNumbers[i] = sc.nextInt();
        }

        // Find max digit length
        int maxNumber = 0;
        for (int i = 0; i < totalWords; i++) {
            if (inputNumbers[i] > maxNumber) {
                maxNumber = inputNumbers[i];
            }
        }
        int maxLength = String.valueOf(maxNumber).length();

        // Step 1: Initialize buckets (Array1, Array2) and result
        int[][] Array1 = new int[totalWords][range];
        int[][] Array2 = new int[totalWords][range];
        int[] resultArray = new int[totalWords];

        // Initialize buckets
        for (int i = 0; i < totalWords; i++) {
            for (int j = 0; j < range; j++) {
                Array1[i][j] = -1;
                Array2[i][j] = -1;
            }
        }

        // Copy input into resultArray
        for (int i = 0; i < totalWords; i++) {
            resultArray[i] = inputNumbers[i];
        }

        // Print the initial inputArray
        System.out.println("Unsorted list:");
        for (int i = 0; i < totalWords; i++) {
            String padded = String.format("%0" + maxLength + "d", inputNumbers[i]);
            System.out.print(padded);
            if (i < totalWords - 1) System.out.print(", ");
        }
        System.out.println("\n");

        // Step 2: Iteration - Radix sort from rightmost character to leftmost character
        int divisor = 1;
        for (int pos = 0; pos < maxLength; pos++) {
            System.out.println("Pass: " + (pos + 1));

            if (pos == 0) {
                // First pass: from paddedInput → Array1 (sort into Array1)
                for (int i = 0; i < totalWords; i++) {
                    int digit = (resultArray[i] / divisor) % 10;
                    int row = 0;
                    while (row < totalWords) {
                        if (Array1[row][digit] == -1) {
                            Array1[row][digit] = resultArray[i];
                            break;
                        }
                        row++;
                    }
                }
            } else if (pos % 2 == 1) {
                // Read from Array1 → sort into Array2 (even pass: 2nd pass, 4th pass, etc)
                for (int col = 0; col < range; col++) {
                    for (int row = 0; row < totalWords; row++) {
                        if (Array1[row][col] != -1) {
                            int num = Array1[row][col];
                            int digit = (num / divisor) % 10;
                            int newRow = 0;
                            while (newRow < totalWords) {
                                if (Array2[newRow][digit] == -1) {
                                    Array2[newRow][digit] = num;
                                    break;
                                }
                                newRow++;
                            }
                            Array1[row][col] = -1;
                        }
                    }
                }
            } else {
                // Read from Array2 → sort into Array1 (odd pass: 3rd pass, 5th pass, etc.)
                for (int col = 0; col < range; col++) {
                    for (int row = 0; row < totalWords; row++) {
                        if (Array2[row][col] != -1) {
                            int num = Array2[row][col];
                            int digit = (num / divisor) % 10;
                            int newRow = 0;
                            while (newRow < totalWords) {
                                if (Array1[newRow][digit] == -1) {
                                    Array1[newRow][digit] = num;
                                    break;
                                }
                                newRow++;
                            }
                            Array2[row][col] = -1;
                        }
                    }
                }
            }

            // Step 3: Reorder
            // Build resultArray from the filled array in Array1 or Array2
            int index = 0;

            // If total pass is even number, take the final answer from Array1
            if (pos % 2 == 0) {
                for (int col = 0; col < range; col++) {
                    for (int row = 0; row < totalWords; row++) {
                        if (Array1[row][col] != -1) {
                            resultArray[index++] = Array1[row][col];
                        }
                    }
                }
            } else {
                // If total pass is odd number, take the final answer from Array2
                for (int col = 0; col < range; col++) {
                    for (int row = 0; row < totalWords; row++) {
                        if (Array2[row][col] != -1) {
                            resultArray[index++] = Array2[row][col];
                        }
                    }
                }
            }

            // Print current pass result
            for (int i = 0; i < totalWords; i++) {
                String padded = String.format("%0" + maxLength + "d", resultArray[i]);
                System.out.print(padded);
                if (i < totalWords - 1) System.out.print(", ");
            }
            System.out.println("\n");

            divisor *= 10;
        }

        // Step 3: Reorder - Print sorted list (cont.)
        System.out.println("Final sorted result:");
        for (int i = 0; i < totalWords; i++) {
            String padded = String.format("%0" + maxLength + "d", resultArray[i]);
            System.out.print(padded);
            if (i < totalWords - 1) System.out.print(", ");
        }
    }
}
