import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class Extra {
    public static void main(String[] args) {
        System.out.println("Ring pattern synthesis");

        BufferedImage image;

        // Image resolution
        int x_res, y_res;

        // Ring center coordinates
        int x_c, y_c;

        // Predefined black and white RGB representations
        // packed as integers
        int black, white;

        // Loop variables - indices of the current row and column
        int x, y;

        // Fixed ring width
        final int w = 60;

        // Get required image resolution from command line arguments
        x_res = Integer.parseInt( args[0].trim() );
        y_res = Integer.parseInt( args[1].trim() );

        // Initialize an empty image, use pixel format
        // with RGB packed in the integer data type
        image = new BufferedImage(  x_res,
                y_res,
                BufferedImage.TYPE_INT_RGB );

        // Create packed RGB representation of black and white colors
        black = int2RGB( 0, 0 ,0);
        white = int2RGB( 255, 255, 255);

        // Find coordinates of the image center
        x_c = x_res / 2;
        y_c = y_res / 2;

        // Process the image, pixel by pixel
        double d;
        int r;
        //int angle = angleBetween2Lines(0, 0, 5, 0,
        //        0, 5, 0, 0);
        int point_x = 0;
        int point_y = y_res / 2;
        int a = 15;
        for ( y = 0; y < y_res; y++) {
            for ( x = 0; x < x_res; x++) {


                // Calculate distance to the image center
                d = Math.sqrt( (x - y_c) * (x - y_c) + (y - x_c) * (y - x_c) );

                // Find the ring index
                r = (int)d / w;

                // Make decistion on the pixel color
                // based on the ring index
                int angle = angleBetween2Lines(point_x, point_y, x_c, y_c, x, y, x_c, y_c);

                if ( angle % 60 >= 30) {
                    // Even ring - set black color
                    image.setRGB( x, y, black);
                }
                else {
                    // Odd ring - set white color
                    image.setRGB( x, y , white);
                }
            }
        }

        try {
            ImageIO.write( image, "bmp", new File( args[2] ) );
            //System.out.println( "Ring image created successfully" );
        }
        catch (IOException e) {
            System.out.println( "The image cannot be stored ");
        }
    }


    // This method assembles RGB color intensities into single
    // packed integer. Arguments must be in <0..255> range
    static int int2RGB( int red, int green, int blue) {
        // Make sure that color intensities are in 0..255 range
        red     = red   & 0x000000FF;
        green   = green & 0x000000FF;
        blue    = blue  & 0x000000FF;

        // Assemble packed RGB using bit shift operations
        return (red << 16) + (green << 8) + blue;
    }

    static int angleBetween2Lines(int line1_x1, int line1_y1,
                                  int line1_x2, int line1_y2,
                                  int line2_x1, int line2_y1,
                                  int line2_x2, int line2_y2) {
        double angle1 = Math.atan2(line1_y1 - line1_y2,
                                    line1_x1 - line1_x2);
        double angle2 = Math.atan2(line2_y1 - line2_y2,
                                    line2_x1 - line2_x2);

        return (int)((angle1-angle2) * 180 / Math.PI);
    }
}
