package com.company;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import java.util.ArrayList;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

public class Scene implements GLEventListener {

    ArrayList<Triangle> triangles;
    private float rtri;  //for angle of rotation
    private GLU glu;
    static float width;
    static float height;
    Camera camera;
    static String name;

    public Scene(ArrayList<Triangle> triangles) {
        this.triangles = new ArrayList<>(triangles);
        glu = new GLU();
        camera = new Camera();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f );
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear (GL2.GL_COLOR_BUFFER_BIT |  GL2.GL_DEPTH_BUFFER_BIT );
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(camera.FIELD_OF_VIEW_ANGLE, (double)width / (double)height,
                camera.Z_NEAR, camera.Z_FAR);
        gl.glMatrixMode(GL_MODELVIEW);
//        gl.glMatrixMode(GL2.GL_MODELVIEW);
        // Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();  // Reset The View
        gl.glRotatef( camera.xAngle, 0.0f, 1.0f, 0.0f );
        gl.glRotatef( camera.yAngle, 1.0f, 0.0f, 0.0f );

        gl.glTranslatef(camera.EYE_X, camera.EYE_Y, camera.EYE_Z);
//        glu.gluLookAt(camera.EYE_X, camera.EYE_Y, camera.EYE_Z,
//                camera.CENTER_X, camera.CENTER_Y, camera.CENTER_Z,
//                camera.UP_X, camera.UP_Y, camera.UP_Z);

        gl.glBegin(GL2.GL_LINES);
        for(Triangle triangle : triangles) {
            // drawing the base
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(triangle.a.x, triangle.a.y, triangle.a.z);
            gl.glVertex3f(triangle.b.x, triangle.b.y, triangle.b.z);
            gl.glEnd();

            // the right edge
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(triangle.c.x, triangle.c.y, triangle.c.z);
            gl.glVertex3f(triangle.a.x, triangle.a.y, triangle.a.z);
            gl.glEnd();

            // the left edge
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(triangle.c.x, triangle.c.y, triangle.c.z);
            gl.glVertex3f(triangle.b.x, triangle.b.y, triangle.b.z);
            gl.glEnd();
        }
        gl.glFlush();
//        camera.EYE_X++;
//        camera.EYE_Y++;
//        camera.EYE_Z++;
//        System.out.println(camera.EYE_X);
        rtri +=0.2f;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        if( height <= 0 )
            height = 1;

        this.width = width;
        this.height = height;
//
//        final float h = ( float ) width / ( float ) height;
//        gl.glViewport( 0, 0, width, height );
//        gl.glMatrixMode( GL2.GL_PROJECTION );
//        glu.gluPerspective(camera.FIELD_OF_VIEW_ANGLE, (double)width / (double)height,
//                camera.Z_NEAR, camera.Z_FAR);
//        gl.glLoadIdentity();

//        glu.gluPerspective( 45.0f, h+100, -3.0, 50.0 );
//        gl.glMatrixMode( GL2.GL_MODELVIEW );
        gl.glLoadIdentity();

        System.out.println("reshape");

    }


}
