import java.util.*;

public class RadixSortWords {
    public static void main(String[] args) {
        // Creating Scanner class object
        Scanner sc = new Scanner(System.in);

        // User input on how many elements (words) user want to sort
        int totalWords=0;
        System.out.print("Enter numbers of words that you would like to sort:" );
        totalWords = sc.nextInt();
        sc.nextLine();

        // String array declaration for accepting list of input from user
        String[] inputArray = new String[totalWords];
        int range = 27; // 26 letters + space

        // User input
        // Accept each word from user
        for (int i = 0; i < totalWords; i++) {
            System.out.print("Enter word " + (i + 1) + ": ");
            inputArray[i] = sc.nextLine().toLowerCase(); // lowercase for consistency
        }

        // Find max length of character in all of the elements
        int maxLength = 0;
        for (int i = 0; i < totalWords; i++) {
            if (inputArray[i].length() > maxLength) {
                maxLength = inputArray[i].length();
            }
        }

        // Pad element(s) in inputArray that has total character less than maxLength with space
        String[] paddedInput = new String[totalWords];
        for (int i = 0; i < totalWords; i++) {
            String word = inputArray[i];
            while (word.length() < maxLength) {
                word += " ";
            }
            paddedInput[i] = word;
        }

        // Step 1: Initialize buckets (Array1, Array2) and result
        String[][] Array1 = new String[totalWords][range];
        String[][] Array2 = new String[totalWords][range];
        String[] resultArray = new String[totalWords];

        // Print the initial inputArray
        System.out.println("Unsorted list:");
        for (int i = 0; i < totalWords; i++) {
            System.out.print(inputArray[i]);
            if (i < totalWords - 1) System.out.print(", ");
        }
        System.out.println("\n");

        // Step 2: Iteration - Radix sort from rightmost character to leftmost character
        for (int currentPos = maxLength - 1; currentPos >= 0; currentPos--) {
            System.out.println("Pass: " + (maxLength - currentPos));

            // First pass: from paddedInput → Array1 (sort into Array1)
            if ((maxLength - 1 - currentPos) == 0) {
                for (int i = 0; i < totalWords; i++) {
                    char c = paddedInput[i].charAt(currentPos);
                    int col;
                    if (c == ' ') {
                        col = 0;
                    } else {
                        col = c - 'a' + 1;
                    }

                    int row = 0;

                    while (row < totalWords) {
                        if (Array1[row][col] == null) {
                            Array1[row][col] = paddedInput[i];
                            break;
                        }
                        row++;
                    }
                }
            } else {
                // Subsequent pass: From Array1 → Array2 (even pass) or Array2 → Array1 (odd pass)
                if ((maxLength - 1 - currentPos) % 2 == 1) {
                    // Read from Array1 → sort into Array2 (even pass: 2nd pass, 4th pass, etc)
                    for (int col = 0; col < range; col++) {
                        for (int row = 0; row < totalWords; row++) {
                            if (Array1[row][col] != null) {
                                String word = Array1[row][col];
                                char c = word.charAt(currentPos);
                                int newCol;
                                // determine which column (bucket) the word should go to in Array2
                                if (c == ' ') {
                                    newCol = 0;
                                } else {
                                    newCol = c - 'a' + 1;
                                }

                                // Insert into Array2
                                int newRow = 0;
                                while (newRow < totalWords) {
                                    if (Array2[newRow][newCol] == null) {
                                        Array2[newRow][newCol] = word;
                                        Array1[row][col] = null; // Clear after moving
                                        break;
                                    }
                                    newRow++;
                                }
                            }
                        }
                    }
                } else {
                    // Read from Array2 → sort into Array1 (odd pass: 3rd pass, 5th pass, etc.)
                    for (int col = 0; col < range; col++) {
                        for (int row = 0; row < totalWords; row++) {
                            if (Array2[row][col] != null) {
                                String word = Array2[row][col];
                                char c = word.charAt(currentPos);
                                int newCol;
                                // determine which column (bucket) the word should go to in Array2
                                if (c == ' ') {
                                    newCol = 0;
                                } else {
                                    newCol = c - 'a' + 1;
                                }

                                // Insert into Array2
                                int newRow = 0;
                                while (newRow < totalWords) {
                                    if (Array1[newRow][newCol] == null) {
                                        Array1[newRow][newCol] = word;
                                        Array2[row][col] = null;
                                        break;
                                    }
                                    newRow++;
                                }
                            }
                        }
                    }
                }
            }

            // Step 3: Reorder
            // Build resultArray from the filled array in Array1 or Array2
            int index = 0;

            // If total pass is even number, take the final answer from Array1
            if ((maxLength - 1 - currentPos) % 2 == 0) {
                for (int col = 0; col < range; col++) {
                    for (int row = 0; row < totalWords; row++) {
                        if (Array1[row][col] != null) {
                            resultArray[index++] = Array1[row][col];
                        }
                    }
                }
            } else {
                // If total pass is odd number, take the final answer from Array2
                for (int col = 0; col < range; col++) {
                    for (int row = 0; row < totalWords; row++) {
                        if (Array2[row][col] != null) {
                            resultArray[index++] = Array2[row][col];
                        }
                    }
                }
            }

            // Show intermediate result
            for (int i = 0; i < totalWords; i++) {
                System.out.print(resultArray[i].trim());
                if (i < totalWords - 1) System.out.print(", "); // Adding comma in between each elements
            }
            System.out.println("\n");
        }

        // Step 3: Reorder - Print sorted list (cont.)
        System.out.println("Final sorted result:");
        for (int i = 0; i < totalWords; i++) {
            System.out.print(resultArray[i].trim());
            if (i < totalWords - 1) System.out.print(", "); // Adding comma in between each elements
        }
    }
}
