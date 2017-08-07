import java.util.HashSet;
import java.util.Scanner;

/**
 * AlphabetSort takes input from stdin and prints to stdout.
 * The first line of input is the alphabet permutation.
 * The the remaining lines are the words to be sorted.
 * <p>
 * The output should be the sorted words, each on its own line,
 * printed to std out.
 */
public class AlphabetSort {

    private static void trieSort(Trie.Node alphaNode, String rule) {
        for (char c : rule.toCharArray()) {
            if (alphaNode.links.containsKey(c)) {
                trieSortHelper(alphaNode.links.get(c), rule, "" + c);
            }
        }
    }

    private static void trieSortHelper(Trie.Node alphaNode, String rule, String current) {
        if (alphaNode.exists) {
            System.out.println(current);
        }
        for (char c : rule.toCharArray()) {
            if (alphaNode.links.containsKey(c)) {
                trieSortHelper(alphaNode.links.get(c), rule, current + c);
            }
        }
    }


    /**
     * Reads input from standard input and prints out the input words in
     * alphabetical order.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        if (!scan.hasNextLine()) {
            throw new IllegalArgumentException("No alphabet is given.");
        }
        String rule = scan.nextLine();
        HashSet checkRepeat = new HashSet();
        for (char c : rule.toCharArray()) {
            if (checkRepeat.contains(c)) {
                throw new IllegalArgumentException("A letter appears multiple times.");
            }
            checkRepeat.add(c);
        }
        if (!scan.hasNextLine()) {
            throw new IllegalArgumentException("No words is given.");
        }
        Trie alphaTire = new Trie();
        while (scan.hasNextLine()) {
            alphaTire.insert(scan.nextLine());
        }
        trieSort(alphaTire.getRoot(), rule);
    }
}
