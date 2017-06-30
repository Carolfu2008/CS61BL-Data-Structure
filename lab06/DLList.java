public class DLList {
    DLNode sentinel;
    int size;

    public class DLNode {
        Object item;
        DLNode prev, next;

        public DLNode(Object item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }

        public DLNode(Object item, DLNode prev, DLNode next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

    }

    /**
     * Construct a new DLList with a sentinel that points to itself.
     */
    public DLList() {
        sentinel = new DLNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /**
     *
     * Insert into the end of this list
     * @param o Object to insert
     */
    public void insertBack(Object o) {
        DLNode n = new DLNode(o, sentinel.prev, sentinel);
        n.next.prev = n;
        n.prev.next = n;
        size++;
    }

    public void insertAt(Object o,DLNode x) {
        DLNode n = new DLNode(o, x, x.next);
        n.next.prev = n;
        n.prev.next = n;
        size++;
    }


    /**
     * Get the value at position pos. If the position does not exist, return null (the item of
     * the sentinel).
     * @param position to get from
     * @return the Object at the position in the list.
     */
    public Object get(int position) {
        DLNode curr = sentinel.next;
        while (position > 0 && curr != sentinel) {
            curr = curr.next;
            position--;
        }
        return curr.item;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("DLList(");
        DLNode curr = sentinel.next;
        while (curr != sentinel) {
            s.append(curr.item.toString());
            if (curr.next != sentinel) s.append(", ");
            curr = curr.next;
        }
        s.append(')');
        return s.toString();
    }

    /* Fill these in! */

    /**
     * Insert a new node into the DLList.
     * @param o Object to insert
     * @param position position to insert into. If position exceeds the size of the list, insert into
     *            the end of the list.
     */
    public void insert(Object o, int position) {
        // fill me in
        if (position==0)
            insertFront(o);
        else if (position>=size)
            insertBack(o);
        else {
            DLNode p=sentinel;
            while (position!=0){
                p=p.next;
                position--;
            }
            insertAt(o,p);
        }
    }

    /**
     * Insert into the front of this list. You should can do this with a single call to insert().
     * @param o Object to insert
     */
    public void insertFront(Object o) {
        // fill me in
        DLNode n = new DLNode(o, sentinel, sentinel.next);
        n.next.prev = n;
        n.prev.next = n;
        size++;
    }

    /**
     * Remove all copies of Object o in this list
     * @param o Object to remove
     */
    public void remove(Object o) {
        // fill me in
        DLNode pl=sentinel;
        for(int i=0;i<=size;i++){
            if(pl.next.item==o){
                remove(pl.next);

            }
            else
                pl=pl.next;
        }

    }

    /**
     * Remove a DLNode from this list. Does not error-check to make sure that the node actually
     * belongs to this list.
     * @param n DLNode to remove
     */
    public void remove(DLNode n) {
        // fill me in
        n.prev.next=n.next;
        n.next.prev=n.prev;
        n.prev=null;
        n.next=null;
        size--;
    }


    /**
     * Duplicate each node in this linked list destructively.
     */
    public void doubleInPlace() {
        // fill me in
        if (size==0)
            return;
        DLNode pointer=sentinel.next;
        int cnt =size;
        while (cnt!=0){
            insertAt(pointer.item,pointer);
            pointer=pointer.next.next;
            cnt--;
        }
    }

    /**
     * Reverse the order of this list destructively.
     */
    public void reverse() {
        // fill me in
        DLNode po1=sentinel.next;
        for (int i=0;i<size-1;i++){
            DLNode tmp= po1.next;
            remove(po1.next);
            insertFront(tmp.item);
        }

    }

    private static DLNode rev(DLNode ori, DLNode soFar) {
        if (ori == null) {
            return soFar;
        } else {
            DLNode temp = ori.next;
            ori.next = soFar;
            return rev( temp , ori );
        }
    }

    public static void main(String[] args) {
        // you can add some quick tests here if you would like

    }
}
