package com.swingy.model;

class Helm implements Artifact {
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

    /**
     * @return the _hitpoints
     */
    public int get_stats() {
        return _hitpoints;
    }

}