package com.swingy.model;
import java.util.Random;

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
    
    public void killvillain(){
        // check if this actually gets rid of it in memory
        System.out.println("The Vile Villain "+map[_hero_ylocation][_hero_xlocation][0].get_name()+" has been slain.");
        map[_hero_ylocation][_hero_xlocation][0] = null;
    }

    private void newherolocation(int y, int x){
        if (y >= 0 && y < _mapsize && x >= 0 && x < _mapsize){
            _prev_hero_xlocation = _hero_xlocation;
            _prev_hero_ylocation = _hero_ylocation;
            map[y][x][1] = map[_hero_ylocation][_hero_xlocation][1];
            map[_hero_ylocation][_hero_xlocation][1] = null;
            _hero_ylocation = y;
            _hero_xlocation = x;
        } else {
            System.out.println("But you can no longer move that way.");
        }
    }

    public void ranaway(){
        map[_prev_hero_ylocation][_prev_hero_xlocation][1] = map[_hero_ylocation][_hero_xlocation][1];
        map[_hero_ylocation][_hero_xlocation][1] = null;
        _hero_ylocation = _prev_hero_ylocation;
        _hero_xlocation = _prev_hero_xlocation;
    }

    

    public void movehero(String direction){

        switch (direction.toUpperCase()) {
            case "NORTH":
                System.out.println("You move NORTH.");
                newherolocation(_hero_ylocation - 1, _hero_xlocation);
                break;
            case "SOUTH":
                System.out.println("You move SOUTH.");
                newherolocation(_hero_ylocation + 1, _hero_xlocation);
                break;
            case "WEST":
                System.out.println("You move WEST.");
                newherolocation(_hero_ylocation, _hero_xlocation - 1);
                break;
            case "EAST":
                System.out.println("You move EAST.");
                newherolocation(_hero_ylocation, _hero_xlocation + 1);
                break;
            default:
                System.out.println("Invalid direction.");
                break;
        }
        
    }

    public void generatenewmap(GameCharacter hero){
        _mapsize = (hero.get_level()-1)*5+10-(hero.get_level()%2);
        System.out.println("Making map of size "+_mapsize);
        
        // init map 
        map = new GameCharacter[_mapsize][_mapsize][2];

        _hero_xlocation = _hero_ylocation = Math.round(_mapsize/2);
        
        // put player on map
        System.out.println("Placing Hero on map");
        map[_hero_ylocation][_hero_xlocation][1] = hero;
        
        // put mobs and stuff here
        Random rand = new Random();
        int maxmobs = _mapsize * 4;
        int currentmobs = 0;
    
        System.out.println("Preping to summon Villains");
        for(int y = 0; y<_mapsize; y++){
            for(int x = 0; x<_mapsize; x++){
                // check if a villain randomly spawns and if the max amount of mobs is not reached for the player level.
                if (rand.nextBoolean() == true && rand.nextBoolean() == true && currentmobs < maxmobs){
                    if (map[y][x][1] != hero){
                        System.out.println("Making Villain");
                        map[y][x][0] = Villainmaker.makevillain(hero);
                        currentmobs++;
                    }
                }
            }   
        }
        System.out.println("Hero "+map[_hero_ylocation][_hero_xlocation][1].get_name()+" is ready to fight");
    }
}