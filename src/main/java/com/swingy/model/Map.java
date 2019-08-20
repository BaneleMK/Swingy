package com.swingy.model;
import java.util.Random;

import com.swingy.view.Game;

public class Map {
    private int _mapsize;
    GameCharacter[][][] map;

    private int _hero_xlocation;
    private int _hero_ylocation;

    private int _prev_hero_xlocation;
    private int _prev_hero_ylocation;

    /**
     * @return the map
     */
    public GameCharacter[][][] getMap() {
        return map;
    }

    /**
     * @return the _mapsize
     */
    public int get_mapsize() {
        return _mapsize;
    }

    /**
     * @return the _hero_xlocation
     */
    public int get_Hero_xlocation() {
        return _hero_xlocation;
    }

    /**
     * @return the _hero_ylocation
     */
    public int get_Hero_ylocation() {
        return _hero_ylocation;
    }
    
    public void killvillain(Game GameView){
        // check if this actually gets rid of it in memory
        GameView.consolelog("The Vile Villain "+map[_hero_ylocation][_hero_xlocation][0].get_name()+" has been slain.");
        map[_hero_ylocation][_hero_xlocation][0] = null;
    }

    public void newherolocation(int y, int x, Game GameView){
        if (y >= 0 && y < _mapsize && x >= 0 && x < _mapsize){
            _prev_hero_xlocation = _hero_xlocation;
            _prev_hero_ylocation = _hero_ylocation;
            map[y][x][1] = map[_hero_ylocation][_hero_xlocation][1];
            map[_hero_ylocation][_hero_xlocation][1] = null;
            _hero_ylocation = y;
            _hero_xlocation = x;
        } else {
            GameView.consolelog("But you can no longer move that way.");
        }
    }

    public void ranaway(){
        map[_prev_hero_ylocation][_prev_hero_xlocation][1] = map[_hero_ylocation][_hero_xlocation][1];
        map[_hero_ylocation][_hero_xlocation][1] = null;
        _hero_ylocation = _prev_hero_ylocation;
        _hero_xlocation = _prev_hero_xlocation;
    }


    public void generatenewmap(GameCharacter hero, Game GameView){
        _mapsize = (hero.get_level()-1)*5+10-(hero.get_level()%2);
        GameView.consolelog("Making map of size "+_mapsize);
        
        // init map 
        map = new GameCharacter[_mapsize][_mapsize][2];

        _hero_xlocation = _hero_ylocation = Math.round(_mapsize/2);
        
        // put player on map
        GameView.consolelog("Placing Hero on map");
        map[_hero_ylocation][_hero_xlocation][1] = hero;
        
        // put mobs and stuff here
        Random rand = new Random();
        int maxmobs = _mapsize * 4;
        int currentmobs = 0;
    
        GameView.consolelog("Preping to summon Villains");
        for(int y = 0; y<_mapsize; y++){
            for(int x = 0; x<_mapsize; x++){
                // check if a villain randomly spawns and if the max amount of mobs is not reached for the player level.
                if (rand.nextBoolean() == true && rand.nextBoolean() == true && currentmobs < maxmobs){
                    if (map[y][x][1] != hero){
                        map[y][x][0] = LootTable.genVillain(hero.get_level());
                        currentmobs++;
                    }
                }
            }   
        }
        GameView.consolelog("Villains made");

        GameView.consolelog("Hero "+map[_hero_ylocation][_hero_xlocation][1].get_name()+" is ready to fight");
    }
}