import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads lines of numbers from input.txt, sorts each line
 * with bubble sort, and writes the results to output.txt.
 *
 * @author MFROB
 * @version 1.0
 * @since 2026-11-12
 */
final class BubbleSort {

    /**
     * Prevent instantiation.
     *
     * @throws IllegalStateException if called
     */
    private BubbleSort() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Sorts a list of integers using bubble sort.
     *
     * @param listOfNum the list to sort
     * @return the sorted list
     */
    public static List<Integer> bubbleSort(final List<Integer> listOfNum) {
        // "end" is the index where the unsorted part stops.
        // Everything at or after "end" is already in its final place.
        int end = listOfNum.size();

        // Tracks whether any swap happened during a pass.
        // Starts true so the loop runs at least once.
        boolean swapped = true;

        // Keep making passes until a full pass has no swaps.
        while (swapped) {
            // Assume sorted until a swap proves otherwise.
            swapped = false;

            // Compare each pair of neighbours in the unsorted part.
            for (int i = 0; i < end - 1; i++) {
                // If the left number is bigger, the pair is out of order.
                if (listOfNum.get(i) > listOfNum.get(i + 1)) {
                    // Swap the pair using a temporary variable.
                    final int temp = listOfNum.get(i);
                    listOfNum.set(i, listOfNum.get(i + 1));
                    listOfNum.set(i + 1, temp);

                    // Remember that we changed something this pass.
                    swapped = true;
                }
            }

            // The largest number of this pass has "bubbled" to the end,
            // so the next pass can skip it.
            end--;
        }
        return listOfNum;
    }

    /**
     * The starting method.
     *
     * @param args unused
     * @throws IOException if a file cannot be read or written
     */
    public static void main(final String[] args) throws IOException {
        // Read every line of input.txt into a list of strings.
        final List<String> lines = Files.readAllLines(
                Paths.get("input.txt"));

        // Holds one sorted line of text for each input line.
        final List<String> results = new ArrayList<>();

        // Process each line of the input file separately.
        for (final String line : lines) {
            // Skip blank lines (assume otherwise valid input).
            if (line.trim().isEmpty()) {
                continue;
            }

            // Split the line on spaces and convert each piece to an int.
            final List<Integer> numbers = new ArrayList<>();
            for (final String token : line.trim().split("\\s+")) {
                numbers.add(Integer.parseInt(token));
            }

            // Sort the numbers on this line.
            final List<Integer> sorted = bubbleSort(numbers);

            // Join the sorted numbers back into one space-separated line.
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < sorted.size(); i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(sorted.get(i));
            }
            results.add(sb.toString());
        }

        // Write all sorted lines to output.txt.
        Files.write(Paths.get("output.txt"), results);
        System.out.println("Done. See output.txt");
    }
}