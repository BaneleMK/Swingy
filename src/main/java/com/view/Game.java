package com.swingy.view;

import com.swingy.model.*;

public interface Game {

    public void consolelog(String message);

    public void rendermap(GameCharacter[][][] map, int mapsize);

    public void hero_weapon(Hero hero);

    public void hero_armor(Hero hero);

    public void hero_helm(Hero hero);

    public void hero_stats(Hero hero);

    public void villain_stats(Villain villain);
}