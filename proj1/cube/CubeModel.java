package cube;

import java.util.Observable;

import static java.lang.Math.abs;

//import static java.lang.System.arraycopy;

/**
 * Models an instance of the Cube puzzle: a cube with color on some sides
 * sitting on a cell of a square grid, some of whose cells are colored.
 * Any object may register to observe this model, using the (inherited)
 * addObserver method.  The model notifies observers whenever it is modified.
 *
 * @author P. N. Hilfinger
 */
public class CubeModel extends Observable {

    private int locside = 4;
    private int currCol = 0;
    private int currRow = 0;
    private int moves = 0;
    private boolean[][] pnted = new boolean[locside][locside];
    private boolean[] pface = new boolean[6];


    /**
     * A blank cube puzzle of size 4.
     */
    public CubeModel() {
        this.locside = 4;
    }

    /**
     * A copy of CUBE.
     */
    public CubeModel(CubeModel cube) {
        initialize(cube);
    }

    public CubeModel(int side, boolean[][] painted, boolean[] facePainted) {
        this.locside = side;
        this.currRow = 0;
        this.currCol = 0;
        this.pnted = painted;
        this.pface = facePainted;
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
        this.locside = side;
        this.currRow = row0;
        this.currCol = col0;
        this.pnted = painted;
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
        this.locside = cube.locside;
        this.currCol = cube.currCol;
        this.currRow = cube.currRow;
        this.moves = cube.moves;
        boolean[][] p = new boolean[cube.side()][cube.side()];
        for (int i = 0; i < cube.side(); i++) {
            System.arraycopy(cube.pnted[i], 0, p[i], 0, cube.pnted[i].length);
        }
        this.pnted = p;
        boolean[] f = new boolean[6];
        System.arraycopy(cube.pface, 0, f, 0, cube.pface.length);
        this.pface = f;
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
        if (row >= locside || row < 0 || col >= locside || col < 0) {
            throw new IllegalArgumentException("Wrong range");
        }
        if (abs(currRow - row) + abs(currCol - col) != 1) {
            throw new IllegalArgumentException("Wrong range");
        }
        if (currRow == row && currCol - col == -1) {
            boolean tmp = pface[2];
            pface[2] = pface[4];
            pface[4] = pface[3];
            pface[3] = pface[5];
            pface[5] = tmp;
        } else if (currRow == row && col - currCol == -1) {
            boolean tmp = pface[2];
            pface[2] = pface[5];
            pface[5] = pface[3];
            pface[3] = pface[4];
            pface[4] = tmp;
        } else if (currRow - row == 1 && col == currCol) {
            boolean tmp = pface[5];
            pface[5] = pface[1];
            pface[1] = pface[4];
            pface[4] = pface[0];
            pface[0] = tmp;
        } else if (row - currRow == 1 && col == currCol) {
            boolean tmp = pface[5];
            pface[5] = pface[0];
            pface[0] = pface[4];
            pface[4] = pface[1];
            pface[1] = tmp;
        }
        this.currCol = col;
        this.currRow = row;
        if (pface[4] != pnted[currRow][currCol]) {
            boolean tmp = pface[4];
            pface[4] = pnted[currRow][currCol];
            pnted[currRow][currCol] = tmp;
        }
        moves++;
        setChanged();
        notifyObservers();
    }

    /**
     * Return the number of squares on a side.
     */
    public int side() {
        return this.locside; //
    }

    /**
     * Return true iff square ROW, COL is painted.
     * Requires 0 <= ROW, COL < board size.
     */
    public boolean isPaintedSquare(int row, int col) {
        if (row >= locside || row < 0 || col >= locside || col < 0) {
            throw new IllegalArgumentException("Wrong range");
        }
        if (pnted[row][col]) {
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
