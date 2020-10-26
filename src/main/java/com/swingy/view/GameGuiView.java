package com.swingy.view;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import java.io.File;
import java.awt.*;

import javax.swing.*;
import com.swingy.model.*;


public class GameGuiView implements Game{

    private boolean hero_present = false;
    private JFrame frame = new JFrame("Swingy"); 

    private JButton btnewhero = new JButton("New Hero");
    private JButton bloadhero = new JButton("Load Hero");
    // new hero name diobox
    // new hero class diobox

    private JDialog heroclassDialog = new JDialog(frame, "New Hero");
    private JLabel classlable = new JLabel("");        
    private JButton submitButtonclass= new JButton("submit");

    // load a hero 
    private JDialog loadheroDialog = new JDialog(frame, "Load Hero");
    private JLabel loadlable = new JLabel("");        
    private JButton submitButtonload= new JButton("submit");
    private JList<String> heroeslist = new JList<String>();
    private JPanel jPanelload = new JPanel();
    private int heroes_saves = 0;

    // hero name and class
    private PlaceholderTextField nametextfield = new PlaceholderTextField("enter hero name" ,20);
    private PlaceholderTextField classtextfield = new PlaceholderTextField("enter hero class" ,20);
    
    JMenuBar gameoptions = new JMenuBar();
    JMenu game = new JMenu("game");
    JMenuItem newgame,loadgame, savegame;
    JDialog newherodDialog = new JDialog(frame, "New Hero");
    JDialog gamesaved = new JDialog(frame, "game save");
    JLabel message = new JLabel("Thy Game Saved!");
    JButton dismiss = new JButton("OK");

    JTextArea guiconsole = new JTextArea();
    JScrollPane guiscroll = new JScrollPane(guiconsole); 
    JButton moveButtonN = new JButton("Move North");
    JButton moveButtonS = new JButton("Move South");
    JButton moveButtonW = new JButton("Move West");
    JButton moveButtonE = new JButton("Move East");
    JButton HeroButton = new JButton("Hero Stats");
    JButton clearButton = new JButton("Clear");
    JButton mapButton = new JButton("Map");
    JButton fightButton = new JButton("Fight");
    JButton runButton = new JButton("Run");
    JButton compareButton = new JButton("Compare");
    JButton useButton = new JButton("Use");
    JButton leaveButton = new JButton("Leave");
    JButton vilButton = new JButton("Villain stats");
    JLabel namelable = new JLabel("");        
    JButton submitButtonname= new JButton("submit");
    String filenames[];
    
    

    public GameGuiView(){
        makewindow();
    }

    public void consolelog(String message){
        guiconsole.append(message+"\n---------------------------\n");
    }

    public void consolelogmap(String message){
        guiconsole.append(message);
    }

    public void rendermap(GameCharacter[][][] map, int mapsize){
        for(int y = 0; y<mapsize; y++){
            for(int x = 0; x<mapsize; x++){
                // check if a villain randomly spawns and if the max amount of mobs is not reached for the player level.
                if (map[y][x][0] == null && map[y][x][1] == null){
                    consolelogmap("[ ] ");
                } else if (map[y][x][0] != null && map[y][x][1] != null){
                    consolelogmap("[F] ");
                } else if (map[y][x][0] != null) {
                    consolelogmap("[V] ");
                } else if (map[y][x][1] != null) {
                    consolelogmap("[H] ");
                } else {
                    consolelogmap("[#] ");
                }
            }
            consolelogmap("\n");
        }   
    }

    public void hero_weapon(Hero hero){
        if (hero.get_weapon() != null)
            consolelog("Weapon: LV "+ hero.get_weapon().get_level()+" "+hero.get_weapon().get_name()+" + "+hero.get_weapon().get_attack()+" ATK");
        else 
            consolelog("Weapon: N/A");
    }

    public void hero_armor(Hero hero){
        if (hero.get_armor() != null)
            consolelog("Armor: LV "+ hero.get_armor().get_level()+" "+hero.get_armor().get_name()+" + "+hero.get_armor().get_defense()+" DEF");
        else
            consolelog("Armor: N/A");
    }

    public void hero_helm(Hero hero){
        if (hero.get_helm() != null)
            consolelog("Helm: LV "+ hero.get_helm().get_level()+" "+hero.get_helm().get_name()+" + "+hero.get_helm().get_hitpoints()+" HP" );
        else
            consolelog("Helm: N/A");
    }
    public void hero_stats(Hero hero){
        if (hero != null){
            consolelog("Name: "+hero.get_name()
            +"\nClass: "+hero.get_class()
            +"\nLevel: "+hero.get_level()
            +"\nXP: "+hero.get_experience()+" / "+hero.get_xp_to_next_lv()
            +"\nHitpoints: "+hero.get_hitpoints()
            +"\nAttack: "+hero.get_attack()
            +"\ndefense: "+hero.get_defense());

            hero_weapon(hero);
            hero_armor(hero);
            hero_helm(hero);
        } else {
            consolelog("Info not available");
        }
    }

    public void villain_stats(Villain villain){
        if (villain != null){
            consolelog("Name: "+villain.get_name()
            +"\nClass: "+villain.get_class()
            +"\nLevel: "+villain.get_level()
            +"\nHitpoints: "+villain.get_hitpoints()
            +"\nAttack: "+villain.get_attack()
            +"\ndefense: "+villain.get_defense());
        } else {
            consolelog("Info not available");
        }
    }
    

    public void newheroname(){
        // dialogue box layout
        newherodDialog.setLayout(new FlowLayout());
        // set invisible in the beginning
        newherodDialog.setSize(480, 150);
        newherodDialog.add(namelable);
        
        // dialog box button for adding name
        
        // adds button and textspace to dialog box
        newherodDialog.add(nametextfield);
        newherodDialog.add(submitButtonname).requestFocusInWindow();
        
        // conform name sends info to makehero
        //nametextfield.setBounds(120, 40, 160, 20);
        
        // resets the placeholdername
        nametextfield.setText(nametextfield.Placeholder);
    }

    public void newheroclass(){
        // dialogue box layout
        heroclassDialog.setLayout(new FlowLayout());
        // set invisible in the beginning
        
        // dialog size
        heroclassDialog.setSize(480, 150);
        heroclassDialog.add(classlable);
        // dialog box button for adding name
        
        // adds button and textspace to dialog box
        heroclassDialog.add(classtextfield);
        heroclassDialog.add(submitButtonclass).requestFocusInWindow();
        
        // resets the placeholdername
        classtextfield.setText(classtextfield.Placeholder);
    }

    public void loadhero(){
        // dialogue box (load hero)
        // dialog size
        loadheroDialog.setSize(700, 500);
        loadheroDialog.getContentPane().add(heroeslist);

        // check if hero selected and load
        DefaultListModel<String> list = new DefaultListModel<String>();
        File file = new File("src/main/java/com/swingy/model/heroes/");
        filenames = file.list();
        if (filenames.length != heroes_saves){
            loadheroDialog.add(submitButtonload);
            heroes_saves = filenames.length;
            for(int i = 0; i < filenames.length ; i++){
                list.addElement(filenames[i]);
                // System.out.println(list.get(i));
            }
            heroeslist = new JList<>(list);
            loadheroDialog.add(heroeslist);
        } else if (filenames.length == 0) {
            loadlable.setText("HELLO NO SAVED HEROES IN THIS WORLD");
        } else {
            loadlable.setText("SELECT SOMETHING PLEASE");
        }

        loadheroDialog.add(loadlable);
        // loadheroDialog.add(loadlable);
        loadheroDialog.setLayout(new FlowLayout());
    }

    public void makewindow(){
        newgame = new JMenuItem("New Game");
        loadgame = new JMenuItem("Load Game");
        savegame = new JMenuItem("Save Game");

        game.add(newgame);
        game.add(loadgame);
        game.add(savegame);
        gameoptions.add(game);
        frame.setJMenuBar(gameoptions);

        // setting close operation

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
        // sets 800 width and 800 height 
        frame.setSize(800, 800);
          
        // uses no layout managers 
        frame.setLayout(null);
          
        // makes the frame visible 
        frame.setVisible(true);

        //----------------TEXT AREAS----------------//
        
        guiconsole.setBounds(10, 170, 600, 500);
        guiscroll.setBounds(10, 170, 770, 550);
        guiscroll.setVisible(true);
        guiconsole.setEditable(false);
        guiconsole.setBackground(Color.magenta);
        guiconsole.setVisible(true);
        frame.add(guiscroll);

        // -------------- MOVEMENT BUTTONS ------------------ //
        
        
        frame.add(moveButtonN);
        moveButtonN.setBounds(320, 10, 100, 20);

        
        frame.add(moveButtonS);
        moveButtonS.setBounds(430, 10, 100, 20);

        
        frame.add(moveButtonW);
        moveButtonW.setBounds(320, 40, 100, 20);

        
        frame.add(moveButtonE);
        moveButtonE.setBounds(430, 40, 100, 20);

        // -------------- STAT BUTTONS ---------------------- //

        
        frame.add(HeroButton);
        HeroButton.setBounds(320, 70, 100, 20);

        frame.add(vilButton);
        vilButton.setBounds(430, 70, 100, 20);

        frame.add(clearButton);
        clearButton.setBounds(540, 70, 100, 20);

        frame.add(mapButton);
        mapButton.setBounds(650, 70, 100, 20);

        // -------------- FIGHT/CONFLICt BUTTONS ------------------------//

        
        frame.add(fightButton);
        fightButton.setBounds(320, 100, 100, 20);

        
        frame.add(runButton);
        runButton.setBounds(430, 100, 100, 20);

        // -------------------- ITEM BUTTON -----------------------------//
        
        frame.add(compareButton);
        compareButton.setBounds(320, 140, 100, 20);
        
        
        frame.add(useButton);
        useButton.setBounds(430, 140, 100, 20);

        
        frame.add(leaveButton);
        leaveButton.setBounds(540, 140, 100, 20);
    };

    /**
     * @return the bloadhero
     */
    public JButton getBloadhero() {
        return bloadhero;
    }
    /**
     * @return the btnewhero
     */
    public JButton getBtnewhero() {
        return btnewhero;
    }/**
     * @return the classlable
     */
    public JLabel getClasslable() {
        return classlable;
    }/**
     * @return the classtextfield
     */
    public JTextField getClasstextfield() {
        return classtextfield;
    }/**
     * @return the clearButton
     */
    public JButton getClearButton() {
        return clearButton;
    }/**
     * @return the compareButton
     */
    public JButton getCompareButton() {
        return compareButton;
    }/**
     * @return the dismiss
     */
    public JButton getDismiss() {
        return dismiss;
    }/**
     * @return the fightButton
     */
    public JButton getFightButton() {
        return fightButton;
    }/**
     * @return the frame
     */
    public JFrame getFrame() {
        return frame;
    }/**
     * @return the game
     */
    public JMenu getGame() {
        return game;
    }/**
     * @return the gameoptions
     */
    public JMenuBar getGameoptions() {
        return gameoptions;
    }/**
     * @return the gamesaved
     */
    public JDialog getGamesaved() {
        return gamesaved;
    }/**
     * @return the guiconsole
     */
    public JTextArea getGuiconsole() {
        return guiconsole;
    }/**
     * @return the heroButton
     */
    public JButton getHeroButton() {
        return HeroButton;
    }/**
     * @return the heroclassDialog
     */
    public JDialog getHeroclassDialog() {
        return heroclassDialog;
    }/**
     * @return the heroes_saves
     */
    public int getHeroes_saves() {
        return heroes_saves;
    }/**
     * @return the heroeslist
     */
    public JList<String> getHeroeslist() {
        return heroeslist;
    }/**
     * @return the leaveButton
     */
    public JButton getLeaveButton() {
        return leaveButton;
    }/**
     * @return the loadgame
     */
    public JMenuItem getLoadgame() {
        return loadgame;
    }/**
     * @return the loadheroDialog
     */
    public JDialog getLoadheroDialog() {
        return loadheroDialog;
    }/**
     * @return the loadlable
     */
    public JLabel getLoadlable() {
        return loadlable;
    }/**
     * @return the mapButton
     */
    public JButton getMapButton() {
        return mapButton;
    }/**
     * @return the message
     */
    public JLabel getMessage() {
        return message;
    }/**
     * @return the moveButtonE
     */
    public JButton getMoveButtonE() {
        return moveButtonE;
    }/**
     * @return the moveButtonN
     */
    public JButton getMoveButtonN() {
        return moveButtonN;
    }/**
     * @return the moveButtonS
     */
    public JButton getMoveButtonS() {
        return moveButtonS;
    }/**
     * @return the moveButtonW
     */
    public JButton getMoveButtonW() {
        return moveButtonW;
    }/**
     * @return the nametextfield
     */
    public JTextField getNametextfield() {
        return nametextfield;
    }/**
     * @return the newgame
     */
    public JMenuItem getNewgame() {
        return newgame;
    }/**
     * @return the newherodDialog
     */
    public JDialog getNewherodDialog() {
        return newherodDialog;
    }/**
     * @return the runButton
     */
    public JButton getRunButton() {
        return runButton;
    }/**
     * @return the savegame
     */
    public JMenuItem getSavegame() {
        return savegame;
    }/**
     * @return the submitButtonclass
     */
    public JButton getSubmitButtonclass() {
        return submitButtonclass;
    }/**
     * @return the submitButtonload
     */
    public JButton getSubmitButtonload() {
        return submitButtonload;
    }/**
     * @return the useButton
     */
    public JButton getUseButton() {
        return useButton;
    }/**
     * @return the jPanelload
     */
    public JPanel getjPanelload() {
        return jPanelload;
    }/**
     * @return the namelable
     */
    public JLabel getNamelable() {
        return namelable;
    }/**
     * @return the submitButtonname
     */
    public JButton getSubmitButtonname() {
        return submitButtonname;
    }/**
     * @return the filenames
     */
    public String[] getFilenames() {
        return filenames;
    }/**
     * @return the vilButton
     */
    public JButton getVilButton() {
        return vilButton;
    }/**
     * @return the guiscroll
     */
    public JScrollPane getGuiscroll() {
        return guiscroll;
    }
    /**
    * @return the hero_present
    */
   public boolean getHero_present() {
       return hero_present;
   }
   /**
    * @param hero_present the hero_present to set
    */
   public void setHero_present(boolean hero_present) {
       this.hero_present = hero_present;
   }

    
}