package rit;

import java.io.IOException;

/**
 * Rich Image Tool compressor.  This program takes a raw image file of
 * grayscale values (0-255) and compresses them into the RIT format.
 * It is expected this raw image file is perfectly square, e.g.
 * 1x1, 2x2, 4x4, 16x16, 256x256, 512x512.  In other words, there
 * are 2^n x 2^n pixels.
 *
 * $ java RITCompress input-file.txt output-file.rit
 *
 * @author RIT CS
 */
public class RITCompress {

    /**
     * The main routine.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java RITCompress input-file.raw output-file.rit");
            return;
        }

        try {
            // initialize the tree
            QTree tree = new QTree();
            // compress the image
            try{
            tree.compress(args[0]);}
            catch (IOException io){
                System.err.println("java.io.FileNotFoundException: "+args[0]+" (No such file or directory)");
            }
            // display the tree in preorder
            System.out.println(tree);
            // write the compressed tree out to output-file
            tree.writeCompressed(args[1]);

            // display statistics regarding the compression efficiency
            System.out.println("Raw image size: " + tree.getRawSize());
            System.out.println("Compressed image size: " + tree.getCompressedSize());
            System.out.println("Compression %: " +
                    (1.0 - (double) tree.getCompressedSize() / tree.getRawSize()) * 100);
        } catch (Exception e) {
            if(e instanceof IOException){
                System.err.println("java.io.FileNotFoundException: "+args[1]+" (Permission denied)");
            }
        }
    }
}
