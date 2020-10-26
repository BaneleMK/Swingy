package com.swingy.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Armor implements Artifact {
    
    @NotNull
    private String name;

    @NotNull
    private int defense;
    
    private final String type = "Armor";
    
    @NotNull
    private int level;

    public Armor(String name, int defense, int level){
        this.name = name;
        this.defense = defense * (level + 1) / 2;
        this.level = level;
    }

    /**
     * @return the _name
     */
    public String get_name(){
        return name;
    }

    /**
     * @return the _level
     */
    public int get_level() {
        return level;
    }

    /**
     * @return the type
     */
    public String get_type() {
        return type;
    }

    /**
     * @return the _defense
     */
    public int get_defense() {
        return defense;
    }

    /**
     * @return the _defense
     */
    public int get_stats() {
        return defense;
    }
}
