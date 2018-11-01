import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Demo8 {
    public static void main(String[] args) {
        /*
         * args[0] - picture width args[1] - picture height args[2] - blurred zone width
         * args[3] - file name
         */
        System.out.println("Blurred ring pattern synthesis");
        BufferedImage image;

        int xRes, yRes;

        double xRingCenter, yRingCenter;

        int black, white;

        xRes = Integer.parseInt(args[0].trim());
        yRes = Integer.parseInt(args[1].trim());



        image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);

        black = int2RGB(0, 0, 0);
        white = int2RGB(255, 255, 255);

        xRingCenter = xRes / 2;
        yRingCenter = yRes / 2;

        double diameter;

        for (int i = 0; i < yRes; i++) {
            for (int j = 0; j < xRes; j++) {
                diameter = Math.sqrt((i - yRingCenter) * (i - yRingCenter) + (j - xRingCenter) * (j - xRingCenter));
                if (ring((int) diameter)) {
                    image.setRGB(j, i, black);
                } else {
                    image.setRGB(j, i, white);
                }
            }
        }

        try {
            ImageIO.write(image, "bmp", new File(args[2]));
            System.out.println("Ring image created successfully");
        } catch (IOException e) {
            System.out.println("This image cannot be stored");
        }
    }

    static int int2RGB(int red, int green, int blue) {
        red = red & 0x000000FF;
        green = green & 0x000000FF;
        blue = blue & 0x000000FF;

        return (red << 16) + (green << 8) + blue;
    }

    static boolean ring(int diameter) {
        int limit = 2;
        int d = 0;
        while (d != diameter) {
            if (d == limit) {
                limit = limit + limit / 2;
            }
            d++;
        }
        if (100 * diameter < 100 * limit * 0.80) {
            return true;
        } else {
            return false;
        }
    }
}
