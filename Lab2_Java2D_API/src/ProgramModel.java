import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ProgramModel {
    final int frameWidth = 16 * 60;
    final int frameHeight = 9 * 60;

    private int drawingWidth;
    private int drawingHeight;
    final int controllerPanelWidth = 150;
    final int controllerPanelHeight = 300;

    final int drawingMinWidth = 300;
    final int drawingMinHeight = 300;
    final int controllerPaneleMinWidth = 150;
    final int controllerPanelMinHeight = 300;
    final int splitDividerLocation = 200;

    public BufferedImage image;
    private ArrayList<ColoredShape> coloredShapes = new ArrayList<>();


    public ProgramModel() {
        drawingWidth = 500;
        drawingHeight = 500;
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

