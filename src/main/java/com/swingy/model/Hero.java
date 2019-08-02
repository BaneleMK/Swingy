package com.swingy.model;

public class Hero extends GameCharacter {
    private int _experience;
    private Weapon _weapon = null;
    private Armor _armor = null;
    private Helm _helm = null;
    protected int _xp_to_next_lv = (_level*1000+((_level - 1)*(_level - 1))*450);
    private int _lastfighthp = 0;

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
        if (_armor != null)
            return (_defense + _armor.get_stats());
        else
            return _defense;
    }

    /**
     * @return the _lastfighthp
     */
    public int get_lastfighthp() {
        return _lastfighthp;
    }

    /**
     * @param _lastfighthp the _lastfighthp to set
     */
    public void set_lastfighthp(int _lastfighthp) {
        this._lastfighthp = _lastfighthp;
    }


    public void set_hitpoints(int _hitpoints){
        this._hitpoints = _hitpoints;
    }

    public void set_attack(int _attack){
        this._attack = _attack;
    }

    public void set_defense(int _defense){
        this._defense = _defense;
    }

    /**
     * @return the _weapon
     */
    public Weapon get_weapon() {
        return _weapon;
    }

    /**
     * @return the _armor
     */
    public Armor get_armor() {
        return _armor;
    }

    /**
     * @return the _helm
     */
    public Helm get_helm() {
        return _helm;
    }

    /**
     * @return the _experience
     */
    public int get_experience() {
        return _experience;
    }

    /**
     * @return the _xp_to_next_lv
     */
    public int get_xp_to_next_lv() {
        return _xp_to_next_lv;
    }

    public void set_level(int _level){
        this._level = _level;
    }

    /**
     * @param _armor the _Armor to set
     */
    public void set_armor(Armor _Armor) {
        this._armor = _Armor;
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

    /**
     * @param _experience the _experience to set
     */
    public void set_experience(int _experience) {
        this._experience = _experience;
    }

    /**
     * @param _xp_to_next_lv the _xp_to_next_lv to set
     */
    public void set_xp_to_next_lv(int _xp_to_next_lv) {
        this._xp_to_next_lv = _xp_to_next_lv;
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
