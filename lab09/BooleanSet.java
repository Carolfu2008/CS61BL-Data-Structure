/** Represent a set of nonnegative ints from 0 to maxElement - 1 for some
 *	initially specified maxElement.
 */
public class BooleanSet implements SimpleSet {

	/** contains[k] is true if k is in the set, false if it isn't. */
	private boolean[] contains;

	/** Initializes a set of ints from 0 to maxElement - 1. */
	public BooleanSet (int maxElement) {
	    // YOUR CODE HERE
	}

	/** Adds k to the set.
	 *	precondition: 0 <= k < maxElement.
	 *	postcondition: k is in this set.
	 */
	public void add(int k) {
		// YOUR CODE HERE
	}

	/** Removes k from the set.
	 *	precondition: 0 <= k < maxElement.
	 *	postcondition: k is not in this set.
	 */
	public void remove(int k) {
		// YOUR CODE HERE
	}

	/** Return true if k is in this set, false otherwise.
	 *	precondition: 0 <= k < maxElement
	 */
	public boolean contains (int k) {
		// YOUR CODE HERE
	}

	/** Return true if this set is empty, false otherwise. */
	public boolean isEmpty() {
		// YOUR CODE HERE
	}

	/** Returns the number of items in the set. */
	public int size() {
		// YOUR CODE HERE
	}
}
