package com.swingy.model;

public class Hero extends GameCharacter {
    private int _experience;
    private Weapon _weapon = null;
    private Armor _Armor = null;
    private Helm _helm = null;
    protected int _xp_to_next_lv;

    public Hero(String name, String char_class){
        super(name, char_class);
        this._hitpoints = 40;
    }


    // i can remove the supers and retain the behavior since its all public

    @Override
    public int get_attack() {
        if (_weapon != null)
            return (_attack +  _weapon.get_attack());
        else
            return _attack;
    }

    @Override
    public int get_hitpoints() {
//        return (super.get_hitpoints() + _helm.get_stats());
        if (_helm != null)
            return (_hitpoints + _helm.get_hitpoints());
        else
            return _hitpoints;
    }

    @Override
    public int get_defense() {
        // return (super.get_defense() + _Armor.get_stats());
        if (_Armor != null)
            return (_defense + _Armor.get_stats());
        else
            return _defense;
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

    public void getxp(int xp){
        _experience += xp;
        checklevelup();
    }

    public void checklevelup(){
        _xp_to_next_lv = (_level*1000+((_level - 1)*(_level - 1))*450);
        if (_experience >= _xp_to_next_lv){
            _experience -= _xp_to_next_lv;
            _level++;
            _xp_to_next_lv = (_level*1000+((_level - 1)*(_level - 1))*450);
            System.out.println("YOU LEVELED UP TO LV "+_level+"!");
        }
    }
}
