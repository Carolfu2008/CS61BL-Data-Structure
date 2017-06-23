/**
 * Created by lifesaver on 22/06/2017.
 */
public class TriangleDrawer2 {

    public static void main(String []args){
        int SIZE=10;
        for(int col=1;col<=SIZE;col++){
            for(int row=1;row<=col;row++)
                System.out.print("*");
            System.out.println();
        }

    }
}
