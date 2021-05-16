import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.Math;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.MouseInputListener;



public class HIV{
    HIVBoard b = new HIVBoard();


    public HIV() {

        /*Create and set up window frame*/
        JFrame frame = new JFrame("HIV Model in Java");
        JPanel panel = new JPanel();
        frame.setSize(800,800);
        frame.setLayout(new FlowLayout());
        frame.add(b.getPanel1());
        //frame.add(b.getPanel2());
        frame.add(b.getPanel3());
        frame.add(b.getPanel4());
        //frame.add(b.getPanel2());
        //frame.add(b.getPanel3());
        //frame.add(b.getPanel4());
        //frame.add(b.getPanel5());

        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set Listener for mouse actions
        frame.pack();

        /*Make frame visible, disable resizing*/
        frame.setVisible(true);
        frame.setResizable(false);

        /*Start the frames*/
        //stepFrame(true);

    }

    /*Step frame to move the app one step*/
    public void stepFrame(boolean running) {
        if(running) {
        }
        else {

        }
    }

    public void setupWindow(JPanel j) {
    }

    public void setupGlobals() {
    }

    public void setupPeople() {

    }

    /*Main function simply creates a new HIV instance*/
    public static void main(String[] args)
    {
        HIV c = new HIV();
    }

   
}
