package com.swingy.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

import com.swingy.SaveHero;
import com.swingy.model.*;
import com.swingy.view.*;

//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
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


    static Scanner gameinput = new Scanner(System.in);
    static boolean game = true;


    private  void updatemapview(GameView gameview, Map map){
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

    private  void checkevent(Map map, GameView gameview){
        Villain villain = (Villain)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][0];
        Hero hero = (Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1];
        if (villain != null && hero != null){
            gameview.consolelog(ANSI_RED+"A WILD LV "+villain.get_level()+" "+villain.get_class()+" "+villain.get_name()+" APPEARS. do you FIGHT OR RUN?"+ANSI_RESET);
            gameview.consolelog(ANSI_PURPLE+"Available commands: FIGHT, RUN, HERO STATS and VILLAIN STATS"+ANSI_RESET);
            
            boolean conflict = true;
            while (conflict){
                int fight = 2;
                switch (gameinput.nextLine().toUpperCase()) {
                    case "FIGHT":
                        gameview.consolelog("you decide fight the villain");
                        startfight(hero, villain);
                        fight = startfight(hero, villain);
                        conflict = false;
                        break;
                    case "RUN":
                        gameview.consolelog("you decide try and run from the villain");

                        Random rand = new Random();
                        if (rand.nextBoolean() == true){
                            gameview.consolelog("turns out you ran away just fine");
                            map.ranaway();
                        } else {
                            gameview.consolelog(ANSI_RED+"Your attempt to RUN away FAILED. Now you have to FIGHT!"+ANSI_RESET);
                            fight = startfight(hero, villain);
                        }
                        conflict = false;
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
                    
                    if (villain.give_artifact() != null){
                        Random rand = new Random();
                        if (rand.nextBoolean() == true){
                            boolean artifact = true;
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
                    }
                    int xpgained = villain.get_level() * 200;
                    map.killvillain();
                    gameview.consolelog(ANSI_YELLOW+"You won the FIGHT with "+ANSI_GREEN+hero.get_lastfighthp()+" / "+hero.get_hitpoints()+ANSI_YELLOW+" hp remaining! you have gained "+ANSI_GREEN+xpgained+ANSI_YELLOW+" XP"+ANSI_RESET);
                    if (hero.getxp(xpgained) == true){
                        gameview.consolelog("YOU LEVELED UP TO LV "+hero.get_level()+"!");
                    }
                } else if (fight == 0) {
                    int xplost = hero.get_experience() / 2;
                    gameview.consolelog(ANSI_RED+"YOU DIED | you lost "+ xplost +" xp"+ANSI_RESET);
                    hero.set_experience(xplost);
                    map.generatenewmap(hero);
                }
            }
        } if (map.get_Hero_xlocation() == 0 || map.get_Hero_xlocation() == (map.get_mapsize() - 1) || map.get_Hero_ylocation() == 0 || map.get_Hero_ylocation() == (map.get_mapsize() - 1)){
            gameview.consolelog("You have escaped the dungeon, put simply you win");
            map.generatenewmap(hero);
        }
    }

    public  void savehero(Hero hero){
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

    }

    public  Hero loadhero(Map map, GameView gameview){
        int line = 1;
        File file = new File("src/main/java/com/swingy/model/heroes/");
        String filenames[] = file.list();
        if (filenames.length != 0){
            for(int i = 0; i < filenames.length ; i++){
                gameview.consolelog(i+" - "+filenames[i]);
            }
            boolean character_selected = false;
            int selected = 0;
            try {
            while (character_selected == false){
                if ((selected = Integer.parseInt(gameinput.nextLine())) <= (filenames.length - 1)) {
                
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
                    character_selected = true;
                    return loaded_hero;
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

    public Hero makeHero(GameView gameview){
        @Size(min = 4, max = 20, message = "Heroes have names with 1 - 20 characters") String name = null;
        String tempname = null;
        String char_class = null;
        while (name == "" || name == null){
            gameview.consolelog("[NAME YOUR HERO]");
            tempname = gameinput.nextLine();
            name = tempname;
            if (name.contains(" ") || name.equals("")){
                gameview.consolelog("[HERO NAMES DONT HAVE SPACES INSIDE AND ARE NOT BLANK]");
                name = null;
            } else if (name.equals("Rodger")){
                gameview.consolelog(ANSI_PURPLE+"RODGER IS MY HERO"+ANSI_RESET);
            } else if (name.equals("Shroud")){
                gameview.consolelog(ANSI_PURPLE+"THE KING OF REDDIT"+ANSI_RESET);
            } else if (name.equals("null")){
                gameview.consolelog(ANSI_RED+"haha, not funny. no really this almost broke it, do it again and do it right"+ANSI_RESET);
                name = null;
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
        return new Hero(name, char_class);
    }
    
        public Hero makeHerogui(GameGuiView g){
            return new Hero(g.nametextfield.getText() , g.classtextfield.getText());
        }   

    public void makeorloadhero(){
        boolean select = false;
        Hero hero = null;
        Map gamemap = new Map();
        GameView gameview = new GameView();
        while (select == false)
        {
            gameview.consolelog(ANSI_PURPLE+"Available commands: NEW HERO and LOAD HERO"+ANSI_RESET);
            switch (gameinput.nextLine().toUpperCase()) {
                case "NEW HERO":
                    hero = makeHero(gameview);
                    select = true;
                    break;
                case "LOAD HERO":
                    hero = loadhero(gamemap, gameview);
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
        rungame(gameview, gamemap, hero);;
    }

    public void rungame(GameView gameview, Map map, Hero hero){
        map.generatenewmap(hero);
        updatemapview(gameview, map);
        while (game){
            
            gameview.consolelog(ANSI_PURPLE+"Available commands: MAP, MOVE, HERO, CLEAR, SAVE, LOAD, NEW HERO AND QUIT"+ANSI_RESET);
            switch (gameinput.nextLine().toUpperCase()) {
                case "MAP":
                    updatemapview(gameview, map);
                    break;
                case "MOVE":
                    gameview.consolelog("Choose direction [NORTH, EAST, SOUTH, WEST]");
                    map.movehero(gameinput.nextLine());
                    checkevent(map, gameview);
                    break;
                case "HERO":
                    gameview.hero_stats((Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1]);
                    break;
                case "QUIT":
                    game = false;
                    savehero((Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1]);
                    gameview.consolelog("Hero "+hero.get_name()+" saved");
                    break;
                case "CLEAR":
                    System.out.print("\033[H\033[2J");  
                    System.out.flush();
                    break;
                case "SAVE":
                    savehero((Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1]);
                    gameview.consolelog("Hero "+hero.get_name()+" saved");
                    break;
                case "LOAD":
                    Hero temphero = loadhero(map, gameview);
                    if (temphero != null){
                        map.generatenewmap(temphero);
                        temphero = null;
                    }
                    break;
                case "NEW HERO":
                    map.generatenewmap(makeHero(gameview));
                    break;
                default:
                    gameview.consolelog("Invalid command");
                    break;
           }
           
        }
        gameview.consolelog("-_-[GAME OVER]-_-");
    }
    
}