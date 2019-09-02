package com.swingy.controller;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.swingy.SaveHero;
import com.swingy.model.Armor;
import com.swingy.model.Helm;
import com.swingy.model.Hero;
import com.swingy.model.LootTable;
import com.swingy.model.Map;
import com.swingy.model.Validatorclass;
import com.swingy.model.Villain;
import com.swingy.model.Weapon;
import com.swingy.view.Game;
import com.swingy.view.GameGuiView;
import com.swingy.view.GameView;

import javax.management.RuntimeErrorException;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * GameEngine
 */
public class GameEngine{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    static boolean conflict = false;
    static boolean artifact = false;
    static int fight = 2;
    Villain villain = null;
    Hero hero = null;

    static Scanner gameinput = new Scanner(System.in);
    static boolean game = true;

    Map map;
    GameGuiView gameGuiView = null;

    private  void updatemapview(Game gameview, Map map){
        gameview.rendermap(map.getMap(), map.get_mapsize());
    }

    private  int takedamage(int hp, int defense, int damage){
        if (damage > defense){
            return hp + (defense - damage);
        }
        return hp;
    }

    private  int startfight(Hero hero, Villain villain) {
        int herohp = hero.get_hitpoints();
        int villainhp = villain.get_hitpoints();
        
        while(true){
            villainhp = takedamage(villainhp, villain.get_defense(), hero.get_attack());
            if(villainhp <= 0)
                break;
            herohp = takedamage(herohp, hero.get_defense(), villain.get_attack());
            if(herohp <=0 )
                break;
        }
        hero.set_lastfighthp(herohp);
        if(villainhp <= 0){
            return 1;
        } else {
            return 0;
        }
    }


    public void movehero(String direction, Game GameView){

        switch (direction.toUpperCase()) {
            case "NORTH":
                GameView.consolelog("You move NORTH.");
                map.newherolocation(map.get_Hero_ylocation() - 1, map.get_Hero_xlocation(),  GameView);
                break;
            case "SOUTH":
                GameView.consolelog("You move SOUTH.");
                map.newherolocation(map.get_Hero_ylocation() + 1, map.get_Hero_xlocation(),  GameView);
                break;
            case "WEST":
                GameView.consolelog("You move WEST.");
                map.newherolocation(map.get_Hero_ylocation(), map.get_Hero_xlocation() - 1,  GameView);
                break;
            case "EAST":
                GameView.consolelog("You move EAST.");
                map.newherolocation(map.get_Hero_ylocation(), map.get_Hero_xlocation() + 1,  GameView);
                break;
            default:
                GameView.consolelog("Invalid direction.");
                break;
        }
        
    }

    private void fight(Game gameview){
        gameview.consolelog("you decide fight the villain");
        fight = startfight(hero, villain);
        conflict = false;
    }

    private void artifactdrop(Game gameview){
        
        while (artifact){
            gameview.consolelog(villain.get_name()+
            " has dropped a level "+ANSI_GREEN+villain.give_artifact().get_level()+ANSI_RESET+" "+villain.give_artifact().get_type()+" "+villain.give_artifact().get_name()+
            " with the stats "+ANSI_GREEN+villain.give_artifact().get_stats()+ANSI_RESET+
            "\n"+ANSI_PURPLE+"Available commands: to USE, COMPARE or LEAVE"+ANSI_RESET);
            switch(gameinput.nextLine().toUpperCase()){
                case "USE":
                    switch(villain.give_artifact().get_type()){
                        case "Weapon":
                            hero.set_weapon((Weapon)villain.give_artifact());
                            break;
                        case "Armor":
                            hero.set_armor((Armor)villain.give_artifact());
                            break;
                        case "Helm":
                            hero.set_helm((Helm)villain.give_artifact());
                            break;
                    }
                    artifact = false;
                    break;
                case "COMPARE":
                    gameview.consolelog("YOU HAVE:");
                    switch(villain.give_artifact().get_type()){
                        case "Weapon":
                            gameview.hero_weapon(hero);
                            break;
                        case "Armor":
                            gameview.hero_armor(hero);
                            break;
                        case "Helm":
                            gameview.hero_helm(hero);
                            break;
                    }
                    break;
                case "LEAVE":
                    artifact = false;
                    break;
            }
        }
    }

    private void checkartifact(){
        if (villain.give_artifact() != null){
            Random rand = new Random();
            if (rand.nextBoolean() == true){
                artifact = true;
            }
        }
    }

    private void fightwon(Game gameview){
        int xpgained = villain.get_level() * 200;
        map.killvillain(gameview);
        villain = null;
        if (gameGuiView == null)
            gameview.consolelog(ANSI_YELLOW+"You won the FIGHT with "+ANSI_GREEN+hero.get_lastfighthp()+" / "+hero.get_hitpoints()+ANSI_YELLOW+" hp remaining! you have gained "+ANSI_GREEN+xpgained+ANSI_YELLOW+" XP"+ANSI_RESET);
        else 
            gameview.consolelog("You won the FIGHT with "+hero.get_lastfighthp()+" / "+hero.get_hitpoints()+" hp remaining! you have gained "+xpgained+" XP");

        if (hero.getxp(xpgained) == true){
            gameview.consolelog("YOU LEVELED UP TO LV "+hero.get_level()+"!");
        }
    }

    private void fightlost(Game gameview){
        int xplost = hero.get_experience() / 2;
        if (gameGuiView == null)
            gameview.consolelog(ANSI_RED+"YOU DIED | you lost "+ xplost +" xp"+ANSI_RESET);
        else
            gameview.consolelog("YOU DIED | you lost "+ xplost +" xp");
        hero.set_experience(xplost);
        map.generatenewmap(hero, gameview);
    }

    private void run(Game gameview){
        gameview.consolelog("you decide try and run from the villain");

        Random rand = new Random();
        if (rand.nextBoolean() == true){
            gameview.consolelog("turns out you ran away just fine");
            map.ranaway();
            conflict = false;
            fight = 2;
        } else {
            if (gameGuiView == null)
                gameview.consolelog(ANSI_RED+"Your attempt to RUN away FAILED. Now you have to FIGHT!"+ANSI_RESET);
            else
                gameview.consolelog("Your attempt to RUN away FAILED. Now you have to FIGHT!");
            fight(gameview);
        }
    }

    private void checkevent(Game gameview){
        villain = (Villain)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][0];
        hero = (Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1];
        if (villain != null && hero != null){
            gameview.consolelog(ANSI_RED+"A WILD LV "+villain.get_level()+" "+villain.get_class()+" "+villain.get_name()+" APPEARS. do you FIGHT OR RUN?"+ANSI_RESET);
            gameview.consolelog(ANSI_PURPLE+"Available commands: FIGHT, RUN, HERO STATS and VILLAIN STATS"+ANSI_RESET);
            conflict = true;            
            while (conflict){
                fight = 2;
                switch (gameinput.nextLine().toUpperCase()) {
                    case "FIGHT":
                        fight(gameview);
                        break;
                    case "RUN":
                        run(gameview);
                        break;
                    case "VILLAIN STATS":
                        gameview.villain_stats(villain);
                        break;
                    case "HERO STATS":
                        gameview.hero_stats((Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1]);
                        break;
                    default:
                        gameview.consolelog("Are you sure you didnt want to fight? ... or run?!");
                        break;
                }
                if (fight == 1){
                    checkartifact();
                    if (artifact)
                        artifactdrop(gameview);
                    fightwon(gameview);
                } else if (fight == 0) {
                    fightlost(gameview);
                }
            }
        } if (map.get_Hero_xlocation() == 0 || map.get_Hero_xlocation() == (map.get_mapsize() - 1) || map.get_Hero_ylocation() == 0 || map.get_Hero_ylocation() == (map.get_mapsize() - 1)){
            gameview.consolelog("You have escaped the dungeon, put simply you win");
            map.generatenewmap(hero, gameview);
        }
    }

    public void savehero(Hero hero, Game gameview){
        if (hero != null){
            SaveHero.openfile(hero.get_name());
            SaveHero.put("Name: "+hero.get_name()
            +"\nClass: "+hero.get_class()
            +"\nLevel: "+hero.get_level()
            +"\nXP: "+hero.get_experience()+" / "+hero.get_xp_to_next_lv()
            +"\nHitpoints: "+hero.get_hitpoints()
            +"\nAttack: "+hero.get_attack()
            +"\ndefense: "+hero.get_defense());
    
            if (hero.get_weapon() != null)
                SaveHero.put("Weapon: LV "+ hero.get_weapon().get_level()+" "+hero.get_weapon().get_name()+" + "+hero.get_weapon().get_attack()+" ATK");
            else 
                SaveHero.put("Weapon:"+" N/A");
    
            if (hero.get_armor() != null)
                SaveHero.put("Armor: LV "+ hero.get_armor().get_level()+" "+hero.get_armor().get_name()+" + "+hero.get_armor().get_defense()+" DEF");
            else
                SaveHero.put("Armor: N/A");
    
            if (hero.get_helm() != null)
                SaveHero.put("Helm: LV "+ hero.get_helm().get_level()+" "+hero.get_helm().get_name()+" + "+hero.get_helm().get_hitpoints()+" HP");
            else
                SaveHero.put("Helm: N/A");
            SaveHero.closelog();
        } else {
            gameview.consolelog("No hero to save");
        }
    }

    public Hero loadheroselected(String filenames[] ,int selected, int line, Game gameview){
        try{
                    BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/swingy/model/heroes/"+filenames[selected]));
                    String name = br.readLine().split(" ")[1];
                    line++;
                    String char_class = br.readLine().split(" ")[1];
                    line++;

                    Hero loaded_hero = new Hero(name, char_class);
                    line++;
                    loaded_hero.set_level(Integer.parseInt(br.readLine().split(" ")[1]));

                    String xpline = br.readLine();

                    line++;
                    loaded_hero.set_experience(Integer.parseInt(xpline.split(" ")[1]));
                    loaded_hero.set_xp_to_next_lv(Integer.parseInt(xpline.split(" ")[3]));
                    line++;

                    loaded_hero.set_hitpoints(Integer.parseInt(br.readLine().split(" ")[1]));
                    line++;

                    loaded_hero.set_attack(Integer.parseInt(br.readLine().split(" ")[1]));
                    line++;

                    loaded_hero.set_defense(Integer.parseInt(br.readLine().split(" ")[1]));
                    line++;


                    String weaponline = br.readLine();

                    if (!weaponline.split(" ")[1].equals("N/A")){
                        for(int i = 0; i < LootTable.getWeaponnames().length; i++){
                            String weapon1 = LootTable.getWeaponnames()[i];
                            String weapon2 = weaponline.split(" ")[3];
                            if (weapon1.equals(weapon2)){
                                loaded_hero.set_weapon(new Weapon(LootTable.getWeaponnames()[i], LootTable.getWeapondamages()[i], Integer.parseInt(weaponline.split(" ")[2])));
                                break;
                            }
                        }
                    }
                    line++;
                    String armorline = br.readLine();
                
                    if (!armorline.split(" ")[1].equals("N/A")){
                        for(int i = 0; i < LootTable.getArmornames().length; i++){
                            String armor1 = LootTable.getArmornames()[i];
                            String armor2 = armorline.split(" ")[3];
                            if (armor1.equals(armor2)){
                                loaded_hero.set_armor(new Armor(LootTable.getArmornames()[i], LootTable.getArmordefense()[i], Integer.parseInt(armorline.split(" ")[2])));
                                break;
                            }
                        }
                    }
                
                
                    line++;
                    String helmline = br.readLine();
                
                    if (!helmline.split(" ")[1].equals("N/A")){
                        for(int i = 0; i < LootTable.getHelmnames().length; i++){
                            String helm1 = LootTable.getHelmnames()[i];
                            String helm2 = helmline.split(" ")[3];
                            if (helm1.equals(helm2)){
                                loaded_hero.set_helm(new Helm(LootTable.getHelmnames()[i], LootTable.getHelmhitpoints()[i], Integer.parseInt(helmline.split(" ")[2])));
                                break;
                            }
                        }
                    }
                    br.close();
                    return loaded_hero;
                } catch (Exception e) {
                    throw  new RuntimeErrorException(null, "file tempered with or in wrong format...or both");
                }
    };

    public Hero loadhero(Map map, Game gameview){
        int line = 1;
        File file = new File("src/main/java/com/swingy/model/heroes/");
        String filenames[] = file.list();
        if (filenames.length != 0){
            for(int i = 0; i < filenames.length ; i++){
                gameview.consolelog(i+" - "+filenames[i]);
            }
            int selected = 0;
            try {
            while (true){
                if ((selected = Integer.parseInt(gameinput.nextLine())) <= (filenames.length - 1)) {
                    return loadheroselected(filenames, selected, line, gameview);
                } else{
                    gameview.consolelog("Selection is out of bound");
                }
            }

            } catch (Exception e){
                gameview.consolelog("error at line " + line +": "+ e.getMessage());
            }
        } else {
            gameview.consolelog("No saved heroes would you like to make a new one or continue with current if available");
            gameview.consolelog(ANSI_PURPLE+"Available commands: CONTINUE and NEW_HERO"+ANSI_RESET);
            boolean select = false;
            while (select == false){
                switch (gameinput.nextLine().toUpperCase()) {
                    case "NEW_HERO":
                        return makeHero(gameview);
                    case "CONTINUE":
                        return null;
                }
            }
        }
        return null;
    }
    
    public Hero makeHero(Game gameview){
        String char_class = null;
        String tname = null;
        Hero temphero = null;
        boolean hero = false;
        while (hero == false){
            while (tname == "" || tname == null){
                gameview.consolelog("[NAME YOUR HERO]");
                tname = gameinput.nextLine();
                
                Set<ConstraintViolation<String>> violations = Validatorclass.validator.validate(tname);
                for (ConstraintViolation<String> violation : violations) {
                    gameview.consolelog(violation.getMessage());
                }
                

                if (tname.contains(" ") || tname.equals("")){
                    gameview.consolelog("[HERO NAMES DONT HAVE SPACES INSIDE AND ARE NOT BLANK]");
                    tname = null;
                } else if (tname.equals("Rodger")){
                gameview.consolelog(ANSI_PURPLE+"RODGER IS MY HERO"+ANSI_RESET);
                } else if (tname.equals("Shroud")){
                    gameview.consolelog(ANSI_PURPLE+"THE KING OF REDDIT"+ANSI_RESET);
                } else if (tname.equals("null")){
                    gameview.consolelog(ANSI_RED+"haha, not funny. no really this almost broke it, do it again and do it right"+ANSI_RESET);
                    tname = null;
                }
            }
            while (char_class == "" || char_class == null){
                gameview.consolelog("[GIVE YOUR HERO A CLASS]");
                char_class = gameinput.nextLine();
                if (char_class.contains(" ")){
                    gameview.consolelog("[HERO CLASSES NAMES DONT HAVE SPACES INSIDE]");
                    char_class = null;
                }
            }
            temphero = new Hero(tname, char_class);
            Set<ConstraintViolation<Hero>> violations = Validatorclass.validator.validate(temphero);
            if (violations.size() != 0){
                hero = false;
            } else {
                hero = true;
            }

            for (ConstraintViolation<Hero> violation : violations) {
                gameview.consolelog(violation.getMessage());
            }
        }
    
        
        return temphero;

    }

    public void makeorloadhero(){
        boolean select = false;
        map = new Map();
        Game gameview = new GameView();
        while (select == false)
        {
            gameview.consolelog(ANSI_PURPLE+"Available commands: NEW HERO and LOAD HERO"+ANSI_RESET);
            switch (gameinput.nextLine().toUpperCase()) {
                case "NEW HERO":
                    hero = makeHero(gameview);
                    select = true;
                    break;
                case "LOAD HERO":
                    hero = loadhero(map, gameview);
                    if (hero == null){
                        gameview.consolelog("It seems like you dont have a hero, you need a hero to go on adventures friend. Let us make one.");
                        hero = makeHero(gameview);
                    }
                    select = true;
                    break;
                default:
                gameview.consolelog("invalid command");
            }
        }
        rungame(gameview);;
    }

    public void rungame(Game gameview){
        map.generatenewmap(hero, gameview);
        updatemapview(gameview, map);
        while (game){
            
            gameview.consolelog(ANSI_PURPLE+"Available commands: MAP, MOVE, HERO, CLEAR, SAVE, LOAD, NEW HERO AND QUIT"+ANSI_RESET);
            switch (gameinput.nextLine().toUpperCase()) {
                case "MAP":
                    updatemapview(gameview, map);
                    break;
                case "MOVE":
                    gameview.consolelog("Choose direction [NORTH, EAST, SOUTH, WEST]");
                    movehero(gameinput.nextLine(), gameview);
                    checkevent(gameview);
                    break;
                case "HERO":
                    gameview.hero_stats((Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1]);
                    break;
                case "QUIT":
                    game = false;
                    savehero((Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1], gameview);
                    gameview.consolelog("Hero "+hero.get_name()+" saved");
                    break;
                case "CLEAR":
                    System.out.print("\033[H\033[2J");  
                    System.out.flush();
                    break;
                case "SAVE":
                    savehero((Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1], gameview);
                    gameview.consolelog("Hero "+hero.get_name()+" saved");
                    break;
                case "LOAD":
                    Hero temphero = loadhero(map, gameview);
                    if (temphero != null){
                        map.generatenewmap(temphero, gameview);
                        temphero = null;
                    }
                    break;
                case "NEW HERO":
                    map.generatenewmap(makeHero(gameview), gameview);
                    break;
                default:
                    gameview.consolelog("Invalid command");
                    break;
           }
           
        }
        gameview.consolelog("-_-[GAME OVER]-_-");
    }
    
    public void GUIcontoller(GameGuiView gv){
        map = new Map();
        gameGuiView = gv;
        initview();
    }

    public void initview(){
        
        gameGuiView.getSavegame().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (gameGuiView.getHero_present()){
                    gameGuiView.getGamesaved().setVisible(true);
                    gameGuiView.getGamesaved().setLayout(new FlowLayout());
                    gameGuiView.getGamesaved().setSize(380, 100);
                    
                    gameGuiView.getDismiss().addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            gameGuiView.getGamesaved().setVisible(false);
                        }
                    });
                    gameGuiView.getGamesaved().add(gameGuiView.getMessage());
                    gameGuiView.getGamesaved().add(gameGuiView.getDismiss());
                    savehero((Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1], gameGuiView);
                } else {
                    gameGuiView.consolelog("NO HERO TO SAVE");
                }
            }
        });

        gameGuiView.getSubmitButtonload().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    if (gameGuiView.getHeroeslist().getSelectedValue().equals("") || gameGuiView.getHeroeslist().getSelectedValue().equals(null)){
                        gameGuiView.getLoadlable().setText("[A QUEST WIHOUT A HERO? no...PLEASE CHOOSE A HERO]");
                    } else {
                        gameGuiView.getLoadheroDialog().setVisible(false);
                        //gameGuiView.getHeroeslist().setVisible(false);
                        // 0 is in place instead of a line int because im too lazy to integrate the line issue
                        Hero h = loadheroselected(gameGuiView.getFilenames(), gameGuiView.getHeroeslist().getSelectedIndex(), 0, gameGuiView);
                        gameGuiView.consolelog("Hero Made");                    
                        map.generatenewmap(h, gameGuiView);
                        gameGuiView.setHero_present(true);
                        gameGuiView.getLoadlable().setText("");
                    }
                } catch(NullPointerException exception){
                    gameGuiView.getLoadlable().setText("[A QUEST WIHOUT A HERO? no...PLEASE CHOOSE A HERO]");
                } catch (Exception exception) {
                    gameGuiView.consolelog("big boo boo - "+exception.getMessage());
                }
            }
        });

        gameGuiView.getNewgame().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                gameGuiView.getNewherodDialog().setVisible(true);
                gameGuiView.newheroname();
                gameGuiView.newheroclass();
            }
        });

        gameGuiView.getLoadgame().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                gameGuiView.getLoadheroDialog().setVisible(true);
                
                gameGuiView.loadhero();
            }
        });

        gameGuiView.getSubmitButtonname().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (gameGuiView.getNametextfield().getText().contains(" ") || gameGuiView.getNametextfield().getText().equals("")){
                    gameGuiView.getNamelable().setText("[HERO NAMES DON'T HAVE SPACES INSIDE AND ARE NOT BLANK]");
                } else {
                    gameGuiView.getNewherodDialog().setVisible(false);
                    gameGuiView.getHeroclassDialog().setVisible(true);
                }
            }
        });

        gameGuiView.getSubmitButtonclass().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e){
                if (gameGuiView.getClasstextfield().getText().contains(" ") || gameGuiView.getClasstextfield().getText().equals("")){
                    gameGuiView.getClasslable().setText("[HERO CLASSES DON'T HAVE SPACES INSIDE AND ARE NOT BLANK]");
                } else {
                    gameGuiView.getHeroclassDialog().setVisible(false);
                    map.generatenewmap(new Hero(gameGuiView.getNametextfield().getText(), gameGuiView.getClasstextfield().getText()), gameGuiView);
                    gameGuiView.setHero_present(true);
                }
            }
        });

        gameGuiView.getFrame().addWindowListener(new WindowListener(){
            @Override
            public void windowClosing(WindowEvent windowEvent){
                savehero(hero, gameGuiView);
                System.exit(0);
            }

            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        gameGuiView.getHeroButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (gameGuiView.getHero_present()){
                    gameGuiView.hero_stats((Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1]);
                } else {
                    gameGuiView.consolelog("Please make or load a new hero for all our sakes");
                }
            }
        });

        gameGuiView.getVilButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (gameGuiView.getHero_present()){
                    if (villain != null){
                        gameGuiView.villain_stats((Villain)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][0000]);
                    } else if (artifact){
                        gameGuiView.consolelog("Available commands: to USE, COMPARE or LEAVE");
                    } 
                } else {
                    gameGuiView.consolelog("No villain available");
                }
            }
        });

        gameGuiView.getMapButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                updatemapview(gameGuiView, map);
            }
        });

        gameGuiView.getClearButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                gameGuiView.getGuiconsole().setText("CLEARED\n");
            }
        });

        // -------------- MOVEMENT LISTNERS --------------- //
        gameGuiView.getMoveButtonN().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (gameGuiView.getHero_present()){                
                    if (!conflict && !artifact){
                        movehero("NORTH", gameGuiView);
                        checkconflict(gameGuiView);
                    } else if (conflict){
                        gameGuiView.consolelog("Available commands: FIGHT, RUN, HERO STATS and VILLAIN STATS");
                    } else if (artifact) {
                        gameGuiView.consolelog("Available commands: to USE, COMPARE or LEAVE");
                    }
                } else {
                    gameGuiView.consolelog("Hero is needed to move, make or load one please");
                }
            }
         });
        gameGuiView.getMoveButtonS().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (gameGuiView.getHero_present()){                
                    if (!conflict && !artifact){
                        movehero("SOUTH", gameGuiView);
                        checkconflict(gameGuiView);
                    } else if (conflict){
                        gameGuiView.consolelog("Available commands: FIGHT, RUN, HERO STATS and VILLAIN STATS");
                    } else if (artifact) {
                        gameGuiView.consolelog("Available commands: to USE, COMPARE or LEAVE");
                    }
                } else {
                    gameGuiView.consolelog("Hero is needed to move, make or load one please");
                }
            }
        });
        gameGuiView.getMoveButtonW().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (gameGuiView.getHero_present()){                
                    if (!conflict && !artifact){
                        movehero("WEST", gameGuiView);
                        checkconflict(gameGuiView);
                    } else if (conflict){
                        gameGuiView.consolelog("Available commands: FIGHT, RUN, HERO STATS and VILLAIN STATS");
                    } else if (artifact) {
                        gameGuiView.consolelog("Available commands: to USE, COMPARE or LEAVE");
                    
                    } 
                } else {
                    gameGuiView.consolelog("Hero is needed to move, make or load one please");
                }
            }
        });
        gameGuiView.getMoveButtonE().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (gameGuiView.getHero_present()){                
                    if (!conflict && !artifact){
                        movehero("EAST", gameGuiView);
                        checkconflict(gameGuiView);
                    } else if (conflict){
                        gameGuiView.consolelog("Available commands: FIGHT, RUN, HERO STATS and VILLAIN STATS");
                    } else if (artifact) {
                        gameGuiView.consolelog("Available commands: to USE, COMPARE or LEAVE");
                    }
                } else {
                    gameGuiView.consolelog("Hero is needed to move, make or load one please");
                }
            }
        });
        gameGuiView.getFightButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (conflict){
                    fight(gameGuiView);
                    if (fight == 1){
                        checkartifact();
                        if (artifact){
                            gameGuiView.consolelog(villain.get_name()+
                            " has dropped a level "+villain.give_artifact().get_level()+" "+villain.give_artifact().get_type()+" "+villain.give_artifact().get_name()+
                            " with the stats "+villain.give_artifact().get_stats()+"\nAvailable commands: to USE, COMPARE or LEAVE");
                        } else {
                            fightwon(gameGuiView);
                        }
                    } else {
                        fightlost(gameGuiView);
                    }
                } else if (artifact){
                    gameGuiView.consolelog("Available commands: to USE, COMPARE or LEAVE");
                } else {
                    gameGuiView.consolelog("Nothing to fight... yet");
                }
            }
        });

        gameGuiView.getRunButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (conflict){
                    run(gameGuiView);
                    if (fight == 0) {
                        fightlost(gameGuiView);
                    } else if (fight == 1) {
                        checkartifact();
                        if (artifact){
                            gameGuiView.consolelog(villain.get_name()+
                            " has dropped a level "+villain.give_artifact().get_level()+" "+villain.give_artifact().get_type()+" "+villain.give_artifact().get_name()+
                            " with the stats "+villain.give_artifact().get_stats()+"\nAvailable commands: to USE, COMPARE or LEAVE");
                        }
                        fightwon(gameGuiView);
                    } else if (fight == 2) {
                        conflict = false;
                    }
                } else if (artifact){
                    gameGuiView.consolelog("Available commands: to USE, COMPARE or LEAVE");
                } else {
                    gameGuiView.consolelog("Nothing to run from... yet");
                }
            }
        });

        gameGuiView.getUseButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (artifact){
                    switch(villain.give_artifact().get_type()){
                        case "Weapon":
                            hero.set_weapon((Weapon)villain.give_artifact());
                            break;
                        case "Armor":
                            hero.set_armor((Armor)villain.give_artifact());
                            break;
                        case "Helm":
                            hero.set_helm((Helm)villain.give_artifact());
                            break;
                    }
                    artifact = false;
                    gameGuiView.consolelog("You equiped it");
                    fightwon(gameGuiView);
                } else if (conflict){
                    gameGuiView.consolelog("Available commands: FIGHT, RUN, HERO STATS and VILLAIN STATS");
                } else {
                    gameGuiView.consolelog("Nothing to use");
                }
            }
        });

        gameGuiView.getCompareButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (artifact){
                    gameGuiView.consolelog("YOU HAVE:");
                    switch(villain.give_artifact().get_type()){
                        case "Weapon":
                            gameGuiView.hero_weapon(hero);
                            break;
                        case "Armor":
                            gameGuiView.hero_armor(hero);
                            break;
                        case "Helm":
                            gameGuiView.hero_helm(hero);
                            break;
                    }
                } else if (conflict){
                    gameGuiView.consolelog("Available commands: FIGHT, RUN, HERO STATS and VILLAIN STATS");
                } else {
                    gameGuiView.consolelog("Nothing to compare");
                }
            }
        });

        gameGuiView.getLeaveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (artifact){
                    gameGuiView.consolelog("You left it");
                    artifact = false;
                    fightwon(gameGuiView);
                } else if (conflict){
                    gameGuiView.consolelog("Available commands: FIGHT, RUN, HERO STATS and VILLAIN STATS");
                } else {
                    gameGuiView.consolelog("Nothing to leave");
                }
            }
        });
        
    }

    public void checkconflict(Game gameView){
        villain = (Villain)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][0];
        hero = (Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1];
        if (villain != null && hero != null){
            gameView.consolelog("A WILD LV "+villain.get_level()+" "+villain.get_class()+" "+villain.get_name()+" APPEARS. do you FIGHT OR RUN?");
            gameView.consolelog("Available commands: FIGHT, RUN, HERO STATS and VILLAIN STATS");
            conflict = true;
        } else if (map.get_Hero_xlocation() == 0 || map.get_Hero_xlocation() == (map.get_mapsize() - 1) || map.get_Hero_ylocation() == 0 || map.get_Hero_ylocation() == (map.get_mapsize() - 1)){
            gameView.consolelog("You have escaped the dungeon, put simply you win");
            map.generatenewmap(hero, gameView);
            conflict = false;
        } else {
            conflict = false;
        }
    }

}