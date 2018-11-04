import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ColoredShape {

    static int numRectangle;
    static int numEllipse;
    static int numPolygon;

    private Color strokeColor;
    private int strokeSize = 2;
    private Shape shape;
    private int type;

    private String id;
    private BufferedImage fragment;

    public ColoredShape(Color strokeColor, Rectangle2D.Double shape)
    {
        numRectangle++;
        this.strokeColor = strokeColor;
        this.shape = shape;
        this.type = ProgramModel.RECTANGLE_SHAPE;
        this.id = "Prostokat_" + numRectangle;
    }

    public ColoredShape(Color strokeColor, Ellipse2D.Double shape)
    {
        numEllipse++;
        this.strokeColor = strokeColor;
        this.shape = shape;
        this.type = ProgramModel.ELLIPSE_SHAPE;
        this.id = "Elipsa_" + numEllipse;
    }

    public ColoredShape(Color strokeColor, Polygon shape)
    {
        numPolygon++;
        this.strokeColor = strokeColor;
        this.shape = shape;
        this.type = ProgramModel.POLYGON_SHAPE;
        this.id = "Wielokat_" + numPolygon;
    }


    public Color getStrokeColor()
    {
        return this.strokeColor;
    }

    public void setStrokeColor(Color strokeColor)
    {
        this.strokeColor = strokeColor;
    }

    public Shape getShape()
    {
        return shape;
    }

    public String getId() {
        return id;
    }

    public int getStrokeSize() {
        return strokeSize;
    }

    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
    }

    public void saveFragment(BufferedImage image) {
        if(type == ProgramModel.RECTANGLE_SHAPE) {
            Rectangle2D.Double rectangle = (Rectangle2D.Double) shape;
            fragment = image.getSubimage((int)rectangle.x, (int)rectangle.y, (int)rectangle.width, (int)rectangle.height);
        }
        else if(type == ProgramModel.ELLIPSE_SHAPE) {
            Ellipse2D.Double ellipse = (Ellipse2D.Double) shape;
            fragment = new BufferedImage(image.getWidth(),
                                        image.getHeight(),
                                                BufferedImage.TYPE_INT_ARGB);

            for(int y = 0; y < image.getHeight(); y++) {
                for( int x = 0; x < image.getWidth(); x++) {
                    if(shape.contains(new Point2D.Double(x, y))) {
                        fragment.setRGB(x, y, image.getRGB(x, y));
                    }
                }
            }

            fragment = fragment.getSubimage((int)ellipse.x, (int)ellipse.y, (int)ellipse.width, (int)ellipse.height);
//            for(int y = 0; y < image.getHeight(); y++) {
//                for(int x = 0; x < image.getWidth(); x++) {
//                    if(shape.contains(new Point2D.Double(x, y))) {
//                        System.out.println("( " + x + ", " + y + " )");
//                    }
//                }
//            }
        } else if(type == ProgramModel.POLYGON_SHAPE) {
            Polygon polygon = (Polygon) shape;
            fragment = new BufferedImage(image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            for(int y = 0; y < image.getHeight(); y++) {
                for( int x = 0; x < image.getWidth(); x++) {
                    if(shape.contains(new Point2D.Double(x, y))) {
                        fragment.setRGB(x, y, image.getRGB(x, y));
                    }
                }
            }

            Rectangle2D frame = polygon.getBounds2D();

            fragment = fragment.getSubimage((int)frame.getX(), (int)frame.getY(), (int)frame.getWidth(), (int)frame.getHeight());
        }

//        for(int y = 0; y < image.getHeight(); y++) {
//            for(int x = 0; x < image.getWidth(); x++) {
//                if(shape.contains(new Point2D.Double(x, y))) {
//                    System.out.println(x);
//                }
//            }
//        }
    }

    public void saveToXML() {
        try {
            File fragmentImageFile = new File("images/"+ this.id + ".png");
            ImageIO.write(fragment, "png", fragmentImageFile);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private int int2ARGB(int alpha,int r, int g, int b) {
        return alpha << 24 + r << 16 + g << 8 + b;
    }

    @Override
    public String toString() {
        return this.id;
    }

}
