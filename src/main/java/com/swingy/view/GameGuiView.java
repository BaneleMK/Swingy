package com.swingy.view;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import java.awt.event.*;
import java.io.File;
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
    static JPanel jPanelload = new JPanel();
    static int heroes_saves = 0;

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
        // dialog size
        loadheroDialog.setSize(700, 500);
        loadheroDialog.getContentPane().add(heroeslist);

        // check if hero selected and load
        DefaultListModel<String> list = new DefaultListModel<String>();
        File file = new File("src/main/java/com/swingy/model/heroes/");
        String filenames[] = file.list();
        if (filenames.length != heroes_saves){
            heroes_saves = filenames.length;
            for(int i = 0; i < filenames.length ; i++){
                list.addElement(i+" - "+filenames[i]);
                // System.out.println(list.get(i));
            }
            heroeslist = new JList<>(list);
        } else if (filenames.length == 0) {
            System.out.println("HELLO NO HEROES IN THIS WORLD");
        } else {
            System.out.println("SELECT SOMETHING PLEASE");
        }

        submitButtonload.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    try {
                        if (heroeslist.getSelectedValue().equals("") || heroeslist.getSelectedValue().equals(null)){
                            loadlable.setText("[A QUEST WIHOUT A HERO? no...PLEASE CHOOSE A HERO]");
                            System.out.println("op = A");
                        } else {
                            loadheroDialog.setVisible(false);
                            System.out.println("op = B "+heroeslist.getSelectedValue()+" - "+null);
                        }
                        System.out.println("op = C");    
                    } catch(NullPointerException exception){
                        System.out.println("op = A");
                        loadlable.setText("[A QUEST WIHOUT A HERO? no...PLEASE CHOOSE A HERO]");
                    } catch (Exception exception) {
                        System.out.println("op = A");
                        System.out.println("big boo boo");
                    }
                    
            }
        });
        loadheroDialog.add(submitButtonload);
        loadheroDialog.add(heroeslist);
        loadheroDialog.add(loadlable);
        loadheroDialog.setLayout(new FlowLayout());
    }

    static public void makewindow(){
        heroclassDialog.setVisible(false);
        newherodDialog.setVisible(false);

        JMenuBar gameoptions = new JMenuBar();
        JMenu game = new JMenu("game");
        JMenuItem newgame,loadgame;

        newgame = new JMenuItem("New Game");
        loadgame = new JMenuItem("Load Game");

        newgame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                newherodDialog.setVisible(true);
                newhero();
            }
        });

        loadgame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                loadheroDialog.setVisible(true);
                loadhero();
            }
        });

        game.add(newgame);
        game.add(loadgame);
        gameoptions.add(game);
        frame.setJMenuBar(gameoptions);

        // setting close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
        // sets 800 width and 800 height 
        frame.setSize(800, 800);
          
        // uses no layout managers 
        frame.setLayout(null);
          
        // makes the frame visible 
        frame.setVisible(true);

        JTextArea guiconsole = new JTextArea();
        guiconsole.setBounds(10, 10, 300, 700);
        guiconsole.setEditable(false);
        guiconsole.setBackground(Color.GREEN);
        frame.add(guiconsole);

        //--------------------LOAD HERO------------------//


    };
    
}