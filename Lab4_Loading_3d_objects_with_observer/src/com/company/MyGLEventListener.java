package com.company;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class MyGLEventListener implements GLEventListener {

    private GLU glu;
    private final static double EYE_X = 5.0;
    private final static double EYE_Y = -2.0;
    private final static double EYE_Z = 10.0;
    private final static double CENTER_X = 0.0;
    private final static double CENTER_Y = 0.0;
    private final static double CENTER_Z = 0.0;
    private final static double UP_X = 0.0;
    private final static double UP_Y = 1.0;
    private final static double UP_Z = 0.0;
    private final static double FIELD_OF_VIEW_ANGLE = 30.0;
    private final static double Z_NEAR = 0.1;
    private final static double Z_FAR = 1000.0;


    public MyGLEventListener() {
        glu = new GLU();
    }


    @Override
    public void init(GLAutoDrawable autoDrawable) {
        GL2 gl2 = autoDrawable.getGL().getGL2();
        gl2.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void dispose(GLAutoDrawable autoDrawable) {
        GL2 gl2 = autoDrawable.getGL().getGL2();
    }

    @Override
    public void display(GLAutoDrawable autoDrawable) {
        GL2 gl2 = autoDrawable.getGL().getGL2();

        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);

        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();

        glu.gluLookAt(EYE_X, EYE_Y, EYE_Z,
                      CENTER_X, CENTER_Y, CENTER_Z,
                      UP_X, UP_Y, UP_Z);

        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(0.0f, 1.0f, 0.0f);
        gl2.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl2.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl2.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl2.glEnd();

        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(1.0f, 0.0f, 0.0f);
        gl2.glVertex3f(1.0f, 1.0f, -1.0f);
        gl2.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl2.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl2.glEnd();

        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(0.0f, 0.0f, 1.0f);
        gl2.glVertex3f( 1.0f,-1.0f, 1.0f);
        gl2.glVertex3f(  -1.0f, -1.0f,-1.0f);
        gl2.glVertex3f(1.0f,  -1.0f,-1.0f);
        gl2.glEnd();

        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(1.0f, 0.0f, 0.0f);
        gl2.glVertex3f(1.0f, 1.0f, -1.0f);
        gl2.glVertex3f(1.0f, -1.0f,-1.0f);
        gl2.glVertex3f(-1.0f,-1.0f,-1.0f);
        gl2.glEnd();

        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(0.0f, 1.0f, 0.0f);
        gl2.glVertex3f(-1.0f,-1.0f,-1.0f);
        gl2.glVertex3f(-1.0f,1.0f,1.0f);
        gl2.glVertex3f(-1.0f,1.0f,-1.0f);
        gl2.glEnd();

        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(0.0f, 0.0f, 1.0f);
        gl2.glVertex3f(1.0f,-1.0f,1.0f);
        gl2.glVertex3f(-1.0f,-1.0f,1.0f);
        gl2.glVertex3f(-1.0f,-1.0f,-1.0f);
        gl2.glEnd();

        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(1.0f, 0.0f, 0.0f);
        gl2.glVertex3f(-1.0f,1.0f,1.0f);
        gl2.glVertex3f(-1.0f,-1.0f,1.0f);
        gl2.glVertex3f(1.0f,-1.0f,1.0f);
        gl2.glEnd();


        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(0.0f, 0.0f, 1.0f);
        gl2.glVertex3f(1.0f,1.0f,1.0f);
        gl2.glVertex3f(1.0f,-1.0f,-1.0f);
        gl2.glVertex3f(1.0f,1.0f,-1.0f);
        gl2.glEnd();

        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(0.0f, 0.0f, 1.0f);
        gl2.glVertex3f(1.0f,-1.0f,-1.0f);
        gl2.glVertex3f(1.0f,1.0f,1.0f);
        gl2.glVertex3f(1.0f,-1.0f,1.0f);
        gl2.glEnd();

        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(0.0f, 1.0f, 0.0f);
        gl2.glVertex3f(1.0f,1.0f,1.0f);
        gl2.glVertex3f(1.0f,1.0f,-1.0f);
        gl2.glVertex3f(-1.0f,1.0f,-1.0f);
        gl2.glEnd();

        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(0.0f, 1.0f, 0.0f);
        gl2.glVertex3f(1.0f,1.0f,1.0f);
        gl2.glVertex3f(-1.0f,1.0f,-1.0f);
        gl2.glVertex3f(-1.0f,1.0f,1.0f);
        gl2.glEnd();

        gl2.glBegin(GL2.GL_TRIANGLES);
        gl2.glColor3f(1.0f, 0.0f, 1.0f);
        gl2.glVertex3f(1.0f,1.0f,1.0f);
        gl2.glVertex3f(-1.0f,1.0f,1.0f);
        gl2.glVertex3f(1.0f,-1.0f,1.0f);
        gl2.glEnd();

        gl2.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable autoDrawable, int x, int y, int width, int height) {
        GL2 gl2 = autoDrawable.getGL().getGL2();

        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();

        glu.gluPerspective(FIELD_OF_VIEW_ANGLE,
                (double) width / (double) height,
                Z_NEAR, Z_FAR);
    }
}
