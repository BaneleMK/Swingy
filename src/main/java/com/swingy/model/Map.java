package com.swingy.model;
import java.util.Random;

class Map {
    private int _mapsize;
    Character[][][] map;

    public void generatenewmap(Character hero){
        _mapsize = (hero.get_level()-1)*5+10-(hero.get_level()%2);
        
        // init map 
        map = new Character[_mapsize][_mapsize][2];
        
        // put player on map
        map[Math.round(_mapsize/2)][Math.round(_mapsize/2)][1] = hero;
        
        // put mobs and stuff here
        Random rand = new Random();
        int maxmobs = hero.get_level() * 2 * 2;
        int currentmobs = 0;
        for(int y = 0; y<_mapsize; y++){
            for(int x = 0; x<_mapsize; x++){
                // check if a villain randomly spawns and if the max amount of mobs is not reached for the player level.
                if (rand.nextBoolean() == true && maxmobs < currentmobs){
                    if (map[y][x][1] != hero){
                        map[y][x][0] = Villainmaker.makevillain(hero);
                        currentmobs++;
                    }
                }
            }   
        }
        // peep poop peep poop
    }
}