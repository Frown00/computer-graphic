package com.company;

import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Camera implements KeyListener {
    static float EYE_X = -0.5f;
    static float EYE_Y = -0.5f;
    static float EYE_Z = -0.5f;
    double CENTER_X = 0.0;
    double CENTER_Y = 0.0;
    double CENTER_Z = 0.0;
    double UP_X = 0.0;
    double UP_Y = 0.0;
    double UP_Z = 0.0;
    double FIELD_OF_VIEW_ANGLE = 90.0;
    double Z_NEAR = 1.0;
    double Z_FAR = 100.0;

    static float xAngle = 0;
    static float yAngle = 0;

    // Set of currently pressed keys
    private final Set<Integer> pressed = new HashSet<>();

    public Camera()
    {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public synchronized void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        pressed.add(k);

        if (pressed.size() > 0) {
            for(Integer key : pressed) {
                if (key == KeyEvent.VK_D) {
//            dx = -1
                    EYE_X -= 0.05;
                }

                if (key == KeyEvent.VK_A) {
//            dx = 1;
                    EYE_X += 0.05;
                }

                if (key == KeyEvent.VK_UP) {
//            dy = -1;
                    EYE_Y += 0.05;
                }

                if (key == KeyEvent.VK_DOWN) {
//            dy = 1;
                    EYE_Y -= 0.05;
                }

                if (key == KeyEvent.VK_W) {
                    EYE_Z += 0.05;
                }

                if (key == KeyEvent.VK_S) {
                    EYE_Z -= 0.05;
                }

                if (key == KeyEvent.VK_F1) {
                    saveSettings();
                }
            }
            // More than one key is currently pressed.
            // Iterate over pressed to get the keys.
        }

    }

    private void saveSettings() {
        BufferedWriter bufferedWriter = null;
        try {
            String strContent = Float.toString(EYE_X) + " " +
                                Float.toString(EYE_Y) + " " +
                                Float.toString(EYE_Z);
            File myFile = new File("sceny/" + Scene.name +".cam");
            // check if file exist, otherwise create the file before writing
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            Writer writer = new FileWriter(myFile);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(strContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(bufferedWriter != null) bufferedWriter.close();
                System.out.println("Camera settings saved");
            } catch(Exception ex){

            }
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }
}
