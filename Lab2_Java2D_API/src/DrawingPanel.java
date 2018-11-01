import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class DrawingPanel extends JPanel {
    BufferedImage image;


    DrawingPanel() {
        image = null;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage( image, 0, 0, null);

//        GradientPaint gradient = new GradientPaint(70, 70, Color.RED,
//                                                   150, 150, Color.BLUE);
//        g2d.setXORMode(new Color(1, 1, 1));
//        g2d.setStroke(new BasicStroke(5));
//        Rectangle2D.Double rectangle = new Rectangle2D.Double(40, 40, 60, 90);
//        g2d.draw(rectangle);
//        g2d.draw(rectangle);
//
//        BufferedImage image;
//        image = new BufferedImage( 500, 500, BufferedImage.TYPE_INT_RGB);
//
//        int color;
//        int gray;
//        int i, j;
//        int height = image.getHeight();
//        int width = image.getWidth();
//        double m = 255.0 / width;
//        for( i = 0; i < height; i++) {
//            for( j = 0; j < width; j++) {
//                gray = (int)(j * m);
//                color = int2RGB(gray, gray, gray);
//                image.setRGB(j, i, color);
//            }
//        }

    }



    static int int2RGB( int red, int green, int blue) {
        // Make sure that color intensities are in 0..255 range
        red     = red   & 0x000000FF;
        green   = green & 0x000000FF;
        blue    = blue  & 0x000000FF;

        // Assemble packed RGB using bit shift operations
        return (red << 16) + (green << 8) + blue;
    }
}

