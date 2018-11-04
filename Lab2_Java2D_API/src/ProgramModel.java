import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ProgramModel {
    final int frameWidth = 16 * 60;
    final int frameHeight = 9 * 60;

    private int drawingWidth;
    private int drawingHeight;
    final int controllerPanelWidth = 200;
    final int controllerPanelHeight = 800;

    final int drawingMinWidth = 300;
    final int drawingMinHeight = 300;
    final int controllerPanelMinWidth = 150;
    final int controllerPanelMinHeight = 700;
    final int splitDividerLocation = 200;
    final static int RECTANGLE_SHAPE = 1;
    final static int ELLIPSE_SHAPE = 2;
    final static int POLYGON_SHAPE = 3;

    boolean isDrawing = false;

    public BufferedImage image;
    ArrayList<ColoredShape> coloredShapes = new ArrayList<>();

    public ColoredShape shape;
    public Color shapeColor;
    public String selectedTypeOfShape;

    public ProgramModel() {
        drawingWidth = 500;
        drawingHeight = 500;
        shapeColor = Color.BLACK;
    }


    ////    GETTERS AND SETTERS    ////
    public int getDrawingWidth() {
        return drawingWidth;
    }

    public void setDrawingWidth(int newWidth) {
        drawingWidth = newWidth;
    }

    public int getDrawingHeight() {
        return drawingHeight;
    }

    public void setDrawingHeight(int newHeight) {
        drawingHeight = newHeight;
    }


    ////////////////////////////////////

}

