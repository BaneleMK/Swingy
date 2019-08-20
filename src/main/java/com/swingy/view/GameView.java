package com.swingy.view;

import com.swingy.model.*;

public class GameView implements Game{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public GameView(){
        consolelog("--- LET THE GAME BEGIN ---");
    }

    @Override
    public void consolelog(String message){
        System.out.println(message);
    }

    public void rendermap(GameCharacter[][][] map, int mapsize){
        for(int y = 0; y<mapsize; y++){
            for(int x = 0; x<mapsize; x++){
                // check if a villain randomly spawns and if the max amount of mobs is not reached for the player level.
                if (map[y][x][0] == null && map[y][x][1] == null){
                    System.out.print(". ");
                } else if (map[y][x][0] != null && map[y][x][1] != null){
                    System.out.print("F ");
                } else if (map[y][x][0] != null) {
                    System.out.print("V ");
                } else if (map[y][x][1] != null) {
                    System.out.print("H ");
                } else {
                    System.out.print("# ");
                }
            }
            System.out.print("\n");
        }   
    }

    public void hero_weapon(Hero hero){
        if (hero.get_weapon() != null)
            consolelog("Weapon: LV "+ANSI_GREEN+ hero.get_weapon().get_level()+ANSI_RESET+" "+hero.get_weapon().get_name()+" + "+ANSI_GREEN+hero.get_weapon().get_attack()+" ATK"+ ANSI_RESET);
        else 
            consolelog("Weapon:"+ANSI_RED+" N/A"+ ANSI_RESET);
    }

    public void hero_armor(Hero hero){
        if (hero.get_armor() != null)
            consolelog("Armor: LV "+ANSI_GREEN+ hero.get_armor().get_level()+ANSI_RESET+" "+hero.get_armor().get_name()+" + "+ANSI_GREEN+hero.get_armor().get_defense()+" DEF"+ ANSI_RESET);
        else
            consolelog("Armor:"+ANSI_RED+" N/A"+ ANSI_RESET);
    }

    public void hero_helm(Hero hero){
        if (hero.get_helm() != null)
            consolelog("Helm: LV "+ANSI_GREEN+ hero.get_helm().get_level()+ANSI_RESET+" "+hero.get_helm().get_name()+" + "+ANSI_GREEN+hero.get_helm().get_hitpoints()+" HP" + ANSI_RESET);
        else
            consolelog("Helm:"+ANSI_RED+" N/A"+ ANSI_RESET);
    }

    public void hero_stats(Hero hero){
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
    }

    public void villain_stats(Villain villain){
        consolelog("Name: "+villain.get_name()
        +"\nClass: "+villain.get_class()
        +"\nLevel: "+villain.get_level()
        +"\nHitpoints: "+villain.get_hitpoints()
        +"\nAttack: "+villain.get_attack()
        +"\ndefense: "+villain.get_defense());
    }
}