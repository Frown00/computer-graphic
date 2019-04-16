import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class DrawShape {

    private String filename;
    private Color color;
    private int stroke;
    private ArrayList<String> parameters;
    private ArrayList<String> transforms;
    public ArrayList<Line2D.Double> lines;

    public DrawShape(String filename) throws IOException {
        color = Color.BLUE;
        stroke = 3;
        parameters = getParametersFromFile(filename);
    }

    public DrawShape(String filename, Color color, int stroke) throws IOException {
        parameters = getParametersFromFile(filename);
        lines = new ArrayList<>();
        getLines(parameters);


        this.color = color;
        this.stroke = stroke;
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

    private void getLines(ArrayList<String> parameters) {
        Line2D.Double line;
        for(int i = 0; i < parameters.size(); i+=4) {
            line = new Line2D.Double(Double.parseDouble(parameters.get(i)),
                    Double.parseDouble(parameters.get(i + 1)),
                    Double.parseDouble(parameters.get(i + 2)),
                    Double.parseDouble(parameters.get(i + 3)));
            lines.add(line);
        }

    }

    public void translate(double x, double y) {
        for(int i = 0; i < lines.size(); i++) {
            double[][] posMatrix1 = {{lines.get(i).getX1()}, {lines.get(i).getY1()}, {1}};
            double[][] posMatrix2 = {{lines.get(i).getX2()}, {lines.get(i).getY2()}, {1}};

            Matrix m1 = new Matrix(posMatrix1);
            Matrix m2 = new Matrix(posMatrix2);
            Matrix result1;
            Matrix result2;
            result1 = Matrix.multiply(Matrix.translate(x, y), m1);
            result2 = Matrix.multiply(Matrix.translate(x, y), m2);
            int x1 = (int)result1.v[0][0];
            int y1 = (int)result1.v[1][0];
            int x2 = (int)result2.v[0][0];
            int y2 = (int)result2.v[1][0];
//            System.out.println(x1 + " " + y1 + "; " + x2 + " " + y2);
            lines.get(i).setLine(x1, y1, x2, y2);
        }
    }

    public void scale(double scaleX, double scaleY) {
        for(int i = 0; i < lines.size(); i++) {
            double[][] posMatrix1 = {{lines.get(i).getX1()}, {lines.get(i).getY1()}, {1}};
            double[][] posMatrix2 = {{lines.get(i).getX2()}, {lines.get(i).getY2()}, {1}};

            Matrix m1 = new Matrix(posMatrix1);
            Matrix m2 = new Matrix(posMatrix2);
            Matrix result1;
            Matrix result2;
            result1 = Matrix.multiply(Matrix.scale(scaleX, scaleY), m1);
            result2 = Matrix.multiply(Matrix.scale(scaleX, scaleY), m2);
            int x1 = (int)result1.v[0][0];
            int y1 = (int)result1.v[1][0];
            int x2 = (int)result2.v[0][0];
            int y2 = (int)result2.v[1][0];
//            System.out.println(x1 + " " + y1 + "; " + x2 + " " + y2);
            lines.get(i).setLine(x1, y1, x2, y2);
        }

        translate((scaleX-1) * -100.0, (scaleY-1) * -100);
    }

    public void rotate(double deg) {
        deg = deg % 360;
//        System.out.println("Stopni:" + deg);
        double e = 0;
        double f = 0;

        for(int i = 0; i < lines.size(); i++) {
            e += lines.get(i).getX1() + lines.get(i).getX2();
            f += lines.get(i).getY1() + lines.get(i).getY2();
        }
        e = e / (lines.size() * 2);
        f = f / (lines.size() * 2);

//        System.out.println(e + ":" + f);
        Matrix affineTransform;
        Matrix translateMinus = Matrix.translate(-e, -f);
        Matrix translatePlus = Matrix.translate(e, f);
        Matrix movedTransform = Matrix.multiply(translateMinus, Matrix.rotate(deg));
        //movedTransform.display();
        affineTransform = Matrix.multiply(movedTransform, translatePlus);
        //affineTransform.display();
        for(int i = 0; i < lines.size(); i++) {


            double[][] posMatrix1 = {{lines.get(i).getX1()}, {lines.get(i).getY1()}, {1}};
            double[][] posMatrix2 = {{lines.get(i).getX2()}, {lines.get(i).getY2()}, {1}};

            Matrix m1 = new Matrix(posMatrix1);
            Matrix m2 = new Matrix(posMatrix2);
            Matrix result1;
            Matrix result2;


            result1 = Matrix.multiply(Matrix.rotate(deg), m1);
            result2 = Matrix.multiply(Matrix.rotate(deg), m2);
            int x1 = (int)Math.round(result1.v[0][0]);
            int y1 = (int)Math.round(result1.v[1][0]);
            int x2 = (int)Math.round(result2.v[0][0]);
            int y2 = (int)Math.round(result2.v[1][0]);
//            System.out.println(x1 + " " + y1 + "; " + x2 + " " + y2);
            lines.get(i).setLine(x1, y1, x2, y2);
        }

        double dx = 0;
        double dy = 0;
        if(deg > 0 && deg <= 90) {
            dy = deg * (f * 2 / 90);
        } else if(deg > 90 && deg < 180) {
            dx = (deg % 90) * (e * 2 / 90);
            dy = f*2;
            if(deg <= 135) {
                deg = deg % 45;
                if(deg == 0) {
                    dy += 50;
                } else {
                    dy += deg/45 * 50;
                }
            } else if(deg < 180){
                deg = deg % 45;
                if(deg == 0) {
                    dy += 0;
                } else {
                    dy += 50 - deg/45 * 50;
                }
            }
        } else if(deg >= 180 && deg < 270) {
            dx = e * 2;
            dy = f * 2 - ((deg % 90) * (f * 2 / 90));

            if(deg <= 225 && deg > 180) {
                deg = deg % 45;
                if(deg == 0) {
                    dx += 50;
                } else {
                    dx += deg/45 * 50;
                }
            } else if(deg < 270){
                deg = deg % 45;
                if(deg == 0) {
                    dx += 0;
                } else {
                    dx += 50 - deg/45 * 50;
                }
            }
        } else if(deg == 270) {
            dx = 300;
            dy = 0;
        }
        else if(deg > 270 && deg < 360){
            dx = e * 2 - ((deg % 90) * (e * 2 / 90));

            if(deg <= 315) {
                deg = deg % 45;
                if(deg == 0) {
                    dy += -40;
                } else {
                    dy += - (deg/45 * 50);
                }
            } else if(deg < 360){
                deg = deg % 45;
                if(deg == 0) {
                    dy += 0;
                } else {
                    dy += -(50 - deg/45 * 50);
                }
            }
        }
//        System.out.println("dx: " + dx);
//        System.out.println("dy: " + dy);

        translate(dx, dy);

    }

    public void transform(String filename) throws IOException {
        transforms = getParametersFromFile(filename);
        for(int i = 0; i < transforms.size(); i++) {
            if(transforms.get(i).equals("translate")) {
                translate(Double.parseDouble(transforms.get(i+1)), Double.parseDouble(transforms.get(i+2)));
                i+=2;
            } else if(transforms.get(i).equals("scale")) {
                scale(Double.parseDouble(transforms.get(i+1)), Double.parseDouble(transforms.get(i+2)));
                i+=2;
            } else if(transforms.get(i).equals("rotate")) {
                rotate(Double.parseDouble(transforms.get(i+1)));
                i++;
            }
        }
        //translate(100, 100);

    }

    public void drawOnGraphic(Graphics2D g) {
        g.setStroke(new BasicStroke(stroke));
        g.setColor(color);
        for(int i = 0; i < lines.size(); i++) {
            g.drawLine((int)lines.get(i).getX1(), (int)lines.get(i).getY1(), (int)lines.get(i).getX2(), (int)lines.get(i).getY2());
        }
    }

}
