package com.swingy.model;

public class Armor implements Artifact {
    private String _name;
    private int _defense;
    private String _type = "Armor";
    private int _level;

    public Armor(String name, int defense, int level){
        this._name = name;
        this._defense = defense * (level + 1) / 2;
        //System.out.println("armor "+_name+" made with "+_defense+" defence points");
        this._level = level;
    }

    /**
     * @return the _name
     */
    public String get_name(){
        return _name;
    }

    /**
     * @return the _level
     */
    public int get_level() {
        return _level;
    }

    /**
     * @return the type
     */
    public String get_type() {
        return _type;
    }

    /**
     * @return the _defense
     */
    public int get_defense() {
        return _defense;
    }

    /**
     * @return the _defense
     */
    public int get_stats() {
        return _defense;
    }
}
