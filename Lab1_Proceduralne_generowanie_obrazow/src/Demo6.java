import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.util.HashMap;

public class Demo6 {
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
        //int i, j;



        // Get required image resolution from command line arguments
        x_res = Integer.parseInt( args[0].trim() );
        y_res = Integer.parseInt( args[1].trim() );

        // Initialize an empty image, use pixel format
        // with RGB packed in the integer data type
        image = new BufferedImage(  x_res,
                y_res,
                BufferedImage.TYPE_INT_RGB );

        // Create packed RGB representation of black and white colors
        black = int2RGB( 0, 0,0);
        white = int2RGB( 255, 255, 255);

        // Find coordinates of the image center
        x_c = x_res / 2;
        y_c = y_res / 2;

        // Process the image, pixel by pixel
        double d;
        int r;

        // Fixed ring width
        final int w = 10;
        // Blur area and amount of grayScale colors
//        final int blur = 30;

        // Map of grayScale colors
//        HashMap<Integer, Integer> grayScale = new HashMap<>();
//
//        final int diff = 255 / blur;
//        int currentGray = 0;
//        for (int i = 0; i < blur; i++) {
//            int gray = int2RGB(currentGray, currentGray, currentGray);
//            grayScale.put(i, gray);
//
//            currentGray += diff;
//        }
        //System.out.println(grayScale.get(25));
        int size = 0;
        int quantity_of_circle = (int) (Math.log(x_res) / Math.log(2.0));
        System.out.println(quantity_of_circle);
        for ( int i = 0; i < y_res; i++) {
            //System.out.println();
            for ( int j = 0; j < x_res; j++) {

                // Calculate distance to the image center
                d = Math.sqrt( (i - y_c) * (i - y_c) + (j - x_c) * (j - x_c) );

                // Find the ring index
                r = (int)d / w;

                // Find the grayScale index
               // int gsId = ((int) d - (r * w + w)) + blur;

                // Edge of ring full color ( white or black)
                if(r == 0) {
                    size = (r * w + w);

                } else if(r%2 == 0) {
                    //size = (2 * r * size + size);
                }

                // Make decistion on the pixel color
                // based on the ring index
                if ( d <= size) {
                    image.setRGB( j, i, black);
                } else {
                    image.setRGB( j, i, white);
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
}
