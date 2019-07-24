package com.swingy.controller;

import java.util.Random;
import java.util.Scanner;

import com.swingy.model.*;
import com.swingy.view.*;

/**
 * GameEngine
 */
public class GameEngine{

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

    private static void checkevent(Map map){
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
                    int xpgained = villain.get_level() * 200;
                    map.killvillain();
                    System.out.println("You won the FIGHT! you have gained "+xpgained+"XP");
                    hero.getxp(xpgained);
                } else if (fight == 0) {
                    //map.killhero();
                    System.out.println("YOU DIED");                                
                }
            }
        } else if (map.get_Hero_xlocation() == 0 || map.get_Hero_xlocation() == (map.get_mapsize() - 1) || map.get_Hero_ylocation() == 0 || map.get_Hero_ylocation() == (map.get_mapsize() - 1)){
            System.out.println("You have escaped the dungeon, put simply you win");
            map.generatenewmap(hero);
        }
    }

    public static void rungame(GameView gameview, Map map){
        updatemapview(gameview, map);
        while (game){
            
            System.out.println("Available commands: MAP, MOVE AND QUIT");
            switch (gameinput.nextLine().toUpperCase()) {
                case "MAP":
                    updatemapview(gameview, map);
                    break;
                case "MOVE":
                    System.out.println("Choose direction [NORTH, EAST, SOUTH, WEST]");
                    map.movehero(gameinput.nextLine());
                    checkevent(map);
                    break;
                case "HERO":
                    System.out.println("Name");
                    break;
                case "QUIT":
                    game = false;
                    break;
                case "CLEAR":
                    System.out.print("\033[H\033[2J");  
                    System.out.flush();
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
           }
           
        }
        System.out.println("-_-[GAME OVER]-_-");
    }
    
}