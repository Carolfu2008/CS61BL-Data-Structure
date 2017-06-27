/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author Maurice Lee and Wan Fung Chui
 */

public class IntList {

    /** The integer stored by this node. */
    private int item;
    /** The next node in this IntList. */
    private IntList next;

    /** Constructs an IntList storing ITEM and next node NEXT. */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /** Constructs an IntList storing ITEM and no next node. */
    public IntList(int item) {
        this(item, null);
    }

    /** Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints (1 2 3) */
    public static IntList list(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]);
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    /** Returns the integer stored by this IntList. */
    public int item() {
        return item;
    }

    /** Returns the next node stored by this IntList. */
    public IntList next() {
        return next;
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        // YOUR CODE HERE
        IntList pointer= this;
        int cnt=0;
        while(pointer.next()!=null) {
            pointer = pointer.next();
            cnt++;
        }
        if (position==0)
            return this.item();
        else if (position>cnt||position<0){
            throw new IllegalArgumentException("Wrong range");
        }
        else{
            pointer= this;
            for (int i=0;i<position;i++)
                pointer=pointer.next();
            return pointer.item();
        }
    }

    /**
     * Returns the size of the list.
     *
     * @return The size of the list.
     */
    public int size() {
        // YOUR CODE HERE
        IntList pointer=this;
        int cnt=0;
        while(pointer.next()!=null) {
            cnt++;
            pointer=pointer.next();
        }
        return cnt+1;
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "( 1 2 3 )".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        // YOUR CODE HERE
        IntList pointer=this;
        String ret="( ";
        while (pointer!=null){
            ret=ret+pointer.item()+" ";
            pointer=pointer.next();
        }
        ret=ret+")";
        return ret;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * @param //obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object l1) {
        // YOUR CODE HERE
        if (l1 instanceof IntList) {
            IntList pointer=this;
            IntList l2=(IntList)l1;
            while (pointer != null && l2 != null) {
                if (pointer.item() != l2.item())
                    return false;
                pointer = pointer.next();
                l2 = l2.next();
            }
            if (pointer == null && l2 == null)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    /**
     * Adds the given item at the end of the list.
     *
     * @param item, the int to be added.
     */
    public void add(int item) {
        // YOUR CODE HERE
        IntList pointer = this;
        while(pointer.next()!=null){
            pointer=pointer.next();
        }
        pointer.next=new IntList(item);
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        // YOUR CODE HERE
        IntList pointer=this;
        int tmpv=this.item();
        while (pointer!=null){
            if(pointer.item()<tmpv){
                tmpv=pointer.item();
            }
            pointer=pointer.next();
        }
        return tmpv;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        // YOUR CODE HERE
        IntList pointer=this;
        int cnt=0;
        while (pointer!=null){
            cnt+=pointer.item()*pointer.item();
            pointer=pointer.next();
        }
        return cnt;
    }

    /**
     * Returns a new IntList consisting of L1 followed by L2,
     * non-destructively.
     *
     * @param l1 list to be on the front of the new list.
     * @param l2 list to be on the back of the new list.
     * @return new list with L1 followed by L2.
     */
    public static IntList append(IntList l1, IntList l2) {
        // YOUR CODE HERE
        /*if (l1==null)
            return l2;
        IntList lf=new IntList(l1.item());
        IntList p1 = l1.next();
        IntList pf = lf;
        for(int i=1;i<l1.size();i++){
            pf.next=new IntList(p1.item());
            p1=p1.next();
            pf=pf.next();
        }
        pf.next=l2;
        return lf;*/
        if (l1==null)
            return l2;
        return new IntList(l1.item(),append(l1.next(),l2));
    }

}