import java.awt.image.BufferedImage;

public class ProgramModel {
    private final int frameWidth = 16 * 60;
    private final int frameHeight = 9 * 60;

    private int drawingWidth;
    private int drawingHeight;
    private final int interfaceWidth = 150;
    private final int interfaceHeight = 300;

    private final int drawingMinWidth = 300;
    private final int drawingMinHeight = 300;
    private final int interfaceMinWidth = 150;
    private final int interfaceMinHeight = 300;
    private final int splitDividerLocation = 200;
    public BufferedImage image;


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

