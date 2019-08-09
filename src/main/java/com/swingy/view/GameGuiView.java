package com.swingy.view;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.awt.*;

import javax.swing.*;

public class GameGuiView implements View{
    static private JFrame frame = new JFrame("Swingy");

    static JButton btnewhero = new JButton("New Hero");
    static JButton bloadhero = new JButton("Load Hero");
    // new hero name diobox

    static JDialog newherodDialog = new JDialog(frame, "New Hero");
    static JLabel namelable = new JLabel("");        
    static JButton submitButtonname= new JButton("submit");
    static JTextField nametextfield = new JTextField("enter hero name" ,20);
    // new hero class diobox

    static JDialog heroclassDialog = new JDialog(frame, "New Hero");
    static JLabel classlable = new JLabel("");        
    static JButton submitButtonclass= new JButton("submit");
    static JTextField classtextfield = new JTextField("enter hero class" ,20);

    // load a hero 
    static JDialog loadheroDialog = new JDialog(frame, "Load Hero");
    static JLabel loadlable = new JLabel("");        
    static JButton submitButtonload= new JButton("submit");
    static JList<String> heroeslist = new JList<String>();

    public GameGuiView(){

    }

    public void consolelog(String message){
        // add some text to some elements
    }

    static public void newhero(){
        // ----------------NEW HERO------------------------ //
        newheroname();
        newheroclass();
    }

    static public void newheroname(){
        // dialoge box (new hero)
        // dialoge box layout
        newherodDialog.setLayout(new FlowLayout());
        // set invisible in the beginning
        // dialog size
        newherodDialog.setSize(480, 150);
        newherodDialog.add(namelable);
        // dialog box button for adding name
        submitButtonname.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (nametextfield.getText().contains(" ") || nametextfield.getText().equals("")){
                    namelable.setText("[HERO NAMES DON'T HAVE SPACES INSIDE AND ARE NOT BLANK]");
                } else {
                    newherodDialog.setVisible(false);
                    heroclassDialog.setVisible(true);
                }
            }
        });
        // adds button and textspace to dialog box
        newherodDialog.add(nametextfield);
        newherodDialog.add(submitButtonname);
        
        //conform name sends info to makehero
        nametextfield.setBounds(120, 40, 160, 20);
    }

    static public void newheroclass(){
        // dialoge box layout
        heroclassDialog.setLayout(new FlowLayout());
        // set invisible in the beginning
        // dialog size
        heroclassDialog.setSize(480, 150);
        heroclassDialog.add(classlable);
        // dialog box button for adding name
        submitButtonclass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (classtextfield.getText().contains(" ") || classtextfield.getText().equals("")){
                    classlable.setText("[HERO CLASSES DON'T HAVE SPACES INSIDE AND ARE NOT BLANK]");
                } else {
                    heroclassDialog.setVisible(false);
                }
            }
        });
        // adds button and textspace to dialog box
        heroclassDialog.add(classtextfield);
        heroclassDialog.add(submitButtonclass);
    }

    static public void loadhero(){
        // dialoge box (load hero)
        loadheroDialog.setLayout(new FlowLayout());
        // dialog size
        loadheroDialog.setSize(480, 150);
        submitButtonload.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // check if hero selected and load
                ArrayList<String> list = new ArrayList<String>();
                File file = new File("src/main/java/com/swingy/model/heroes/");
                String filenames[] = file.list();
                if (filenames.length != 0){
                    for(int i = 0; i < filenames.length ; i++){
                        list.add(i+" - "+filenames[i]);
                    }
                    String[] heroes;
                    for (int i = 0; i < list.size ; i++){
                        heroes[i] = (String)list.get(i);
                    }
                    heroeslist = new JList(heroes);
                    if (heroeslist.getSelectedValue().equals("")){
                        classlable.setText("[A QUEST WIHOUT A HERO? no...PLEASE CHOOSE A HERO]");
                    } else {
                        heroclassDialog.setVisible(false);
                    }
                } else {
                    // HAVE A MAIN LABEL THAT POPS UP AND SAYS NO HEROES SAVED
                }
            }
        });
        loadheroDialog.add(submitButtonload);
        loadheroDialog.add(heroeslist);
    }

    static public void makewindow(){
        
        
        heroclassDialog.setVisible(false);
        newherodDialog.setVisible(false);

        //button for new hero
        btnewhero.setBounds(10, 10, 100, 20);
        bloadhero.setBounds(10, 40, 100, 20);        
        //button pops up dialog box for hero name
        btnewhero.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                newherodDialog.setVisible(true);
                newhero();
            }
        });

        bloadhero.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                loadheroDialog.setVisible(true);
                loadhero();
            }
        });
        //adds button to frame
        frame.add(btnewhero);
        frame.add(bloadhero);

        // setting close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
        // sets 500 width and 600 height 
        frame.setSize(300, 200);
          
        // uses no layout managers 
        frame.setLayout(null);
          
        // makes the frame visible 
        frame.setVisible(true);

        //--------------------LOAD HERO------------------//


    };
    
}