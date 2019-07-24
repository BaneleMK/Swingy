package com.swingy.model;

public class Villain extends GameCharacter {
    
    public Villain(String name, String char_class, int hero_level){
        super(name, char_class);
        this._level = hero_level;
        this._attack *= hero_level / 2; 
        this._maxhitpoints *= hero_level / 2; 
        this._hitpoints *= hero_level / 2; 
        System.out.println("New Villain "+this._name+" made of class "+this._class+" lv "+this._level);        
    }

}
