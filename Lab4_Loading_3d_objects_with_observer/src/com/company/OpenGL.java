package com.company;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class OpenGL {
    private JFrame frame;
    ArrayList<Triangle> triangles;
//    Triangle triangle = new Triangle(
//            new Vertex(-0.5f, -0.5f,0),
//            new Vertex(0.5f, -0.5f, 0),
//            new Vertex(0f, 0.5f, 0));
    GLProfile profile;
    GLCapabilities capabilities;
    GLJPanel panel;
    GLCanvas rightPanel;
    // The canvas
    final GLCanvas glcanvas;

    Point mousePt;

    public OpenGL() {
        profile = GLProfile.get(GLProfile.GL2);
        capabilities = new GLCapabilities(profile);
        glcanvas = new GLCanvas( capabilities );
        panel = new GLJPanel(capabilities);
        rightPanel = new GLCanvas(capabilities);
        triangles = new ArrayList<>();

        glcanvas.setSize(400, 400);
    }
    public void GUI() {
        frame = new JFrame();
//        frame.setLayout(new BorderLayout());
//        triangles.add(triangle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Wczytywanie scen");
//        rightPanel.setBackground(Color.CYAN);
        frame.add(panel);
//        panel.setSize(100, 100);
        panel.addKeyListener(new Camera());

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
            }
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mousePt.x;
                int dy = e.getY() - mousePt.y;
//                System.out.println("dx: " + dx);
//                System.out.println("dy: " + dy);
                Camera.xAngle = (Camera.xAngle + dx) % 360;
                Camera.yAngle = (Camera.yAngle + dy) % 360;
                mousePt = e.getPoint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
//                float dx = e.getX() / Scene.width;
//                float dy = e.getY() / Scene.height;
//
//                Camera.xAngle = dx * 360;
//                Camera.yAngle = dy * 360;
            }
        });

        //frame.add(rightPanel, BorderLayout.EAST);
//        frame.getContentPane().add(topPanel);
//        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setSize(1200, 800);
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setUndecorated(true);
        frame.setVisible(true);
//Instantiating and Initiating Animator
        final FPSAnimator animator = new FPSAnimator(panel, 300,true);
        animator.start();
    }

    public void loadCameraSettings(String filename) {
        String fileName = filename;
        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            String[] lineParts;
            while((line = bufferedReader.readLine()) != null) {
                lineParts = line.split(" ");
                Camera.EYE_X = Float.parseFloat(lineParts[0]);
                Camera.EYE_Y = Float.parseFloat(lineParts[1]);
                Camera.EYE_Z = Float.parseFloat(lineParts[2]);
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

    }

    public void loadScene(Scene scene) {
//        triangles.clear();
//        for(int i = 0; i < t.size(); i++) {
//            triangles.set(i, t.get(i));
//        }
//        //frame.
//        for(int i = 0; i < triangles.size(); i++) {
//            panel.removeGLEventListener(triangles.get(i));
//        }

        panel.addGLEventListener(scene);

        panel.repaint();

    }

    // loading triangles from file to make a scene
    public void loadScene(String scene) {
        Scene.name = scene;
        String filename = "sceny/" + scene +".scn";
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Triangle> triangles = new ArrayList<>();
        // The name of the file to open.
        String fileName = filename;
        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            boolean isVertex = false;
            boolean isTriangles = false;
            String[] lineParts;
            while((line = bufferedReader.readLine()) != null) {
                lineParts = line.split(" ");

                if(isVertex) {
                    if(lineParts.length == 3) {
                        Vertex v = new Vertex(  Float.parseFloat(lineParts[0]),
                                                Float.parseFloat(lineParts[1]),
                                                Float.parseFloat(lineParts[2]));
                        vertices.add(v);
                    }
                }

                if(isTriangles) {
                    if(lineParts.length == 3) {
                        Vertex v1 = vertices.get(Integer.parseInt(lineParts[0]));
                        Vertex v2 = vertices.get(Integer.parseInt(lineParts[1]));
                        Vertex v3 =  vertices.get(Integer.parseInt(lineParts[2]));
                        Triangle triangle = new Triangle(v1, v2, v3);
                        triangles.add(triangle);
                    }
                }

                if(!isVertex) {
                    //System.out.println(line);
                }

                // when found list of vertex
                if(lineParts[0].contains("points_count")){
                    isVertex = true;
                } else if(line.isEmpty()){
                    isVertex = false;
                }

                // when found list of triangles
                if(lineParts[0].contains("triangles_count")) {
                    isTriangles = true;
                } else if(line.isEmpty()) {
                    isTriangles = false;
                }


            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        loadCameraSettings("sceny/" + scene +".cam");
        loadScene(new Scene(triangles));

    }
}
