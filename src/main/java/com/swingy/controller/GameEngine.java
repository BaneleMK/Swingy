package com.swingy.controller;

import java.util.Random;
import java.util.Scanner;

import com.swingy.SaveHero;
import com.swingy.model.*;
import com.swingy.view.*;

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


    private static void updatemapview(GameView gameview, Map map){
        gameview.rendermap(map.getMap(), map.get_mapsize());
    }

    private static int startfight(Hero hero, Villain villain) {
        if (hero.get_attack() > villain.get_attack()) {
            return 1;
        } else {
            return 0;
        }
    }

    private static void checkevent(Map map, GameView gameView){
        Villain villain = (Villain)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][0];
        Hero hero = (Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1];
        if (villain != null && hero != null){
            System.out.println("A WILD "+villain.get_name()+" APPEARS . do you ["+hero.get_name()+"] FIGHT OR RUN?");
            boolean conflict = true;
            while (conflict){
                int fight = 2;
                switch (gameinput.nextLine().toUpperCase()) {
                    case "FIGHT":
                        System.out.println("you decide fight the villain");
                        startfight(hero, villain);
                        fight = startfight(hero, villain);
                        conflict = false;
                        break;
                    case "RUN":
                        System.out.println("you decide try and run from the villain");

                        Random rand = new Random();
                        if (rand.nextBoolean() == true){
                            System.out.println("turns out you ran away just fine");
                            map.ranaway();
                        } else {
                            System.out.println("Your attempt to RUN away FAILED. Now you have to FIGHT!");
                            fight = startfight(hero, villain);
                        }
                        conflict = false;
                        break;
                    default:
                        System.out.println("Are you sure you didnt want to fight? ... or run?!");
                        break;
                }
                if (fight == 1){
                    
                    if (villain.give_artifact() != null){
                        Random rand = new Random();
                        if (rand.nextBoolean() == true){
                            boolean artifact = true;
                            while (artifact){
                                System.out.println(villain.get_name()+
                                " has dropped a level "+ANSI_GREEN+villain.give_artifact().get_level()+ANSI_RESET+" "+villain.give_artifact().get_type()+" "+villain.give_artifact().get_name()+
                                " with the stats "+ANSI_GREEN+villain.give_artifact().get_stats()+ANSI_RESET+
                                "\nWould you like to USE, COMPARE or LEAVE it?");
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
                                        System.out.println("YOU HAVE:");
                                        switch(villain.give_artifact().get_type()){
                                            case "Weapon":
                                                gameView.hero_weapon(hero);
                                                break;
                                            case "Armor":
                                                gameView.hero_armor(hero);
                                                break;
                                            case "Helm":
                                                gameView.hero_helm(hero);
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
                    System.out.println("You won the FIGHT! you have gained "+ANSI_GREEN+xpgained+ANSI_RESET+"XP");
                    hero.getxp(xpgained);
                } else if (fight == 0) {
                    //map.killhero();
                    System.out.println("YOU DIED");                                
                }
            }
        } if (map.get_Hero_xlocation() == 0 || map.get_Hero_xlocation() == (map.get_mapsize() - 1) || map.get_Hero_ylocation() == 0 || map.get_Hero_ylocation() == (map.get_mapsize() - 1)){
            System.out.println("You have escaped the dungeon, put simply you win");
            map.generatenewmap(hero);
        }
    }

    public static void savehero(Hero hero){
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

    public static void rungame(GameView gameview, Map map){
        updatemapview(gameview, map);
        while (game){
            
            System.out.println("Available commands: MAP, MOVE, HERO, CLEAR, SAVE AND QUIT");
            switch (gameinput.nextLine().toUpperCase()) {
                case "MAP":
                    updatemapview(gameview, map);
                    break;
                case "MOVE":
                    System.out.println("Choose direction [NORTH, EAST, SOUTH, WEST]");
                    map.movehero(gameinput.nextLine());
                    checkevent(map, gameview);
                    break;
                case "HERO":
                    gameview.hero_stats((Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1]);
                    break;
                case "QUIT":
                    game = false;
                    break;
                case "CLEAR":
                    System.out.print("\033[H\033[2J");  
                    System.out.flush();
                    break;
                case "SAVE":
                    savehero((Hero)map.getMap()[map.get_Hero_ylocation()][map.get_Hero_xlocation()][1]);
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
           }
           
        }
        System.out.println("-_-[GAME OVER]-_-");
    }
    
}