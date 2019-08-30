package com.swingy.model;

import javax.validation.constraints.NotNull;

public class Weapon implements Artifact{

    @NotNull
    private String _name;

    @NotNull
    private int _attack;
    
    private String _type = "Weapon";

    @NotNull
    private int _level;

    public Weapon(String name, int attack, int level){
        this._name = name;
        this._attack = attack * (level + 1) / 2;
        this._level = level;
        //System.out.println("lv "+_level+" weapon "+_name+" made with "+_attack+" attack points");
    }

    /**
     * @return the _name
     */
    public String get_name(){
        return _name;
    }

     /**
     * @return the type
     */
    public String get_type() {
        return _type;
    }

    /**
     * @return the _level
     */
    public int get_level() {
        return _level;
    }

    /**
     * @return the _attack
     */
    public int get_attack() {
        return _attack;
    }

    public int get_stats() {
        return _attack;
    }



}