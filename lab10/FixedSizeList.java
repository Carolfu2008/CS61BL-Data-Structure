import java.util.Iterator;

public class FixedSizeList implements SimpleList, Iterable {

    /**
     * List of elements.
     */
    protected int[] values;
    /**
     * Number of array cells used by the list.
     */
    int count;

    private class FixedSizeListIterator implements Iterator<Integer> {

        int nextIndexToReturn;    // index of next item to return by iterator

        public Integer next() {
            int valToReturn = values[nextIndexToReturn];
            nextIndexToReturn++;
            return valToReturn;
        }

        public boolean hasNext() {
            return nextIndexToReturn < count;
        }
    }

    public Iterator<Integer> iterator() {
        return new FixedSizedListIterator();
    }

    /**
     * Initializes a FixedSizeList with specified capacity. The capacity is the
     * the actual size of the array (i.e. the max number of items it can hold).
     */
    public FixedSizeList(int capacity) {
        this.values = new int[capacity];
        this.count = 0;
    }

    /**
     * Returns the number of items in the list.
     */
    public int size() {
        return count;
    }

    /**
     * Returns true if the list is empty, else false.
     */
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * Adds the int k to the list by placing it in the first unused spot in
     * values.
     */
    public void add(int k) {
        if (count == values.length) {
            throw new ListException("Full!");
        } else {
            values[count] = k;
            count++;
        }
    }

    /**
     * Removes k from the list if it is present. If k appears multiple times,
     * it only removes the first occurence of k.
     */
    public void remove(int k) {
        int cnt = -1;
        for (int i = 0; i < count; i++) {
            if (values[i] == k) {
                cnt = i;
                break;
            }
        }
        if (cnt != -1) {
            removeIndex(cnt);
        } else {
            return;
        }
    }

    /**
     * Returns if the collection contains k.
     */
    public boolean contains(int k) {
        for (int i = 0; i < count; i++) {
            if (values[i] == k) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the integer stored at the i-th index in the list.
     */
    public int get(int i) {
        if (i >= count) {
            throw new ListException("Illegal input!");
        } else {
            return values[i];
        }
    }

    /**
     * Inserts k into the list at position i by shifting each element at index
     * i and onwards one entry to the right.
     * Precondition: i is between 0 and count, inclusive.
     */
    public void add(int i, int k) {
        for (int j = count; j >= i + 1; j--) {
            values[j] = values[j - 1];
        }
        values[i] = k;
        count++;
    }

    /**
     * Removes the entry at position i by shifting each element after position
     * i one entry to the left.
     */
    public void removeIndex(int i) {
        for (int j = i; j < count; j++) {
            values[j] = values[j + 1];
        }
        count--;
    }

    public static void main(String [] args) {
        FixedSizeList list = new FixedSizeList(10);
        list.add(5);
        list.add(3);
        // list now contains the integers 5, 3;
        // thus values[0] is 5, values[1] is 3,
        // and count is 2.

    }
}
