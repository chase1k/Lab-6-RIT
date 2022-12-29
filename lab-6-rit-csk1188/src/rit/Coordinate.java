package rit;

/**
 * Represents a 2-D coordinate.  This class is immutable after construction.
 *
 * @author RIT CS
 */
public class Coordinate {
    /** the row */
    private int row;

    /** the column */
    private int col;

    /**
     * Create a new coordinate.
     *
     * @param row the row
     * @param col the column
     */
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Get the row.
     *
     * @return the row
     */
    public int getRow() { return this.row; }

    /**
     * Get the column.
     *
     * @return the column
     */
    public int getCol() { return this.col; }

    /**
     * Returns a string in the format "(row, col)".
     *
     * @return string representation of coordinate
     */
    @Override
    public String toString() {
        return "(" + this.row + ", " + this.col + ")";
    }
}
