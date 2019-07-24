package com.swingy.view;

import com.swingy.model.*;

public class GameView{
    public GameView(){
        System.out.println("--- LET THE GAME BEGIN ---");
    }

    public void rendermap(GameCharacter[][][] map, int mapsize){
        for(int y = 0; y<mapsize; y++){
            for(int x = 0; x<mapsize; x++){
                // check if a villain randomly spawns and if the max amount of mobs is not reached for the player level.
                if (map[y][x][0] == null && map[y][x][1] == null){
                    System.out.print(". ");
                } else if (map[y][x][0] != null && map[y][x][1] != null){
                    System.out.print("F ");
                } else if (map[y][x][0] != null) {
                    System.out.print("V ");
                } else if (map[y][x][1] != null) {
                    System.out.print("H ");
                } else {
                    System.out.print("# ");
                }
            }
            System.out.print("\n");
        }   
    }
}