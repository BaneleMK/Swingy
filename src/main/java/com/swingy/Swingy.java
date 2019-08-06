package com.swingy;

import com.swingy.controller.GameEngine;


public final class Swingy {
    private Swingy() {
    }

    /**
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        if (args.length == 1){
            if (args[0].equals("console"))
                GameEngine.makeorloadhero();
            else if (args[0].equals("gui"))
                System.out.println("WIP");
            else {
                System.out.println("invalid view mode");
            }
        } else {
            System.out.println("Run with one argument either 'console' or 'gui' as argument for view method");
        }
    }
}
