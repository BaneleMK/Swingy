package com.swingy.model;

/*
static class Class_Varables{
    Class_Varables getClass_Varable = Class_Varables();

    private Class_Varables(){

    }
}
*/

class Character{

    protected String _name;
    protected String _class;
    protected int _level;
    protected int _attack;
    protected int _defense;
    protected int _hitpoints;

    protected Character(String name, String char_class){
        this._name = name;
        this._class = char_class;
        this._level = 1;
        this._attack = 5;
        this._defense = 0;
        this._hitpoints = 20;
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
}

class Villainmaker{
    static Character makevillain(Character hero){
        
        // do stuff and get a villain a less or a bit stronger then the hero.
        return new Villain("skummy", "slime", hero.get_level());
    }

}

class Villain extends Character{
    
    Villain(String name, String char_class, int hero_level){
        super(name, char_class);
        
    }

}

class Hero extends Character{
    private int _experience;
    private Weapon _weapon;
    private Armor _Armor;
    private Helm _helm;
    protected int _xp_to_next_lv;

    Hero(String name, String char_class){
        super(name, char_class);
        this._hitpoints = 40;
    }


    // i can remove the supers and retain the behavior since its all public

    @Override
    public int get_attack() {
        return (super.get_attack() +  _weapon.get_attack());
    }

    @Override
    public int get_hitpoints() {
//        return (super.get_hitpoints() + _helm.get_hitpoints());
        return (super.get_hitpoints() + _helm.get_stats());
    }

    @Override
    public int get_defense() {
        // return (super.get_defense() + _Armor.get_defense());
        return (super.get_defense() + _Armor.get_stats());
    }

    /**
     * @param _Armor the _Armor to set
     */
    public void set_Armor(Armor _Armor) {
        this._Armor = _Armor;
    }

    /**
     * @param _weapon the _weapon to set
     */
    public void set_weapon(Weapon _weapon) {
        this._weapon = _weapon;
    }

    /**
     * @param _helm the _helm to set
     */
    public void set_helm(Helm _helm) {
        this._helm = _helm;
    }

    public void checklevelup(){
        _xp_to_next_lv = (_level*1000+((_level - 1)*(_level - 1))*450);
        if (_experience >= _xp_to_next_lv){
            _experience -= _xp_to_next_lv;
            _level++;
            _xp_to_next_lv = (_level*1000+((_level - 1)*(_level - 1))*450);
        }
    }
}

