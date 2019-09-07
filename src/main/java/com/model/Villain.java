package com.swingy.model;

public class Villain extends GameCharacter {
    private Artifact _carried_Artifact = null;
    
    public Artifact give_artifact(){
        return _carried_Artifact;
    }

    public Villain(String name, String char_class, int hero_level, int damage, int hp, int defense){
        super(name, char_class);
        this._level = hero_level;
        this._attack += damage * (hero_level / 2);
        this._maxhitpoints += hp * (hero_level / 2);
        this._hitpoints += hp * (hero_level / 2);
        this._defense += defense* (hero_level / 2);
        this._carried_Artifact = LootTable.genArtifact(this._level);
        //System.out.println("New Villain "+this._name+" msade of class "+this._class+" lv "+this._level);        
    }

}
