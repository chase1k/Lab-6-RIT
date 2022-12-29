package rit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A JavaFX application that uncompresses a .rit file into a .raw file
 * and displays the resulting image.
 *
 * Usage: java RITUncompress rit-file raw-file
 *
 * @author RIT CS
 */
public class RITUncompress extends Application {
    /** desired screen width for scaled image */
    private final static int SCREEN_WIDTH = 256;
    /** desired screen height for scaled image */
    private final static int SCREEN_HEIGHT = 256;
    /** the maximum size to scale for */
    private final static int MAX_DIM_SCALE = 32;
    /** name of .rit file */
    private String ritFilename;
    /** the raw image matrix */
    private int[][] image;
    /** the dimension of the image */
    private int DIM;

    @Override
    public void init() {
        // get the command line args
        List<String> args = getParameters().getRaw();

        if (args.size() == 2) {
            try {
                this.ritFilename = args.get(0);
                String rawFilename = args.get(1);

                // create an empty tree and then uncompress it
                QTree tree = new QTree();

                tree.uncompress(this.ritFilename);

                // print the tree in preorder
                System.out.println(tree);
                // get the raw image and dimension
                this.image = tree.getImage();
                this.DIM = tree.getDim();
                // generate the raw file
                tree.writeUncompressed(rawFilename);
            } catch (IOException | QTException e) {
                if (e instanceof IOException){
                    System.err.println("java.io.FileNotFoundException: input-file (No such file or directory)");
                } else {
                    System.err.println("QTException: Error writing uncompressed file. File has not been uncompressed.");
                }

                Platform.exit();
            }

        } else {
            System.out.println("Usage: java RITUncompress input-file.rit output-file.raw");
            Platform.exit();
        }
    }

    /**
     * Create a GridPane that represents the visual image.  Here we use
     * rectangles that are scaled proportionally to the image size so that
     * smaller images are still viewable.
     *
     * @return the populated pane
     */
    private GridPane makeGridPane() {
        GridPane gridPane = new GridPane();
        for (int row = 0; row < this.DIM; row++) {
            for (int col = 0; col < this.DIM; col++) {
                Rectangle rec = new Rectangle();
                // here we try and scale the rectangles inversely to the image size
                rec.setWidth(Math.ceil(SCREEN_WIDTH/(double)this.DIM));
                rec.setHeight(Math.ceil(SCREEN_HEIGHT/(double)this.DIM));
                // get the pixel color and create a filled rectangle for it
                int color = image[row][col];
                rec.setFill(Color.rgb(color, color, color, 1));
                // set some properties for the rectangle in the grid
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
                GridPane.setHalignment(rec, HPos.CENTER);
                gridPane.getChildren().addAll(rec);
            }
        }
        return gridPane;
    }

    /**
     * Set the pixel values in the graphics context to the color values in the
     * image.  For larger images this is much faster than scaling.
     *
     * @param gc the graphics context we are drawing into
     */
    private void drawComponents(GraphicsContext gc) {
        for (int row = 0; row < this.DIM; row++) {
            for (int col = 0; col < this.DIM; col++) {
                int c = image[row][col];
                Color color = Color.rgb(c, c, c);
                gc.setFill(color);
                gc.fillRect(col, row, 1, 1);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        // include the filename in the title
        Path p = Paths.get(this.ritFilename);
        String filename = p.getFileName().toString();
        stage.setTitle(filename.substring(0, filename.lastIndexOf('.')));

        // if scaling create the grid pane and set it in the scene as the only thing
        if (this.DIM <= MAX_DIM_SCALE) {
            stage.setScene(new Scene(makeGridPane()));
        } else {
            // otherwise just paint the pixels (much faster for larger images)
            Group root = new Group();
            Canvas canvas = new Canvas(this.DIM, this.DIM);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            drawComponents(gc);
            root.getChildren().add(canvas);
            stage.setScene(new Scene(root));
        }
        // make stage unresizable and show that bad boy!
        stage.setResizable(false);
        stage.show();
    }

    /**
     * The main method expects the host and port.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
