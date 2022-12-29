package rit;

/**
 * Represents a Quadtree node in the tree for an image compressed using the
 * Rich Image Tool file format.
 *
 * A node contains a value which is either a grayscale color (0-255) for a
 * region, or rit.QTree.QUAD_SPLIT meaning this node cannot hold a single color
 * and thus has split itself into 4 sub-regions.
 *
 * @author RIT CS
 */
public class QTNode {
    /** The node's value */
    private int val;

    /** quadrant II */
    private QTNode ul;

    /** quadrant I */
    private QTNode ur;

    /** quadrant III */
    private QTNode ll;

    /** quadrant IV */
    private QTNode lr;

    /**
     * Construct a leaf node with no children.
     * @param val node value
     */
    public QTNode(int val) {
        this(val, null, null, null, null);
    }

    /**
     * Construct a quad tree node.
     *
     * @param val the node's value
     * @param ul the upper left sub-node
     * @param ur the upper right sub-node
     * @param ll the lower left sub-node
     * @param lr the lower right sub-node
     */
    public QTNode(int val, QTNode ul, QTNode ur, QTNode ll, QTNode lr) {
        this.val = val;
        this.ul = ul;
        this.ur = ur;
        this.ll = ll;
        this.lr = lr;
    }

    /**
     * Get the node's value.
     *
     * @return node's value
     */
    public int getVal() { return this.val; }

    /**
     * Get the upper left sub-node.
     *
     * @return upper left sub-node
     */
    public QTNode getUpperLeft() { return this.ul; }

    /**
     * Get the upper right sub-node.
     *
     * @return upper right sub-node
     */
    public QTNode getUpperRight() { return this.ur; }

    /**
     * Get the lower left sub-node.
     *
     * @return lower left sub-node
     */
    public QTNode getLowerLeft() { return this.ll; }

    /**
     * Get the lower right sub-node
     *
     * @return lower right sub-node
     */
    public QTNode getLowerRight() { return this.lr; }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }
}
