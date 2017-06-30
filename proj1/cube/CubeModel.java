package cube;

import java.util.Observable;

import static java.lang.Math.abs;
import static java.lang.System.arraycopy;

/**
 * Models an instance of the Cube puzzle: a cube with color on some sides
 * sitting on a cell of a square grid, some of whose cells are colored.
 * Any object may register to observe this model, using the (inherited)
 * addObserver method.  The model notifies observers whenever it is modified.
 *
 * @author P. N. Hilfinger
 */
public class CubeModel extends Observable {

    private int side = 4;
    private int currCol = 0;
    private int currRow = 0;
    private int moves = 0;
    private boolean[][] painted = new boolean[side][side];
    private boolean[] pface = new boolean[6];


    /**
     * A blank cube puzzle of size 4.
     */
    public CubeModel() {
        this.side = 4;
    }

    /**
     * A copy of CUBE.
     */
    public CubeModel(CubeModel cube) {
        initialize(cube);
    }

    /**
     * Initialize puzzle of size SIDExSIDE with the cube initially at
     * ROW0 and COL0, with square r, c painted iff PAINTED[r][c], and
     * with face k painted iff FACEPAINTED[k] (see isPaintedFace).
     * Assumes that
     * * SIDE > 2.
     * * PAINTED is SIDExSIDE.
     * * 0 <= ROW0, COL0 < SIDE.
     * * FACEPAINTED has length 6.
     */
    public void initialize(int side, int row0, int col0, boolean[][] painted,
                           boolean[] facePainted) {
        this.side = side;
        this.currRow = row0;
        this.currCol = col0;
        this.painted = painted;
        this.pface = facePainted;
        setChanged();
        notifyObservers();
    }

    /**
     * Initialize puzzle of size SIDExSIDE with the cube initially at
     * ROW0 and COL0, with square r, c painted iff PAINTED[r][c].
     * The cube is initially blank.
     * Assumes that
     * * SIDE > 2.
     * * PAINTED is SIDExSIDE.
     * * 0 <= ROW0, COL0 < SIDE.
     */
    public void initialize(int side, int row0, int col0, boolean[][] painted) {
        initialize(side, row0, col0, painted, new boolean[6]);
    }

    /**
     * Initialize puzzle to be a copy of CUBE.
     */
    public void initialize(CubeModel cube) {
        this.side = cube.side;
        this.pface = cube.pface;
        this.currCol = cube.currCol;
        this.currRow = cube.currRow;
        this.moves = cube.moves;
        this.painted = cube.painted;
        setChanged();
        notifyObservers();
    }

    /**
     * Move the cube to (ROW, COL), if that position is on the board and
     * vertically or horizontally adjacent to the current cube position.
     * Transfers colors as specified by the rules.
     * Throws IllegalArgumentException if preconditions are not met.
     */
    public void move(int row, int col) {
        // FIXME
        if (row >= side || row < 0 || col >= side || col < side)
            throw new IllegalArgumentException("Wrong range");
        if (abs(currRow - row) + abs(currCol - col) != 1)
            throw new IllegalArgumentException("Wrong range");
        this.currCol = col;
        this.currRow = row;
        moves++;
        setChanged();
        notifyObservers();
    }

    /**
     * Return the number of squares on a side.
     */
    public int side() {
        return this.side; //
    }

    /**
     * Return true iff square ROW, COL is painted.
     * Requires 0 <= ROW, COL < board size.
     */
    public boolean isPaintedSquare(int row, int col) {
        if (row >= side || row < 0 || col >= side || col < 0) {
            throw new IllegalArgumentException("Wrong range");
        }
        if (painted[row][col]) {
            return true;
        }
        return false;
    }

    /**
     * Return current row of cube.
     */
    public int cubeRow() {
        return this.currRow;
    }

    /**
     * Return current column of cube.
     */
    public int cubeCol() {
        return this.currCol;
    }

    /**
     * Return the number of moves made on current puzzle.
     */
    public int moves() {
        return this.moves;
    }

    /**
     * Return true iff face #FACE, 0 <= FACE < 6, of the cube is painted.
     * Faces are numbered as follows:
     * 0: Vertical in the direction of row 0 (nearest row to player).
     * 1: Vertical in the direction of last row.
     * 2: Vertical in the direction of column 0 (left column).
     * 3: Vertical in the direction of last column.
     * 4: Bottom face.
     * 5: Top face.
     */
    public boolean isPaintedFace(int face) {
        if (face < 0 || face > 5) {
            System.out.println("Wrong range of face");
            return false;
        }
        if (pface[face]) {
            return true;
        }
        return false;
    }

    /**
     * Return true iff all faces are painted.
     */
    public boolean allFacesPainted() {
        for (int i = 0; i < 6; i++) {
            if (!pface[i]) {
                return false;
            }
        }
        return true;
    }


    // ADDITIONAL FIELDS AND PRIVATE METHODS HERE, AS NEEDED.

}
