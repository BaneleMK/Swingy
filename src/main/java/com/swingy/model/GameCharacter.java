package com.swingy.model;

/*
static class Class_Varables{
    Class_Varables getClass_Varable = Class_Varables();

    private Class_Varables(){

    }
}
*/

public abstract class GameCharacter{

    protected String _name;
    protected String _class;
    protected int _level;
    protected int _attack;
    protected int _defense;
    protected int _hitpoints;
    protected int _maxhitpoints;

    protected GameCharacter(String name, String char_class){
        this._name = name;
        this._class = char_class;
        this._level = 1;
        this._attack = 5;
        this._defense = 0;
        this._hitpoints = 20;
        this._maxhitpoints = 20;
    }

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

    public void takedamage(int damage){
        damage += (damage - _defense);
        if (damage > 0){
            this._hitpoints -= damage;
        }
    }
}

class Villainmaker{
    public static GameCharacter makevillain(GameCharacter hero){
        // do stuff and get a villain a less or a bit stronger then the hero.
        //System.out.println("Making new lv "+hero.get_level()+" Villain");        
        return new Villain("skummy", "slime", hero.get_level());
    }

}


