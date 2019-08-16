package com.swingy.view;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import java.awt.event.*;
import java.io.File;
import java.awt.*;

import javax.swing.*;

import com.swingy.controller.GameEngine;

public class GameGuiView implements Game{
    static private JFrame frame = new JFrame("Swingy");

    static GameEngine controller;

    static JButton btnewhero = new JButton("New Hero");
    static JButton bloadhero = new JButton("Load Hero");
    // new hero name diobox

    
    // new hero class diobox

    static JDialog heroclassDialog = new JDialog(frame, "New Hero");
    static JLabel classlable = new JLabel("");        
    static JButton submitButtonclass= new JButton("submit");

    // load a hero 
    static JDialog loadheroDialog = new JDialog(frame, "Load Hero");
    static JLabel loadlable = new JLabel("");        
    static JButton submitButtonload= new JButton("submit");
    static JList<String> heroeslist = new JList<String>();
    static JPanel jPanelload = new JPanel();
    static int heroes_saves = 0;

    // hero name and class
    static JTextField nametextfield = new JTextField("enter hero name" ,20);
    static JTextField classtextfield = new JTextField("enter hero class" ,20);
    


    public GameGuiView(GameEngine c){
        controller = c;
    }

    public void consolelog(String message){
        // add some text to some elements
    }

    public void newhero(JDialog newherodDialog){
        // ----------------NEW HERO------------------------ //
        newheroname(newherodDialog);
        newheroclass();
    }

    static public void newheroname(JDialog newherodDialog){
        JLabel namelable = new JLabel("");        
        JButton submitButtonname= new JButton("submit");
        // dialoge box (new hero)
        // dialoge box layout
        newherodDialog.setLayout(new FlowLayout());
        // set invisible in the beginning
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

    public void newheroclass(){
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
                    controller.makeHerogui(this);
                }
            }
        });
        // adds button and textspace to dialog box
        heroclassDialog.add(classtextfield);
        heroclassDialog.add(submitButtonclass);
    }

    public void loadhero(){
        // dialoge box (load hero)
        // dialog size
        loadheroDialog.setSize(700, 500);
        loadheroDialog.getContentPane().add(heroeslist);

        // check if hero selected and load
        DefaultListModel<String> list = new DefaultListModel<String>();
        File file = new File("src/main/java/com/swingy/model/heroes/");
        String filenames[] = file.list();
        if (filenames.length != heroes_saves){
            loadheroDialog.add(GameGuiView.submitButtonload);
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
        loadheroDialog.add(heroeslist);
        loadheroDialog.add(loadlable);
        loadheroDialog.setLayout(new FlowLayout());
    }

    public void makewindow(){
        heroclassDialog.setVisible(false);
        

        JMenuBar gameoptions = new JMenuBar();
        JMenu game = new JMenu("game");
        JMenuItem newgame,loadgame, savegame;

        /*

            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(
                (int) ((dimension.getWidth() - frame.getWidth())/2),
                (int) ((dimension.getHeight() - frame.getHeight())/2));

        */
        newgame = new JMenuItem("New Game");
        loadgame = new JMenuItem("Load Game");
        savegame = new JMenuItem("Save Game");

        newgame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JDialog newherodDialog = new JDialog(frame, "New Hero");
                newherodDialog.setVisible(true);
                newhero(newherodDialog);
            }
        });

        loadgame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                loadheroDialog.setVisible(true);
                loadhero();
            }
        });

        savegame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JDialog gamesaved = new JDialog(frame, "game save");
                gamesaved.setVisible(true);
                gamesaved.setLayout(new FlowLayout());
                gamesaved.setSize(380, 100);

                JLabel message = new JLabel("Thy Game Saved!");
                JButton dismiss = new JButton("OK");
                
                dismiss.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        gamesaved.setVisible(false);
                    }
                });
                gamesaved.add(message);
                gamesaved.add(dismiss);
            }
        });

        game.add(newgame);
        game.add(loadgame);
        game.add(savegame);
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

        //----------------TEXT AREAS----------------//
        JTextArea guiconsole = new JTextArea();
        guiconsole.setBounds(10, 10, 300, 700);
        guiconsole.setEditable(false);
        guiconsole.setBackground(Color.GREEN);
        guiconsole.setVisible(true);
        frame.add(guiconsole);

        /*
        
        // hopefully wont need a second text area

        JTextArea guiconsole = new JTextArea();
        guiconsole.setBounds(10, 10, 300, 700);
        guiconsole.setEditable(false);
        guiconsole.setBackground(Color.GREEN);
        guiconsole.setVisible(true);
        frame.add(guiconsole);*/


        // -------------- MOVEMENT BUTTONS ------------------ //
        
        JButton moveButtonN = new JButton("Move North");
        frame.add(moveButtonN);
        moveButtonN.setBounds(320, 10, 100, 20);

        JButton moveButtonS = new JButton("Move South");
        frame.add(moveButtonS);
        moveButtonS.setBounds(430, 10, 100, 20);

        JButton moveButtonW = new JButton("Move West");
        frame.add(moveButtonW);
        moveButtonW.setBounds(320, 40, 100, 20);

        JButton moveButtonE = new JButton("Move East");
        frame.add(moveButtonE);
        moveButtonE.setBounds(430, 40, 100, 20);

        // -------------- STAT BUTTONS ---------------------- //

        JButton HeroButton = new JButton("Hero Stats");
        frame.add(HeroButton);
        HeroButton.setBounds(320, 70, 100, 20);

        JButton clearButton = new JButton("Clear console");
        frame.add(clearButton);
        clearButton.setBounds(430, 70, 100, 20);

        JButton mapButton = new JButton("Map");
        frame.add(mapButton);
        mapButton.setBounds(540, 70, 100, 20);

        // -------------- FIGHT/CONFLICt BUTTONS ------------------------//


        JButton fightButton = new JButton("Fight");
        frame.add(fightButton);
        fightButton.setBounds(320, 100, 100, 20);

        JButton runButton = new JButton("Run");
        frame.add(runButton);
        runButton.setBounds(430, 100, 100, 20);

        // -------------------- ITEM BUTTON -----------------------------//
        JButton compareButton = new JButton("Compare");
        frame.add(compareButton);
        compareButton.setBounds(320, 140, 100, 20);
        
        JButton useButton = new JButton("Use");
        frame.add(useButton);
        useButton.setBounds(430, 140, 100, 20);

        JButton leaveButton = new JButton("Leave");
        frame.add(leaveButton);
        leaveButton.setBounds(540, 140, 100, 20);
    };
    
}