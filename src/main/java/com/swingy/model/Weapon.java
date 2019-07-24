package com.swingy.model;

public class Weapon implements Artifact{

    private String _name;
    private int _attack;

    public Weapon(String name, int attack){
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

    public int get_stats() {
        return _attack;
    }



}