import java.util.Observable;

import static java.lang.System.arraycopy;

/** Models an instance of the Cube puzzle: a cube with color on some sides
 *  sitting on a cell of a square grid, some of whose cells are colored.
 *  Any object may register to observe this model, using the (inherited)
 *  addObserver method.  The model notifies observers whenever it is modified.
 *  @author P. N. Hilfinger
 */
public class CubeModel extends Observable {

    private int side;
    private int cudeCol;
    private int cudeRow;
    private int moves;
    private boolean [][]painted;
    private boolean []pface;

    /** A blank cube puzzle of size 4. */
    public CubeModel() {
        // FIXME
    }

    /** A copy of CUBE. */
    public CubeModel(CubeModel cube) {
        initialize(cube);
    }

    /** Initialize puzzle of size SIDExSIDE with the cube initially at
     *  ROW0 and COL0, with square r, c painted iff PAINTED[r][c], and
     *  with face k painted iff FACEPAINTED[k] (see isPaintedFace).
     *  Assumes that
     *    * SIDE > 2.
     *    * PAINTED is SIDExSIDE.
     *    * 0 <= ROW0, COL0 < SIDE.
     *    * FACEPAINTED has length 6.
     */
    public void initialize(int side, int row0, int col0, boolean[][] painted,
                    boolean[] facePainted) {
        // FIXME
        setChanged();
        notifyObservers();
    }

    /** Initialize puzzle of size SIDExSIDE with the cube initially at
     *  ROW0 and COL0, with square r, c painted iff PAINTED[r][c].
     *  The cube is initially blank.
     *  Assumes that
     *    * SIDE > 2.
     *    * PAINTED is SIDExSIDE.
     *    * 0 <= ROW0, COL0 < SIDE.
     */
    public void initialize(int side, int row0, int col0, boolean[][] painted) {
        initialize(side, row0, col0, painted, new boolean[6]);
    }

    /** Initialize puzzle to be a copy of CUBE. */
    public void initialize(CubeModel cube) {
        // FIXME
        setChanged();
        notifyObservers();
    }

    /** Move the cube to (ROW, COL), if that position is on the board and
     *  vertically or horizontally adjacent to the current cube position.
     *  Transfers colors as specified by the rules.
     *  Throws IllegalArgumentException if preconditions are not met.
     */
    public void move(int row, int col) {
        // FIXME
        setChanged();
        notifyObservers();
    }

    /** Return the number of squares on a side. */
    public int side() {
        return this.side; //
    }

    /** Return true iff square ROW, COL is painted.
     *  Requires 0 <= ROW, COL < board size. */
    public boolean isPaintedSquare(int row, int col) {
        if (row>side ||row<0){
            System.out.println("Wrong range of row");
            return false;
        }
        if (col>side||col<0){
            System.out.println("Wrong range of column");
            return false;
        }
        if (painted[row][col])
                return true;
        return false;
    }

    /** Return current row of cube. */
    public int cubeRow() {
        return this.cudeRow;
    }

    /** Return current column of cube. */
    public int cubeCol() {
        return this.cudeCol;
    }

    /** Return the number of moves made on current puzzle. */
    public int moves() {
        return this.moves;
    }

    /** Return true iff face #FACE, 0 <= FACE < 6, of the cube is painted.
     *  Faces are numbered as follows:
     *    0: Vertical in the direction of row 0 (nearest row to player).
     *    1: Vertical in the direction of last row.
     *    2: Vertical in the direction of column 0 (left column).
     *    3: Vertical in the direction of last column.
     *    4: Bottom face.
     *    5: Top face.
     */
    public boolean isPaintedFace(int face) {
        if (face<0||face>5){
            System.out.println("Wrong range of face");
            return false;
        }
        if (pface[face])
            return true;
        return false;
    }

    /** Return true iff all faces are painted. */
    public boolean allFacesPainted() {
        for (int i=0;i<6;i++){
            if (!pface[i])
                return false;
        }
        return true;
    }
}
