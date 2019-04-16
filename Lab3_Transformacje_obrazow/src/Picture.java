import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Picture {
    Pixel[][] pixels;
    private ArrayList<String> transforms;

    int xLen;
    int yLen;
    String imagePath;

    public Picture(BufferedImage image, String imagePath) throws IOException {
        Matrix m = Matrix.translate(10.0, 10.0);
        m.display();
        m = Matrix.transpose(m.v);
        m.display();
        pixels = new Pixel[image.getWidth()][image.getHeight()];
        xLen = image.getWidth();
        yLen = image.getHeight();
        makePicture(image);
        this.imagePath = imagePath;
        //translate(100.0, 100.0);
        //scale(1.0, 0.5);
        //rotate(350.0);

    }

    private void makePicture(BufferedImage image) {
        pixels = new Pixel[image.getWidth()][image.getHeight()];
        for(int x = 0; x <image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                Color c = new Color(image.getRGB(x, y), true);
                Pixel pixel = new Pixel(x, y, c);
                pixels[x][y] = pixel;
            }
        }
    }

    private BufferedImage makeImage(double rotate) {
        BufferedImage copy = new BufferedImage(xLen, yLen, BufferedImage.TYPE_INT_ARGB);
        for(int x = 0; x < pixels.length; x++) {
            for(int y = 0; y < pixels[x].length; y++) {
                Color c = pixels[x][y].getColor();
                int xCoord = (int)Math.round(pixels[x][y].getX());
                int yCoord = (int)Math.round(pixels[x][y].getY());
//                System.out.println(xCoord + " " + yCoord);

                if(xCoord >= 0 && yCoord >= 0 && xCoord < copy.getWidth() && yCoord < copy.getHeight()) {
                    copy.setRGB(xCoord, yCoord, c.getRGB());
                }
                if(rotate % 90 != 0) {
                    if(xCoord+1 >= 0 && yCoord+1 >= 0 && xCoord+1 < copy.getWidth() && yCoord+1 < copy.getHeight()) {
                        copy.setRGB(xCoord+1, yCoord+1, c.getRGB());
                    }
                    if(xCoord+2 >= 0 && yCoord+2 >= 0 && xCoord+2 < copy.getWidth() && yCoord+2 < copy.getHeight()) {
                        copy.setRGB(xCoord+2, yCoord+2, c.getRGB());
                    }
                }
            }
        }
        return copy;
    }


    public static BufferedImage copyImage(BufferedImage source){
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    public void translate(double dx, double dy) {
        xLen += dx;
        yLen += dy;
        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[i].length; j++) {
                double[][] posMatrix1 = {{pixels[i][j].getX()}, {pixels[i][j].getY()}, {1}};
                Matrix m1 = new Matrix(posMatrix1);
                Matrix result1;
                result1 = Matrix.multiply(Matrix.translate(dx, dy), m1);
                pixels[i][j].setX(result1.v[0][0]);
                pixels[i][j].setY(result1.v[1][0]);
            }
        }

    }

    public void scale(double scaleX, double scaleY) {
        xLen *= scaleX;
        yLen *= scaleY;
        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[i].length; j++) {
                double[][] posMatrix1 = {{pixels[i][j].getX()}, {pixels[i][j].getY()}, {1}};
                Matrix m1 = new Matrix(posMatrix1);
                Matrix result1;
                result1 = Matrix.multiply(Matrix.scale(scaleX, scaleY), m1);
                pixels[i][j].setX(result1.v[0][0]);
                pixels[i][j].setY(result1.v[1][0]);
            }
        }
        if(scaleX > 0 || scaleY > 0) {
            interpolation(scaleX, scaleY);
        }
       // translate((scaleX-1) * -100.0, (scaleY-1) * -100);
    }

    private void interpolation(double scaleX, double scaleY) {
        BufferedImage notInterpolatedImage = makeImage(0.0);
        makePicture(notInterpolatedImage);

        int distanceX= (int)Math.ceil(scaleX);
        int distanceY= (int)Math.ceil(scaleY);
        for(int x = 0; x < pixels.length - distanceX; x+= distanceX) {
            for(int y = 0; y < pixels[0].length; y+=distanceY) {
                Pixel p1 = pixels[x][y];
                Pixel p2 = pixels[x+distanceX][y];
                int p1ColorRed = p1.getColor().getRed();
                int p1ColorGreen = p1.getColor().getGreen();
                int p1ColorBlue = p1.getColor().getBlue();
                int p1ColorAlpha = p1.getColor().getAlpha();

                int p2ColorRed = p2.getColor().getRed();
                int p2ColorGreen = p2.getColor().getGreen();
                int p2ColorBlue = p2.getColor().getBlue();
                int p2ColorAlpha = p2.getColor().getAlpha();

                int red;
                int green;
                int blue;
                int alpha;
                double dX = (double) distanceX;
                double dY = (double) distanceY;

                double iter;

                for(int i = 1; i < distanceX; i++) {
                    iter = (double)i;
                    red = (int)(((dX-iter)/dX * p1ColorRed) + (iter/dX * p2ColorRed));
                    green = (int)(((dX-iter)/dX * p1ColorGreen) + (iter/dX * p2ColorGreen));
                    blue = (int)(((dX-iter)/dX * p1ColorBlue) + (iter/dX * p2ColorBlue));
                    alpha = (int)(((dX-iter)/dX * p1ColorAlpha) + (iter/dX * p2ColorAlpha));


                    pixels[x+i][y].color = new Color(red, green, blue, alpha);
                }

                if(y % distanceY == 0 && y != 0 && y < pixels[0].length - distanceY - 1) {
                    Pixel p1y = pixels[x][y];
                    Pixel p2y = pixels[x][y+distanceY];
                    int p1yColorRed = p1y.getColor().getRed();
                    int p1yColorGreen = p1y.getColor().getGreen();
                    int p1yColorBlue = p1y.getColor().getBlue();
                    int p1yColorAlpha = p1y.getColor().getAlpha();

                    int p2yColorRed = p2y.getColor().getRed();
                    int p2yColorGreen = p2y.getColor().getGreen();
                    int p2yColorBlue = p2y.getColor().getBlue();
                    int p2yColorAlpha = p2y.getColor().getAlpha();
                    for(int i = 0; i < distanceX; i++) {
                        for(int j = 1; j < distanceY; j++) {
                            iter = (double)j;
                            red = (int)(((dY-iter)/dY * p1yColorRed) + (iter/dY * p2yColorRed));
                            green = (int)(((dY-iter)/dY * p1yColorGreen) + (iter/dY * p2yColorGreen));
                            blue = (int)(((dY-iter)/dY * p1yColorBlue) + (iter/dY * p2yColorBlue));
                            alpha = (int)(((dY-iter)/dY * p1yColorAlpha) + (iter/dY * p2yColorAlpha));
                            pixels[x+i][y+j].color = new Color(red, green, blue, alpha);
                        }

                    }
                }
            }
        }

    }



    public void rotate(double deg) {
//        BufferedImage notNormalized = makeImage();
//        makePicture(notNormalized);
        deg = deg % 360;
//        System.out.println("Stopni:" + deg);
        double e = 0;
        double f = 0;

//        for(int i = 0; i < pixels.length; i++) {
//            for(int j = 0; j < pixels[i].length; j++) {
//                e += pixels[i][j].getX();
//                f += pixels[i][j].getY();
//            }
//        }
        //e = e / (pixels.length * 2);
        //f = f / (pixels[0].length * 2);
        e = pixels.length / 2;
        f = pixels[0].length / 2;
        System.out.println(e);
        System.out.println(f);

//        System.out.println(e + ":" + f);
        Matrix affineTransform;
        Matrix translateMinus = Matrix.translateRotate(-e, -f);
        Matrix translatePlus = Matrix.translateRotate(e, f);
        Matrix movedTransform = Matrix.multiply(translateMinus, Matrix.rotate(deg));
        //movedTransform.display();
        affineTransform = Matrix.multiply(movedTransform, translatePlus);
        //affineTransform.display();

        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[i].length; j++) {
                double[][] posMatrix1 = {{pixels[i][j].getX()}, {pixels[i][j].getY()}, {1}};
                Matrix m1 = new Matrix(posMatrix1);
                Matrix result1;
                result1 = Matrix.multiply(Matrix.transpose(affineTransform.v), m1);

                pixels[i][j].setX(Math.round(result1.v[0][0]));
                pixels[i][j].setY(Math.round(result1.v[1][0]));
            }
        }


    }


    public void transform(String filename) throws IOException {
        transforms = getParametersFromFile(filename);
        double rotation = 0.0;
        for(int i = 0; i < transforms.size(); i++) {
            if(transforms.get(i).equals("translate")) {
                translate(Double.parseDouble(transforms.get(i+1)), Double.parseDouble(transforms.get(i+2)));
                i+=2;

            } else if(transforms.get(i).equals("scale")) {

                scale(Double.parseDouble(transforms.get(i+1)), Double.parseDouble(transforms.get(i+2)));
                i+=2;

            } else if(transforms.get(i).equals("rotate")) {
                double singleRotation = Double.parseDouble(transforms.get(i+1));
                rotation += singleRotation;
                rotate(singleRotation);
                i++;
            }
        }
        BufferedImage transformed = makeImage(rotation);

        ImageIO.write(transformed, "png", new File("transformed_" + imagePath));
        //translate(100, 100);

    }

    private ArrayList<String> getParametersFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String line;
        String[] parts;
        ArrayList<String> parameters = new ArrayList<>();

        while((line = br.readLine()) != null){
            //process the line
            parts = line.split(" ");
            Collections.addAll(parameters, parts);
        }

        return parameters;
    }

    private Pixel getPixel(int x, int y) {
        return pixels[x][y];
    }
}
