package com.swingy.model;

class Armor implements Artifact {
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

    /**
     * @return the _defense
     */
    public int get_stats() {
        return _defense;
    }
}
