import java.util.Map;
import java.util.TreeMap;

/**
 * Prefix-Trie. Supports linear time find() and insert().
 * Should support determining whether a word is a full word in the
 * Trie or a prefix.
 *
 * @author
 */
public class Trie {

    private Node root = new Node();

    public Node getRoot() {
        return root;
    }

    public boolean find(String s, boolean isFullWord) {
        if (s.length() == 0 || s.isEmpty()) {
            throw new IllegalArgumentException("A null or empty string is added.");
        }
        return root.find(s, isFullWord, 0);
    }

    public void insert(String s) {
        if (s.length() == 0 || s.isEmpty()) {
            throw new IllegalArgumentException("A null or empty string is added.");
        }
        root.put(s, 0);
    }

    public class Node {
        boolean exists;
        Map<Character, Node> links;

        public Node() {
            links = new TreeMap<Character, Node>();
        }

        public void put(String s, int digit) {
            if (s.length() == digit) {
                exists = true;
                return;
            }
            char curr = s.charAt(digit);
            if (links.containsKey(curr)) {
                links.get(curr).put(s, digit + 1);
            } else {
                links.put(curr, new Node());
                links.get(curr).put(s, digit + 1);
            }
        }

        public boolean find(String s, boolean isFullWord, int digit) {
            if (s.length() == digit) {
                return exists || !isFullWord;
            }
            char curr = s.charAt(digit);
            if (links.containsKey(curr)) {
                return links.get(curr).find(s, isFullWord, digit + 1);
            }
            return false;
        }
    }

//    public static void main(String[] args) {
//        Trie t = new Trie();
//        t.insert("hello");
//        t.insert("hey");
//        t.insert("goodbye");
//        System.out.println(t.find("hell", false));
//        System.out.println(t.find("hello", true));
//        System.out.println(t.find("good", false));
//        System.out.println(t.find("bye", false));
//        System.out.println(t.find("heyy", false));
//        System.out.println(t.find("hell", true));
//    }
}
