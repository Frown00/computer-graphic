package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class AngleListener implements MouseMotionListener {

    final int SLOWER_FACTOR = 100;
    Point mousePt;
    public AngleListener() {}

    @Override
    public void mouseDragged(MouseEvent e) {

        int dx = e.getX();
        int dy = e.getY();

        mousePt = e.getPoint();

        System.out.println("dx: " + dx);
        System.out.println("dy: " + dy);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        float dx = e.getX() / Scene.width;
        float dy = e.getY() / Scene.height;

        Camera.xAngle = dx * 360;
        Camera.yAngle = dy * 360;

        if(dx < 0.5) {
            //Camera.xAngle = (Camera.xAngle - dx * 100) % 360;
            //System.out.println(Camera.xAngle - 1.0f);
        } else {
            //Camera.xAngle = dx * 180;
        }

        if(dy < 0.5) {
//            Camera.yAngle = (Camera.yAngle - dy * 100) % 360;
        } else {
            //Camera.xAngle = dy * 180;
//            Camera.yAngle = (Camera.yAngle + dy * 100) % 360;
        }
    }
}
