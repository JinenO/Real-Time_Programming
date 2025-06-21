package Chapter9;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.Random;

public class FindMax extends RecursiveTask<Integer> {

    private static final long serialVersionUID = 1L;

    private int[] numbers;
    private int startIndex;
    private int endIndex;

    public FindMax(int[] numbers, int startIndex, int endIndex) {
        this.numbers = numbers;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    protected Integer compute() {
        int sliceLength = (endIndex - startIndex) + 1;

        if (sliceLength > 2) {
            int mid = startIndex + (sliceLength / 2) - 1;

            // Split task into two subtasks
            FindMax lowerHalf = new FindMax(numbers, startIndex, mid);
            FindMax upperHalf = new FindMax(numbers, mid + 1, endIndex);

            // Fork the first subtask
            lowerHalf.fork(); /*enables concurrent*/

            // Compute second subtask directly to improve efficiency
            int upperResult = upperHalf.compute();

            // Join first subtask
            int lowerResult = lowerHalf.join(); /*wait for the lowerhalf results task to complete & return its result */

            return Math.max(lowerResult, upperResult);
        } else {
            // Direct comparison when 2 or fewer elements
            if (startIndex == endIndex) {
                return numbers[startIndex];
            }
            return Math.max(numbers[startIndex], numbers[endIndex]);
        }
    }

    public static void main(String[] args) {
        int[] numbers = new int[100];
        Random random = new Random(System.currentTimeMillis());

        // Fill the array with random numbers between 0 and 99
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }

        // Display array contents (optional)
        System.out.print("Array: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();

        // Use ForkJoinPool with available processors
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Integer min = pool.invoke(new FindMax(numbers, 0, numbers.length - 1));

        System.out.println("Maximum value in the array: " + min);
    }
}

