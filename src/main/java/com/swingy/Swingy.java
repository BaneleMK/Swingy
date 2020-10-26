package com.swingy;

import com.swingy.controller.GameEngine;
import com.swingy.view.*;


public final class Swingy {
    private Swingy() {
    }

    /**
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        if (args.length == 1){
            GameEngine gameEngine = new GameEngine();
            if (args[0].equals("console")){
                gameEngine.makeorloadhero();
            } else if (args[0].equals("gui")){
                GameGuiView gameGuiView = new GameGuiView();
                gameEngine.GUIcontoller(gameGuiView);
            } else {
                System.out.println("invalid view mode");
            }
        } else {
            System.out.println("Run with one argument either 'console' or 'gui' as argument for view method");
        }
    }
}
