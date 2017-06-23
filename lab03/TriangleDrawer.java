/**
 * Created by lifesaver on 22/06/2017.
 */
public class TriangleDrawer {

    public static void main(String []args){
        int SIZE=10;
        int col=0;

        while(col<=SIZE){
            int row=0;
            while(row<col) {
                System.out.print("*");
                row++;
            }
            System.out.println();
            col++;
        }

    }
}
