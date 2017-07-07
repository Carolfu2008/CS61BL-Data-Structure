/**
 * Created by lifesaver on 06/07/2017.
 */
public class ResizableList extends FixedSizeList {

    public ResizableList() {
        super(1);
    }

    @Override
    public void add(int k) {
        if (count < values.length) {
            super.add(k);
        } else {
            int[] newlist = new int[values.length * 2];
            for (int p = 0; p < count; p++) {
                newlist[p] = values[p];
            }
            values = newlist;
            add(k);
        }
    }

    @Override
    public void add(int i, int k) {
        if (count < values.length) {
            super.add(i, k);
        } else {
            int[] newlist = new int[values.length * 2];
            for (int p = 0; p < count; p++) {
                newlist[p] = values[p];
            }
            values = newlist;
            add(i, k);
        }
    }

}


