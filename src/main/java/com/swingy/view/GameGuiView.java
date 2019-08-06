package com.swingy.view;

import javax.swing.*;

public class GameGuiView{
    static private JFrame frame = new JFrame("Swingy");

    public GameGuiView(){

    }

    static public void makewindow(){
        JButton button1 = new JButton("New Hero");
        JButton button2 = new JButton("Load Hero");

        button1.setBounds(10, 10, 100, 20);
        button2.setBounds(10, 40, 100, 20);

        // setting close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // adds button in JFrame 
        frame.add(button1);
        frame.add(button2);
  
        // sets 500 width and 600 height 
        frame.setSize(500, 600);
          
        // uses no layout managers 
        frame.setLayout(null);
          
        // makes the frame visible 
        frame.setVisible(true);
    };
    
}