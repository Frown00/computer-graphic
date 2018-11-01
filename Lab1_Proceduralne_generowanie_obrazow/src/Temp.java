import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Temp {



        public static void main(String[] args) {

            BufferedImage image;

            // Image resolution
            int x_res, y_res;

            // Predefined fields colors
            int first_color, second_color;

            // Loop variables - indices of the current row and column
            int i, j;



            // Get required image resolution from command line arguments
            x_res = Integer.parseInt( args[0].trim() );
            y_res = Integer.parseInt( args[1].trim() );

            // Initialize an empty image, use pixel format
            // with RGB packed in the integer data type
            image = new BufferedImage(  x_res,
                    y_res,
                    BufferedImage.TYPE_INT_RGB );

            // Create packed RGB representation of black and white colors
            first_color = int2RGB( 0, 0 ,0);
            second_color = int2RGB( 255, 255, 255);

            // Process the image, pixel by pixel

            // Squere
            // Field size
            final int w = 10;
            int square_left_border;
            int square_right_border;
            int square_top_border = 0;
            int square_bottom_border = w;

            //
            int n_y = 0;
            int tilt = (w / 2) - 1;
            // color to fill squere
            int color;

            for ( i = 0; i < y_res; i++) {

                // Next level
                if( i != 0 && (i % w == 0)) {
                    square_top_border += w;
                    square_bottom_border += w;
                    n_y++;
                    if(tilt == 0) {
                        tilt = (w / 2) - 1;
                    }
                }
                if(tilt == 0) {
                    tilt = (w/2) - 1;
                } else {
                    tilt = (w/2) - 1 - i;
                }
                square_left_border = 0;
                square_right_border = w;
                for ( j = 0; j < x_res; j++) {

                    if(j != 0 && (j % (2*w) == 0)) {
                        square_right_border += 2*w;
                        square_left_border += 2*w;
                        tilt += 2 * w;
                    }


                    if((j >= square_left_border && j < square_right_border)
                            && (i >= square_top_border && i < square_bottom_border)) {
                        int squere_cross_section = (square_left_border + square_right_border) / 2 - 1;
                        if(j >= tilt && j <= (squere_cross_section + (squere_cross_section - tilt )) && n_y % 2 == 0){
                            color = first_color;
                        }
                        else {
                            color = second_color;
                        }

                    } else {
                        color = (n_y % 2 == 0) ? second_color : first_color;
                    }
                    image.setRGB(j, i, color);
                }
            }

            try {
                ImageIO.write( image, "bmp", new File( args[2] ) );
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
