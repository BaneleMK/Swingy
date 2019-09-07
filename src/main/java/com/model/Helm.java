package com.swingy.model;

import javax.validation.constraints.NotNull;

public class Helm implements Artifact {
    
    @NotNull
    private String _name;

    @NotNull
    private int _hitpoints;

    private String _type = "Helm";

    @NotNull
    private int _level;

    public Helm(String name, int hitpoints, int level){
        this._name = name;
        this._hitpoints = hitpoints * (level + 1) / 2;;
        this._level = level;
    }

    /**
     * @return the _name
     */
    public String get_name(){
        return _name;
    }

    @Override
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