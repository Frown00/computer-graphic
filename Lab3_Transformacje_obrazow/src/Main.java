import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {

        int r = 255;
        double d = 2.0;
        double i = 1.0;
        int red = 0;
        red = (int)((d-i)/d * r + (i/d * r));

        System.out.println(red);
        String fileName = "polygon.txt";
        String transformFilename = "transform.txt";
        String transformFilename2 = "transformImage.txt";

        BufferedImage bi = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = (Graphics2D) bi.getGraphics();
        DrawShape drawShape = new DrawShape(fileName, Color.RED, 10);

        drawShape.transform(transformFilename);

        drawShape.drawOnGraphic(g);
        ImageIO.write(bi, "png", new File("shape.png"));

        String transformImage = "kostki.png";
        BufferedImage image = ImageIO.read(new File(transformImage));
        Picture picture = new Picture(image, transformImage);
        picture.transform(transformFilename2);


    }



}
