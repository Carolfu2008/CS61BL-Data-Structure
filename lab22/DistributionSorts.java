import java.util.Arrays;
import java.util.HashMap;

import static java.lang.Math.pow;

public class DistributionSorts {

    /**
     * Modify arr to be sorted. Assume arr only contains 0, 1, ..., 9
     */
    public static void countingSort(int[] arr) {
        // TODO your code here!
        int[] myArr = new int[10];
        for (int i : arr) {
            myArr[i]++;
        }
        int base = 0;
        for (int i = 0; i < myArr.length; i++) {
            for (int j = 0; j < myArr[i]; j++) {
                arr[base] = i;
                base++;
            }
        }
    }

    /**
     * Sorts the given array using LSD radix sort.
     */
    public static void LSDRadixSort(int[] arr) {
        int maxDigit = mostDigitsIn(arr);
        for (int d = 0; d < maxDigit; d++) {
            countingSortOnDigit(arr, d);
        }
    }

    /**
     * A helper method for radix sort. Modifies arr to be sorted according to
     * digit. When digit is equal to 0, sort the numbers by the rightmost digit of each number.
     * <p>
     * You will need to create an auxiliary array in order to put elements in the
     * correct spot before transferring them back into arr.
     */
    private static void countingSortOnDigit(int[] arr, int digit) {
        // TODO your code here!
        int output[] = new int[arr.length]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);

        for (i = 0; i < arr.length; i++)
            count[(arr[i] / (int) Math.pow(10, digit)) % 10]++;

        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (i = arr.length - 1; i >= 0; i--) {
            output[count[(arr[i] / (int) Math.pow(10, digit)) % 10] - 1] = arr[i];
            count[(arr[i] / (int) Math.pow(10, digit)) % 10]--;
        }
        
        for (i = 0; i < arr.length; i++)
            arr[i] = output[i];
    }

    /**
     * Returns the highest number of digits that any integer in arr happens to
     * have.
     */
    private static int mostDigitsIn(int[] arr) {
        int maxDigitsSoFar = 0;
        for (int num : arr) {
            int numDigits = (int) (Math.log10(num) + 1);
            if (numDigits > maxDigitsSoFar) {
                maxDigitsSoFar = numDigits;
            }
        }
        return maxDigitsSoFar;
    }

    /**
     * Returns a random integer between 0 and 9999.
     */
    private static int randomInt() {
        return (int) (10000 * Math.random());
    }

    /**
     * Returns a random integer between 0 and 9.
     */
    private static int randomDigit() {
        return (int) (10 * Math.random());
    }

    /**
     * Runs some very basic tests of counting sort and radix sort.
     */
    public static void main(String[] args) {
        int[] arr1 = new int[20];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = randomDigit();
        }
        System.out.println("Original array: " + Arrays.toString(arr1));
        countingSort(arr1);
        if (arr1 != null) {
            System.out.println("Should be sorted: " + Arrays.toString(arr1));
        }

        int[] arr2 = new int[3];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = randomDigit();
        }
        System.out.println("Original array: " + Arrays.toString(arr2));
        LSDRadixSort(arr2);
        System.out.println("Should be sorted: " + Arrays.toString(arr2));

        int[] arr3 = new int[30];
        for (int i = 0; i < arr3.length; i++) {
            arr3[i] = randomInt();
        }
        System.out.println("Original array: " + Arrays.toString(arr3));
        LSDRadixSort(arr3);
        System.out.println("Should be sorted: " + Arrays.toString(arr3));
    }
}
