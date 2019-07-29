package com.swingy;

import com.swingy.controller.GameEngine;
import com.swingy.model.Hero;
import com.swingy.model.Map;
import com.swingy.view.GameView;


public final class Swingy {
    private Swingy() {
    }

    /**
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        Hero jeff = new Hero("Jeff", "Viking");
        Map gamemap = new Map();
        GameView gameview = new GameView();
        gamemap.generatenewmap(jeff);
        GameEngine.rungame(gameview, gamemap);
    }
}
