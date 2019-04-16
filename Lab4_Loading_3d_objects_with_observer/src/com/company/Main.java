package com.company;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        OpenGL program = new OpenGL();
        program.GUI();
//        Triangle triangle = new Triangle(
//            new Vertex(-0.5f, -0.5f,0),
//            new Vertex(0.5f, -0.5f, 0),
//            new Vertex(0f, 0.5f, 0));
        //program.loadScene(triangles);
        boolean isRunning = true;
        while(isRunning){
            Scanner keyboard = new Scanner(System.in);
            System.out.println("enter a scene");
            String scene = keyboard.nextLine();
            if(scene.contains("exit")){
                isRunning = false;
            }
            program.loadScene(scene);
        }


    }


}
