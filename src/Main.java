import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filename = args.length != 1 ? new File("numbers.txt").getAbsolutePath() : args[0];
        int[] arr = readFileToNumberArray(filename);

        long sum = arr[0];
        double arithmeticMean;
        int curIncSeq = 1;
        int curDecSeq = 1;
        int maxIncSeq = 1;
        int maxDecSeq = 1;
        int maxIncSeqIndex = 0;
        int maxDecSeqIndex = 0;

        for (int i = 1; i < arr.length; i++) {
            sum += arr[i];
            if (arr[i] > arr[i - 1]) {
                if (curDecSeq > maxDecSeq) {
                    maxDecSeq = curDecSeq;
                    maxDecSeqIndex = i - maxDecSeq;
                }
                curDecSeq = 1;
                curIncSeq++;
            } else if (arr[i] < arr[i - 1]) {
                if (curIncSeq > maxIncSeq) {
                    maxIncSeq = curIncSeq;
                    maxIncSeqIndex = i - maxIncSeq;
                }
                curIncSeq = 1;
                curDecSeq++;
            } else {
                if (curDecSeq > maxDecSeq) {
                    maxDecSeq = curDecSeq;
                    maxDecSeqIndex = i - maxDecSeq;
                }
                if (curIncSeq > maxIncSeq) {
                    maxIncSeq = curIncSeq;
                    maxIncSeqIndex = i - maxIncSeq;
                }
                curDecSeq = 1;
                curIncSeq = 1;
            }
        }
        arithmeticMean = (double) sum / arr.length;
        int[] maxIncSeqArr = new int[maxIncSeq];
        System.arraycopy(arr, maxIncSeqIndex, maxIncSeqArr, 0, maxIncSeq);
        int[] maxDecSeqArr = new int[maxDecSeq];
        System.arraycopy(arr, maxDecSeqIndex, maxDecSeqArr, 0, maxDecSeq);

        Arrays.sort(arr);

        long minValue = arr[0];
        long maxValue = arr[arr.length - 1];
        double median = arr.length % 2 == 1 ? arr[arr.length / 2] : (arr[arr.length / 2 - 1] + arr[arr.length / 2]) * 0.5;

        System.out.println("Maximum value is " + maxValue);
        System.out.println("Minimum value is " + minValue);
        System.out.println("Median is " + median);
        System.out.println("Arithmetic mean is " + arithmeticMean);
        System.out.println("The longest increasing sequence length is: " + maxIncSeq);
        System.out.println("The longest decreasing sequence length is: " + maxDecSeq);
        System.out.println("Maximum increasing sequence: ");
        System.out.println(Arrays.toString(maxIncSeqArr));
        System.out.println("Maximum decreasing sequence: ");
        System.out.println(Arrays.toString(maxDecSeqArr));

        System.out.println("\nPress enter to close the program.");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    public static int[] readFileToNumberArray(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines().mapToInt(Integer::parseInt).toArray();
            //One more solution below. It checks that line is a number, but works slower
            //return reader.lines().filter(s -> s.matches("\\d+")).mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {
            System.err.println("""
                    Error reading the file.
                    The program should launches from cmd with file path as an argument or file should be in the same directory as .jar.
                    The file should consist of integers only.""");
            System.exit(1);
            return new int[0];
        }
    }
}