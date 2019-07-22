package com.swingy;

import java.util.Random;

abstract interface artifact{

}

/*
static class Class_Varables{
    Class_Varables getClass_Varable = Class_Varables();

    private Class_Varables(){

    }
}
*/

class Weapon implements artifact{

    private String _name;
    private int _attack;

    Weapon(String name, int attack){
        this._name = name;
        this._attack = attack;
        System.out.println("weapon "+_name+" made with "+_attack+" attack points");
    }

    /**
     * @return the _name
     */
    public String get_name(){
        return _name;
    }

    /**
     * @return the _attack
     */
    public int get_attack() {
        return _attack;
    }

}

class Armor implements artifact{
    private String _name;
    private int _defense;

    Armor(String name, int defense){
        this._name = name;
        this._defense = defense;
        System.out.println("armor "+_name+" made with "+_defense+" defence points");
    }

    /**
     * @return the _name
     */
    public String get_name(){
        return _name;
    }

    /**
     * @return the _defense
     */
    public int get_defense() {
        return _defense;
    }

}

class Helm implements artifact{
    private String _name;
    private int _hitpoints;

    Helm(String name, int hitpoints){
        this._name = name;
        this._hitpoints = hitpoints;
        System.out.println("armor "+_name+" made with "+_hitpoints+" hp");
    }

    /**
     * @return the _name
     */
    public String get_name(){
        return _name;
    }
    
    /**
     * @return the _hitpoints
     */
    public int get_hitpoints() {
        return _hitpoints;
    }

}


class Map{
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

class Character{

    protected String _name;
    protected String _class;
    protected int _level;
    protected int _attack;
    protected int _defense;
    protected int _hitpoints;

    /**
     * @return the _name
     */
    public String get_name() {
        return _name;
    }

    /**
     * @return the _attack
     */
    public int get_attack() {
        return _attack;
    }

    /**
     * @return the _class
     */
    public String get_class() {
        return _class;
    }

    /**
     * @return the _defense
     */
    public int get_defense() {
        return _defense;
    }

    /**
     * @return the _hitpoints
     */
    public int get_hitpoints() {
        return _hitpoints;
    }

    /**
     * @return the _level
     */
    public int get_level() {
        return _level;
    }
}

class Villainmaker{
    static Character makevillain(Character hero){
        hero.get_level();
        
        // do stuff and get a villain a less or a bit stronger then the hero.
        return new Villain();
    }

}

class Villain extends Character{
    
}

class Hero extends Character{
    private int _experience;
    private Weapon _weapon;
    private Armor _Armor;
    private Helm _helm;
    protected int _xp_to_next_lv;

    @Override
    public int get_attack() {
        return (super.get_attack() +  _weapon.get_attack());
    }

    @Override
    public int get_hitpoints() {
        return (super.get_hitpoints() + _helm.get_hitpoints());
    }

    @Override
    public int get_defense() {
        return (super.get_defense() + _Armor.get_defense());
    }

    /**
     * @param _Armor the _Armor to set
     */
    public void set_Armor(Armor _Armor) {
        this._Armor = _Armor;
    }

    /**
     * @param _weapon the _weapon to set
     */
    public void set_weapon(Weapon _weapon) {
        this._weapon = _weapon;
    }

    /**
     * @param _helm the _helm to set
     */
    public void set_helm(Helm _helm) {
        this._helm = _helm;
    }

    public void checklevelup(){
        _xp_to_next_lv = (_level*1000+((_level - 1)*(_level - 1))*450);
        if (_experience >= _xp_to_next_lv){
            _experience -= _xp_to_next_lv;
            _level++;
            _xp_to_next_lv = (_level*1000+((_level - 1)*(_level - 1))*450);
        }
    }
}

