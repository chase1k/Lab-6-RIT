package rit;

/**
 * A class used to communicate errors with operations involving the rit.QTree
 * and the files it uses for compressing and uncompressing.
 *
 * @author RIT CS
 */
public class QTException extends Exception {
    /**
     * Create a new rit.QTException
     * @param msg the message associated with the exception
     */
    public QTException(String msg) {
        super(msg);
    }
}
