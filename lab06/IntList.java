/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 * Encapsulated version.
 */
public class IntList {

    /**
     * The head of the list is the first node in the list.
     * If the list is empty, head is null
     */
    private IntListNode head;
    private int size;

    /**
     * IntListNode is a nested class. It can be instantiated
     * when associated with an instance of IntList.
     */

    public IntListNode getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }

    public IntList() {
    }

    public IntList(IntListNode head){
        this.head=head;
        this.size=1;
    }

    public IntList(int[] initial) {
        for (int i = initial.length - 1; i >= 0; i--) {
            head = new IntListNode(initial[i], head);
        }
        size = initial.length;
    }

    /**
     * Get the value at position pos. If the position does not exist, throw an
     * IndexOutOfBoundsException.
     * @param position to get from
     * @return the int at the position in the list.
     */
    public int get(int position) {
        if (position >= size) throw new IndexOutOfBoundsException("Position larger than size of list.");
        IntListNode curr = head;
        while (position > 0) {
            curr = curr.next;
            position--;
        }
        return curr.item;
    }

    /* Fill in below! */

    /**
     * Insert a new node into the IntList.
     * @param x value to insert
     * @param position position to insert into. If position exceeds the size of the list, insert into
     *            the end of the list.
     */
    public void insert(int x, int position) {
        // Fill me in!
        IntListNode pointer=head;
        if(position>=getSize()){
            position=size;
        }
        if (position==0){
            IntListNode ins = new IntListNode(x, head);
            this.head=ins;
        }
        else{
            for (int i=0;i<position-1;i++){
                pointer=pointer.next;
            }
            if (size!=0) {
                IntListNode ins = new IntListNode(x, pointer.next);
                pointer.next = ins;
            }
            else{
                this.head=new IntListNode(x,null);
            }
        }
        size++;
    }

    /**
     * Merge two sorted IntLists a and b into one sorted IntList containing all of their elements.
     * @return a new IntList without modifying either parameter
     */
    public static IntList merge(IntList a, IntList b) {
        // Fill me in!

        IntList l=new IntList(new IntListNode(a.getHead().item));
        IntListNode p1=a.getHead().next;
        IntListNode p2=b.getHead();
        IntListNode pf=l.getHead();
        int cnt=1;
        while(p1!=null&&p2!=null){
            if (cnt%2!=0) {
                l.insert(p2.item, l.getSize());
                p2=p2.next;
            }
            else{
                l.insert(p1.item,l.getSize());
                p1=p1.next;
            }
            cnt++;
            pf=pf.next;
        }
        if (p1!=null) {
            pf.next = p1;
            while (p1!=null){
                p1=p1.next;
                l.size++;
            }
        }
        else {
            pf.next = p2;
            while (p2!=null){
                p2=p2.next;
                l.size++;
            }
        }
        return l;
    }


    /**
     * Reverse the current list recursively, using a helper method.
     */
    public void reverse() {
        // Fill me in!
        this.head=reverseHelper(this.head,null);
    }

    private static IntListNode reverseHelper(IntListNode l, IntListNode soFar) {
        if (l == null) {
            return soFar;
        } else {
            IntListNode temp = l.next;
            l.next = soFar;
            return reverseHelper( temp , l );
        }
    }

    /* Optional! */

    /**
     * Remove the node at position from this list.
     * @param position int representing the index of the node to remove. If greater than the size
     *                 of this list, throw an IndexOutOfBoundsException.
     */
    public void remove(int position) {
        if (position >= size) throw new IndexOutOfBoundsException();
        // fill me in
        if(position==0){
            IntListNode h1=head;
            head=head.next;
            h1.next=null;
        }else if (position==size-1){
            IntListNode pointer = head;
            while (pointer.next!=null){
                pointer=pointer.next;
            }
            pointer.next=null;
        }else{
            IntListNode pointer = head;
            for(int i=0;i<position-1;i++)
                pointer=pointer.next;
            IntListNode p2=pointer.next;
            pointer.next=pointer.next.next;
            p2.next=null;
        }
    }



    public static class IntListNode {
        int item;
        IntListNode next;

        public IntListNode() {
            this.item = item;
            this.next = null;
        }

        public IntListNode(int item) {
            this.item = item;
            this.next = null;
        }

        public IntListNode(int item, IntListNode next) {
            this.item = item;
            this.next = next;
        }

        @Override
        public String toString() {
            return "IntListNode{" +
                    "item=" + item +
                    ", next=" + next +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            IntListNode that = (IntListNode) o;

            if (item != that.item)
                return false;
            return next != null ? next.equals(that.next) : that.next == null;
        }

        @Override
        public int hashCode() {
            int result = item;
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }
    }
}
