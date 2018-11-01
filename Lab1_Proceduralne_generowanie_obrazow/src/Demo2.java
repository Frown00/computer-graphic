import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class Demo2 {
    public static void main(String[] args) {
        //System.out.println("Ring pattern synthesis");

        BufferedImage image;

        // Image resolution
        int x_res, y_res;

        // Loop variables - indices of the current row and column
        int i, j;

        // Fixed ring width
        //final int w = 3;

        // Get required image resolution from command line arguments
        x_res = Integer.parseInt( args[0].trim() );
        y_res = Integer.parseInt( args[1].trim() );

        // Initialize an empty image, use pixel format
        // with RGB packed in the integer data type
        image = new BufferedImage(  x_res,
                                    y_res,
                                    BufferedImage.TYPE_INT_RGB );



        // Process the image, pixel by pixel
        int n_x = 1;
        int n_y = 1;
        int line_witdh = 10;
        int distance_lines_center = 30;
        int line_color = int2RGB(5, 5 ,5);
        int bgc_color = int2RGB(255, 255,122);
        int grid_shift = 20;

        for ( i = 0; i < y_res; i++) {

            int line_y = n_y * line_witdh;
            int square_y = n_y * 2 * distance_lines_center;
            int stripe_y = line_y + square_y + grid_shift + line_witdh;
            int square_bottom_border = line_y + square_y + grid_shift;
            int square_top_border = line_y + grid_shift;

            if(  i != 0 && i % stripe_y == 0) {
                n_y++;
            }
            n_x = 1;

            for ( j = 0; j < x_res; j++) {
                int line_x = n_x * line_witdh;
                int square_x = n_x * 2 * distance_lines_center;
                int stripe_x = grid_shift + line_x + square_x + line_witdh ;
                int square_right_border = grid_shift + line_x + square_x;
                int square_left_border = grid_shift + line_x ;

                if (j != 0 && j % stripe_x == 0) {
                    n_x++;
                }


                if( i < n_y * grid_shift || j < n_x * grid_shift){

                   if(j >= grid_shift  && j <= square_right_border || i >= grid_shift && i <= square_bottom_border) {
                       if(j <= square_left_border && j >= grid_shift || i <= square_top_border && i >= grid_shift) {
                           image.setRGB(j, i, line_color);
                       }
                       else
                        image.setRGB(j, i, bgc_color);

                   }
                   else if(j < grid_shift && i < grid_shift) {
                       image.setRGB(j, i, bgc_color);
                   }

                   else
                       image.setRGB(j, i, line_color);

                }
                else if(i > square_top_border && i < square_bottom_border) {

                    if (j > square_left_border && j < square_right_border) {
                        image.setRGB(j, i, bgc_color);
                    } else {
                        image.setRGB(j, i, line_color);
                    }
                }
                else {
                    image.setRGB(j, i, line_color);
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
